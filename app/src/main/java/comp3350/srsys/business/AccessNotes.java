package comp3350.srsys.business;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import comp3350.srsys.application.Services;
import comp3350.srsys.business.validators.NoteValidator;
import comp3350.srsys.objects.Course;
import comp3350.srsys.objects.Note;
import comp3350.srsys.persistence.INotePersistence;

public class AccessNotes {

    private INotePersistence dataAccess;
    private List<Note> notes;
    private Note note;
    private int currentNote;

    public AccessNotes() {
        this.initialize();
        dataAccess = Services.getNotePersistence(null);
    }

    public AccessNotes(Course course) {
        this.initialize();
        dataAccess = Services.getNotePersistence(course);
    }

    public AccessNotes(final INotePersistence notePersistence) {
        this.dataAccess = notePersistence;
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

    public Note insertNote(String topic, int num) {
        Note newNote = new Note("New Note", "", topic, num);
        Note result;
        result = dataAccess.insertNote(newNote);
        return result;
    }

    public Note updateNotes(Note currentNote) {
        Note result;
        currentNote.updateDate();
        result = dataAccess.updateNote(currentNote);
        return result;
    }

    public void deleteNotes(Note currentNote) {
        dataAccess.deleteNote(currentNote);
        notes.remove(currentNote);
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
}
