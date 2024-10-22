package book.mappings.tasks.setup;

import book.mappings.tasks.MappingsTask;

/**
 * A task that depends on {@value book.mappings.Constants#INTERMEDIARY_MAPPINGS_NAME} mappings
 * for the current Minecraft version.
 * <p>
 * If {@link book.mappings.BookMappingsPlugin QuiltMappingsPlugin} is applied,
 * {@code IntermediaryDependantTask}s will {@linkplain org.gradle.api.Task#onlyIf(org.gradle.api.specs.Spec) only run} if
 * {@value book.mappings.BookMappingsPlugin#INTERMEDIARY_MAPPINGS_CONFIGURATION_NAME}
 * {@link org.gradle.api.artifacts.Configuration Configuration} successfully
 * {@linkplain org.gradle.api.artifacts.Configuration#resolve() resolves}.
 */
public interface IntermediaryDependantTask extends MappingsTask { }
