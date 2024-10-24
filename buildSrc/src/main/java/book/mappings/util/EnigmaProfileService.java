package book.mappings.util;

import org.gradle.api.GradleException;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.services.BuildService;
import org.gradle.api.services.BuildServiceParameters;
import org.quiltmc.enigma.api.EnigmaProfile;

import java.io.IOException;
import java.nio.file.Path;

public abstract class EnigmaProfileService implements BuildService<EnigmaProfileService.Params> {
    public static final String ENIGMA_PROFILE_SERVICE_NAME = "enigmaProfile";

    private final EnigmaProfile profile;

    public EnigmaProfileService() {
        final Path profilePath = this.getParameters().getProfileConfig().get().getAsFile().toPath();
        try {
            this.profile = EnigmaProfile.read(profilePath);
        } catch (IOException e) {
            throw new GradleException("Failed to read enigma profile", e);
        }
    }

    public EnigmaProfile getProfile() {
        return profile;
    }

    public interface Params extends BuildServiceParameters {
        RegularFileProperty getProfileConfig();
    }
}
