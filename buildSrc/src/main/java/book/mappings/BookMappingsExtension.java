package book.mappings;

import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.file.RegularFileProperty;

import org.quiltmc.enigma.api.EnigmaProfile;

public abstract class BookMappingsExtension {
    public static final String EXTENSION_NAME = "bookMappings";

    public abstract DirectoryProperty getMappingsDir();

    /**
     * Don't parse this to create an {@link EnigmaProfile}, use {@link #enigmaProfile} instead.
     * <p>This is exposed so it can be passed to external processes.
     */
    public abstract RegularFileProperty getEnigmaProfileConfig();

    public abstract RegularFileProperty getUnpickMeta();
}
