package comp3350.srsys.tests.business.stubs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import java.util.Objects;

import comp3350.srsys.application.Services;
import comp3350.srsys.objects.Course;
import comp3350.srsys.objects.Note;

import comp3350.srsys.business.AccessNotes;
import comp3350.srsys.persistence.stubs.CoursePersistenceStub;
import comp3350.srsys.persistence.stubs.NotePersistenceStub;

public class AccessNotesTestStub {

    private AccessNotes accessNotes;
    private Note note;
    private int expectedNoteId;
    private int expectedSize;

    @Before
    public void setUp() {
        accessNotes = new AccessNotes(new NotePersistenceStub());
        note = accessNotes.getNotesSequential();
        expectedNoteId = 1;
        System.out.println("Starting test for AccessNotes");
    }

    @Test
    public void testGetNotesSequential() {
        System.out.println("Starting testGetNotesSequential");
        note = accessNotes.getNotesSequential();

        assertNotNull(note);
        assertEquals(2,note.getId());

        note = accessNotes.getNotesSequential();

        assertNotNull(note);
        assertEquals(3, note.getId());

        System.out.println("Finished testGetNotesSequential");
    }

    @Test
    public void testGetNotesSequentialToEnd() {

        System.out.println("Starting testGetNotesSequentialToEnd");

        for(int i=0; i<accessNotes.getNotes().size(); i++) {
            note = accessNotes.getNotesSequential();
        }

        //expected to be the first note, after the loop
        assertNull(note);

        System.out.println("Finished testGetNotesSequentialToEnd");
    }

    @Test
    public void testInsertNote() {
        System.out.println("Starting testInsertNote");

        expectedSize = accessNotes.getNotes().size();

        note = accessNotes.insertNote("COMP",3350);
        expectedSize++;

        assertEquals(expectedSize, accessNotes.getNotes().size());

        System.out.println("Finished testInsertNote");
    }

    @Test
    public void testDeleteNote() {
        System.out.println("Starting testDeleteNote");

        expectedSize = accessNotes.getNotes().size();

        note = accessNotes.getNotesSequential();
        accessNotes.deleteNotes(note);
        expectedSize--;

        assertEquals(expectedSize, accessNotes.getNotes().size());

        System.out.println("Finished testDeleteNote");
    }

    @Test
    public void testDeleteNoteById() {
        System.out.println("Starting testDeleteNoteById");

        expectedSize = accessNotes.getNotes().size();

        note = accessNotes.getNotesSequential();
        accessNotes.deleteNoteById(note.getId());
        expectedSize--;

        assertEquals(expectedSize, accessNotes.getNotes().size());

        System.out.println("Finished testDeleteNoteById");
    }

    @Test
    public void testUpdateNotes() {
        System.out.println("Starting testUpdateNotes");

        Note newnote = accessNotes.getNotes().get(1);

        newnote.setTitle("AAA:Update Note");
        accessNotes.updateNotes(newnote);

        accessNotes.sortNotesByTitle();

        assertEquals("AAA:Update Note", accessNotes.getNotes().get(0).getTitle());

        System.out.println("Finished testUpdateNotes");
    }

    @After
    public void tearDown() {
        Services.clean();
    }
}
