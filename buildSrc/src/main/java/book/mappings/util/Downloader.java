package book.mappings.util;

import de.undercouch.gradle.tasks.download.DownloadAction;
import org.gradle.api.Project;
import org.gradle.api.Task;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class Downloader {
    private final Project project;
    private final Task task;
    private URL src;
    private File dest;
    private boolean overwrite;

    public Downloader(Task task) {
        this.task = task;
        this.project = task.getProject();
    }

    public Downloader src(String url) {
        try {
            this.src = new URI(url).toURL();
        } catch (MalformedURLException | URISyntaxException e) {
            throw new IllegalArgumentException("Invalid URL", e);
        }

        return this;
    }

    public Downloader dest(File file) {
        this.dest = file;
        return this;
    }

    public Downloader overwrite(boolean overwrite) {
        this.overwrite = overwrite;
        return this;
    }

    public void download() throws IOException {
        final DownloadAction downloadAction = new DownloadAction(this.project, this.task);
        downloadAction.src(this.src);
        downloadAction.dest(this.dest);
        downloadAction.overwrite(this.overwrite);

        downloadAction.execute();
    }
}
