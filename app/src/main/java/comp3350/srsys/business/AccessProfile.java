package comp3350.srsys.business;

import comp3350.srsys.application.Services;
import comp3350.srsys.objects.Profile;
import comp3350.srsys.persistence.IProfilePersistence;
import comp3350.srsys.persistence.IQuizPersistence;

public class AccessProfile {

    private IProfilePersistence dataAccess;
    private Profile profile;

    public AccessProfile() {
       dataAccess = Services.getProfilePersistence();
       this.profile = dataAccess.getProfile();
    }

    public AccessProfile(final IProfilePersistence profilePersistence) {
        this.dataAccess = profilePersistence;
    }

    public Profile getProfile() {
        return dataAccess.getProfile();
    }

    public void updateProfile(Profile profile) {
        dataAccess.updateProfile(profile);
    }
}
