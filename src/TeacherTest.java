import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileNotFoundException;
import java.util.Map;

public class TeacherTest {

    @Test
    public void testSortAlphabetically() {
        try {
            // Create a mock Display object for testing
            Display display = new Display();

            // Create a sample database with unsorted student users
            Database originalDatabase = new Database();
            Map<String, UserProfile> userProfileMap = originalDatabase.getUserProfileMap();

            // Initialize TeacherView with the mock Display object
            TeacherView teacher = new TeacherView(display);

            // Call the method to reorder and store students alphabetically
            java.util.List<UserProfile> resultJsonFilePath = teacher.sortAlphabetically(userProfileMap);

            // Check if the first user after sorting is "anothweewerstudent"
            String firstUser = "anothweewerstudent";
            String alphaUser = resultJsonFilePath.get(0).getUsername();
            assertEquals(firstUser, alphaUser);

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
