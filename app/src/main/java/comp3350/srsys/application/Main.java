package comp3350.srsys.application;

public class Main
{
    private static String dbName = "QuizMonkeyDB";

    public static void setDBPathName(final String name) {
        try {
            Class.forName("org.hsqldb.jdbcDriver").newInstance();
        } catch (InstantiationException | ClassNotFoundException | IllegalAccessException e) {e.printStackTrace();}
        dbName = name;
    }

    public static String getDBPathName() {
        return dbName;
    }
}
