package comp3350.srsys.objects;

public class Profile {

    private final int id;
    private String name;
    private String username;
    private String email;
    private String phone;
    private int numCourses;
    private String semester;

    /*
        Profile Constructor
            - used when creating instance of Profile with data from database
     */
    public Profile(int id, String name, String username, String email, String phone,
                   int numCourses, String semester) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.numCourses = numCourses;
        this.semester = semester;
    }

    public int getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public int getNumCourses() {
        return numCourses;
    }

    public String getSemester() {
        return semester;
    }
}
