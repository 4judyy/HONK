import com.google.gson.*;
import com.google.gson.reflect.*;
import java.lang.reflect.*;
import java.io.*;
import java.util.*;

/**
 * Manages user profiles stored in the users.json JSON file.
 * <p>
 *     Utilizes the GSON library serializing and deserializing user profiles including score and usernames.
 *     Enables easy storage and retrieval.
 * </p>
 * @author Di Zhou
 */
public class Database {
    /** map of users */
    private Map<String, UserProfile> userProfileMap;
    /** json file path */
    private String jsonFilePath = "json_files/users.json";

    /**
     * Constructor for Database
     * Initializes userProfileMap and processing JSON files
     * @throws FileNotFoundException when json file is not found
     */
    public Database() throws FileNotFoundException {

        // determine the correct type to be deserialized
        Type listOfUsersObject = new TypeToken<ArrayList<UserProfile>>(){}.getType();

        // work with GSON's Library
        Gson gson = new Gson();

        // Create file reader
        try (FileReader reader = new FileReader(jsonFilePath)) {
            // make it a list of user profiles from json file
            List<UserProfile> userProfileList = gson.fromJson(reader, listOfUsersObject);

            // create the userProfileMap
            userProfileMap = new HashMap<>();

            // populate the userProfileMap
            for (UserProfile userProfile : userProfileList) {
                userProfileMap.put(userProfile.getUsername(), userProfile);
            }
        } catch (Exception e) {
            // Handle any exceptions
            e.printStackTrace();
        }
    }

    /**
     * method to search for a user profile in the map when given username
     * @param username entered by user at login
     * @param password entered by user at login
     * @param role entered by user at login
     * @return a string array of the password and role if it exists,
     * otherwise returns null and creates a new profile with given info.
     */
    public String[] searchUser(String username, String password, String role) {
        UserProfile userProfile = userProfileMap.get(username);
        if (userProfile != null) {
            //store password and role in array
            return new String[]{userProfile.getPassword(), userProfile.getPermission()};
        } else {
            // create username
            UserProfile newUser = new UserProfile(username, password, role, 0, 0, 0, 0);
            userProfileMap.put(username, newUser); // Add the new user profile to the map
            saveDataToJson(); // Save the updated data to JSON file
            return null; // User profile not found
        }
    }

    /**
     * Finds the top 10 users with the highest score
     * @param category of the top 10 users
     * @return a list of the top 10 users
     */
    public List<UserProfile> topUsers(String category) {

        //determine method to call
        String methodName = "get" + category;

        //create list for profiles
        List<UserProfile> allProfiles = new ArrayList<>(userProfileMap.values());

        // Sort the list based on the specified category using Reflection
        try {
            allProfiles.sort(Comparator.comparingInt(userProfile -> {
                try {
                    return (int) UserProfile.class.getMethod(methodName).invoke(userProfile);
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                    // Return a default value if an error occurs during method invocation
                    return 0;
                }
            }).reversed());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Return the top ten profiles (or less if there are fewer than three profiles)
        return allProfiles.subList(0, Math.min(10, allProfiles.size()));
    }

    /**
     * method to update and save data to json file
     */
    public void saveDataToJson() {

        //create file writer
        try (FileWriter fileWriter = new FileWriter(jsonFilePath)) {
            // Convert the map values to a list of UserProfile objects
            List<UserProfile> userProfileList = new ArrayList<>(userProfileMap.values());

            // Create Gson instance with pretty printing
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            // Serialize the list to JSON and write to file
            gson.toJson(userProfileList, fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * getter method to access the userProfileMap
     * @return the user profile map
     */
    public Map<String, UserProfile> getUserProfileMap() {
        return userProfileMap;
    }

}
