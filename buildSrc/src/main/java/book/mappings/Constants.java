package book.mappings;

public class Constants {
    public static final String MINECRAFT_VERSION = "1.21.2-rc2";

    public static final String MAPPINGS_NAME = "book-mappings";

    public static final String PER_VERSION_MAPPINGS_NAME = "hashed";

    public static final String PER_VERSION_INVERTED_MAPPINGS_NAME = PER_VERSION_MAPPINGS_NAME
            + "-inverted";

    public static final String INTERMEDIARY_MAPPINGS_NAME = "intermediary";

    // TODO why does this use a system variable? CI/CD?
    //  Could it go in gradle.properties instead?
    public static final String MAPPINGS_VERSION = MINECRAFT_VERSION + "+build."
            + System.getenv().getOrDefault("BUILD_NUMBER", "local");

    public static final String UNPICK_NAME = "unpick";

    public static final class Groups {
        public static final String SETUP = "jar setup";
        public static final String MAPPINGS = MAPPINGS_NAME;
        public static final String BUILD_MAPPINGS = "build mappings";
        public static final String MAP_JAR = "jar mapping";
        public static final String DECOMPILE = "decompile";
        public static final String UNPICK = UNPICK_NAME;
        public static final String LINT = "lint";
        public static final String UNPICK_GEN = UNPICK_NAME + "gen";
        public static final String DIFF = "diff";
        public static final String JAVADOC_GENERATION = "javadoc generation";
    }
}
