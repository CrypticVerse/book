package book.mappings.tasks.setup;

import book.mappings.tasks.ExtractSingleZippedFileTask;

import book.mappings.Constants;

public abstract class ExtractTinyMappingsTask extends ExtractSingleZippedFileTask {
    private static final String TINY_MAPPINGS_PATTERN = "**/*mappings.tiny";
    public static final String EXTRACT_TINY_PER_VERSION_MAPPINGS_TASK_NAME = "extractTinyPerVersionMappings";

    public ExtractTinyMappingsTask() {
        super(Constants.Groups.SETUP, filterable -> filterable.include(TINY_MAPPINGS_PATTERN));
    }
}
