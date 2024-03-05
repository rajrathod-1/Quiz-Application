package comp3350.srsys.application;

import comp3350.srsys.persistence.ICoursePersistence;
import comp3350.srsys.persistence.IEventPersistence;
import comp3350.srsys.persistence.INotePersistence;
import comp3350.srsys.persistence.IQuizPersistence;
import comp3350.srsys.persistence.hsqldb.CoursePersistenceHSQLDB;
import comp3350.srsys.persistence.hsqldb.EventPersistenceHSQLDB;
import comp3350.srsys.persistence.hsqldb.NotePersistenceHSQLDB;
import comp3350.srsys.persistence.hsqldb.QuizPersistenceHSQLDB;

public class Services {
    private static ICoursePersistence coursePersistence = null;
    private static IQuizPersistence quizPersistence = null;
    private static INotePersistence notePersistence = null;
    private static IEventPersistence eventPersistence = null;


    public static synchronized ICoursePersistence getCoursePersistence()
    {
        if (coursePersistence == null)
        {
            //coursePersistence = new CoursePersistenceStub();
            coursePersistence = new CoursePersistenceHSQLDB(Main.getDBPathName());
        }

        return coursePersistence;
    }

    public static synchronized IQuizPersistence getQuizPersistence()
    {
        if (quizPersistence == null)
        {
            //quizPersistence = new QuizPersistenceStub();
            quizPersistence = new QuizPersistenceHSQLDB(Main.getDBPathName());
        }

        return quizPersistence;
    }

    public static synchronized INotePersistence getNotePersistence()
    {
        if (notePersistence == null)
        {
            //notePersistence = new NotePersistenceStub();
            notePersistence = new NotePersistenceHSQLDB(Main.getDBPathName());
        }

        return notePersistence;
    }

    public static synchronized IEventPersistence getEventPersistence()
    {
        if (eventPersistence == null)
        {
            //eventPersistence = new EventPersistenceStub();
            eventPersistence = new EventPersistenceHSQLDB(Main.getDBPathName());
        }

        return eventPersistence;
    }

    public static synchronized void clean() {
        coursePersistence = null;
        quizPersistence = null;
        notePersistence = null;
        eventPersistence = null;
    }

}
