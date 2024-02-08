package comp3350.srsys.persistence;

import java.util.List;

import comp3350.srsys.objects.Note;

public interface INotePersistence {

    List<Note> getNoteList();

    Note insertNote(final Note note);


    void deleteNote(final Note note);

    void deleteNoteById(final int id);

}
