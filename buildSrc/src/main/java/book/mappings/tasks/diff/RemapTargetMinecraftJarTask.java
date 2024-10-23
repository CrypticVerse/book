package book.mappings.tasks.diff;

import book.mappings.Constants;
import book.mappings.tasks.jarmapping.MapJarTask;

public abstract class RemapTargetMinecraftJarTask extends MapJarTask implements UnpickVersionsMatchConsumingTask {
    public static final String TASK_NAME = "remapTargetMinecraftJar";

    public RemapTargetMinecraftJarTask() {
        super(Constants.Groups.DIFF, Constants.PER_VERSION_MAPPINGS_NAME, "named");
        this.getAdditionalMappings().putAll(JAVAX_TO_JETBRAINS);
    }
}
