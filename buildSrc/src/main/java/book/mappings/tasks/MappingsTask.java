package book.mappings.tasks;

import org.gradle.api.Task;

import book.mappings.util.DownloadImmediate;

public interface MappingsTask extends Task {
    default DownloadImmediate.Builder startDownload() {
        return new DownloadImmediate.Builder(this);
    }

    default void outputsNeverUpToDate() {
        this.getOutputs().upToDateWhen(task -> false);
    }
}
