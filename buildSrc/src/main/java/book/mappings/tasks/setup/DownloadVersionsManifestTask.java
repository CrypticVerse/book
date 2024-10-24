package book.mappings.tasks.setup;

import book.mappings.tasks.SimpleDownloadTask;
import book.mappings.util.SerializableVersionEntry;
import org.gradle.api.file.RegularFile;
import org.gradle.api.provider.Provider;

import book.mappings.Constants;

public abstract class DownloadVersionsManifestTask extends SimpleDownloadTask {
    public static final String DOWNLOAD_VERSIONS_MANIFEST_TASK_NAME = "downloadVersionsManifest";

    public Provider<SerializableVersionEntry> provideVersionEntry() {
        return this.getDest()
                .map(RegularFile::getAsFile)
                .map(SerializableVersionEntry::of);
    }

    public DownloadVersionsManifestTask() {
        super(Constants.Groups.SETUP);

        this.getPreDownloadLifecycle().convention(":downloading minecraft versions manifest");

        this.getUrl().convention("https://piston-meta.mojang.com/mc/game/version_manifest_v2.json");
    }
}
