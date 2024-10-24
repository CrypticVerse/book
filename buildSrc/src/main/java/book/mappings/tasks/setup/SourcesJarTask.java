package book.mappings.tasks.setup;

import book.mappings.Constants;
import book.mappings.tasks.MappingsTask;
import org.gradle.api.file.ConfigurableFileCollection;
import org.gradle.api.tasks.InputFiles;
import org.gradle.jvm.tasks.Jar;

public abstract class SourcesJarTask extends Jar implements MappingsTask {
    public static final String SOURCES_JAR_TASK_NAME = "sourcesJar";

    @InputFiles
    public abstract ConfigurableFileCollection getSources();

    public SourcesJarTask() {
        this.setGroup(Constants.Groups.SETUP);

        this.getArchiveClassifier().convention("sources");

        this.from(this.getSources());
    }
}
