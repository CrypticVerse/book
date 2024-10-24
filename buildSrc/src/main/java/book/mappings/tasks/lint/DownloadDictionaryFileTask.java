package book.mappings.tasks.lint;

import book.mappings.tasks.SimpleDownloadTask;

import book.mappings.Constants;

public abstract class DownloadDictionaryFileTask extends SimpleDownloadTask {
    public static final String DOWNLOAD_DICTIONARY_FILE_TASK_NAME = "downloadDictionaryFile";

    public DownloadDictionaryFileTask() {
        super(Constants.Groups.LINT);
    }
}
