package book.mappings.tasks.build;

import book.mappings.tasks.setup.IntermediaryDependantTask;

import javax.inject.Inject;

public abstract class IntermediaryMappingsV2JarTask extends MappingsV2JarTask implements IntermediaryDependantTask {

    @Inject
    public IntermediaryMappingsV2JarTask(String unpickVersion) {
        super(unpickVersion);
    }
}
