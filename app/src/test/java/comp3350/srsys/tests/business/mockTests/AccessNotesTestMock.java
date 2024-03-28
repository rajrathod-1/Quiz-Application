package comp3350.srsys.tests.business.mockTests;

import static junit.framework.TestCase.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

import comp3350.srsys.business.AccessNotes;
import comp3350.srsys.objects.Note;
import comp3350.srsys.persistence.INotePersistence;

public class AccessNotesTestMock {

    @Mock
    private INotePersistence mockNotePersistence;

    private AccessNotes accessNotes;

    @Before
    public void setUp() {
        mockNotePersistence = mock(INotePersistence.class);
        accessNotes = new AccessNotes(mockNotePersistence);
    }

    @Test
    public void testGetNotes() {
        List<Note> expectedNotes = Arrays.asList(new Note("Note1", "2023-01-01", "Topic1", 1),
                new Note("Note2", "2023-01-02", "Topic2", 2));
        when(mockNotePersistence.getNoteList()).thenReturn(expectedNotes);

        List<Note> actualNotes = accessNotes.getNotes();

        assertNotNull(actualNotes);
        assertEquals(expectedNotes.size(), actualNotes.size());
        verify(mockNotePersistence).getNoteList();
    }

    @Test
    public void testInsertNote() {
        Note newNote = new Note("New Note", "2023-03-28", "New Topic", 3);
        when(mockNotePersistence.insertNote(any(Note.class))).thenReturn(newNote);

        Note result = accessNotes.insertNote("New Topic", 3);

        assertNotNull(result);
        assertEquals(newNote, result);
        verify(mockNotePersistence).insertNote(any(Note.class));
    }
}
