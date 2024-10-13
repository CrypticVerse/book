package book.mappings.tasks.mappings;

import book.mappings.Constants;
import book.mappings.tasks.EnigmaProfileConsumingTask;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.tasks.InputDirectory;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.JavaExec;

public abstract class AbstractEnigmaMappingsTask extends JavaExec implements EnigmaProfileConsumingTask {
    @InputFile
    public abstract RegularFileProperty getJarToMap();

    @InputDirectory
    public abstract DirectoryProperty getMappingsDir();

    public AbstractEnigmaMappingsTask() {
        this.setGroup(Constants.Groups.MAPPINGS_GROUP);
    }

    @Override
    public void exec() {
        this.args(
                "-jar", this.getJarToMap().get().getAsFile().getAbsolutePath(),
                "-mappings", this.getMappingsDir().get().getAsFile().getAbsolutePath(),
                "-profile", this.getEnigmaProfileConfig().get().getAsFile().getAbsolutePath()
        );

        super.exec();
    }
}