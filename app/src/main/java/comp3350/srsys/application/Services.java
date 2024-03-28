package comp3350.srsys.application;

import comp3350.srsys.objects.Course;
import comp3350.srsys.persistence.ICoursePersistence;
import comp3350.srsys.persistence.IEventPersistence;
import comp3350.srsys.persistence.INotePersistence;
import comp3350.srsys.persistence.IProfilePersistence;
import comp3350.srsys.persistence.IQuizPersistence;
import comp3350.srsys.persistence.hsqldb.CoursePersistenceHSQLDB;
import comp3350.srsys.persistence.hsqldb.EventPersistenceHSQLDB;
import comp3350.srsys.persistence.hsqldb.NotePersistenceHSQLDB;
import comp3350.srsys.persistence.hsqldb.ProfilePersistenceHSQLDB;
import comp3350.srsys.persistence.hsqldb.QuizPersistenceHSQLDB;

public class Services {
    private static ICoursePersistence coursePersistence = null;
    private static IQuizPersistence quizPersistence = null;
    private static INotePersistence notePersistence = null;
    private static IEventPersistence eventPersistence = null;
    private static IProfilePersistence profilePersistence = null;


    public static synchronized ICoursePersistence getCoursePersistence()
    {
        if (coursePersistence == null)
        {
            coursePersistence = new CoursePersistenceHSQLDB(Main.getDBPathName());
        }

        return coursePersistence;
    }

    public static synchronized IQuizPersistence getQuizPersistence(Course course)
    {
        if (quizPersistence == null)
        {
            if (course != null) {
                quizPersistence = new QuizPersistenceHSQLDB(Main.getDBPathName(), course);
            }
            else {
                quizPersistence = new QuizPersistenceHSQLDB(Main.getDBPathName());
            }
        }

        return quizPersistence;
    }

    public static synchronized INotePersistence getNotePersistence(Course course)
    {
        if (notePersistence == null)
        {
            if (course != null) {
                notePersistence = new NotePersistenceHSQLDB(Main.getDBPathName(), course);
            }
            else {
                notePersistence = new NotePersistenceHSQLDB(Main.getDBPathName());
            }
        }

        return notePersistence;
    }

    public static synchronized IEventPersistence getEventPersistence()
    {
        if (eventPersistence == null)
        {
            eventPersistence = new EventPersistenceHSQLDB(Main.getDBPathName());
        }

        return eventPersistence;
    }

    public static synchronized IProfilePersistence getProfilePersistence()
    {
        if(profilePersistence == null)
        {
            profilePersistence = new ProfilePersistenceHSQLDB(Main.getDBPathName());
        }

        return profilePersistence;
    }

    //Method used for testing. Will clean persistence objects before and after a test has completed
    public static synchronized void clean() {
        coursePersistence = null;
        quizPersistence = null;
        notePersistence = null;
        eventPersistence = null;
        profilePersistence = null;
    }

}
