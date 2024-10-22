package book.mappings.tasks.build;

import java.io.IOException;
import java.util.Map;

import book.mappings.tasks.decompile.DecompileVineflowerTask;
import org.gradle.api.tasks.TaskAction;

public abstract class GenFakeSourceTask extends DecompileVineflowerTask {
    public static final String TASK_NAME = "genFakeSource";

    public GenFakeSourceTask() {
        this.getDecompilerOptions().putAll(Map.of(
                "rsy", "1",
                "dgs", "1",
                "pll", "99999"
        ));
    }

    @Override
    @TaskAction
    public void decompile() throws IOException {
        super.decompile();

        this.getLogger().lifecycle(":Fake source generated");
    }
}
