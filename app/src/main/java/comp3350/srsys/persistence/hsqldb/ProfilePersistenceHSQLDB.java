package comp3350.srsys.persistence.hsqldb;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import comp3350.srsys.objects.Profile;
import comp3350.srsys.persistence.IProfilePersistence;

public class ProfilePersistenceHSQLDB implements IProfilePersistence {

    private final String url;
    private Profile profile;

    public ProfilePersistenceHSQLDB(String dbPath) {
        this.url = "jdbc:hsqldb:file:" + dbPath + ";shutdown=true";
        loadProfile();
    }

    @Override
    public Profile getProfile() {
        return profile;
    }

    @Override
    public void updateProfile(Profile profile) {
        System.out.println("[LOG] Updating Profile");
        System.out.println(profile.getID() + " " + profile.getName() + " " + profile.getUsername() + " " + profile.getEmail() + " " + profile.getPhone() + " " + profile.getNumCourses() + " " + profile.getSemester());
        try (Connection con = connect()) {
            String query = "UPDATE PROFILE SET name=?, username=?, email=?, phone=?, numCourses=?, semester=? WHERE id=?";
            final PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, profile.getName());
            ps.setString(2, profile.getUsername());
            ps.setString(3, profile.getEmail());
            ps.setString(4, profile.getPhone());
            ps.setInt(5, profile.getNumCourses());
            ps.setString(6, profile.getSemester());
            ps.setInt(7, profile.getID());

            ps.executeUpdate();
            ps.close();

            // update profile
            this.profile = profile;

        } catch (final SQLException e) {
            Log.e("Connect SQL", e.getMessage() + e.getSQLState());
            e.printStackTrace();
        }
    }

    public void clearDatabase() {
        try (Connection con = connect()) {
            Statement statement = con.createStatement();

            // Drop the PROFILE table if it exists
            statement.executeUpdate("DROP TABLE IF EXISTS PROFILE");

            // Recreate the PROFILE table
            statement.executeUpdate("CREATE TABLE PROFILE ("
                    + "id INTEGER PRIMARY KEY,"
                    + "name VARCHAR(255),"
                    + "username VARCHAR(255),"
                    + "email VARCHAR(255),"
                    + "phone VARCHAR(255),"
                    + "numCourses INTEGER,"
                    + "semester VARCHAR(255)"
                    + ")");

            statement.close();
        } catch (final SQLException e) {
            Log.e("Connect SQL", e.getMessage() + e.getSQLState());
            e.printStackTrace();
        }
    }


    private Connection connect() throws SQLException {
        return DriverManager.getConnection(this.url, "SA", "");
    }

    private Profile fromResultSet(final ResultSet rs) throws SQLException {
        int profileID = rs.getInt("id");
        String name = rs.getString("name");
        String username = rs.getString("username");
        String email = rs.getString("email");
        String phone = rs.getString("phone");
        int numCourses = rs.getInt("numCourses");
        String semester = rs.getString("semester");

        return new Profile(profileID, name, username, email, phone, numCourses, semester);
    }

    private void loadProfile() {
        try (Connection con = connect()) {
            final Statement statement = con.createStatement();
            final ResultSet rs = statement.executeQuery("SELECT * FROM PROFILE");

            while(rs.next()) {
                this.profile = fromResultSet(rs);
            }

            statement.close();
            rs.close();

        } catch (final SQLException e) {
            Log.e("Connect SQL", e.getMessage() + e.getSQLState());
            e.printStackTrace();
        }
    }
}
