/**
 * Class for the current user session.
 * It holds the user profile information and provides methods to access and modify it.
 * @author Di Zhou
 */

public class UserSession {
    private static UserSession instance;
    private UserProfile userProfile;

    private UserSession() {}

    // Private constructor to prevent direct instantiation
    /**
     * Retrieves instance of the UserSession or creates a new one if it doesn't exist.
     * @return Instance of UserSession
     */
    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    /**
     * Sets the user profile for the current session.
     * @param userProfile The UserProfile object representing the user's profile
     */
    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    /**
     * Retrieves the user profile of the current session.
     * @return The UserProfile object representing the user's profile
     */
    public UserProfile getUserProfile() {
        return userProfile;
    }
}
