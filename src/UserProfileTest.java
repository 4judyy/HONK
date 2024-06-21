import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This class contains unit tests for the {@link UserProfile} class.
 * @author Matthew Yu
 */

public class UserProfileTest {
    UserProfile user = new UserProfile("bingo", "goose", "Student", 400, 250, 100, 50);
    /**
     * Tests all constructor and getter methods of the UserProfile class.
     * Checks if the constructor initializes the UserProfile object
     * and the getters return the expected values.
     */
    @Test
    public void testGetUserProfile(){
        Assertions.assertEquals("bingo", user.getUsername());
        Assertions.assertEquals("goose", user.getPassword());
        Assertions.assertEquals("Student", user.getPermission());
        Assertions.assertEquals(400, user.getTotalScore());
        Assertions.assertEquals(250, user.getMiddlesexScore());
        Assertions.assertEquals(100, user.getNaturalSciencesScore());
        Assertions.assertEquals(50, user.getTalbotScore());
    }

    @Test
    public void testSetUserProfile(){
        user.setTotalScore(680);
        user.setMiddlesexScore(400);
        user.setNaturalSciencesScore(120);
        user.setTalbotScore(160);

        Assertions.assertEquals(680, user.getTotalScore());
        Assertions.assertEquals(400, user.getMiddlesexScore());
        Assertions.assertEquals(120, user.getNaturalSciencesScore());
        Assertions.assertEquals(160, user.getTalbotScore());
    }

}
