package comp3350.srsys.business;

import java.util.Collections;
import java.util.List;

import comp3350.srsys.application.Services;
import comp3350.srsys.business.validators.NoteValidator;
import comp3350.srsys.objects.Note;
import comp3350.srsys.objects.Quiz;
import comp3350.srsys.persistence.INotePersistence;

public class AccessNotes {

    private INotePersistence dataAccess;
    private List<Note> notes;
    private Note note;
    private int currentNote;

    public AccessNotes() {
        dataAccess = Services.getNotePersistence();
        this.initialize();
    }

    public List<Note> getNotes() {
        notes = dataAccess.getNoteList();
        return Collections.unmodifiableList(notes);
    }

    /*
     *  Access the quizzes one at a time in sequential order.
     */
    public Note getNotesSequential() {
        if(notes == null) {
            notes = dataAccess.getNoteList();
            currentNote = 0;
        }
        if(currentNote < notes.size()) {
            note = notes.get(currentNote);
            currentNote++;
        }
        else {
            currentNote = 0;
            note = notes.get(currentNote);
        }
        return note;
    }
    public Note getPrevNoteSequential() {
        if(notes == null) {
            notes = dataAccess.getNoteList();
            currentNote = 0;
        }
        if(currentNote > 0) {
            currentNote--;
            note = notes.get(currentNote);
        }
        return note;
    }


    public Note insertNotes(Note currentNote) {
        Note result = null;
        if(NoteValidator.validate(currentNote)) {
            result = dataAccess.insertNote(currentNote);
        }
        return result;
    }


    public void deleteNotes(Note currentNote) {
        if(NoteValidator.validate(currentNote)) {
            dataAccess.deleteNote(currentNote);
        }
    }

    public void deleteNotesById(int id) {
        if(id > 0) {
            dataAccess.deleteNoteById(id);
        }
    }

    private void initialize() {
        notes = null;
        note = null;
        currentNote = 0;
    }
}
