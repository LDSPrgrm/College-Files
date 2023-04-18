import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import java.time.YearMonth;

public class InformationSystem {
    // id,lastname,firstname,middlename,course,gender,phone,email,address
    static ArrayList<Student> students = new ArrayList<Student>();
    static Scanner input = new Scanner(System.in);
    static String path = "Data.csv";

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new FileReader(path));

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
        catch (Exception e) {
            System.out.print(e);
            input.nextLine();
        }
        finally {
            br.close();
            input.close();
            clrscr();
            System.out.println("===================");
            System.out.println("Program terminated!");
            System.out.println("===================");
            System.exit(0);
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

    public static void show_students() throws Exception {
        clrscr();

        BufferedReader br = new BufferedReader(new FileReader(path));

        String line;
        br.readLine();
        int count = 1;
        while ((line = br.readLine()) != null) {
            String[] values = line.split(",");
            Student student = new Student(values[0], values[1], values[2], values[3], values[4], values[5], values[6], values[7], values[8]);
            System.out.println(count + ". " + student.getID() + " - " + student.getLastName() + ", " +  student.getFirstName() + " " + student.getMiddleName() + " " + student.getCourse() + " " + student.getGender() + " " + student.getPhone() + " " + student.getEmail() + " " + student.getAddress());
            count++;
        }
        br.close();
        
        System.out.println("\n=====================");
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
                    main(null);
                else {
                    menu = 0;
                    exception();
                    show_students();
                }
            }
        }
        
        catch (Exception e) {
            System.out.print(e);
            exception();
            input.nextLine();
            show_students();
        }
    }

    public static void show_by_ID() throws IOException {
        clrscr();

        BufferedReader br = new BufferedReader(new FileReader(path));

        String line;
        br.readLine();
        int count = 1;
        while ((line = br.readLine()) != null) {
            String[] values = line.split(",");
            Student student = new Student(values[0], values[1], values[2], values[3], values[4], values[5], values[6], values[7], values[8]);
            System.out.println(count + ". " + student.getID() + " - " + student.getLastName() + ", " + student.getFirstName() + " " + student.getMiddleName());
            count++;
        }
        br.close();

        System.out.println("\n=====================");
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
                    show_students();
                else {
                    menu = 0;
                    exception();
                    show_by_ID();
                }
            }
        }
        catch (Exception e) {
            System.out.print(e);
            exception();
            input.nextLine();
            show_by_ID();
        }
    }

    public static void edit_student() throws IOException {
        clrscr();
        BufferedReader br = new BufferedReader(new FileReader(path));
        
        String line;
        while ((line = br.readLine()) != null) {
            String[] values = line.split(",");
            Student student = new Student(values[0], values[1], values[2], values[3], values[4], values[5], values[6], values[7], values[8]);
            students.add(student);
        }
        br.close();

        while (true) {
            clrscr();
            System.out.print("Enter the student ID you want to edit: ");
            String idToDelete = input.nextLine();
            boolean found = false;

            String id = "";
            for (int i = 0; i < students.size(); i++) {
                if (students.get(i).getID().equals(idToDelete)) {
                    id = students.get(i).getID();
                    found = true;
                    break;
                }
            }

            if (!found) {
                System.out.print("Student not found. Please try again.");
                input.nextLine();
                show_by_ID();
            }

            System.out.print("Enter the edited student's last name: ");
            String lastName = input.nextLine();
            System.out.print("Enter the edited student's first name: ");
            String firstName = input.nextLine();
            System.out.print("Enter the edited student's middle name: ");
            String middleName = input.nextLine();

            System.out.print("Enter the edited student's course: ");
            String course = input.nextLine();

            System.out.print("Enter the edited student's gender: ");
            String gender = input.nextLine();

            System.out.print("Enter the edited student's phone number: ");
            String phone = input.nextLine();

            System.out.print("Enter the edited student's email: ");
            String email = input.nextLine();

            System.out.print("Enter the edited student's address: ");
            String address = input.nextLine();

            while (true) {
                System.out.print("\nAre you sure you want to edit this student? (\"YES\" or \"NO\"): ");
                String choice = input.nextLine();

                if (choice.equalsIgnoreCase("YES")) {
                    BufferedWriter bw = new BufferedWriter(new FileWriter(path));
                    for (Student student : students) {
                        if (student.getID().equals(id)) {
                            student.setFirstName(firstName);
                            student.setLastName(lastName);
                            student.setFirstName(firstName);
                            student.setMiddleName(middleName);
                            student.setCourse(course);
                            student.setGender(gender);
                            student.setPhone(phone);
                            student.setEmail(email);
                            student.setAddress(address);
                            found = true;
                        }
                        bw.write(student.getID()  + "," + student.getLastName() + "," + student.getFirstName()  + "," + student.getMiddleName()  + "," + student.getCourse()  + "," + student.getGender()  + "," + student.getPhone()  + "," + student.getEmail()  + "," + student.getAddress());
                        bw.newLine();
                    }
                    bw.close();
                    break;
                }
                else if (choice.equalsIgnoreCase("NO"))
                    return;
                else {
                    exception();
                    continue;
                }
            }

            System.out.println("Student edited successfully.\n");
            
            boolean edit = false;
            while (true) {
                System.out.print("Would you like to edit another student? (\"YES\" or \"NO\"): ");
                String choice = input.nextLine();

                if (choice.equalsIgnoreCase("YES")) {
                    edit = true;
                    break;
                }
                else if (choice.equalsIgnoreCase("NO"))
                    break;
                else {
                    exception();
                    continue;
                }
            }
            if (edit)
                continue;
            break;
        }
    }

    public static void delete_student() throws IOException {
        clrscr();
        BufferedReader br = new BufferedReader(new FileReader(path));

        String line;
        while ((line = br.readLine()) != null) {
            String[] values = line.split(",");
            Student student = new Student(values[0], values[1], values[2], values[3], values[4], values[5], values[6], values[7], values[8]);
            students.add(student);
        }
        br.close();
        
        while (true) {
            clrscr();
            System.out.print("Enter ID you want to remove: ");
            String idToDelete = input.nextLine();
            int indexToDelete = -1;

            for (int i = 0; i < students.size(); i++) {
                if (students.get(i).getID().equals(idToDelete)) {
                    indexToDelete = i;
                    break;
                }
            }
            
            if (indexToDelete != -1)
                students.remove(indexToDelete);
            else {
                System.out.print("Student not found. Please try again.");
                input.nextLine();
                show_by_ID();
            }

            while (true) {
                System.out.print("Are you sure you want to delete this student? (\"YES\" or \"NO\"): ");
                String choice = input.nextLine();

                if (choice.equalsIgnoreCase("YES")) {
                    break;
                }
                else if (choice.equalsIgnoreCase("NO"))
                    return;
                else {
                    exception();
                    continue;
                }
            }
            
            BufferedWriter bw = new BufferedWriter(new FileWriter(path));
            for (Student student : students) {
                bw.write(student.getID() + "," + student.getLastName() + "," + student.getFirstName() + "," + student.getMiddleName() + "," + student.getCourse() + "," + student.getGender() + "," + student.getPhone() + "," + student.getEmail() + "," + student.getAddress());
                bw.newLine();
            }
            bw.close();

            System.out.println("Student deleted successfully.\n");
            
            boolean delete = false;
            while (true) {
                System.out.print("Would you like to delete another student? (\"YES\" or \"NO\"): ");
                String choice = input.nextLine();

                if (choice.equalsIgnoreCase("YES")) {
                    delete = true;
                    break;
                }
                else if (choice.equalsIgnoreCase("NO"))
                    break;
                else {
                    exception();
                    continue;
                }
            }
            if (delete)
                continue;
            break;
        }
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
                    show_by_ID();
                else {
                    menu = 0;
                    exception();
                    show_subjects();
                }
            }
        }
        
        catch (Exception e) {
            System.out.println(e);
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

    public static void add_students() {
        try {
            while (true) {
                clrscr();
                System.out.print("Enter the student's last name: ");
                String lastName = input.nextLine();
                System.out.print("Enter the student's first name: ");
                String firstName = input.nextLine();
                System.out.print("Enter the student's middle name: ");
                String middleName = input.nextLine();

                System.out.print("Enter the student's course: ");
                String course = input.nextLine();

                System.out.print("Enter the student's gender: ");
                String gender = input.nextLine();

                System.out.print("Enter the student's phone number: ");
                String phone = input.nextLine();

                System.out.print("Enter the student's email: ");
                String email = input.nextLine();

                System.out.print("Enter the student's address: ");
                String address = input.nextLine();
                
                YearMonth now = YearMonth.now();
                String yearMonth = String.format("%02d%02d", now.getYear() % 100, now.getMonthValue());
                int registrationNumber = get_next_id_number(yearMonth);
                String id = String.format("%s%03d", yearMonth, registrationNumber);

                System.out.println("The student's ID is: " + id);

                clrscr();
                System.out.println("Student's id:      " + id);
                System.out.println("Student's name:    " + lastName + ", " + firstName + " " + middleName);
                System.out.println("Student's course:  " + course);
                System.out.println("Student's gender:  " + gender);
                System.out.println("Student's phone:   " + phone);
                System.out.println("Student's email:   " + email);
                System.out.println("Student's address: " + address);

                System.out.print("\nPress enter key to confirm.");
                input.nextLine();

                BufferedWriter bw = new BufferedWriter(new FileWriter(path, true));
                students.add(new Student(id, lastName, firstName, middleName, course, gender, phone, email, address));
                bw.write(id + "," + lastName + "," + firstName + "," + middleName + "," + course + "," + gender + "," + phone + "," + email + "," + address);
                bw.newLine();
                System.out.println("\nStudent successfully added!");
                
                bw.close();
                boolean add = false;
                while (true) {
                    System.out.print("Do you want to add another student? (\"YES\" or \"NO\"): ");
                    String choice = input.nextLine();

                    if (choice.equalsIgnoreCase("YES")) {
                        add = true;
                        break;
                    }
                    else if (choice.equalsIgnoreCase("NO"))
                        break;
                    else {
                        exception();
                        continue;
                    }
                }
                if (add)
                    continue;
                break;
            }
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
                    main(null);
                else {
                    menu = 0;
                    exception();
                    search_students();;
                }
            }
        }
        
        catch (Exception e) {
            System.out.println(e);
            exception();
            input.nextLine();
            show_subjects();
        }
    }

    public static void search_by_ID() throws IOException {
        while (true) {
            clrscr();
            BufferedReader br = new BufferedReader(new FileReader(path));
            System.out.print("Search a student by ID: ");
            String search = input.nextLine();
            
            boolean found = false;
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                Student student = new Student(values[0], values[1], values[2], values[3], values[4], values[5], values[6], values[7], values[8]);

                if (values[0].contains(search)) {
                    System.out.println(student.getID() + " - " + student.getLastName() + ", " + student.getFirstName() + " " + student.getMiddleName());
                    found = true;
                }
            }

            if (!found)
                System.out.println("No student found.");

            boolean search_again = false;
            while (true) {
                System.out.print("\nDo you want to search another student by ID? (\"YES\" or \"NO\"): ");
                String choice = input.nextLine();

                if (choice.equalsIgnoreCase("YES")) {
                    search_again = true;
                    break;
                }
                else if (choice.equalsIgnoreCase("NO"))
                    break;
                else {
                    exception();
                    continue;
                }
            }
            if (search_again)
                continue;
            br.close(); 
            break;
        } 
        search_students();
    }

    public static void search_by_name() throws IOException {                
        while (true) {
            clrscr();
            BufferedReader br = new BufferedReader(new FileReader(path));
            System.out.print("Search a student by name: ");
            String search = input.nextLine();
            int count = 1;

            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                Student student = new Student(values[0], values[1], values[2], values[3], values[4], values[5], values[6], values[7], values[8]);

                if (values[1].toLowerCase().contains(search.toLowerCase()) || values[2].toLowerCase().contains(search.toLowerCase()) || values[3].toLowerCase().contains(search.toLowerCase())) {
                    System.out.println(count + ". " + student.getID() + " - " + student.getLastName() + ", " + student.getFirstName() + " " + student.getMiddleName());
                    count++;
                }
            }

            if (count == 1)
                System.out.println("No student found.");

            boolean search_again = false;
            while (true) {
                System.out.print("\nDo you want to search another student by name? (\"YES\" or \"NO\"): ");
                String choice = input.nextLine();

                if (choice.equalsIgnoreCase("YES")) {
                    search_again = true;
                    break;
                }
                else if (choice.equalsIgnoreCase("NO"))
                    break;
                else {
                    exception();
                    continue;
                }
            }
            if (search_again)
                continue;
            br.close(); 
            break;
        } 
        search_students();      
    }

    public static int get_next_id_number(String yearMonth) throws IOException {
        int registrationNumber = 1;
        BufferedReader br = new BufferedReader(new FileReader("ID.csv"));

        String line;
        while ((line = br.readLine()) != null) {
            String[] fields = line.split(",");
            if (fields[0].equals(yearMonth)) {
                registrationNumber = Integer.parseInt(fields[1]) + 1;
            }
        }
        br.close();

        BufferedWriter bw = new BufferedWriter(new FileWriter("ID.csv", true));
        bw.write(String.format("%s,%03d", yearMonth, registrationNumber));
        bw.newLine();
        bw.close();

        return registrationNumber;
    }

    public static void display_students() {
        for (Student student : students){
            System.out.println(student.getID() + "," + student.getLastName() + "," + student.getFirstName() + "," + student.getMiddleName() + "," + student.getCourse() + "," + student.getGender() + "," + student.getPhone() + "," + student.getEmail() + "," + student.getAddress());    
        }
    }

    public static void saveData() {
        
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
            bw.write("id,lastname,firstname,middlename,course,gender,phone,email,address\n");
            for(Student student : students){
                for(int i = 0; i < 9; i++){
                    bw.write(student.getID());
                    if (i != 9 - 1){
                        bw.write(",");
                    }
                }
                bw.newLine();
            }
        }
        catch(IOException e){}
    }

    public static void exception() {
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

class Student {
    private String id;
    private String lastName;
    private String firstName;
    private String middleName;
    private String course;
    private String gender;
    private String phone;
    private String email;
    private String address;

    public Student(String id, String lastName, String firstName, String middleName, String course, String gender, String phone, String email, String address) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.course = course;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    public String getID() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getCourse() {
        return course;
    }

    public String getGender() {
        return gender;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public void setID(String id) {
        this.id = id;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}