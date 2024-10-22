package book.mappings.tasks.jarmapping;

import book.mappings.Constants;

public abstract class MapNamedJarTask extends MapJarTask {
    public static final String TASK_NAME = "mapNamedJar";

    public MapNamedJarTask() {
        super(Constants.Groups.MAP_JAR, Constants.PER_VERSION_MAPPINGS_NAME, "named");
        this.getAdditionalMappings().putAll(JAVAX_TO_JETBRAINS);
    }
}
