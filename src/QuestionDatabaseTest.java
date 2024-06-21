import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class QuestionDatabaseTest {
    @Test
    public void testvalidateAnswer(){
        try{
            // Test validateAnswer in QuestionDatabase()
            List<String> options = new ArrayList<>();
            QuestionDatabase question = new QuestionDatabase();

            List<String> answers = new ArrayList<>();
            answers.add("17");
            Question q = new Question(1, "middlesex", "4+13", "written", options, answers, 1);
            Assertions.assertTrue(question.validateAnswer(q, "17"));

            List<String> answers2 = new ArrayList<>();
            answers2.add("21");
            Question q2 = new Question(1, "middlesex", "13+18", "written", options, answers2, 1);
            Assertions.assertFalse(question.validateAnswer(q2, "14"));

            List<String> answers3 = new ArrayList<>();
            answers3.add("16");
            Question q3 = new Question(1, "middlesex", "8x3-4*2", "written", options, answers3, 2);
            Assertions.assertTrue(question.validateAnswer(q3, "16"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetRandomQuestion() throws FileNotFoundException {
        QuestionDatabase question = new QuestionDatabase();

        Question q = question.getRandomQuestion(300, "middlesex");
        Assertions.assertEquals(1, q.getDifficulty());

        Question q2 = question.getRandomQuestion(750, "middlesex");
        Assertions.assertEquals(2, q2.getDifficulty());

        Question q3 = question.getRandomQuestion(2000, "middlesex");
        Assertions.assertEquals(3, q3.getDifficulty());
    }

    @Test
    public void testGetQuestion(){

        try{
            // Test getQuestion() in QuestionDatabase
            List<String> answers4 = new ArrayList<>();
            answers4.add("36");
            QuestionDatabase databaseQuestion = new QuestionDatabase();
            Question questionGet = databaseQuestion.getQuestion("4*9");
            Assertions.assertEquals(answers4, questionGet.getAnswer());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
