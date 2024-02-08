package comp3350.srsys.application;

import comp3350.srsys.persistence.ICoursePersistence;
import comp3350.srsys.persistence.IEventPersistence;
import comp3350.srsys.persistence.INotePersistence;
import comp3350.srsys.persistence.IQuizPersistence;
import comp3350.srsys.persistence.ITagPersistence;
import comp3350.srsys.persistence.stubs.CoursePersistenceStub;
import comp3350.srsys.persistence.stubs.EventPersistenceStub;
import comp3350.srsys.persistence.stubs.NotePersistenceStub;
import comp3350.srsys.persistence.stubs.QuizPersistenceStub;
import comp3350.srsys.persistence.stubs.TagPersistenceStub;

public class Services {
    private static ICoursePersistence coursePersistence = null;
    private static IQuizPersistence quizPersistence = null;
    private static INotePersistence notePersistence = null;
    private static IEventPersistence eventPersistence = null;
    private static ITagPersistence tagPersistence = null;


    public static synchronized ICoursePersistence getCoursePersistence()
    {
        coursePersistence = new CoursePersistenceStub();
        //coursePersistence = new CoursePersistenceHSQLDB(Main.getDBPathName());

        return coursePersistence;
    }

    public static synchronized IQuizPersistence getQuizPersistence()
    {
        quizPersistence = new QuizPersistenceStub();

        return quizPersistence;
    }

    public static synchronized INotePersistence getNotePersistence()
    {

        notePersistence = new NotePersistenceStub();


        return notePersistence;
    }

    public static synchronized IEventPersistence getEventPersistence()
    {

        eventPersistence = new EventPersistenceStub();
        //eventPersistence = new EventPersistenceHSQLDB(Main.getDBPathName());


        return eventPersistence;
    }

    public static synchronized ITagPersistence getTagPersistence()
    {
        if (tagPersistence == null)
        {
            tagPersistence = new TagPersistenceStub();
        }

        return tagPersistence;
    }

}
