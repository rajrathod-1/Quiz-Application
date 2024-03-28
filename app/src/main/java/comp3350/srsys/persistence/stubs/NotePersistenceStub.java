package comp3350.srsys.persistence.stubs;

import android.nfc.Tag;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import comp3350.srsys.objects.Course;
import comp3350.srsys.objects.Note;

import comp3350.srsys.persistence.INotePersistence;

public class NotePersistenceStub implements INotePersistence {

    private List<Note> noteList;

    public NotePersistenceStub() {

        this.noteList = new ArrayList<>();

        Date date = new Date();

        // Note 1
        Note note1 = new Note(
                1,
                date,
                "Agile Methodology",
                "A. Individuals and Interactions. \n" +
                        "B. Working Software. \n" +
                        "C. Customer Collaboration. \n" +
                        "Responding to change. \n",
                "COMP",
                3350
        );

        // Note 2
        Note note2 = new Note(
                2,
                date,
                "Addition",
                "Addition is one of the four basic operations of arithmetic, " +
                        "the other three being subtraction, multiplication and division. " +
                        "The addition of two whole numbers results in the total amount or " +
                        "sum of those values combined.",
                "COMP",
                3350
        );

        // Note 3
        Note note3 = new Note(
                3,
                date,
                "What are Germs?",
                "The term \"germs\" refers to the microscopic bacteria, viruses, " +
                        "fungi, and protozoa that can cause disease",
                "COMP",
                3350
        );

        noteList.add(note1);
        noteList.add(note2);
        noteList.add(note3);
    }

    @Override
    public List<Note> getNoteList() {

        return noteList;
    }

    @Override
    public Note insertNote(Note note) {
        Note result = null;
        if(!noteList.contains(note)) {
            noteList.add(note);
            result = note;
        }
        return result;
    }

    @Override
    public Note updateNote(Note newNote) {
        Note result = null;
        int index = noteList.indexOf(newNote);

        if(index >= 0) {
            noteList.set(index, newNote);
            result = newNote;
        }

        return result;
    }

    @Override
    public void deleteNote(Note note) {

        int index = noteList.indexOf(note);
        if(index >= 0) {
            noteList.remove(index);
        }
    }

    @Override
    public void deleteNoteById(int id) {

        int index = -1;
        for(int i=0; i<noteList.size(); i++) {
            if(noteList.get(i).getId() == id) {
                index = i;
            }
        }

        if(index >= 0) {
            noteList.remove(index);
        }
    }

    //the below function is used to delete a specific note from a course.
    //This function is utilized for hsqldb database connections to change the presently presented note
    //after a note has been deleted. As such it is used in database connection and cannot be tested with a unit test.
    @Override
    public void deleteNotesByCourse(Course course) {

        for (Note note : noteList) {
            if (note.getCourseNum() == course.getCourseNum() &&
                    Objects.equals(note.getCourseTopic(), course.getTopic())) {
                noteList.remove(note);
            }
        }
    }
}
