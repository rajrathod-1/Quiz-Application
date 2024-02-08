package comp3350.srsys.tests.business.stubs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;


import comp3350.srsys.objects.Note;

import comp3350.srsys.business.AccessNotes;

public class AccessNotesTestStub {

    private AccessNotes accessNotes;
    private Note note;
    private int expectedNoteId;
    private int expectedSize;

    @Before
    public void setUp() {
        System.out.println("Starting test for AccessNotes");
    }

    @Test
    public void testGetNotesSequential() {

        System.out.println("Starting testGetNotesSequential");

        accessNotes = new AccessNotes();
        note = accessNotes.getNotesSequential();
        expectedNoteId = 1;

        assertNotNull(note);
        assertEquals(expectedNoteId,note.getId());

        note = accessNotes.getNotesSequential();
        expectedNoteId++;

        assertNotNull(note);
        assertEquals(expectedNoteId, note.getId());

        System.out.println("Finished testGetNotesSequential");
    }

    @Test
    public void testGetNotesSequentialToEnd() {

        System.out.println("Starting testGetNotesSequentialToEnd");

        initialize();

        for(int i=0; i<accessNotes.getNotes().size(); i++) {
            note = accessNotes.getNotesSequential();
        }

        //expected to be the first note, after the loop
        assertEquals(expectedNoteId, note.getId());

        System.out.println("Finished testGetNotesSequentialToEnd");
    }

    @Test
    public void testGetReverseSequential() {
        System.out.println("Starting testGetReverseSequential");

        initialize();

        // expected to not change
        note = accessNotes.getPrevNoteSequential();        // note 1

        assertNotNull(note);
        assertEquals(expectedNoteId, note.getId());

        // move up once, then go back to first note

        note = accessNotes.getNotesSequential();          // note 2
        note = accessNotes.getPrevNoteSequential();        // note 1

        assertNotNull(note);
        assertEquals(expectedNoteId, note.getId());

        System.out.println("Finished testGetReverseSequential");
    }

    @Test
    public void testInsertNote() {
        System.out.println("Starting testInsertNote");

        initialize();
        expectedSize = accessNotes.getNotes().size();

        Note newnote = new Note("Inserting Note",
                "This note will be inserted and" +
                        "expect the size to go up by one");

        note = accessNotes.insertNotes(newnote);
        expectedSize++;

        assertNotNull(note);
        assertEquals(expectedSize, accessNotes.getNotes().size());

        System.out.println("Finished testInsertNote");
    }

    @Test
    public void testDeleteNote() {
        System.out.println("Starting testDeleteNote");

        initialize();
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

        initialize();
        expectedSize = accessNotes.getNotes().size();

        note = accessNotes.getNotesSequential();
        accessNotes.deleteNotesById(note.getId());
        expectedSize--;

        assertEquals(expectedSize, accessNotes.getNotes().size());

        System.out.println("Finished testDeleteNoteById");
    }

    private void initialize() {
        accessNotes = new AccessNotes();
        note = accessNotes.getNotesSequential();
        expectedNoteId = 1;
    }
}
