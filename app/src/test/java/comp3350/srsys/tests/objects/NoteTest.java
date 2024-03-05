package comp3350.srsys.tests.objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import android.nfc.Tag;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import comp3350.srsys.objects.Note;

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
