import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.*;

/**
 * This class creates a database of the question json
 * Helps us navigate and process questions for mini-games
 * @author Marissa Wang
 */
public class QuestionDatabase {
    /** Map of questions */
    private Map<String, Question> questionMap;
    /** file path of question json file */
    private String jsonFilePath = "json_files/question.json";

    /**
     * Question database constructor.
     * Creates a database for our questions
     * @throws FileNotFoundException when json file is not found
     */
    public QuestionDatabase() throws FileNotFoundException {

        // work with GSON's Library
        Gson gson = new Gson();
        Type type = new TypeToken<List<Question>>() {}.getType();

        // Create file reader
        try (FileReader reader = new FileReader(jsonFilePath)) {
            // make it a list of user profiles from json file
            List<Question> questionList = gson.fromJson(reader, type);

            // create the Map
            questionMap = new HashMap<>();

            // populate the Map
            for (Question question : questionList) {
                questionMap.put(question.getQuestion(), question);
            }

        } catch (Exception e) {
            // Handle any exceptions
            e.printStackTrace();
        }
    }

    /**
     * get random question method.
     * returns random question, appropriate for users current mini-game and current score
     * @param score of user
     * @param miniGame currently playing
     * @return random question from question json file
     */
    public Question getRandomQuestion(int score, String miniGame) {
        // Get all questions from the questionMap
        List<Question> allQuestions = new ArrayList<>(questionMap.values());

        int difficulty = 0;

        // Checks user score to determine difficulty of questions
        if (score >= 1000) {difficulty = 3;}
        else if (score >= 500) {difficulty = 2;}
        else {difficulty = 1;}

        // Filter questions by the specified difficulty and mini game
        List<Question> filteredQuestions = new ArrayList<>();
        for (Question question : allQuestions) {
            if (question.getDifficulty() == difficulty && question.getMinigame().equals(miniGame)) {
                filteredQuestions.add(question);
            }
        }

        // If no questions of the specified difficulty are found, return null
        if (filteredQuestions.isEmpty()) {
            return null;
        }

        // Generate a random index for the filtered questions
        Random random = new Random();
        int randomIndex = random.nextInt(filteredQuestions.size());

        // Return the question at the random index from the filtered questions
        return filteredQuestions.get(randomIndex);
    }


    /**
     * validates answer entered by user,
     * for a specific question
     * @param question given to user
     * @param attempt answer by user
     * @return boolean, true -> user's attempt was correct, false -> users attempt was incorrect
     */
    public boolean validateAnswer(Question question, String attempt) {
        if (question == null) {
            return false;
        }
        List<String> answerList = question.getAnswer();
        for (String element : answerList) {
            if (element.equalsIgnoreCase(attempt)) {
                return true;
            }
        }
        return false;
    }

    /**
     * returns question object given questions String text
     * @param questionText String text of question
     * @return question object corresponding to the text
     */
    public Question getQuestion(String questionText) {
        for (Question question : questionMap.values()) {
            if (question.getQuestion().equalsIgnoreCase(questionText)) {
                return question;
            }
        }
        return null; // Return null if no matching question is found

    }
}