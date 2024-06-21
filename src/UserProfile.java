/**
 * Represents a user profile containing user information and scores.
 * @author judy zhou
 */
public class UserProfile {

    // Set up user's information
    private String username;
    private String password;
    private String permission;
    private int totalScore;
    private int middlesexScore;
    private int naturalSciencesScore;
    private int talbotScore;

    /**
     * Constructs a new UserProfile object.
     * @param username The username of the user
     * @param password The password of the user
     * @param permission The permission level of the user (Student, Teacher, Debugger)
     * @param totalScore The total score of the user
     * @param middlesexScore The cumulative score of the user in the Middlesex game
     * @param naturalSciencesScore The cumulative score of the user in the Natural Sciences game
     * @param talbotScore The cumulative score of the user in the Talbot game
     */
    public UserProfile(String username, String password, String permission, int totalScore,
                       int middlesexScore, int naturalSciencesScore, int talbotScore) {
        this.username = username;
        this.password = password;
        this.permission = permission;
        this.totalScore = totalScore;
        this.middlesexScore = middlesexScore;
        this.naturalSciencesScore = naturalSciencesScore;
        this.talbotScore = talbotScore;
    }

    // Getters and Setters

    /**
     * Gets the username of the user
     * @return The username of the user
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the password of the user
     * @return The password of the user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Gets the permission level of the user
     * @return The permission level of the user
     */
    public String getPermission() {
        return permission;
    }

    /**
     * Gets the total score of the user
     * @return The total score of the user
     */
    public int getTotalScore() {
        return totalScore;
    }

    /**
     * Sets the total score of the user
     * @param totalScore The total score to be set
     */
    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    /**
     * Gets the score of the user in the Middlesex game
     * @return The score of the user in the Middlesex game
     */
    public int getMiddlesexScore() {
        return middlesexScore;
    }

    /**
     * Sets the score of the user in the Middlesex game
     * @param middlesexScore The score to be set in the Middlesex game
     */
    public void setMiddlesexScore(int middlesexScore) {
        this.middlesexScore = middlesexScore;
    }

    /**
     * Gets the score of the user in the Natural Sciences game
     * @return The score of the user in the Natural Sciences game
     */
    public int getNaturalSciencesScore() {
        return naturalSciencesScore;
    }

    /**
     * Sets the score of the user in the Natural Sciences game
     * @param naturalSciencesScore The score to be set in the Natural Sciences game
     */
    public void setNaturalSciencesScore(int naturalSciencesScore) {
        this.naturalSciencesScore = naturalSciencesScore;
    }

    /**
     * Gets the score of the user in the Talbot game
     * @return The score of the user in the Talbot game
     */
    public int getTalbotScore() {
        return talbotScore;
    }

    /**
     * Sets the score of the user in the Talbot category.
     * @param talbotScore The score to be set in the Talbot category
     */
    public void setTalbotScore(int talbotScore) {
        this.talbotScore = talbotScore;
    }
}