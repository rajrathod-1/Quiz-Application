package comp3350.srsys.business;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import comp3350.srsys.application.Services;
import comp3350.srsys.business.validators.NoteValidator;
import comp3350.srsys.objects.Note;
import comp3350.srsys.persistence.INotePersistence;

public class AccessNotes {

    private INotePersistence dataAccess;
    private List<Note> notes;
    private Note note;
    private int currentNote;

    public AccessNotes() {
        dataAccess = Services.getNotePersistence();
        this.initialize();
        notes = dataAccess.getNoteList();
    }

    public AccessNotes(final INotePersistence notePersistence) {
        this.dataAccess = notePersistence;
        notes = dataAccess.getNoteList();
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
            note = notes.get(currentNote++);
        }
        else {
            this.initialize();
        }
        return note;
    }

    public Note insertNote() {
        Note newNote = new Note("New Note", "Contents of note");
        Note result = null;
        if(NoteValidator.validate(newNote)) {
            if (newNote != null){
                if (notes == null){
                    notes = dataAccess.getNoteList();
                }
                notes.add(newNote);
            }
            result = dataAccess.insertNote(newNote);
        }
        return result;
    }

    public Note updateNotes(Note currentNote) {
        Note result = null;
        if(NoteValidator.validate(currentNote)) {
            result = dataAccess.updateNote(currentNote);
        }
        result.updateDate();
        return result;
    }

    public void deleteNotes(Note currentNote) {
        if(NoteValidator.validate(currentNote)) {
            dataAccess.deleteNote(currentNote);
            notes.remove(currentNote);
        }
    }

    public void deleteNoteById(int id) {
        if(id > 0) {
            dataAccess.deleteNoteById(id);
        }
    }

    private void initialize() {
        notes = null;
        note = null;
        currentNote = 0;
    }

    public void sortNotesByTitle(){
        notes = dataAccess.getNoteList();
        notes.sort(new Comparator<Note>() {
               @Override
               public int compare(Note note1, Note note2) {
                   return note1.getTitle().compareToIgnoreCase(note2.getTitle());
               }
           }

        );
    }

    public void sortByDate(){
        notes = dataAccess.getNoteList();
        notes.sort(new Comparator<Note>() {
            @Override
            public int compare(Note note1, Note note2) {
                return note2.getDate().compareTo(note1.getDate());
            }
        });
    }

    public void purgeTestNotes() {
        notes = null;
    }
}
