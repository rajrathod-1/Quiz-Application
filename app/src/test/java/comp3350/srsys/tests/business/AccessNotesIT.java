package comp3350.srsys.tests.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import comp3350.srsys.application.Services;
import comp3350.srsys.business.AccessCourses;
import comp3350.srsys.business.AccessNotes;
import comp3350.srsys.objects.Course;
import comp3350.srsys.objects.Note;
import comp3350.srsys.tests.utils.TestUtils;


public class AccessNotesIT {
    private AccessNotes accessNotes;
    private File tempDB;

    @Before
    public void setUp() throws IOException {
        Services.clean();
        System.out.println("Starting integration test for AccessNotes");
        this.tempDB = TestUtils.copyDB();
        this.accessNotes = new AccessNotes();

        assertNotNull(this.accessNotes);
    }
    @Test
    public void testGetNotes(){
        final List<Note> Notes = accessNotes.getNotes();
        assertEquals(3,Notes.size());

        System.out.println("Finished testGetNotes");
    }


    @Test
    public void testGetNotesSequential() {
        final Note note = accessNotes.getNotesSequential();
        assertNotNull("first sequential course should not be null", note);
        assertTrue("This is the title".equals(note.getTitle()));

        System.out.println("Finished testGetNotesSequential");
    }

    @Test
    public void testInsertNote() {
        accessNotes.insertNote();
        assertEquals(4, accessNotes.getNotes().size());

        System.out.println("Finished testInsertNote");
    }

    @Test
    public void testDeleteNote() {
        accessNotes.deleteNotes(new Note(1, new Date(2024,1,1,12,0,1) , "This is the title", "This is the content"));
        assertEquals(2, accessNotes.getNotes().size());

        System.out.println("Finished testDeleteNote");
    }

    @Test
    public void testDeleteNoteById() {
        accessNotes.deleteNoteById(2);
        accessNotes.getNotesSequential();
        Note checkedNote = accessNotes.getNotesSequential();

        assertEquals(checkedNote.getTitle(),"This is the Third Note");

        System.out.println("Finished testDeleteNoteById");
    }

    @Test
    public void testSortNotesByTitle() {
        accessNotes.sortNotesByTitle();
        Note ourNote = accessNotes.getNotesSequential();
        assertEquals(ourNote.getId(),2);

        System.out.println("Finished testSortNotesByTile");
    }

    @Test
    public void testSortByDate() {
        accessNotes.sortByDate();
        Note ourNote = accessNotes.getNotesSequential();
        assertEquals(ourNote.getTitle(),"This is the Third Note");
    }

    @Test
    public void testUpdateNote() {
        Note firstNote = accessNotes.getNotesSequential();
        firstNote.setTitle("New Title");
        accessNotes.updateNotes(firstNote);

        assertEquals(accessNotes.getNotes().get(0).getTitle(),"New Title");
    }

    @After
    public void tearDown() {
        // reset DB
        this.tempDB.delete();

        Services.clean();
    }
}
