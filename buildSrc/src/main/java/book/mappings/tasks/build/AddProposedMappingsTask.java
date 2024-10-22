package book.mappings.tasks.build;

import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.TaskAction;

import org.jetbrains.annotations.VisibleForTesting;

import book.mappings.Constants;
import book.mappings.tasks.DefaultMappingsTask;
import book.mappings.tasks.EnigmaProfileConsumingTask;
import book.mappings.util.ProviderUtil;

import net.fabricmc.mappingio.MappingWriter;
import net.fabricmc.mappingio.adapter.MappingDstNsReorder;
import net.fabricmc.mappingio.format.MappingFormat;
import net.fabricmc.mappingio.format.tiny.Tiny2FileReader;
import net.fabricmc.mappingio.tree.MemoryMappingTree;
import org.quiltmc.enigma.api.Enigma;
import org.quiltmc.enigma.api.EnigmaProfile;
import org.quiltmc.enigma.api.EnigmaProject;
import org.quiltmc.enigma.api.translation.mapping.EntryMapping;
import org.quiltmc.enigma.api.translation.mapping.MappingDelta;
import org.quiltmc.enigma.api.translation.mapping.serde.MappingSaveParameters;
import org.quiltmc.enigma.api.translation.mapping.tree.DeltaTrackingTree;
import org.quiltmc.enigma.api.translation.mapping.tree.EntryTree;
import org.quiltmc.enigma.command.Command;
import org.quiltmc.enigma.command.CommandsUtil;
import org.quiltmc.enigma.command.FillClassMappingsCommand;
import org.quiltmc.enigma.util.EntryTreePrinter;
import org.quiltmc.enigma.util.Utils;

public abstract class AddProposedMappingsTask extends DefaultMappingsTask implements EnigmaProfileConsumingTask {
    public static final String NAMED_NAMESPACE = "named";

    @InputFile
    public abstract RegularFileProperty getInputJar();

    @InputFile
    public abstract RegularFileProperty getInputMappings();

    @OutputFile
    public abstract RegularFileProperty getOutputMappings();

    /**
     * TODO is this an accurate explanation? Also since this isn't created with the current inputs idk how to test it.
     * This file will only be created if the first namespace of
     * {@link #getInputMappings() inputMappings} is {@value NAMED_NAMESPACE}.
     */
    @OutputFile
    public abstract RegularFileProperty getPreprocessedMappings();

    /**
     * TODO is this an accurate explanation? Also since this isn't created with the current inputs idk how to test it.
     * This file will only be created if the first namespace of
     * {@link #getInputMappings() inputMappings} is {@value NAMED_NAMESPACE}.
     */
    @OutputFile
    public abstract RegularFileProperty getProcessedMappings();

    public AddProposedMappingsTask() {
        super(Constants.Groups.BUILD_MAPPINGS);
    }

    @TaskAction
    public void addProposedMappings() throws Exception {
        this.getLogger().lifecycle(":seeking auto-mappable entries");

        // addProposedMappings(input, output, this.fileConstants.tempDir.toPath(), jar, this.getEnigmaProfile().get());
        addProposedMappings(
                ProviderUtil.getPath(this.getInputMappings()),
                ProviderUtil.getPath(this.getOutputMappings()),
                ProviderUtil.getPath(this.getPreprocessedMappings()),
                ProviderUtil.getPath(this.getProcessedMappings()),
                ProviderUtil.getPath(this.getInputJar()),
                this.getEnigmaProfile().get()
        );
    }

    @VisibleForTesting
    public static void addProposedMappings(
            Path input, Path output, Path preprocessedMappings, Path processedMappings, Path jar, EnigmaProfile profile
    ) throws Exception {

        final List<String> namespaces;
        try (Reader reader = Files.newBufferedReader(input, StandardCharsets.UTF_8)) {
            namespaces = Tiny2FileReader.getNamespaces(reader);
        }

        if (!namespaces.contains(NAMED_NAMESPACE)) {
            throw new IllegalArgumentException("Input mappings must contain the named namespace");
        }

        Files.createDirectories(preprocessedMappings.getParent());
        Files.createDirectories(processedMappings.getParent());

        final boolean extraProcessing = preprocessFile(input, preprocessedMappings);
        final Path commandInput = extraProcessing ? preprocessedMappings : input;
        final Path commandOutput = extraProcessing ? processedMappings : output;

        runCommands(jar,
                commandInput, commandOutput,
                profile,
                namespaces.getFirst(), NAMED_NAMESPACE
        );

        if (extraProcessing) {
            final MemoryMappingTree outputTree = postProcessTree(input, processedMappings);
            try (MappingWriter writer = MappingWriter.create(output, MappingFormat.TINY_2_FILE)) {
                outputTree.accept(writer);
            }
        }
    }

