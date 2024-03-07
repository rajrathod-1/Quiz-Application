package comp3350.srsys.persistence.hsqldb;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import comp3350.srsys.business.exceptions.NoteNotFoundException;
import comp3350.srsys.objects.Note;
import comp3350.srsys.persistence.INotePersistence;
import comp3350.srsys.persistence.utils.DBHelper;


// NOTE VALUES (id, title, content, date)

public class NotePersistenceHSQLDB implements INotePersistence {

    private final String url;
    private List<Note> notes;

    // Constructor
    public NotePersistenceHSQLDB(String dbPath) {
        this.url = "jdbc:hsqldb:file:" + dbPath + ";shutdown=true";
        notes = new ArrayList<>();
        loadAllNotes();
    }

    @Override
    public List<Note> getNoteList() {
        return notes;
    }


    @Override
    public Note insertNote(Note note) {
        System.out.println("[LOG] Inserting Note " + note.getTitle());
        String dateString = DBHelper.getSQLDateString(note.getDate());

        try (Connection con = connect()) {
            String query = "INSERT INTO NOTE VALUES(DEFAULT, ?, ?, ?)";
            final PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, note.getTitle());
            ps.setString(2, note.getContent());
            ps.setString(3, dateString);

            ps.executeUpdate();
            ps.close();

            // reset notes
            this.notes = new ArrayList<>();
            loadAllNotes();

            return note;

        } catch (final SQLException e) {
            Log.e("Connect SQL", e.getMessage() + e.getSQLState());
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Note updateNote(Note note) {
        System.out.println("[LOG] Updating Note");

        try (Connection con = connect()) {
            String query = "UPDATE NOTE SET title=?, content=? WHERE id=?";
            final PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, note.getTitle());
            ps.setString(2, note.getContent());
            ps.setInt(3, note.getId());

            ps.executeUpdate();
            ps.close();

            // reset notes
            this.notes = new ArrayList<>();
            loadAllNotes();

            return note;

        } catch (final SQLException e) {
            Log.e("Connect SQL", e.getMessage() + e.getSQLState());
            e.printStackTrace();
            throw new NoteNotFoundException("Note could not be updated in the database");
        }
    }

    @Override
    public void deleteNote(Note note) {
        System.out.println("[LOG] Deleting note: " + note.getTitle());
        deleteNoteById(note.getId());
    }

    @Override
    public void deleteNoteById(int id) {
        System.out.println("[LOG] Deleting note by ID " + id);

        try (Connection con = connect()) {
            String query = "DELETE FROM NOTE WHERE id = ?";
            final PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);
            ps.executeUpdate();
            ps.close();


            for (Note note : notes) {
                if (note.getId() == id) {
                    notes.remove(note);
                    break;
                }
            }

        } catch (final SQLException e) {
            Log.e("Connect SQL", e.getMessage() + e.getSQLState());
            e.printStackTrace();
            throw new NoteNotFoundException("Note could not be deleted in the database");
        }
    }


    /*
     *      PRIVATE METHODS
     */

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(this.url, "SA", "");
    }

    /*
     *  @param: ResultSet from database
     *  @return: a Note instance created using ResultSet's data
     */
    private Note fromResultSet(final ResultSet rs) throws SQLException {
        int noteID = rs.getInt("id");
        String title = rs.getString("title");
        String content = rs.getString("content");
        Date date = rs.getTimestamp("date");

        return new Note(noteID, date, title, content);
    }

    private void loadAllNotes() {
        try (Connection con = connect()) {
            String query = "SELECT * FROM NOTE";
            final Statement statement = con.createStatement();
            final ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {
                final Note note = fromResultSet(rs);
                this.notes.add(note);
            }

            statement.close();
            rs.close();

        } catch (final SQLException e) {
            Log.e("Connect SQL", e.getMessage() + e.getSQLState());
            e.printStackTrace();
        }
    }
}
