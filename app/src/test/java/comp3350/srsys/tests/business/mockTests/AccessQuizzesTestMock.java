package comp3350.srsys.tests.business.mockTests;

import static junit.framework.TestCase.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

import comp3350.srsys.business.AccessQuizzes;
import comp3350.srsys.objects.Quiz;
import comp3350.srsys.persistence.IQuizPersistence;

public class AccessQuizzesTestMock {

    @Mock
    private IQuizPersistence mockQuizPersistence;

    private AccessQuizzes accessQuizzes;

    @Before
    public void setUp() {
        mockQuizPersistence = mock(IQuizPersistence.class);
        accessQuizzes = new AccessQuizzes(mockQuizPersistence);
    }

    @Test
    public void testGetQuizzes() {
        List<Quiz> expectedQuizzes = Arrays.asList(new Quiz(), new Quiz());
        when(mockQuizPersistence.getQuizList()).thenReturn(expectedQuizzes);

        List<Quiz> actualQuizzes = accessQuizzes.getQuizzes();

        assertNotNull(actualQuizzes);
        assertEquals(expectedQuizzes.size(), actualQuizzes.size());
        verify(mockQuizPersistence).getQuizList();
    }

    @Test
    public void testInsertQuiz() {
        Quiz newQuiz = new Quiz();
        when(mockQuizPersistence.insertQuiz(any(Quiz.class))).thenReturn(newQuiz);

        Quiz result = accessQuizzes.insertQuiz(newQuiz);

        assertNotNull(result);
        assertEquals(newQuiz, result);
        verify(mockQuizPersistence).insertQuiz(any(Quiz.class));
    }

    @Test
    public void testUpdateQuiz() {
        Quiz existingQuiz = new Quiz();
        when(mockQuizPersistence.updateQuiz(any(Quiz.class))).thenReturn(existingQuiz);

        Quiz updatedQuiz = accessQuizzes.updateQuiz(existingQuiz);
        assertNotNull(updatedQuiz);
        assertEquals(existingQuiz, updatedQuiz);
        verify(mockQuizPersistence, times(1)).updateQuiz(existingQuiz);
    }

    @Test
    public void testDeleteQuizById() {
        int quizIdToDelete = 1;
        doNothing().when(mockQuizPersistence).deleteQuizById(quizIdToDelete);

        accessQuizzes.deleteQuizById(quizIdToDelete);
        verify(mockQuizPersistence, times(1)).deleteQuizById(quizIdToDelete);
    }
}