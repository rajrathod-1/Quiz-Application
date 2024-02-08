package comp3350.srsys.tests.business.stubs;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import comp3350.srsys.objects.Tag;

import comp3350.srsys.business.AccessTags;

public class AccessTagsTestStub {

    private AccessTags accessTags;
    private int expectedSize;

    @Before
    public void setUp() {
        System.out.println("Starting test for Tags");

        accessTags = new AccessTags();
        expectedSize = 6;
    }

    @Test
    public void testGetAllTags() {
        System.out.println("Starting testGetAllTags");

        List<Tag> tagList = accessTags.getAllTags();

        assertEquals(expectedSize, tagList.size());
        assertEquals("Quiz", tagList.get(0).getName());
        assertEquals("Software Engineering", tagList.get(2).getName());

        System.out.println("Finished testGetAllTags");
    }
}
