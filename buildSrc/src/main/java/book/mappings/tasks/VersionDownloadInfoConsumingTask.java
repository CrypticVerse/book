package book.mappings.tasks;

import book.mappings.util.VersionDownloadInfo;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;

public interface VersionDownloadInfoConsumingTask extends MappingsTask {
    @Input
    Property<VersionDownloadInfo> getVersionDownloadInfo();
}
