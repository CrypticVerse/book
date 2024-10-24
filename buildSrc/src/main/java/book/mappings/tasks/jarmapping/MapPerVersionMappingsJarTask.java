package book.mappings.tasks.jarmapping;

import book.mappings.Constants;

public abstract class MapPerVersionMappingsJarTask extends MapJarTask {
    public static final String MAP_PER_VERSION_MAPPINGS_JAR_TASK_NAME = "mapPerVersionMappingsJar";

    public MapPerVersionMappingsJarTask() {
        super(Constants.Groups.MAP_JAR, "official", Constants.PER_VERSION_MAPPINGS_NAME);
    }
}
