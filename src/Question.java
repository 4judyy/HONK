import java.util.List;

/**
 * Creates a question object to be used in the middlesex minigame
 * <p>
 *     Creates a question object with various private variables
 * </p>
 * @author Tyler Charles Inwood
 */
public class Question {
    /** id for question */
    private int id;
    /** the minigame the question belongs to */
    private String miniGame;
    /** question text */
    private String question;
    /** type of question */
    private String type;
    /** options for the question */
    private List<String> options;
    /** list of correct answers to the question (different syntax) */
    private List<String> answer;
    /** difficulty of the question */
    private int difficulty;

    /**
     * Question Constructor. creates a question object and sets its variables
     * @param id question id
     * @param miniGame that the question belongs to
     * @param question actual question text
     * @param type of question
     * @param options for the question
     * @param answer of the question text
     * @param difficulty of the question
     */
    public Question(int id, String miniGame, String question, String type, List<String> options, List<String> answer, int difficulty) {
        this.id = id;
        this.miniGame = miniGame;
        this.question = question;
        this.type = type;
        this.options = options;
        this.answer = answer;
        this.difficulty = difficulty;
    }

    /**
     * getter method for question
     * @return the question text.
     */
    public String getQuestion() { return this.question;}

    /**
     * getter method for the answer
     * @return the list of answers
     */
    public List<String> getAnswer() { return this.answer;}

    /**
     * getter method for difficulty
     * @return the difficulty.
     */
    public int getDifficulty() { return this.difficulty;}

    /**
     * getter method for mini-game the question is used in
     * @return the mini-game
     */
    public String getMinigame() { return this.miniGame;}
}