package book.mappings.tasks.diff;

import book.mappings.Constants;
import book.mappings.util.DownloadUtil;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.TaskAction;

import book.mappings.tasks.DefaultMappingsTask;

public abstract class DownloadTargetMappingJarTask extends DefaultMappingsTask implements TargetVersionConsumingTask {
    public static final String DOWNLOAD_TARGET_MAPPINGS_JAR_TASK_NAME = "downloadTargetMappingsJar";

    @OutputFile
    public abstract RegularFileProperty getTargetJar();

    @OutputFile
    public abstract RegularFileProperty getTargetUnpickConstantsFile();

    public DownloadTargetMappingJarTask() {
        super(Constants.Groups.DIFF);
    }

    @TaskAction
    public void download() {
        // TODO eliminate project access in task action
        final String targetVersion = this.getTargetVersion().get();

        final String urlPrefix = "https://maven.bookkeepersmc.com/com/bookkeepersmc/book-mappings/" +
                targetVersion + "/book-mappings-" + targetVersion;

        DownloadUtil.download(
                urlPrefix + "-v2.jar", this.getTargetJar().get().getAsFile(),
                false, this.getLogger()
        );

        DownloadUtil.download(
                urlPrefix + "-constants.jar", this.getTargetUnpickConstantsFile().get().getAsFile(),
                false, this.getLogger()
        );
    }
}
