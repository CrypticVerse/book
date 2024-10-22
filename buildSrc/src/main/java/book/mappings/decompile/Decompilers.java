package book.mappings.decompile;

import book.mappings.decompile.vineflower.VineflowerDecompiler;
import org.gradle.api.logging.Logger;

public enum Decompilers {
    VINEFLOWER(VineflowerDecompiler::new);

    private final Factory factory;

    Decompilers(Factory factory) {
        this.factory = factory;
    }

    public AbstractDecompiler create(Logger logger) {
        return this.factory.create(logger);
    }

    public interface Factory {
        AbstractDecompiler create(Logger logger);
    }
}
