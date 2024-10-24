package book.mappings.tasks;

import book.mappings.util.EnigmaProfileService;
import org.gradle.api.file.ConfigurableFileCollection;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.Internal;

import org.quiltmc.enigma.api.EnigmaProfile;

public interface EnigmaProfileConsumingTask extends MappingsTask {
    @Internal("@ServiceReference is @Incubating")
    Property<EnigmaProfileService> getEnigmaProfileService();

    /**
     * Don't parse this to create an {@link EnigmaProfile}, use {@link #getEnigmaProfileService() enigmaProfile} instead.
     * <p>
     * This is exposed so it can be passed to external processes.
     */
    @InputFile
    RegularFileProperty getEnigmaProfileConfig();

    /**
     * Holds any {@code simple_type_field_names} configuration files obtained from the
     * {@link #getEnigmaProfileService() EnigmaProfile}.
     * <p>
     * {@link EnigmaProfileConsumingTask}s may not access these files directly, but they affect enigma's behavior,
     * so they must be considered for up-to-date checks.
     */
    @InputFiles
    ConfigurableFileCollection getSimpleTypeFieldNamesFiles();
}
