import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class UserAccountManager {
ArrayList<User> accountList;

    public UserAccountManager() {
        this.accountList = new ArrayList<>();
    }


    /**
     * @param userName Users username taken as a string
     * Check if the user is already existing by reading through the existing  arraylist */
    public boolean existingUser(String userName) {
        for (User userSearch : accountList) {
            if (userName.equals(userSearch.getUsername())) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param user User object
     * Takes users as objects and adds them into the array list */
    public void addAccount(User user) {
           this.accountList.add(user);

        try (PrintWriter writer = new PrintWriter(new FileWriter("accountFile.txt"))) {
            writer.println(user.getUsername());
            writer.println(user.getPassword());
        } catch (IOException e) {
            System.err.println("Error saving data to file: " + e.getMessage());
        }
    }

    /**
     * @param fileName The name of the file
     * Reads the file mentioned above and restores the account data */
    public  void restoreAccounts(String fileName) {

        try {
            File file = new File(fileName);


            Scanner scanner = new Scanner(file);

            while (scanner.hasNext()) {
                accountList.add(new User(
                   scanner.next(),
                   scanner.next()
                ));

            }

        } catch(FileNotFoundException e){
            System.out.println("No existing accounts created up to now");
        }
    }

    /**
     * @param username  A string for the username
     * @param password  A string taken for password
     * Checks if the username and password already exist and if it is matching existing users username and password
     * @return  The boolean value true if the user is authorised (Matching usernames and passwords) */
    public boolean authorizeUser(String username, String password){
        for (User userSearch : accountList) {
            if ((username.equals(userSearch.getUsername())) && password.equals(userSearch.getPassword())) {
                return true;
            }
        }
        return false;
    }





}
