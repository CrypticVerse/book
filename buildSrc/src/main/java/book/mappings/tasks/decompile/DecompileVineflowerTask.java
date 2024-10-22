package book.mappings.tasks.decompile;

import java.io.IOException;

import book.mappings.decompile.Decompilers;

import org.apache.commons.io.FileUtils;
import org.gradle.api.tasks.TaskAction;

public abstract class DecompileVineflowerTask extends DecompileTask {
    public static final String TASK_NAME = "decompileVineflower";

    public DecompileVineflowerTask() {
        this.getDecompiler().set(Decompilers.VINEFLOWER);
        this.getDecompiler().finalizeValue();
    }

    @Override
    @TaskAction
    public void decompile() throws IOException {
        FileUtils.deleteDirectory(this.getOutput().get().getAsFile());

        super.decompile();
    }
}
