package book.mappings.tasks.diff;

import book.mappings.Constants;
import book.mappings.tasks.ExtractZippedFilesTask;

public abstract class ExtractTargetMappingJarTask extends ExtractZippedFilesTask implements TargetVersionConsumingTask {
    public static final String EXTRACT_TARGET_MAPPINGS_JAR_TASK_NAME = "extractTargetMappingsJar";

    public ExtractTargetMappingJarTask() {
        super(Constants.Groups.DIFF);

    }
}
