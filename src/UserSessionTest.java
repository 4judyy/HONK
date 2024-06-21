import org.junit.Test;
import static org.junit.Assert.*;
/**
 * Unit tests for the {@link UserSession} class.
 * @author Marissa Wang
 */
public class UserSessionTest {

    /**
     * Test case to verify the behavior of the {@link UserSession#getInstance()} method.
     * Ensures that getInstance method returns a non-null instance and that subsequent calls
     * return the same instance.
     */
    @Test
    public void testGetInstance() {
        // Test if getInstance returns a non-null instance
        UserSession instance1 = UserSession.getInstance();
        assertNotNull(instance1);

        // Test if getInstance returns the same instance on subsequent calls
        UserSession instance2 = UserSession.getInstance();
        assertSame(instance1, instance2);
    }

    /**
     * Test case to verify the behavior of the {@link UserSession#setUserProfile(UserProfile)} and
     * {@link UserSession#getUserProfile()} methods.
     * Creates a UserProfile instance, sets it using setUserProfile,
     * retrieves it using getUserProfile, then checks if the retrieved profile
     * matches the original profile.
     */
    @Test
    public void testSetAndGetUserProfile() {
        // Create a UserProfile instance for testing
        UserProfile userProfile = new UserProfile("username", "email@example.com", "password", 0,0,0,0);

        // Set UserProfile and test getUserProfile
        UserSession.getInstance().setUserProfile(userProfile);
        UserProfile retrievedProfile = UserSession.getInstance().getUserProfile();
        assertNotNull(retrievedProfile);
        assertEquals(userProfile, retrievedProfile);
    }
}
