package book.mappings.tasks.setup;

import book.mappings.Constants;
import book.mappings.tasks.MappingsTask;
import org.gradle.jvm.tasks.Jar;

public abstract class JavadocJarTask extends Jar implements MappingsTask {
    public static final String JAVADOC_JAR_TASK_NAME = "javadocJar";

    public JavadocJarTask() {
        this.setGroup(Constants.Groups.JAVADOC_GENERATION);

        this.getArchiveVersion().convention(Constants.MAPPINGS_VERSION);

        this.getArchiveClassifier().convention("javadoc");
    }
}
