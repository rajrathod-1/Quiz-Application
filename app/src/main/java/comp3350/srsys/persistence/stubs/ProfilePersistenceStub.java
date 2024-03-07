package comp3350.srsys.persistence.stubs;

import java.util.ArrayList;
import java.util.List;

import comp3350.srsys.objects.Course;
import comp3350.srsys.objects.Profile;
import comp3350.srsys.persistence.IProfilePersistence;

public class ProfilePersistenceStub implements IProfilePersistence {
    private Profile profile;

    public ProfilePersistenceStub() {
        this.profile = new Profile(1,"Monkey","Monkey","monkey@monkey.com", "8808-07-8",3, "Moneky");
    }

    @Override
    public Profile getProfile() {
        return profile;
    }

    @Override
    public void updateProfile(Profile profile) {
        this.profile = profile;
    }

    @Override
    public void clearDatabase() {
        profile = null;
    }
}
