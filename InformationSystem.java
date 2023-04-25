import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.YearMonth;

public class InformationSystem {
    static ArrayList<Student> students = new ArrayList<Student>();
    static ArrayList<Subject> subjects = new ArrayList<Subject>();
    static ArrayList<StudentSubject> studentSubjects = new ArrayList<StudentSubject>();
    static Scanner input = new Scanner(System.in);
    static String path = "Students.csv";
    static Student studentToEdit;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new FileReader(path));
    
        String line;
        while ((line = br.readLine()) != null) {
            String[] values = line.split(",");
            Student student = new Student(values[0], values[1], values[2], values[3], values[4], values[5], values[6], values[7], values[8], values[9], values[10]);
            students.add(student);
        }
        br.close();

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
        System.out.println("=====================================================================");
        System.out.println(" [1] SHOW STUDENTS     - Shows the complete list of students");
        System.out.println(" [2] ADD STUDENTS      - Add students and their personal informations");
        System.out.println(" [3] SEARCH STUDENTS   - Search students by student ID or name");
        System.out.println(" [4] TERMINATE PROGRAM - Terminates the program");
        System.out.println("=====================================================================");

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
        
        System.out.println("\n========================================================================");
        System.out.println(" [1] SHOW PAGE NUMBER - Shows the list of students in a paginated manner");
        System.out.println(" [2] EDIT STUDENT     - Edit students and their personal informations");
        System.out.println(" [3] DELETE STUDENT   - Delete students from the database permanently");
        System.out.println(" [4] SHOW SUBJECTS    - Shows the complete list of subjects");
        System.out.println(" [5] BACK             - Go back to main menu");
        System.out.println("========================================================================");

        try {
            System.out.print("\nChoice: ");
            int menu = input.nextInt();
            input.nextLine();

            while (menu != -1) {
                if (menu == 1)
                    show_page_number();
                else if (menu == 2)
                    edit_student();
                else if (menu == 3)
                    delete_student();
                else if (menu == 4)
                    show_subjects();
                else if (menu == 5)
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

    public static void edit_student() throws Exception {
        clrscr();
    
        while (true) {
            display_students();
            System.out.print("Enter the student ID you want to edit or '0' to go back: ");
            String idToEdit = input.nextLine();

            if (idToEdit.equals("0"))
                show_students();

            boolean found = false;
            
            String name = null;
            for (Student student : students) {
                if (student.getID().equals(idToEdit)) {
                    studentToEdit = student;
                    name = student.getLastName() + ", " + student.getFirstName() + " " + student.getMiddleName();
                    found = true;
                    break;
                }
            }

            if (!found) {
                System.out.print("Student not found. Please try again.");
                input.nextLine();
                show_students();
                continue;
            }

            options(idToEdit, name);
            
            boolean edit = false;
            while (true) {
                System.out.print("Would you like to edit another student? (\"YES\" or \"NO\"): ");
                String choice = input.nextLine().toUpperCase();
    
                if (choice.equals("YES")) {
                    edit = true;
                    break;
                }
                else if (choice.equals("NO"))
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
        show_students();
    }
    

    public static void delete_student() throws Exception {
        clrscr();
        
        while (true) {
            clrscr();
            display_students();
            System.out.print("Enter ID you want to remove or '0' to go back: ");
            String idToDelete = input.nextLine();

            if (idToDelete.equals("0"))
                show_students();

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
                show_students();
            }
    
            while (true) {
                System.out.print("Are you sure you want to delete this student? (\"YES\" or \"NO\"): ");
                String choice = input.nextLine().toUpperCase();
    
                if (choice.equals("YES")) {
                    saveData();
                    System.out.println("Student deleted successfully.\n");
                    break;
                }
                else if (choice.equals("NO"))
                    break;
                else {
                    exception();
                    continue;
                }
            }
            
            boolean delete = false;
            while (true) {
                System.out.print("Would you like to delete another student? (\"YES\" or \"NO\"): ");
                String choice = input.nextLine().toUpperCase();
    
                if (choice.equals("YES")) {
                    delete = true;
                    break;
                }
                else if (choice.equals("NO"))
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
        show_students();
    }

    public static void display_subjects() throws IOException {
        clrscr();

        BufferedReader br = new BufferedReader(new FileReader("Subjects.csv"));
    
        System.out.println("---------------------------------------------------------------------------------------------------------");

        String line;
        int count = 0;
        while ((line = br.readLine()) != null) {
            String[] values = line.split(",");

            if (count == 0) {
                System.out.printf("|   #   | %-15s | %-75s |\n", values[0], values[1]);
                System.out.println("---------------------------------------------------------------------------------------------------------");
                count++;
            }

            else {
                System.out.printf("| %5d | %-15s | %-75s |\n", count, values[0], values[1]);
                System.out.println("---------------------------------------------------------------------------------------------------------");
                count++;
            }
        }
        br.close();
    }

    public static void show_subjects() throws IOException {
        display_subjects();

        System.out.println("\n==================================================");
        System.out.println(" [1] ADD SUBJECTS - Add subjects to the database");
        System.out.println(" [2] DELETE SUBJECTS - Delete subjects permanently");
        System.out.println(" [3] BACK - Go back to the previous menu");
        System.out.println("==================================================");

        try {
            System.out.print("\nChoice: ");
            int menu = input.nextInt();
            input.nextLine();

            while (menu != -1) {
                if (menu == 1)
                    add_subjects();
                else if (menu == 2)
                    delete_subjects();
                else if (menu == 3)
                    show_students();
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
            System.out.print("ADD A SUBJECT\nEnter the name of the subject: ");
            String subject = input.nextLine();

            String number;
            while (true) {
                System.out.print("Enter the course number of the subject (Enter '0' if none): ");
                number = input.nextLine();

                if (number.matches("[/^\\d$/]"))
                    break;
            }

            System.out.print("Enter the code of the subject: ");
            String code = input.nextLine().toUpperCase();

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
            if (number.equals("0")) {
                System.out.println("Subject name: " + subject);
                System.out.println("Subject code: " + code);
            }
            else {
                System.out.println("Subject name: " + subject + " " + number);
                System.out.println("Subject code: " + code + number);
            }

            System.out.print("\nPress enter key to confirm.");
            input.nextLine();

            BufferedWriter bw = new BufferedWriter(new FileWriter("Subjects.csv", true));
            if (number.equals("0")) {
                bw.write(code + "," + subject);
                bw.newLine();
            }
            else {   
                bw.write(code + number + "," + subject + " " + number);
                bw.newLine();
            }
            System.out.println("\nSubject successfully added!");
            
            bw.close();

            boolean add = false;
            while (true) {
                System.out.print("Do you want to add another subject? (\"YES\" or \"NO\"): ");
                String choice = input.nextLine().toUpperCase(null);

                if (choice.equals("YES")) {
                    add = true;
                    break;
                }
                else if (choice.equals("NO"))
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
        show_subjects();
    }

    public static void delete_subjects() throws IOException {

        clrscr();
        BufferedReader br = new BufferedReader(new FileReader("Subjects.csv"));

        while (true) {
            clrscr();
            display_subjects();
            System.out.print("Enter the code of the subject that you want to delete or '0' to go back: ");
            String codeToDelete = input.nextLine().toUpperCase();

            if (codeToDelete.equals("0"))
                show_subjects();

            String line, subject = null;
            int indexToDelete = -1;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                subjects.add(new Subject(values[0], values[1]));

                if (values[0].equals(codeToDelete))
                    subject = values[1];
            }
            br.close();
  
            for (int i = 1; i < subjects.size(); i++) {
                if (subjects.get(i).getCode().equals(codeToDelete)) {
                    indexToDelete = i;
                    subjects.remove(indexToDelete);
                    break;
                }
            }

            if (indexToDelete == -1) {
                System.out.print("Subject code not found. Please try again.");
                input.nextLine();
                delete_subjects();
            }

            while (true) {
                System.out.println("Subject code: " + codeToDelete);
                System.out.println("Subject name: " + subject);
                System.out.print("\nAre you sure you want to delete this subject? (\"YES\" or \"NO\"): ");
                String choice = input.nextLine().toUpperCase(null);
    
                if (choice.equals("YES")) {
                    BufferedWriter bw = new BufferedWriter(new FileWriter("Subjects.csv"));
                    for (Subject subjected : subjects) {
                        bw.write(subjected.getCode() + "," + subjected.getName());
                        bw.newLine();
                    }
                    System.out.println("\nSubject successfully deleted!");
                    bw.close();                    
                    break;
                }
                else if (choice.equals("NO"))
                    break;
                else {
                    exception();
                    continue;
                }
            }

            boolean delete = false;
            while (true) {
                System.out.print("Do you want to delete another subject? (\"YES\" or \"NO\"): ");
                String choice = input.nextLine().toUpperCase();

                if (choice.equals("YES")) {
                    delete = true;
                    break;
                }
                else if (choice.equals("NO"))
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
        show_subjects();
    }

    public static void show_page_number() throws Exception {
        paginated();
        show_students();
    }

    public static void add_students() throws IOException{
        BufferedReader br = new BufferedReader(new FileReader("Subjects.csv"));
        while (true) {
            clrscr();
            
            //ID,Last Name,First Name,Middle Name,Course,Gender,Date of Birth,Address,Phone,Email,Date of Enrollment
            String lastName, firstName, middleName, course, gender, dateOfBirth, address, phone, email, dateOfEnrollment;

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
                System.out.print("Enter the student's course (BSCS-I): ");
                course = input.nextLine();

                if (course.matches("^[B][A-Z]+-[I|V]+$"))
                    break;
                else
                    System.out.println("That's not a valid course. Please try again.");
            }

            while (true) {
                System.out.print("Enter the student's gender (M/F): ");
                gender = input.nextLine().toUpperCase();

                if (gender.toUpperCase().matches("[M|F]"))
                    break;
                else
                    System.out.println("That's not a valid gender. Please try again.");
            }

            while (true) {
                System.out.print("Enter the student's date of birth (MM-DD-YYYY): ");
                dateOfBirth = input.nextLine();

                if (dateOfBirth.toUpperCase().matches("^(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])-(19|20)\\d{2}$"))
                    break;
                else
                    System.out.println("That's not a valid date of birth. Please try again.");
            }

            while (true) {
                System.out.print("Enter the student's address (Ex: Barangay / Town or City / Province): ");
                address = input.nextLine();

                if (address.matches("^[A-Za-z0-9-.,()'\\/& ]+( [A-Za-z0-9-.,()'\\/& ]+)*\\s\\/\\s[A-Za-z0-9-.,()'\\/& ]+( [A-Za-z0-9-.,()'\\/& ]+)*$"))
                    break;
                else
                    System.out.println("That's not a valid address. Please try again.");
            }
            
            while (true) {
                System.out.print("Enter the student's phone number (09xxxxxxxxx): ");
                phone = input.nextLine();

                if (phone.matches("^09\\d{9}$"))
                    break;
                else
                    System.out.println("That's not a valid phone number. Please try again.");
            }

            while (true) {
                System.out.print("Enter the student's email: ");
                email = input.nextLine();

                if (email.matches("\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\\b"))
                    break;
                else
                    System.out.println("That's not a valid email. Please try again.");
            }
            
            while (true) {
                System.out.print("Enter the student's date of enrollment (MM-DD-YYYY): ");
                dateOfEnrollment = input.nextLine();

                if (dateOfEnrollment.toUpperCase().matches("^(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])-(19|20)\\d{2}$"))
                    break;
                else
                    System.out.println("That's not a valid date of enrollment. Please try again.");
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
            System.out.println("Student's date of birth:  " + dateOfBirth);
            System.out.println("Student's phone:   " + phone);
            System.out.println("Student's email:   " + email);
            System.out.println("Student's date of enrollment:  " + dateOfEnrollment);

            System.out.print("\nPress enter key to confirm.");
            input.nextLine();

            BufferedWriter bw = new BufferedWriter(new FileWriter(path, true));
            // ID,Last Name,First Name,Middle Name,Course,Gender,Date of Birth,Address,Phone,Email,Date of Enrollment
            students.add(new Student(id, lastName, firstName, middleName, course, gender, dateOfBirth, address, phone, email, dateOfEnrollment));
            bw.write(id + "," + lastName + "," + firstName + "," + middleName + "," + course + "," + gender + "," + dateOfBirth + "," + address + "," + phone + "," + email + "," + dateOfEnrollment);
            bw.newLine();
            System.out.println("\nStudent successfully added!");
            
            bw.close();
            boolean add = false;
            while (true) {
                System.out.print("Do you want to add another student? (\"YES\" or \"NO\"): ");
                String choice = input.nextLine().toUpperCase();

                if (choice.equals("YES")) {
                    add = true;
                    break;
                }
                else if (choice.equals("NO"))
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
    }
    
    public static void search_students() throws IOException {
        clrscr();
        System.out.println("=============================================");
        System.out.println(" [1] SEARCH BY ID - Search students by ID");
        System.out.println(" [2] SEARCH BY NAME - Search students by name");
        System.out.println(" [3] BACK - Go back to the previous menu");
        System.out.println("=============================================");

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
            System.out.print("Search a student by entering an ID or '0' to go back: ");
            String search = input.nextLine();
            int count = 1;

            if (search.equals("0"))
                search_students();
            
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");

                if (values[0].contains(search)) {
                    if (count == 0) {
                        System.out.printf("|   #   | %-7s | %-15s | %-15s | %-15s | %-20s | %-6s | %-15s | %-40s | %-15s | %-40s | %-20s |\n", values[0], values[1], values[2], values[3], values[4], values[5], values[6], values[7], values[8], values[9], values[10]);
                        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                        count++;
                    }
        
                    else {
                        System.out.printf("| %5d | %-7s | %-15s | %-15s | %-15s | %-20s | %-6s | %-15s | %-40s | %-15s | %-40s | %-20s |\n", count, values[0], values[1], values[2], values[3], values[4], values[5], values[6], values[7], values[8], values[9], values[10]);
                        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                        count++;
                    }
                }
            }

            if (count == 1)
                System.out.println("No student found.");

            boolean search_again = false;
            while (true) {
                System.out.print("\nDo you want to search another student by ID? (\"YES\" or \"NO\"): ");
                String choice = input.nextLine().toUpperCase(null);

                if (choice.equals("YES")) {
                    search_again = true;
                    break;
                }
                else if (choice.equals("NO"))
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
            System.out.print("Search a student by entering a name or '0' to go back: ");
            String search = input.nextLine();
            int count = 1;

            if (search.equals("0"))
                search_students();

            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");

                if (values[1].toLowerCase().contains(search.toLowerCase()) || values[2].toLowerCase().contains(search.toLowerCase()) || values[3].toLowerCase().contains(search.toLowerCase())) {
                    if (count == 0) {
                        System.out.printf("|   #   | %-7s | %-15s | %-15s | %-15s | %-20s | %-6s | %-15s | %-40s | %-15s | %-40s | %-20s |\n", values[0], values[1], values[2], values[3], values[4], values[5], values[6], values[7], values[8], values[9], values[10]);
                        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                        count++;
                    }
        
                    else {
                        System.out.printf("| %5d | %-7s | %-15s | %-15s | %-15s | %-20s | %-6s | %-15s | %-40s | %-15s | %-40s | %-20s |\n", count, values[0], values[1], values[2], values[3], values[4], values[5], values[6], values[7], values[8], values[9], values[10]);
                        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                        count++;
                    }
                }
            }

            if (count == 1)
                System.out.println("No student found.");

            boolean search_again = false;
            while (true) {
                System.out.print("\nDo you want to search another student by name? (\"YES\" or \"NO\"): ");
                String choice = input.nextLine().toUpperCase();

                if (choice.equals("YES")) {
                    search_again = true;
                    break;
                }
                else if (choice.equals("NO"))
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
            // /ID,Last Name,First Name,Middle Name,Course,Gender,Date of Birth,Address,Phone,Email,Date of Enrollment
            bw.write(student.getID()  + "," + student.getLastName() + "," + student.getFirstName()  + "," + student.getMiddleName()  + "," + student.getCourse() + "," + student.getGender()  + "," + student.getDateOfBirth()  + "," + student.getAddress()  + "," + student.getPhone() + "," + student.getEmail() + "," + student.getDateOfEnrollment());
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

        BufferedWriter bw = new BufferedWriter(new FileWriter("ID.csv"));
        bw.write(String.format("%s,%03d", yearMonth, registrationNumber));
        bw.close();

        return registrationNumber;
    }

    public static void display_students() throws IOException {
        clrscr();
        BufferedReader br = new BufferedReader(new FileReader(path));
    
        String line;
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

        int count = 0;
        while ((line = br.readLine()) != null) {
            String[] values = line.split(",");

            if (count == 0) {
                System.out.printf("|   #   | %-7s | %-15s | %-15s | %-15s | %-20s | %-6s | %-15s | %-40s | %-15s | %-40s | %-20s |\n", values[0], values[1], values[2], values[3], values[4], values[5], values[6], values[7], values[8], values[9], values[10]);
                System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                count++;
            }

            else {
                System.out.printf("| %5d | %-7s | %-15s | %-15s | %-15s | %-20s | %-6s | %-15s | %-40s | %-15s | %-40s | %-20s |\n", count, values[0], values[1], values[2], values[3], values[4], values[5], values[6], values[7], values[8], values[9], values[10]);
                System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
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
                System.out.printf("|   #   | %-7s |\n", values[0]);
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
        clrscr();
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
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

        while ((line = br.readLine()) != null) {
            if (counter == 101) {
                System.out.println("You are viewing page " + page + " of " + lastPage + ".");
                System.out.print("Press enter key to continue.");
                input.nextLine();
                counter = 0;
                page++;
                clrscr();
                System.out.printf("%-7s %-15s %-15s %-15s %-40s %-10s %-6s %-15s %-40s %-15s %-15s\n", "ID", "Last Name", "First Name", "Middle Name", "Course and Year", "Gender", "Date of Birth", "Address", "Phone", "Email", "Date of Enrollment");
                
            }
            String[] values = line.split(",");
            System.out.printf("| %-7s | %-15s | %-15s | %-15s | %-20s | %-6s | %-15s | %-40s | %-15s | %-40s | %-20s |\n", values[0], values[1], values[2], values[3], values[4], values[5], values[6], values[7], values[8], values[9], values[10]);
            System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            counter++;
        }

        br.close();
        System.out.print("You are viewing page " + page + " of " + lastPage + ".");
        System.out.print("\nEnd of file. Press enter key to go back to SHOW STUDENTS menu.");
        input.nextLine();
    }

    public static void edit_details(Student studentToEdit) throws IOException {

        //ID,Last Name,First Name,Middle Name,Course,Gender,Date of Birth,Address,Phone,Email,Date of Enrollment
        String lastName = "", firstName = "", middleName = "", course = "", gender = "", dateOfBirth = "",  address = "", phone = "", email = "", dateOfEnrollment = "";
        String oldLastName, oldFirstName, oldMiddleName, oldCourse, oldGender, oldDateOfBirth, oldAddress, oldPhone, oldEmail, olddateOfEnrollment;
        
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
            oldCourse = studentToEdit.getCourse();
            System.out.printf("Enter the edited student's course (%s): ", oldCourse);
            course = input.nextLine();

            if (course.matches("^[B][A-Z]+-[I|V]+$")) {
                studentToEdit.setCourse(course);
                break;
            }
            else
                System.out.println("That's not a valid course. Please try again.");
        }
        
        while (true) {
            oldGender = studentToEdit.getGender();
            System.out.printf("Enter the edited student's gender (%s): ", oldGender);
            gender = input.nextLine().toUpperCase();

            if (gender.toUpperCase().matches("[M|F]")) {
                studentToEdit.setGender(gender);
                break;
            }
            else
                System.out.println("That's not a valid gender. Please try again.");
        }

        while (true) {
            oldDateOfBirth = studentToEdit.getDateOfBirth();
            System.out.printf("Enter the edited student's date of birth (%s): ", oldDateOfBirth);
            dateOfBirth = input.nextLine();

            if (dateOfBirth.toUpperCase().matches("^(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])-(19|20)\\d{2}$"))
                break;
            else
                System.out.println("That's not a valid date of birth. Please try again.");
        }

        while (true) {
            oldAddress = studentToEdit.getAddress();
            System.out.printf("Enter the edited student's address (%s): ", oldAddress);
            address = input.nextLine();

            if (address.matches("^[A-Za-z0-9-.,()'\\/& ]+( [A-Za-z0-9-.,()'\\/& ]+)*\\s\\/\\s[A-Za-z0-9-.,()'\\/& ]+( [A-Za-z0-9-.,()'\\/& ]+)*$")) {
                studentToEdit.setAddress(address);
                break;
            }
            else
                System.out.println("That's not a valid address. Please try again.");
        }


        while (true) {
            oldPhone = studentToEdit.getPhone();
            System.out.printf("Enter the edited student's phone number (%s): ", oldPhone);
            phone = input.nextLine();

            if (phone.matches("^09\\d{9}$")) {
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

            if (email.matches("\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\\b")) {
                studentToEdit.setEmail(email);
                break;
            }
            else
                System.out.println("That's not a valid email. Please try again.");
        }
        
        while (true) {
            olddateOfEnrollment = studentToEdit.getDateOfEnrollment();
            System.out.printf("Enter the student's date of enrollment (%s): ", olddateOfEnrollment);
            dateOfEnrollment = input.nextLine();

            if (dateOfEnrollment.toUpperCase().matches("^(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])-(19|20)\\d{2}$"))
                break;
            else
                System.out.println("That's not a valid date of enrollment. Please try again.");
        }

        while (true) {
            System.out.print("\nAre you sure you want to edit this student? (\"YES\" or \"NO\"): ");
            String choice = input.nextLine().toUpperCase();

            if (choice.equals("YES")) {
                studentToEdit.setLastName(lastName);
                studentToEdit.setFirstName(firstName);
                studentToEdit.setMiddleName(middleName);
                studentToEdit.setCourse(course);
                studentToEdit.setGender(gender);
                studentToEdit.setDateOfBirth(dateOfBirth);
                studentToEdit.setAddress(address);
                studentToEdit.setPhone(phone);
                studentToEdit.setEmail(email);
                studentToEdit.setDateOfEnrollment(dateOfEnrollment);
            
                saveData();
                break;
            }
            else if (choice.equals("NO"))
                break;
            else {
                exception();
                continue;
            }
        }  
    }

    public static void delete_this_student(String studentID, String name) throws Exception {
        clrscr();

        while (true) {
            clrscr();
            int indexToDelete = -1;
    
            for (int i = 1; i < students.size(); i++) {
                if (students.get(i).getID().equals(studentID)) {
                    indexToDelete = i;
                    students.remove(indexToDelete);
                    break;
                }
            }
            
            if (!(indexToDelete != -1)) {
                System.out.print("Student not found. Please try again.");
                input.nextLine();
                show_students();
            }
    
            while (true) {
                System.out.print("Are you sure you want to delete this student? (\"YES\" or \"NO\"): ");
                String choice = input.nextLine().toUpperCase();
    
                if (choice.equals("YES")) {
                    saveData();
                    System.out.println("Student deleted successfully.\n");
                    break;
                }
                else if (choice.equals("NO"))
                    break;
                else {
                    exception();
                    continue;
                }
            }
            break;
        }
        options(studentID, name);;
    }

    public static void show_student_subjects(String studentID, String name) throws IOException {
        clrscr();

        BufferedReader br = new BufferedReader(new FileReader("StudentSubjects.csv"));
    
        System.out.printf("Student ID: %s\nSubjects of: %s\n", studentID, name);
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

        String line;
        int count = 0;
        while ((line = br.readLine()) != null) {
            String[] values = line.split(",");
            if (count == 0) {
                System.out.printf("|   #   | %-15s | %-70s | %-30s | %-15s | %-15s | %-15s | %-15s |\n", values[1], values[2], values[3], values[4], values[5], values[6], values[7]);
                System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                count++;
            }
            if (values[0].equals(studentID)) {
                System.out.printf("| %5d | %-15s | %-70s | %-30s | %-15s | %-15s | %-15s | %-15s |\n", count, values[1], values[2], values[3], values[4], values[5], values[6], values[7]);
                System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                count++;
            }
        }
        br.close();
        
        System.out.println("\n==========================================================================");
        System.out.println(" [1] ADD SUBJECTS - Add subjects to this student");
        System.out.println(" [2] ENTER GRADES - Enter the grades in a specific subject of this student");
        System.out.println(" [3] BACK - Go back to the previous menu");
        System.out.println("==========================================================================");

        try {
            System.out.print("\nChoice: ");
            int menu = input.nextInt();
            input.nextLine();

            while (menu != -1) {
                if (menu == 1) {
                    add_student_subjects(studentID, name);
                }
                if (menu == 2) {
                    enter_grades(studentID, name);
                }
                if (menu == 3) {
                    options(studentID, name);;
                }
                else {
                    menu = 0;
                    exception();
                    return;
                }
            }
        }
        
        catch (Exception e) {
            exception();
            input.nextLine();
            show_student_subjects(studentID, name);
        }
        System.out.print("Press enter key to continue.");
        input.nextLine();
        show_student_subjects(studentID, name);
    }

    public static void enter_grades(String studentID, String name) throws IOException{
        clrscr();
        BufferedReader br = new BufferedReader(new FileReader("StudentSubjects.csv"));
    
        System.out.printf("Student ID: %s\nSubjects of: %s\n", studentID, name);
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("|   #   | %-15s | %-70s | %-30s | %-15s | %-15s | %-15s | %-15s |\n", "Subject Code", "Subject Name", "Instructor", "Prelims Grade", "Midterms Grade", "Finals Grade", "Final Rating");
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

        String line;
        int count = 1;
        br.readLine();
        while ((line = br.readLine()) != null) {
            String[] values = line.split(",");
            studentSubjects.add(new StudentSubject(values[0], values[1], values[2], values[3], values[4], values[5], values[6], values[7]));

            if (values[0].equals(studentID)) {
                System.out.printf("| %5d | %-15s | %-70s | %-30s | %-15s | %-15s | %-15s | %-15s |\n", count, values[1], values[2], values[3], values[4], values[5], values[6], values[7]);
                System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                count++;
            }
        }
        br.close();

        br = new BufferedReader(new FileReader("StudentSubjects.csv"));
        String code = null, subject = null, instructor = null;
        while (true) {
            System.out.print("Enter the subject code to enter grades or '0' to go back: ");
            code = input.nextLine().toUpperCase();

            if (code.equals("0"))
                show_student_subjects(studentID, name);

            String lines;
            while ((lines = br.readLine()) != null) {
                String[] values = lines.split(",");
                if (studentID.equals(values[0]) && code.equals(values[1])){
                    subject = values[2];
                    instructor = values[3];
                }
            }
            break;
        }
        br.close();

        if (subject == null || instructor == null)
            show_student_subjects(studentID, name);

        clrscr();
        System.out.println("Subject Code: " + code);
        System.out.println("Subject Name: " + subject);
        System.out.println("Subject Instructor: " + instructor);

        String prelims = null, midterms = null, finals = null;
        String gradePattern = "^(?:100|[6-9]\\d|\\d{2}|[1-5]\\d(?:\\.\\d{1,2})?|60(?:\\.0{1,2})?|0\\.\\d{1,2})$";
        while (true) {
            System.out.print("\nEnter prelims grade (Enter '0' if none): ");
            prelims = input.nextLine();
            
            if (prelims.matches(gradePattern))
                break;
            System.out.println("Invalid grade. Please try again.");
        }

        while (true) {
            System.out.print("Enter midterms grade (Enter '0' if none): ");
            midterms = input.nextLine();
            
            if (prelims.matches(gradePattern))
                break;
            System.out.println("Invalid grade. Please try again.");
        }

        while (true) {
            System.out.print("Enter finals grade (Enter '0' if none): ");
            finals = input.nextLine();
            
            if (prelims.matches(gradePattern))
                break;
            System.out.println("Invalid grade. Please try again.");
        }

        String finalRating = Double.toString(Double.parseDouble(prelims) * 0.3 + Double.parseDouble(midterms) * 0.3 + Double.parseDouble(finals) * 0.4);

        BufferedWriter bw = new BufferedWriter(new FileWriter("StudentSubjects.csv"));
        bw.write("ID,Subject Code,Subject Name,Instructor,Prelims Grade,Midterms Grade,Finals Grade,Final Rating");
        bw.newLine();
        for (int i = 0; i < studentSubjects.size(); i++) {
            if (studentSubjects.get(i).getID().equals(studentID) && studentSubjects.get(i).getCode().equals(code)){
                studentSubjects.get(i).setPrelims(prelims);
                studentSubjects.get(i).setMidterms(midterms);
                studentSubjects.get(i).setFinals(finals);
                studentSubjects.get(i).setFinalsRating(finalRating);
            }
            bw.write(studentSubjects.get(i).getID() + "," + studentSubjects.get(i).getCode() + "," + studentSubjects.get(i).getName() + "," + studentSubjects.get(i).getInstructor() + "," + studentSubjects.get(i).getPrelims() + "," + studentSubjects.get(i).getMidterms() +  "," + studentSubjects.get(i).getFinals() +  "," + studentSubjects.get(i).getFinalRating());
            bw.newLine();
        }
        bw.close();

        System.out.println("\nGrade successfully updated!");

        show_student_subjects(studentID, name);
    }

    public static void add_student_subjects(String studentID, String name) throws IOException {
        clrscr();
        BufferedReader br = new BufferedReader(new FileReader("StudentSubjects.csv"));

        while (true) {
            clrscr();
            System.out.print("ADD A SUBJECT TO THIS STUDENT\nEnter the name of the subject: ");
            String subject = input.nextLine();

            String number;
            while (true) {
                System.out.print("Enter the course number of the subject (Enter '0' if none): ");
                number = input.nextLine();

                if (number.matches("[/^\\d$/]"))
                    break;
            }

            System.out.print("Enter the code of the subject: ");
            String code = input.nextLine();

            String instructor;
            while (true) {
                System.out.print("Enter the instructor's name of the subject: ");
                instructor = input.nextLine();

                if (instructor.matches("^[A-Za-z]+([ ]+[A-Za-z]+)* [A-Za-z]+([ ]+[A-Za-z]+)*$"))
                    break;
                System.out.println("Invalid name. Please try again.");
            }   

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
            String finalCode = null, finalSubject = null;
            if (number == "0") {
                finalCode = code;
                finalSubject = subject;
            }
            else {
                finalCode = code + number;
                finalSubject = subject + " " + number;
            }
            
            System.out.println("Subject name: " + finalSubject);
            System.out.println("Subject code: " + finalCode);
            System.out.println("Subject instructor: " + instructor);

            System.out.print("\nPress enter key to confirm.");
            input.nextLine();

            double prelims = 0.0, midterms = 0.0, finals = 0.0, final_rating = 0.0;
            BufferedWriter bw = new BufferedWriter(new FileWriter("StudentSubjects.csv", true));
            bw.write(studentID + "," + finalCode + "," + finalSubject + "," + instructor + "," + prelims + "," + midterms +  "," + finals +  "," + final_rating);
            bw.newLine();
            bw.close();

            System.out.println("\nSubject successfully added!");

            boolean add = false;
            while (true) {
                System.out.print("Do you want to add another subject? (\"YES\" or \"NO\"): ");
                String choice = input.nextLine().toUpperCase();

                if (choice.equals("YES")) {
                    add = true;
                    break;
                }
                else if (choice.equals("NO"))
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
        show_student_subjects(studentID, name);
    }

    public static void options(String studentID, String name) {
        clrscr();
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("| %-7s | %-15s | %-15s | %-15s | %-20s | %-6s | %-15s | %-40s | %-15s | %-30s | %-20s |\n", "ID", "Last Name", "First Name", "Middle Name", "Course", "Gender", "Date of Birth", "Address", "Phone", "Email", "Date of Enrollment");
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("| %-7s | %-15s | %-15s | %-15s | %-20s | %-6s | %-15s | %-40s | %-15s | %-30s | %-20s |\n", studentToEdit.getID(), studentToEdit.getLastName(), studentToEdit.getFirstName(), studentToEdit.getMiddleName(), studentToEdit.getCourse(), studentToEdit.getGender(), studentToEdit.getDateOfBirth(), studentToEdit.getAddress(), studentToEdit.getPhone(), studentToEdit.getEmail(), studentToEdit.getDateOfEnrollment());
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

        System.out.println("\n=====================================================================================");
        System.out.println(" [1] EDIT STUDENT'S PERSONAL INFORMATIONS - Edit this student's personal informations");
        System.out.println(" [2] DELETE THIS STUDENT - Deletes this student permanently");
        System.out.println(" [3] SHOW STUDENT'S SUBJECTS - Shows the subjects of this student");
        System.out.println(" [4] BACK - Go back to the previous menu");
        System.out.println("=====================================================================================");

        try {
            System.out.print("\nChoice: ");
            int menu = input.nextInt();
            input.nextLine();

            while (menu != -1) {
                if (menu == 1) {
                    edit_details(studentToEdit);
                    return;
                }
                else if (menu == 2) {
                    delete_this_student(studentID, name);
                    return;
                }
                else if (menu == 3) {
                    show_student_subjects(studentID, name);
                    return;
                }
                else if (menu == 4)
                    edit_student();
                else {
                    menu = 0;
                    exception();
                    return;
                }
            }
        } 
        catch (Exception e) {
            exception();
            input.nextLine();
            return;
        }
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
    // ID,Last Name,First Name,Middle Name,Course,Gender,Date of Birth,Address,Phone,Email,Date of Enrollment
    private String id, lastName, firstName, middleName, course, gender, dateOfBirth, address, phone, email, dateOfEnrollment;

    public Student (String id, String lastName, String firstName, String middleName, String course, String gender, String dateOfBirth, String address, String phone, String email, String dateOfEnrollment) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.course = course;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.dateOfEnrollment = dateOfEnrollment;
    }

    public String getID() {return id;}
    public String getLastName() {return lastName;}
    public String getFirstName() {return firstName;}
    public String getMiddleName() {return middleName;}
    public String getCourse() {return course;}
    public String getGender() {return gender;}
    public String getDateOfBirth() {return dateOfBirth;}
    public String getAddress() {return address;}
    public String getPhone() {return phone;}
    public String getEmail() {return email;}
    public String getDateOfEnrollment() {return dateOfEnrollment;}

    public void setID(String id) {this.id = id;}
    public void setLastName(String lastName) {this.lastName = lastName;}
    public void setFirstName(String firstName) {this.firstName = firstName;}
    public void setMiddleName(String middleName) {this.middleName = middleName;}
    public void setCourse(String course) {this.course = course;}
    public void setGender(String gender) {this.gender = gender;}
    public void setDateOfBirth(String dateOfBirth) {this.dateOfBirth = dateOfBirth;}
    public void setAddress(String address) {this.address = address;}
    public void setPhone(String phone) {this.phone = phone;}
    public void setEmail(String email) {this.email = email;}
    public void setDateOfEnrollment(String dateOfEnrollment) {this.dateOfEnrollment = dateOfEnrollment;}
}

class Subject {
    private String code, name;

    public Subject (String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {return code;}
    public String getName() {return name;}
}

class StudentSubject {
    private String id, code, name, instructor, prelims, midterms, finals, finalRating;

    public StudentSubject (String id, String code, String name, String instructor, String prelims, String midterms, String finals, String finalRating) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.instructor = instructor;
        this.prelims = prelims;
        this.midterms = midterms;
        this.finals = finals;
        this.finalRating = Double.toString(Double.parseDouble(this.prelims) * 0.3 + Double.parseDouble(this.midterms) * 0.3 + Double.parseDouble(this.finals) * 0.4);
    }
    public String getID() {return id;}
    public String getCode() {return code;}
    public String getName() {return name;}
    public String getInstructor() {return instructor;}
    public String getPrelims() {return prelims;}
    public String getMidterms() {return midterms;}
    public String getFinals() {return finals;}
    public String getFinalRating() {return finalRating;}
    
    public void setPrelims(String prelims) {this.prelims = prelims;}
    public void setMidterms(String midterms) {this.midterms = midterms;}
    public void setFinals(String finals) {this.finals = finals;}
    public void setFinalsRating(String finalRating) {this.finalRating = finalRating;}
}