package comp3350.srsys.persistence;

import comp3350.srsys.objects.Profile;

public interface IProfilePersistence {

    Profile getProfile();

    void updateProfile(final Profile profile);
    void clearDatabase();
}
