package book.mappings.tasks.unpick.gen;

import book.mappings.tasks.MappingsTask;

/**
 * A task that outputs unpick files.
 * <p>
 * {@link book.mappings.BookMappingsPlugin BookMappingsPlugin} adds the
 * {@link org.gradle.api.Task#getOutputs() outputs} of all
 * {@code UnpickGenTask}s to {@value book.mappings.tasks.unpick.CombineUnpickDefinitionsTask#TASK_NAME}'s
 * {@link book.mappings.tasks.unpick.CombineUnpickDefinitionsTask#getUnpickDefinitions() unpickDefinitions},
 * so implementing tasks should <i>only</i> output unpick files.
 */
public interface UnpickGenTask extends MappingsTask { }
