package book.mappings.tasks;

import book.mappings.util.Downloader;

public interface DownloadTask extends MappingsTask {
    default Downloader startDownload() {
        return new Downloader(this);
    }
}
