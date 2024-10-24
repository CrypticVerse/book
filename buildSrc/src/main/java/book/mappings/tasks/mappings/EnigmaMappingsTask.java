package book.mappings.tasks.mappings;

public abstract class EnigmaMappingsTask extends AbstractEnigmaMappingsTask {
    public static final String MAPPINGS_UNPICKED_TASK_NAME = "mappingsUnpicked";
    public static final String MAPPINGS_TASK_NAME = "mappings";

    public EnigmaMappingsTask() {
        // this configuration can stay here because it's what make this an EnigmaMappingsTask
        this.getMainClass().convention(org.quiltmc.enigma.gui.Main.class.getName());
        this.getMainClass().finalizeValue();
    }
}
