import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;


public class DatabaseTest {

    @Test
    public void testSearchUser() {
        try {
            // Create a Database instance
            Database database = new Database();

            // Test existing user
            String[] existingUser = database.searchUser("goose", "goose", "Teacher");
            assertNotNull(existingUser);
            assertEquals("goose", existingUser[0]);
            assertEquals("Teacher", existingUser[1]);

            // Test new user -- NOTE: must reset username every time, or the next test will fail because the user will be added to the json file
            String[] newUser = database.searchUser("gooersdoo", "newPassword", "newRole");
            assertNull(newUser);
        } catch (FileNotFoundException e) {
            fail("FileNotFoundException occurred while testing searchUser");
        }
    }

    @Test
    public void testTopUsers() {
        try {
            // Create a Database instance
            Database database = new Database();

            // Test top users
            List<UserProfile> topUsers = database.topUsers("TotalScore");
            assertNotNull(topUsers);
            assertTrue(topUsers.size() <= 10); // Ensure maximum size is 10
            assertEquals("JimmyJohn", topUsers.get(0).getUsername());
        } catch (FileNotFoundException e) {
            fail("FileNotFoundException occurred while testing topUsers");
        }
    }

    @Test
    public void testSaveDataToJson() {
        try {
            // Create a Database instance
            Database database = new Database();

            // Save data to JSON
            database.saveDataToJson();
            // No assertion made as it's difficult to verify without reading the saved file
        } catch (FileNotFoundException e) {
            fail("FileNotFoundException occurred while testing saveDataToJson");
        }
    }

    @Test
    public void testGetUserProfileMap() {
        try {
            // Create a Database instance
            Database database = new Database();

            // Get user profile map
            Map<String, UserProfile> userProfileMap = database.getUserProfileMap();
            assertNotNull(userProfileMap);
        } catch (FileNotFoundException e) {
            fail("FileNotFoundException occurred while testing getUserProfileMap");
        }
    }
}
