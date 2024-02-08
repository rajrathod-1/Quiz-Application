package comp3350.srsys.tests.objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import comp3350.srsys.objects.Tag;

public class TagTest {

    @Before
    public void setup() {
        System.out.println("Starting test for Tag");
    }

    @Test
    public void testCreateTag() {
        System.out.println("\nStarting testCreateTag()");
        Tag tag = new Tag("Software Engineering");

        assertNotNull(tag);
        assertEquals("Software Engineering", tag.getName());

        System.out.println("Finished testCreateTag()");
    }

    @Test
    public void testLongTag(){
        Tag tag = new Tag(5,"Software Engineering");

        assertNotNull(tag);
        assertEquals("Software Engineering", tag.getName());
        assertEquals(5,tag.getTagId());
    }

    @Test
    public void testTagToString(){
        Tag tag = new Tag(5,"Software Engineering");

        assertEquals("Tag: {" +
                "Tag Name = " + tag.getName() +
                "}\n",tag.toString());
    }
}
