package comp3350.srsys.persistence.hsqldb;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import comp3350.srsys.business.exceptions.EventNotFoundException;
import comp3350.srsys.objects.Event;
import comp3350.srsys.persistence.IEventPersistence;

public class EventPersistenceHSQLDB implements IEventPersistence {

    //private final String url;
    private final String dbPath;
    private List<Event> events;

    public EventPersistenceHSQLDB(String dbPath) {
        this.dbPath = dbPath;
        System.out.println(dbPath);
        events = new ArrayList<>();
        loadAllEvents();
    }

    @Override
    public List<Event> getEventList() {
        return events;
    }

    @Override
    public Event insertEvent(Event event) {
        System.out.println("[LOG] Inserting Event " + event.getTitle());

        try (final Connection con = connect()) {
            String query = "INSERT INTO EVENT (title, date, time) VALUES (?, ?, ?)";
            final PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, event.getTitle());
            ps.setString(2, event.getNewDate());
            ps.setString(3, event.getTime());

            ps.executeUpdate();
            ps.close();
            events.add(event);

            return event;

        } catch (final SQLException e) {
            Log.e("Connect SQL", e.getMessage() + e.getSQLState());
            e.printStackTrace();
        }

        return null;
    }



    @Override
    public void deleteEvent(Event event) {
        System.out.println("[LOG] Deleting Event: " + event.getTitle());
        deleteEventById(event.getId());
    }

    @Override
    public void deleteEventById(int id) {
        System.out.println("[LOG] Deleting Event by ID = " + id);

        try (Connection con = connect()) {
            String query = "DELETE FROM EVENT WHERE id = ?";
            final PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);

            ps.executeUpdate();
            ps.close();

            for (Event event : events) {
                if (event.getId() == id) {
                    events.remove(event);
                    break;
                }
            }


        } catch (final SQLException e) {
            Log.e("Connect SQL", e.getMessage() + e.getSQLState());
            e.printStackTrace();
            throw new EventNotFoundException("Event could not be deleted in the database");
        }
    }


    /*
     *      PRIVATE METHODS
     */

    private Connection connect() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }

    /*
     *  @param: ResultSet from database
     *  @return: a Event instance created using ResultSet's data
     */
    private Event fromResultSet(final ResultSet rs) throws SQLException {
        int eventID = rs.getInt("id");
        String title = rs.getString("title");
        String date = rs.getString("date");
        String time = rs.getString("time");


        return new Event(eventID, title, date, time);
    }

    private void loadAllEvents() {
        try (Connection con = connect()) {
            if (con != null) {
                // Query to retrieve information about tables in the database
                String query = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'PUBLIC'";

                // Create a statement and execute the query
                Statement statement = con.createStatement();
                ResultSet resultSet = statement.executeQuery(query);

                // Iterate through the result set and print the table names
                System.out.println("Database Schema:");
                while (resultSet.next()) {
                    String tableName = resultSet.getString("TABLE_NAME");
                    System.out.println(tableName);
                }

                // Close the result set (statement will be closed automatically when the try-with-resources block exits)
                resultSet.close();

                // Query to retrieve all events
                query = "SELECT * FROM EVENT";

                // Create a new statement and execute the query
                statement = con.createStatement();
                resultSet = statement.executeQuery(query);

                // Process the result set and populate the events list
                while (resultSet.next()) {
                    final Event event = fromResultSet(resultSet);
                    this.events.add(event);
                }
            } else {
                System.err.println("Failed to establish database connection.");
            }
        } catch (SQLException e) {
            // Handle any SQL errors
            e.printStackTrace();
        }
    }
}
