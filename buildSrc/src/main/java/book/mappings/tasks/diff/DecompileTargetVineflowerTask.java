package book.mappings.tasks.diff;

import book.mappings.tasks.decompile.DecompileVineflowerTask;

public abstract class DecompileTargetVineflowerTask extends DecompileVineflowerTask implements TargetVersionConsumingTask {
    public static final String TASK_NAME = "decompileTargetVineflower";
}
