package book.mappings.tasks.setup;

import book.mappings.Constants;
import book.mappings.tasks.MappingsTask;
import org.gradle.api.file.ConfigurableFileCollection;
import org.gradle.api.tasks.InputFiles;
import org.gradle.jvm.tasks.Jar;

public abstract class ConstantsJarTask extends Jar implements MappingsTask {
    public static final String CONSTANTS_JAR_TASK_NAME = "constantsJar";

    @InputFiles
    public abstract ConfigurableFileCollection getConstants();

    public ConstantsJarTask() {
        this.setGroup(Constants.Groups.SETUP);

        this.getArchiveClassifier().convention("constants");

        this.from(this.getConstants());
    }
}
