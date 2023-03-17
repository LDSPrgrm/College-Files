import java.util.Scanner;
import java.util.ArrayList;
import java.io.IOException;

public class PrelimExam {
    // Global scanner and arraylist objects declaration
    private static Scanner input = new Scanner(System.in);
    private static ArrayList<String> name = new ArrayList<String>();
    private static ArrayList<String> address = new ArrayList<String>();
    private static ArrayList<String> course = new ArrayList<String>();

	public static void main(String[] args) {
        try {
            // Panel initialization
            int panel = -1;
            while (panel != 0) {
                // Display choices
                if (panel == -1)
                    panel = panel();
                // Terminate program
                else if (panel == 0)
                    break;
                // Add profiles panel
                else if (panel == 1)
                    panel = add();
                // Read profiles panel
                else if (panel == 2)
                    panel = read(name);
                // Update profiles panel
                else if (panel == 3)
                    panel = update();
                // Remove profiles panel
                else if (panel == 4)
                    panel = remove();
                else {
                    exception();
                    panel = -1;
                }
            }
        }
        // Catch error
        catch (Exception e) {}
        // Close scanner and program termination
        finally {
            input.close();
            terminate();
        }
	}

    // Choose panel function definition
    private static int panel() {
        ClearScreen();
        System.out.println("=====================");
        System.out.println(" 0 TERMINATE PROGRAM");
        System.out.println(" 1 ADD A PROFILE");
        System.out.println(" 2 READ PROFILES");
        System.out.println(" 3 UPDATE A PROFILE");
        System.out.println(" 4 REMOVE A PROFILE");
        System.out.println("=====================");

        // Try this code
        try {
            System.out.print("\nChoice: ");
            int panel = input.nextInt();
            input.nextLine();
            return panel;
        }
        // If there's an exception, run this code instead
        catch (Exception e) {
            exception();
            input.nextLine();
            return -1;
        }
    }

    // Add profiles function definition
    private static int add() {
        ClearScreen();
        System.out.println("==========ADD A PROFILE==========");
        // Get informations of the user then add them to the arraylists
        System.out.print("Enter name: ");
        name.add(input.nextLine());
        
		System.out.print("Enter address: ");
        address.add(input.nextLine());

        System.out.print("Enter course: ");
        course.add(input.nextLine());
        return -1;
    }

    // Read profiles function definition
    private static int read(ArrayList<String> name) {
        ClearScreen();
        System.out.println("============PROFILES============");
        // Display the profiles
        for (int i = 0; i < name.size(); i++)
            System.out.println("\n" + "Profile " + (i + 1) + "\nName:    " + name.get(i) + "\nAddress: " + address.get(i) + "\nCourse:  " + course.get(i));

        System.out.print("\nPress enter key to continue.");
        input.nextLine();
        return -1;
    }

    // Update profiles function definition
    private static int update() {
        ClearScreen();
        System.out.println("==========UPDATE PROFILES==========");
        System.out.println("0 Back");

        // Display the profiles
        for (int i = 0; i < name.size(); i++)
            System.out.println(i + 1 + " " + name.get(i));
        System.out.println("===================================");
        
        // Try this code
        try {
		    System.out.print("\nChoose a profile to update: ");
		    int index = input.nextInt();
            input.nextLine();
            // Go back to driver code if index is 0
            if (index == 0)
                main(null);
            // If input is greater than the number of profiles, rerun this function
            if (index > name.size()) {
                exception();
                return 3;
            }
            // Get the updated information of the selected user
            else {
                ClearScreen(); 
                System.out.println("==========UPDATING PROFILE==========");
                System.out.print("Enter name: ");
                name.set(index - 1, input.nextLine());

                System.out.print("Enter address: ");
                address.set(index - 1, input.nextLine());
    
                System.out.print("Enter course: ");
                course.set(index - 1, input.nextLine());
            }
            return -1;
        }
        // If there's an exception, run this code instead
        catch (Exception e) {
            exception();
            input.nextLine();
            return 3;
        }
    }

    // Remove profiles function definition
    private static int remove() {
        ClearScreen();
        System.out.println("==========REMOVE PROFILES==========");
        System.out.println("0 Back");
        
        // Display the profiles
        for (int i = 0; i < name.size(); i++)
            System.out.println(i + 1 + " " + name.get(i));
        System.out.println("===================================");

        // Try this code
        try {
            System.out.print("\nChoose a profile to be removed from the list: ");
            int index = input.nextInt();
            input.nextLine();
        
            // Go back if index is 0
            if (index == 0)
                main(null);
            // If input is greater than the number of profiles, rerun this function
            if (index > name.size()) {
                exception();
                return 4;
            }
            // Remove all the information related to the profile selected
            else {
                name.remove(index - 1);
                address.remove(index - 1);
                course.remove(index - 1);
            }
            return -1;
        }
        // If there's an exception, run this code instead
        catch (Exception e) {
            exception();
            input.nextLine();   
            return 4;
        }
    }

    // Exception function definition
    private static void exception() {
        System.out.print("\nPlease choose from the valid inputs. Press enter key to try again.");
        input.nextLine();
    }

    // Terminate program function definition 
    private static void terminate() {
        ClearScreen();
        System.out.println("===================");
        System.out.println("Program terminated!");
        System.out.println("===================");
    }

    // Clears the screen
    private static void ClearScreen() {
        try {
            // Windows
            if (System.getProperty("os.name").contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            // Mac
            else
                Runtime.getRuntime().exec("clear");
        } catch (IOException | InterruptedException ex) {}
    }
}