import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class App {
    static ArrayList<Student> student = new ArrayList<Student>();
    static Scanner input = new Scanner(System.in);
    static String path = "Data.csv";

    public static void main(String[] args) throws Exception {

        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
        }
        catch (FileNotFoundException e) {}

        try {
            int menu = 0;
            while (menu != 4) {
                if (menu == 0)
                    menu = menu();
                else if (menu == 1) {
                    show_students();
                    menu = 0;
                    continue;
                }
                else if (menu == 2) {
                    add_students();
                    menu = 0;
                    continue;
                }
                else if (menu == 3) {
                    search_students();
                    menu = 0;
                    continue;
                }
                else if (menu == 4)
                    break;
                else {
                    menu = 0;
                    exception();
                    continue;
                }
            }
        }
        catch (Exception e) {}
        finally {
            input.close();
            terminate_program();
        }
    }

    public static int menu() {
        clrscr();
        System.out.println("=====================");
        System.out.println(" 1 SHOW STUDENTS");
        System.out.println(" 2 ADD STUDENTS");
        System.out.println(" 3 SEARCH STUDENTS");
        System.out.println(" 4 TERMINATE PROGRAM");
        System.out.println("=====================");

        try {
            System.out.print("\nChoice: ");
            int menu = input.nextInt();
            input.nextLine();
            return menu;
        }
        catch (Exception e) {
            exception();
            input.nextLine();
            return 0;
        }
    }

    public static void show_students() {
        clrscr();
        System.out.println("=====================");
        System.out.println(" 1 SHOW BY ID");
        System.out.println(" 2 SHOW PAGE NUMBER");
        System.out.println(" 3 BACK");
        System.out.println("=====================");

        try {
            System.out.print("\nChoice: ");
            int menu = input.nextInt();
            input.nextLine();

            while (menu != -1) {
                if (menu == 1)
                    show_by_ID();
                else if (menu == 2)
                    show_page_number();
                else if (menu == 3)
                    back_to_main();
                else {
                    menu = 0;
                    exception();
                    show_students();
                }
            }
        }
        
        catch (Exception e) {
            exception();
            input.nextLine();
            show_students();
        }
    }

    public static void show_by_ID() {
        clrscr();
        System.out.println("=====================");
        System.out.println(" 1 EDIT STUDENT");
        System.out.println(" 2 DELETE STUDENT");
        System.out.println(" 3 SHOW SUBJECTS");
        System.out.println(" 4 BACK");
        System.out.println("=====================");

        try {
            System.out.print("\nChoice: ");
            int menu = input.nextInt();
            input.nextLine();

            while (menu != -1) {
                if (menu == 1)
                    edit_student();
                else if (menu == 2)
                    delete_student();
                else if (menu == 3)
                    show_subjects();
                else if (menu == 4)
                    back_to_show_by_id();
                else {
                    menu = 0;
                    exception();
                    show_by_ID();
                }
            }
        }
        
        catch (Exception e) {
            exception();
            input.nextLine();
            show_by_ID();
        }
    }

    public static void edit_student() {
        
    }

    public static void delete_student() {
        
    }

    public static void show_subjects() {
        clrscr();
        System.out.println("=====================");
        System.out.println(" 1 ADD SUBJECTS");
        System.out.println(" 2 ENTER GRADES");
        System.out.println(" 3 BACK");
        System.out.println("=====================");

        try {
            System.out.print("\nChoice: ");
            int menu = input.nextInt();
            input.nextLine();

            while (menu != -1) {
                if (menu == 1)
                    add_subjects();
                else if (menu == 2)
                    enter_grades();
                else if (menu == 3)
                    back_to_show_subjects();
                else {
                    menu = 0;
                    exception();
                    show_subjects();
                }
            }
        }
        
        catch (Exception e) {
            exception();
            input.nextLine();
            show_subjects();
        }
    }

    public static void add_subjects() {
        
    }

    public static void enter_grades() {
        
    }

    public static void show_page_number() {
        
    }

    public static void back_to_main() throws Exception {
        main(null);
    }

    public static void back_to_show_by_id() {
        show_by_ID();
    }

    public static void back_to_show_subjects() {
        show_subjects();
    }

    public static void add_students() {    
        String lineToWrite = "1000,Smith,BSCS";    
        try {
            FileWriter csvWriter = new FileWriter(path, true); 
            csvWriter.write(lineToWrite + "\n");
            csvWriter.flush();

            boolean add = true;
            while (add) {
                String id = "";
                System.out.println("Enter the student's name: ");
                String name = input.nextLine();
                System.out.println("Enter the student's course: ");
                String course = input.nextLine();

                student.add(null);


                System.out.println("Do you want to add another student?");
                int choice = input.nextInt();
                input.nextLine();

                if (choice == 1) {

                }
            }

            csvWriter.close();
            System.out.println("Done!");
            input.nextLine();
        } 
        catch (IOException e) {
            e.printStackTrace();
        } 
    }
    
    public static void search_students() {
        clrscr();
        System.out.println("=====================");
        System.out.println(" 1 SEARCH BY ID");
        System.out.println(" 2 SEARCH BY NAME");
        System.out.println(" 3 BACK");
        System.out.println("=====================");

        try {
            System.out.print("\nChoice: ");
            int menu = input.nextInt();
            input.nextLine();

            while (menu != -1) {
                if (menu == 1)
                    search_by_ID();
                else if (menu == 2)
                    search_by_name();
                else if (menu == 3)
                    back_to_main();
                else {
                    menu = 0;
                    exception();
                    search_students();;
                }
            }
        }
        
        catch (Exception e) {
            exception();
            input.nextLine();
            show_subjects();
        }
    }

    public static void search_by_ID() {
        
    }

    public static void search_by_name() {
        
    }
    
    public static void terminate_program() {
        clrscr();
        System.out.println("===================");
        System.out.println("Program terminated!");
        System.out.println("===================");
    }

    private static void exception() {
        System.out.print("\nPlease choose from the valid inputs. Press enter key to try again.");
        input.nextLine();
    }

    public static void clrscr(){
        try {
            if (System.getProperty("os.name").contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                Runtime.getRuntime().exec("clear");
        }
    catch (IOException | InterruptedException ex) {}
    }
}