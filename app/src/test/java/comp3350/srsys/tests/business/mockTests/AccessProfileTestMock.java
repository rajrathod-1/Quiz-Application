package comp3350.srsys.tests.business.mockTests;

import comp3350.srsys.business.AccessProfile;
import comp3350.srsys.objects.Profile;
import comp3350.srsys.persistence.IProfilePersistence;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class AccessProfileTestMock {

    @Mock
    private IProfilePersistence mockProfilePersistence;

    private AccessProfile accessProfile;

    @Before
    public void setUp() {
        mockProfilePersistence = mock(IProfilePersistence.class);
        accessProfile = new AccessProfile(mockProfilePersistence);
    }

    @Test
    public void testGetProfile() {
        Profile expectedProfile = new Profile(1, "John Doe","jdoe", "johndoe@example.com","999-999-9999",2,"Winter 2023");
        when(mockProfilePersistence.getProfile()).thenReturn(expectedProfile);

        Profile actualProfile = accessProfile.getProfile();

        assertNotNull(actualProfile);
        assertEquals(expectedProfile.getID(), actualProfile.getID());
        assertEquals(expectedProfile.getName(), actualProfile.getName());
        assertEquals(expectedProfile.getEmail(), actualProfile.getEmail());
        verify(mockProfilePersistence).getProfile();
    }

    @Test
    public void testUpdateProfile() {
        Profile updatedProfile = new Profile(1, "John Doe","jdoe", "johndoe@example.com","999-999-9999",2,"Winter 2023");

        doNothing().when(mockProfilePersistence).updateProfile(any(Profile.class));

        accessProfile.updateProfile(updatedProfile);

        verify(mockProfilePersistence).updateProfile(updatedProfile);
    }
}