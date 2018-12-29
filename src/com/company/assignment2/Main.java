package com.company.assignment2;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

public class Main {
    static private String saveFileName = "savefile.dat"; // Name of the file to save the data
    static private ArrayList<User> users;
    static private Set<Integer> rollNumbers = new HashSet<>(); // Used to check for uniqueness of the rollNumbers
    static private Scanner in = new Scanner(System.in);

    // Read data from the saved file, if it exists
    private static void readData() {
        try {
            FileInputStream file = new FileInputStream(saveFileName);
            ObjectInputStream obj = new ObjectInputStream(file);

            // read the data from the file
            users = (ArrayList<User>) obj.readObject();

            for (User user : users) {
                rollNumbers.add(user.getRollNumber());
            }
            System.out.println("Successfully read saved user data from the disk");
            obj.close();
            file.close();
        } catch (IOException e) {
            System.out.println("No previous saved file found");
            users = new ArrayList<>();
        } catch (ClassNotFoundException e) {
            System.out.println("Saved file is invalid");
            users = new ArrayList<>();
        }
        // Sort the users based on the names first, then based on the roll numbers
        Collections.sort(users);
    }

    // Save the data to the external file
    private static void saveData() {
        try {
            FileOutputStream file = new FileOutputStream(saveFileName);
            ObjectOutputStream obj = new ObjectOutputStream(file);

            obj.writeObject(users);

            obj.close();
            file.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error in accessing the output file");
        } catch (IOException e) {
            System.out.println("Failed to save the file, IOException");
        }
    }

    // Helper function to determine if all the characters in the string are unique or not
    static boolean uniqueCharacters(String str) {
        final int MAX_CHAR = 256;
        if (str.length() > MAX_CHAR)
            return false;

        boolean[] chars = new boolean[MAX_CHAR];
        Arrays.fill(chars, false);

        for (int i = 0; i < str.length(); i++) {
            int index = (int) str.charAt(i);
            if (chars[index] == true)
                return false;
            chars[index] = true;
        }
        return true;
    }

    // Returns a User object after reading from the input
    private static User readNewUser() {

        System.out.println("Enter the Name of the New User");
        String name = in.next();

        System.out.println("Enter the age of the New User");
        int age = in.nextInt();

        System.out.println("Enter the Address of the New User");
        String address = in.next();

        String courses;
        boolean courseCheck;
        do {
            courseCheck = true;
            System.out.println("Enter the courses enrolled by the New User: Options A, B, C, D, E, F ");
            System.out.println("Enter 4 letter string for the corresponding courses");
            courses = in.next();
            String patternString = "(A|B|C|D|E|F){4}";
            Pattern pattern = Pattern.compile(patternString);
            // Check if all the courses are unique, also check if valid courses are entered by the user
            if (courses.length() != 4 || !uniqueCharacters(courses) || !pattern.matcher(courses).matches()) {
                System.out.println("Enter exactly 4 unique valid courses");
                courseCheck = false;
            }
        } while (!courseCheck);

        int roll;
        boolean rollUnique;
        do {
            rollUnique = true;
            System.out.println("Enter the Unique Roll Number");
            roll = in.nextInt();
            if (rollNumbers.contains(roll)) {
                System.out.println("The Entered Roll Number is already used for another user");
                rollUnique = false;
            }
        } while (!rollUnique);

        return new User(name, age, address, roll, courses);
    }

    private static void displayUsers() {
        System.out.println("---------------------------------------------------------------");
        System.out.println("Name        RollNo      Age         Address        Courses");
        System.out.println("---------------------------------------------------------------");
        for (User user : users) {
            System.out.println(user.getFullName() + "        " + user.getRollNumber() + "       " +
                    user.getAge() + "        " + user.getAddress() + "        " + user.getCoursesEnrolled());
        }
    }

    // Delete the User
    private static void deleteUser() {
        int roll;
        boolean rollCheck;
        do {
            rollCheck = true;
            System.out.println("Enter the Roll Number of the User to be deleted");
            roll = in.nextInt();
            if (!rollNumbers.contains(roll)) {
                System.out.println("Enter a valid roll number");
                rollCheck = false;
            }
        } while (!rollCheck);

        rollNumbers.remove(roll);
        for (User user : users) {
            if (user.getRollNumber() == roll) {
                users.remove(user);
                break;
            }
        }
        System.out.println("Successfully deleted the user");
    }

    private static void addNewUser() {
        User user = readNewUser();
        rollNumbers.add(user.getRollNumber());
        users.add(user);
        Collections.sort(users);
        System.out.println("Successfully added new User");
    }

    public static void main(String args[]) {
        readData();
        int choice;
        do {
            System.out.println("Enter any of the option below");
            System.out.println("1. Add new User");
            System.out.println("2. Display User");
            System.out.println("3. Delete User");
            System.out.println("4. Save Data to disk");
            System.out.println("5. Exit");
            choice = in.nextInt();

            switch (choice) {
                case 1:
                    addNewUser();
                    break;
                case 2:
                    displayUsers();
                    break;
                case 3:
                    deleteUser();
                    break;
                case 4:
                    saveData();
                    break;
                case 5:
                    break;

            }
        } while (choice != 5);
        System.out.println("Do you want to save the changes to disk?");
        char ch = in.next().charAt(0);
        if (ch == 'y' || ch == 'Y') {
            saveData();
        }
    }

}
