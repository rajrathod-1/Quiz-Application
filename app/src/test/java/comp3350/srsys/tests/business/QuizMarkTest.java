package comp3350.srsys.tests.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static comp3350.srsys.business.AccessCalendarUtils.daysInMonthArray;
import static comp3350.srsys.business.AccessCalendarUtils.monthYearFromDate;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import comp3350.srsys.application.Services;
import comp3350.srsys.business.AccessCourses;
import comp3350.srsys.business.QuizMark;
import comp3350.srsys.objects.Quiz;
import comp3350.srsys.tests.utils.TestUtils;


public class QuizMarkTest {

    @Test
    public void testQuizMarkCreation(){
        QuizMark quizMark = new QuizMark(50);
        assertNotNull(quizMark);
    }

    @Test
    public void testSetQuestionSeenAt(){
        QuizMark quizMark = new QuizMark(50);
        quizMark.setQuestionCorrectAt(5);
        assertTrue(quizMark.gotQuestionCorrectAt(5));
    }


    @Test
    public void testGotQuestionCorrectAt(){
        QuizMark quizMark = new QuizMark(50);
        quizMark.setQuestionSeenAt(10);
        assertTrue(quizMark.questionAlreadySeen(10));
    }

    @Test
    public void testCorrectChoice() {
        QuizMark quizMark = new QuizMark(50);

        quizMark.checkChoice(1,2,1);
        assertTrue(quizMark.questionAlreadySeen(1));
    }
}
