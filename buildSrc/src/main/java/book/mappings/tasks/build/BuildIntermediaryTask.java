package book.mappings.tasks.build;

import book.mappings.Constants;
import book.mappings.tasks.DefaultMappingsTask;

public abstract class BuildIntermediaryTask extends DefaultMappingsTask {
    public static final String BUILD_INTERMEDIARY_TASK_NAME = "buildIntermediary";

    public BuildIntermediaryTask() {
        super(Constants.Groups.BUILD_MAPPINGS);
    }
}
