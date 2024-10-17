package book.mappings.tasks.build;

import static book.mappings.util.ProviderUtil.exists;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;

import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.TaskAction;

import org.jetbrains.annotations.VisibleForTesting;

import book.mappings.Constants;
import book.mappings.tasks.DefaultMappingsTask;
import book.mappings.util.ProviderUtil;

import net.fabricmc.mappingio.MappingReader;
import net.fabricmc.mappingio.adapter.MappingDstNsReorder;
import net.fabricmc.mappingio.adapter.MappingSourceNsSwitch;
import net.fabricmc.mappingio.format.MappingFormat;
import net.fabricmc.mappingio.format.tiny.Tiny2FileWriter;
import net.fabricmc.mappingio.tree.MemoryMappingTree;

public abstract class RemoveIntermediaryTask extends DefaultMappingsTask {
    public static final String TASK_NAME = "removeIntermediary";

    @Optional
    @InputFile
    public abstract RegularFileProperty getInput();

    @OutputFile
    public abstract RegularFileProperty getOutputMappings();

    public RemoveIntermediaryTask() {
        super(Constants.Groups.BUILD_MAPPINGS);

        this.onlyIf(unused -> exists(this.getInput()));
    }

    @TaskAction
    public void removeIntermediary() throws Exception {
        final Path mappingsTinyInput = ProviderUtil.getPath(this.getInput());
        final Path output = ProviderUtil.getPath(this.getOutputMappings());

        this.getLogger().lifecycle(":removing intermediary");
        removeIntermediary(mappingsTinyInput, output);
    }

    @VisibleForTesting
    public static void removeIntermediary(Path mappingsTinyInput, Path output) throws IOException {
        final MemoryMappingTree tree = new MemoryMappingTree(false);
        MappingReader.read(mappingsTinyInput, MappingFormat.TINY_2_FILE, tree);
        try (Tiny2FileWriter w = new Tiny2FileWriter(Files.newBufferedWriter(output), false)) {
            tree.accept(
                new MappingSourceNsSwitch(
                    new MappingDstNsReorder(w, Collections.singletonList("named")), // Remove official namespace
                    "intermediary"
                )
            );
        }
    }
}
