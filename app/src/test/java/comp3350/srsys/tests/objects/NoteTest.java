package comp3350.srsys.tests.objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import comp3350.srsys.objects.Note;
import comp3350.srsys.objects.Tag;

public class NoteTest {

    private Note note;
    private int expectedTagNumbers = 0;

    @Before
    public void setup() {
        System.out.println("Starting test for Note");
    }

    @Test
    public void testCreateNoteWithoutTag() {
        System.out.println("\nStarting testCreateNoteWithoutTag");

        initialize();
        testHelper();

        assertEquals(expectedTagNumbers, note.getCardTagList().size());

        System.out.println("Finished testCreateNoteWithoutTag");
    }

    @Test
    public void testCreateNoteWithOneTag() {
        System.out.println("\nStarting testCreateNoteWithOneTag");

        initialize();
        testHelper();

        note.addCardTag(new Tag("Software Engineering"));
        expectedTagNumbers++;

        assertEquals(expectedTagNumbers, note.getCardTagList().size());

        System.out.println("Finished testCreateNoteWithOneTag");
    }

    @Test
    public void testCreateNoteWithMultipleTags() {
        System.out.println("\nStarting testCreateNoteWithMultipleTags");

        initialize();
        testHelper();

        // Add two Tags
        note.addCardTag(new Tag("Math"));
        note.addCardTag(new Tag("Science"));
        expectedTagNumbers += 2;

        assertEquals(expectedTagNumbers, note.getCardTagList().size());
        assertTrue(note.containsTag(new Tag("Math")));
        assertTrue(note.containsTag(new Tag("Science")));

        System.out.println("Finished testCreateNoteWithMultipleTags");
    }

    @Test
    public void testCreateNoteWithSameTags() {
        System.out.println("\nStarting testCreateNoteWithSameTags");

        initialize();
        testHelper();

        note.addCardTag(new Tag("Math"));
        note.addCardTag(new Tag("Science"));
        expectedTagNumbers += 2;

        // Add two existing Tags, expect size to be unchanged
        note.addCardTag(new Tag("Math"));
        note.addCardTag(new Tag("Science"));

        assertEquals(expectedTagNumbers, note.getCardTagList().size());
        assertTrue(note.containsTag(new Tag("Math")));
        assertTrue(note.containsTag(new Tag("Science")));

        System.out.println("Finished testCreateNoteWithSameTags");
    }

    @Test
    public void testRemoveCardTag() {
        System.out.println("\nStarting testRemoveCardTag");

        initialize();
        testHelper();

        note.addCardTag(new Tag("Science"));
        expectedTagNumbers++;

        assertEquals(expectedTagNumbers, note.getCardTagList().size());

        note.removeCardTag(new Tag("Science"));
        expectedTagNumbers--;

        assertEquals(expectedTagNumbers, note.getCardTagList().size());

        System.out.println("Finished testRemoveCardTag");
    }

    @Test
    public void testNullNote() {
        Note newNote = new Note();

        assertNull(newNote.getContent());
    }

    @Test
    public void testSetTitle() {
        initialize();
        note.setTitle("ECE 4530 Notes");

        assertEquals("ECE 4530 Notes",note.getTitle());
    }

    @Test
    public void testSetContent(){
        initialize();
        note.setContent("Parallel Processing");

        assertEquals("Parallel Processing",note.getContent());
    }

    @Test
    public void testNoteToString(){
        initialize();

        assertEquals("Note: { " +
                "ID = " + note.getId() + "Date = " +
                new Date() +
                "Tag List = []"  +
                "\nTitle = " + note.getTitle() +
                "\nContent = " + note.getContent() +
                "\n}",note.toString());
    }

    @Test
    public void testNoteEqual(){
        Date date = new Date();
        Note note1 = new Note(1,date,"ECE", "4530");
        Note note2 = new Note(1,date,"ECE", "4530");
        assertTrue(note1.equal(note2));
    }

    private void testHelper() {
        assertEquals("This is the title", note.getTitle());
        assertEquals("This is the content", note.getContent());
    }

    private void initialize() {
        note = new Note("This is the title",
                "This is the content");

        expectedTagNumbers = 0;
    }


}
