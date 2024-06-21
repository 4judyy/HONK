import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class QuestionTest {

    @Test
    public void testQuestion(){
        List<String> option = new ArrayList<>();
        List<String> answer = new ArrayList<>();
        answer.add("58");

        Question question = new Question(1, "middlesex", "42+16", "written", option, answer, 1);

        Assertions.assertEquals("42+16", question.getQuestion());
        Assertions.assertEquals(answer, question.getAnswer());
        Assertions.assertEquals(1, question.getDifficulty());
        Assertions.assertEquals("middlesex", question.getMinigame());

    }

}