    private static void runCommands(
            Path jar, Path input, Path output, EnigmaProfile profile, String fromNamespace, String toNamespace
    ) throws Exception {
        final Enigma enigma = Command.createEnigma(profile, null);

        final EnigmaProject project = Command.openProject(jar, input, enigma);

        final boolean debug = System.getProperty("book.addProposedMappings.debug", "false")
                .toLowerCase(Locale.ROOT)
                .equals("true");

        // Proposed names are automatically added when opening a project
        final EntryTree<EntryMapping> withProposals = project.getRemapper().getMappings();

        // TODO: Disable fillAll after fixing the tiny v2 writer to avoid adding unnecessary class names
        final EntryTree<EntryMapping> result = FillClassMappingsCommand
                .exec(project.getJarIndex(), withProposals, true, debug);

        Utils.delete(output);
        final MappingSaveParameters profileParameters = enigma.getProfile().getMappingSaveParameters();
        final MappingSaveParameters saveParameters = new MappingSaveParameters(
                profileParameters.fileNameFormat(), true,
                fromNamespace, toNamespace
        );

        CommandsUtil.getReadWriteService(enigma, output).write(result, output, saveParameters);

        if (debug) {
            final Path deltaFile = output.getParent().resolve(output.getFileName().toString() + "-fill-delta.txt");
            final MappingDelta<EntryMapping> fillDelta = ((DeltaTrackingTree<EntryMapping>) result).takeDelta();

            try (BufferedWriter writer = Files.newBufferedWriter(deltaFile)) {
                final List<String> content = fillDelta.getChanges().getAllEntries()
                        .map(Objects::toString)
                        .toList();

                for (final String s : content) {
                    writer.write(s);
                    writer.newLine();
                }
            }

            final Path debugFile = output.getParent().resolve(output.getFileName().toString() + "-tree.txt");
            try (BufferedWriter writer = Files.newBufferedWriter(debugFile)) {
                EntryTreePrinter.print(new PrintWriter(writer), project.getRemapper().getProposedMappings());
            }
        }
    }

    // Reorder dst namespaces to `<src-namespace> named [others...]`
    // Enigma doesn't support multiple dst namespaces, and just uses the first one
    private static boolean preprocessFile(Path inputMappings, Path output) throws Exception {
        final MemoryMappingTree inputTree = new MemoryMappingTree();
        try (Reader reader = Files.newBufferedReader(inputMappings, StandardCharsets.UTF_8)) {
            Tiny2FileReader.read(reader, inputTree);
        }

        // Reorder destination namespaces to put the named namespace first
        final List<String> dstNamespaces = new ArrayList<>(inputTree.getDstNamespaces());
        if (!dstNamespaces.getFirst().equals(NAMED_NAMESPACE)) {
            final MemoryMappingTree outputTree = new MemoryMappingTree();
            final int i = dstNamespaces.indexOf(NAMED_NAMESPACE);
            dstNamespaces.set(i, dstNamespaces.getFirst());
            dstNamespaces.set(0, NAMED_NAMESPACE);
            inputTree.accept(new MappingDstNsReorder(outputTree, dstNamespaces));

            try (MappingWriter mappingWriter = MappingWriter.create(output, MappingFormat.TINY_2_FILE)) {
                outputTree.accept(mappingWriter);
            }

            return true;
        }

        return false;
    }

    // Merge input mappings with the proposed mappings to restore the lost namespaces
    private static MemoryMappingTree postProcessTree(Path inputMappings, Path processedMappings) throws Exception {
        final MemoryMappingTree inputTree = new MemoryMappingTree();
        try (Reader reader = Files.newBufferedReader(inputMappings, StandardCharsets.UTF_8)) {
            Tiny2FileReader.read(reader, inputTree);
        }

        final MemoryMappingTree processedTree = new MemoryMappingTree();
        try (Reader reader = Files.newBufferedReader(processedMappings, StandardCharsets.UTF_8)) {
            Tiny2FileReader.read(reader, processedTree);
        }

        // Merge trees
        final MemoryMappingTree output = new MemoryMappingTree();
        inputTree.accept(output);
        // Merge processed tree after original to keep the original namespaces order
        processedTree.accept(output);

        return output;
    }
}