package comp3350.srsys.tests.objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;


import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import comp3350.srsys.objects.Note;

public class NoteTest {

    private Note note;

    @Before
    public void setup() {
        System.out.println("Starting test for Note");
    }

    @Test
    public void testCreateNote() {
        System.out.println("\nStarting testCreateNoteWithoutTag");

        initialize();
        testHelper();

        assertEquals("This is the title", note.getTitle());
        assertEquals("This is the content", note.getContent());
        assertEquals("COMP", note.getCourseTopic());
        assertEquals(3350, note.getCourseNum());

        System.out.println("Finished testCreateNoteWithoutTag");
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
    public void testNoteEqual(){
        Date date = new Date();
        Note note1 = new Note(1,date,"TITLE", "CONTENT", "COMP", 3350);
        Note note2 = new Note(1,date,"TITLE", "CONTENT", "COMP", 3350);
        assertTrue(note1.equal(note2));
    }

    private void testHelper() {
        assertEquals("This is the title", note.getTitle());
        assertEquals("This is the content", note.getContent());
    }

    private void initialize() {
        note = new Note("This is the title",
                "This is the content",
                "COMP",
                3350);
    }

    @Test
    public void constructorTest() {
        String title = "Test Note";
        String content = "This is a test note content.";
        Note note = new Note(title, content);

        assertEquals("Title should match", title, note.getTitle());
        assertEquals("Content should match", content, note.getContent());
    }

    @Test
    public void toStringTest() {
        String title = "Test Note";
        String content = "This is a test note content.";
        Note note = new Note(title, content);

        String expectedString = "Note: { " +
                "ID = " + note.getId() +
                "\nTitle = " + title +
                "\nContent = " + content +
                "\n}";
        assertEquals("toString should match expected format", expectedString, note.toString());
    }


}
