package book.mappings.tasks.build;

import static book.mappings.util.ProviderUtil.exists;

import javax.inject.Inject;

public abstract class IntermediaryMappingsV2JarTask extends MappingsV2JarTask {

    @Inject
    public IntermediaryMappingsV2JarTask(String unpickVersion) {
        super(unpickVersion);

        this.onlyIf(unused -> exists(this.getMappings()));
    }
}
