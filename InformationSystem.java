import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.YearMonth;

public class InformationSystem {
    // id,lastname,firstname,middlename,address,course,gender,phone,email
    static ArrayList<Student> students = new ArrayList<Student>();
    static Scanner input = new Scanner(System.in);
    static String path = "Students.csv";

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
        catch (Exception e) {}
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
        System.out.println("=======================");
        System.out.println(" [1] SHOW STUDENTS");
        System.out.println(" [2] ADD STUDENTS");
        System.out.println(" [3] SEARCH STUDENTS");
        System.out.println(" [4] TERMINATE PROGRAM");
        System.out.println("=======================");

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
        display_students();
        
        System.out.println("\n======================");
        System.out.println(" [1] SHOW BY ID");
        System.out.println(" [2] SHOW PAGE NUMBER");
        System.out.println(" [3] BACK");
        System.out.println("======================");

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
            exception();
            input.nextLine();
            show_students();
        }
    }

    public static void show_by_ID() throws IOException {
        display_by_id();

        System.out.println("\n=====================");
        System.out.println(" [1] EDIT STUDENT");
        System.out.println(" [2] DELETE STUDENT");
        System.out.println(" [3] SHOW SUBJECTS");
        System.out.println(" [4] BACK");
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
            exception();
            input.nextLine();
            return;
        }
    }

    public static void edit_student() throws IOException {
        clrscr();
        BufferedReader br = new BufferedReader(new FileReader(path));
        //id,lastname,firstname,middlename,email,address,course,gender,phone
    
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
            String idToEdit = input.nextLine();
            boolean found = false;
    
            Student studentToEdit = null;
            for (Student student : students) {
                if (student.getID().equals(idToEdit)) {
                    studentToEdit = student;
                    found = true;
                    break;
                }
            }

            if (!found) {
                System.out.print("Student not found. Please try again.");
                input.nextLine();
                show_by_ID();
                continue;
            }
    
            String lastName = "", firstName = "", middleName = "", address = "", course = "", gender = "", phone = "", email = "";
        
                String oldLastName, oldFirstName, oldMiddleName, oldAddress, oldCourse, oldGender, oldPhone, oldEmail;
                while (true) {
                    clrscr();
                    oldLastName = studentToEdit.getLastName();
                    System.out.printf("Enter the edited student's last name (%s): ", oldLastName);
                    lastName = input.nextLine();
    
                    oldFirstName = studentToEdit.getFirstName();
                    System.out.printf("Enter the edited student's first name (%s): ", oldFirstName);
                    firstName = input.nextLine();
    
                    oldMiddleName = studentToEdit.getMiddleName();
                    System.out.printf("Enter the edited student's middle name (%s): ", oldMiddleName);
                    middleName = input.nextLine();
    
                    if (lastName.matches("^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$") && firstName.matches("^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$") && middleName.matches("^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$")) {
                        studentToEdit.setLastName(lastName);
                        studentToEdit.setFirstName(firstName);
                        studentToEdit.setMiddleName(middleName);
                        break;
                    }
                    else 
                        System.out.println("That's not a valid name. Please try again.");
                }
                
                while (true) {
                    oldAddress = studentToEdit.getAddress();
                    System.out.printf("Enter the edited student's address (%s): ", oldAddress);
                    address = input.nextLine();
    
                    if (address.matches("^[a-zA-Z0-9\\s\\-\\(\\)]+$")) {
                        studentToEdit.setAddress(address);
                        break;
                    }
                    else
                        System.out.println("That's not a valid course. Please try again.");
                }
    
                while (true) {
                    oldCourse = studentToEdit.getCourse();
                    System.out.printf("Enter the edited student's course (%s): ", oldCourse);
                    course = input.nextLine();
    
                    if (course.matches("^[a-zA-Z0-9\\s\\-\\(\\)]+$")) {
                        studentToEdit.setCourse(course);
                        break;
                    }
                    else
                        System.out.println("That's not a valid course. Please try again.");
                }
    
                while (true) {
                    oldGender = studentToEdit.getGender();
                    System.out.printf("Enter the edited student's gender (%s): ", oldGender);
                    gender = input.nextLine();
    
                    if (gender.toUpperCase().matches("[M|F]")) {
                        studentToEdit.setGender(gender);
                        break;
                    }
                    else
                        System.out.println("That's not a valid gender. Please try again.");
                }
    
                while (true) {
                    oldPhone = studentToEdit.getPhone();
                    System.out.printf("Enter the edited student's phone number (%s): ", oldPhone);
                    phone = input.nextLine();
    
                    if (phone.toUpperCase().matches("^09\\d{9}$")) {
                        studentToEdit.setPhone(phone);
                        break;
                    }
                    else
                        System.out.println("That's not a valid phone number. Please try again.");
                }
    
                while (true) {
                    oldEmail = studentToEdit.getEmail();
                    System.out.printf("Enter the edited student's email (%s): ", oldEmail);
                    email = input.nextLine();
    
                    if (email.toUpperCase().matches("\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\\b")) {
                        studentToEdit.setEmail(email);
                        break;
                    }
                    else
                        System.out.println("That's not a valid email. Please try again.");
                }
    
            while (true) {
                System.out.print("\nAre you sure you want to edit this student? (\"YES\" or \"NO\"): ");
                String choice = input.nextLine();
    
                if (choice.equalsIgnoreCase("YES")) {
                    studentToEdit.setLastName(lastName);
                    studentToEdit.setFirstName(firstName);
                    studentToEdit.setMiddleName(middleName);
                    studentToEdit.setAddress(address);
                    studentToEdit.setCourse(course);
                    studentToEdit.setGender(gender);
                    studentToEdit.setPhone(phone);
                    studentToEdit.setEmail(email);
                
                    saveData();
                    break;
                }
                else if (choice.equalsIgnoreCase("NO"))
                    break;
                else {
                    exception();
                    continue;
                }
            }
            
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
        show_by_ID();
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
    
            for (int i = 1; i < students.size(); i++) {
                if (students.get(i).getID().equals(idToDelete)) {
                    indexToDelete = i;
                    students.remove(indexToDelete);
                    break;
                }
            }
            
            if (!(indexToDelete != -1)) {
                System.out.print("Student not found. Please try again.");
                input.nextLine();
                show_by_ID();
            }
    
            while (true) {
                System.out.print("Are you sure you want to delete this student? (\"YES\" or \"NO\"): ");
                String choice = input.nextLine();
    
                if (choice.equalsIgnoreCase("YES")) {
                    saveData();
                    System.out.println("Student deleted successfully.\n");
                    break;
                }
                else if (choice.equalsIgnoreCase("NO"))
                    break;
                else {
                    exception();
                    continue;
                }
            }
            
            boolean delete = false;
            while (true) {
                System.out.print("Would you like to delete another student? (\"YES\" or \"NO\"): ");
                String choice = input.nextLine();
    
                if (choice.toUpperCase().equals("YES")) {
                    delete = true;
                    break;
                }
                else if (choice.toUpperCase().equals("NO"))
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
        show_by_ID();
    }

    public static void show_subjects() throws IOException {
        clrscr();
    
        BufferedReader br = new BufferedReader(new FileReader("Subjects.csv"));

        String line;
        br.readLine();
        int count = 1;
        while ((line = br.readLine()) != null) {
            String[] values = line.split(",");
            System.out.println(count + ". " + values[0] + " " + values[1]);
            count++;
        }
        br.close();

        System.out.println("\n=====================");
        System.out.println(" [1] ADD SUBJECTS");
        System.out.println(" [2] ENTER GRADES");
        System.out.println(" [3] BACK");
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
            exception();
            input.nextLine();
            return;
        }
    }

    public static void add_subjects() throws IOException {
        clrscr();
        BufferedReader br = new BufferedReader(new FileReader("Subjects.csv"));

        while (true) {
            clrscr();
            System.out.print("Enter the name of the subject: ");
            String subject = input.nextLine();
            System.out.print("Enter the code of the subject: ");
            String code = input.nextLine();

            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                String codes = (values[0]);
                String subjects = (values[1]);
                if (subjects.equals(subject) || codes.equals(code)) {
                    System.out.print("Subject found. Please try to add a different subject.");
                    input.nextLine();
                    show_subjects();
                }
            }
            br.close();

            clrscr();
            System.out.println("Subject name: " + subject);
            System.out.println("Subject code: " + code);

            System.out.print("\nPress enter key to confirm.");
            input.nextLine();

            BufferedWriter bw = new BufferedWriter(new FileWriter("Subjects.csv", true));
            bw.write(code + "," + subject);
            bw.newLine();
            System.out.println("\nSubject successfully added!");
            
            bw.close();

            boolean add = false;
            while (true) {
                System.out.print("Do you want to add another subject? (\"YES\" or \"NO\"): ");
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

    public static void enter_grades() {
        
    }

    public static void show_page_number() throws Exception {
        paginated();
        show_students();
    }

    public static void add_students() throws IOException{
        BufferedReader br = new BufferedReader(new FileReader("Subjects.csv"));
        while (true) {
            clrscr();
            
            String lastName, firstName, middleName, address, course, gender, phone, email;

            while (true) {
                clrscr();
                System.out.print("Enter the student's last name: ");
                lastName = input.nextLine();
                System.out.print("Enter the student's first name: ");
                firstName = input.nextLine();
                System.out.print("Enter the student's middle name: ");
                middleName = input.nextLine();

                if (lastName.matches("^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$") && firstName.matches("^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$") && middleName.matches("^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$"))
                    break;
                else
                    System.out.println("That's not a valid name. Please try again.");
            }
            
            while (true) {
                System.out.print("Enter the student's address: ");
                address = input.nextLine();

                if (address.matches("^[a-zA-Z0-9\\s\\-\\(\\)]+$"))
                    break;
                else
                    System.out.println("That's not a valid course. Please try again.");
            }

            while (true) {
                System.out.print("Enter the student's course: ");
                course = input.nextLine();

                if (course.matches("^[a-zA-Z0-9\\s\\-\\(\\)]+$"))
                    break;
                else
                    System.out.println("That's not a valid course. Please try again.");
            }

            while (true) {
                System.out.print("Enter the student's gender (M/F): ");
                gender = input.nextLine();

                if (gender.toUpperCase().matches("[M|F]"))
                    break;
                else
                    System.out.println("That's not a valid gender. Please try again.");
            }

            while (true) {
                System.out.print("Enter the student's phone number: ");
                phone = input.nextLine();

                if (phone.toUpperCase().matches("^09\\d{9}$"))
                    break;
                else
                    System.out.println("That's not a valid phone number. Please try again.");
            }

            while (true) {
                System.out.print("Enter the student's email: ");
                email = input.nextLine();

                if (email.toUpperCase().matches("\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\\b"))
                    break;
                else
                    System.out.println("That's not a valid email. Please try again.");
            }
            
            YearMonth now = YearMonth.now();
            String yearMonth = String.format("%02d%02d", now.getYear() % 100, now.getMonthValue());
            int registrationNumber = get_next_id_number(yearMonth);
            String id = String.format("%s%03d", yearMonth, registrationNumber);

            System.out.println("The student's ID is: " + id);

            clrscr();
            System.out.println("Student's id:      " + id);
            System.out.println("Student's name:    " + lastName + ", " + firstName + " " + middleName);
            System.out.println("Student's address: " + address);
            System.out.println("Student's course:  " + course);
            System.out.println("Student's gender:  " + gender);
            System.out.println("Student's phone:   " + phone);
            System.out.println("Student's email:   " + email);

            System.out.print("\nPress enter key to confirm.");
            input.nextLine();

            BufferedWriter bw = new BufferedWriter(new FileWriter(path, true));
            students.add(new Student(id, lastName, firstName, middleName, address, course, gender, phone, email));
            bw.write(id + "," + lastName + "," + firstName + "," + middleName + "," + address + "," + course + "," + gender + "," + phone + "," + email);
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
        br.close();
        menu();
    }
    
    public static void search_students() throws IOException {
        clrscr();
        System.out.println("=====================");
        System.out.println(" [1] SEARCH BY ID");
        System.out.println(" [2] SEARCH BY NAME");
        System.out.println(" [3] BACK");
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
            exception();
            input.nextLine();
            return;
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
            int count = 1;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                Student student = new Student(values[0], values[1], values[2], values[3], values[4], values[5], values[6], values[7], values[8]);

                if (values[0].contains(search)) {
                    System.out.println(count + ". " + student.getID() + " - " + student.getLastName() + ", " + student.getFirstName() + " " + student.getMiddleName());
                    found = true;
                    count++;
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

    public static void saveData() throws IOException{
        BufferedWriter bw = new BufferedWriter(new FileWriter(path));
        for (Student student : students) {
            bw.write(student.getID()  + "," + student.getLastName() + "," + student.getFirstName()  + "," + student.getMiddleName()  + "," + student.getAddress() + "," + student.getCourse()  + "," + student.getGender()  + "," + student.getPhone()  + "," + student.getEmail());
            bw.newLine();
        }
        bw.close();
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

    public static void display_students() throws IOException {
        clrscr();
        BufferedReader br = new BufferedReader(new FileReader(path));
    
        String line;
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

        int count = 0;
        while ((line = br.readLine()) != null) {
            String[] values = line.split(",");

            if (count == 0) {
                System.out.printf("| #  | %-7s | %-15s | %-15s | %-15s | %-40s | %-10s | %-6s | %-15s | %-40s |\n", values[0], values[1], values[2], values[3], values[4], values[5], values[6], values[7], values[8]);
                System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                count++;
            }

            else {
                System.out.printf("| %5d | %-7s | %-15s | %-15s | %-15s | %-40s | %-10s | %-6s | %-15s | %-40s |\n", count, values[0], values[1], values[2], values[3], values[4], values[5], values[6], values[7], values[8]);
                System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                count++;
            }
        }
        br.close();
    }

    public static void display_by_id() throws IOException {
        clrscr();
        BufferedReader br = new BufferedReader(new FileReader(path));
    
        String line;
        System.out.println("-------------------");

        int count = 0;
        while ((line = br.readLine()) != null) {
            String[] values = line.split(",");

            if (count == 0) {
                System.out.printf("|  #  | %-7s |\n", values[0]);
                System.out.println("-------------------");
                count++;
            }

            else {
                System.out.printf("| %5d | %-7s |\n", count, values[0]);
                System.out.println("-------------------");
                count++;
            }
        }

        br.close();
    }

    public static void paginated() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(path));
    
        String line;
        int counter = 0;
        int page = 1;
    
        int numLines = 0;
        while (br.readLine() != null)
            numLines++;
        br.close();

        int lastPage = (int) Math.ceil(numLines / 100.0);

        br = new BufferedReader(new FileReader("Students.csv"));
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

        while ((line = br.readLine()) != null) {
            if (counter == 101) {
                System.out.println("You are viewing page " + page + " of " + lastPage + ".");
                System.out.print("Press Enter to continue...");
                input.nextLine();
                counter = 0;
                page++;
                clrscr();
                System.out.printf("%-7s %-15s %-15s %-15s %-40s %-10s %-6s %-15s %-40s\n", "ID", "Last Name", "First Name", "Middle Name", "Address", "Course", "Gender", "Phone", "Email");
                
            }
            String[] values = line.split(",");
            System.out.printf("| %-7s | %-15s | %-15s | %-15s | %-40s | %-10s | %-6s | %-15s | %-40s |\n", values[0], values[1], values[2], values[3], values[4], values[5], values[6], values[7], values[8]);
            System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            counter++;
        }

        br.close();
        System.out.print("You are viewing page " + page + " of " + lastPage + ".");
        System.out.print("\nEnd of file. Press enter key to go back to Show Students menu.");
        input.nextLine();
    }
    
    public static void exception() {
        System.out.print("\nPlease choose from the valid inputs. Press enter key to try again.");
        input.nextLine();
    }

    public static void clrscr() {
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

    public Student(String id, String lastName, String firstName, String middleName, String address, String course, String gender, String phone, String email) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.address = address;
        this.course = course;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
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

    public String getAddress() {
        return address;
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
    
    public void setAddress(String address) {
        this.address = address;
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
}