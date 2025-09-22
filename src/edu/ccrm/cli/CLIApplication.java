package edu.ccrm.cli;
import java.util.List;

//student 
import edu.ccrm.service.StudentService;
import edu.ccrm.domain.Student;

//courses
import edu.ccrm.service.CourseService;
import edu.ccrm.domain.Course;
import edu.ccrm.domain.Semester;
import edu.ccrm.service.EnrollmentService;

//grades
import edu.ccrm.service.GradingService;
import edu.ccrm.domain.TranscriptEntry;

//io
import edu.ccrm.io.ImportExportService;
import java.io.IOException;

//utility
import edu.ccrm.util.BackupUtility;
import java.nio.file.Paths;

import java.util.Scanner;

public class CLIApplication {
    private StudentService studentService = new StudentService();
    private CourseService courseService = new CourseService();
    private EnrollmentService enrollmentService = new EnrollmentService();
    private GradingService gradingService = new GradingService();
    private ImportExportService importExportService = new ImportExportService();

    private Scanner sc = new Scanner(System.in);

    public void run() {
        boolean running = true;
        while (running) {
            System.out.println("==== Main Menu ====");
            System.out.println("1. Add Student");
            System.out.println("2. List Students");
            System.out.println("3. Add Course");
            System.out.println("4. List Courses");
            System.out.println("5. Enroll Student in Course");
            System.out.println("6. View Student Enrollments");
            System.out.println("7. Assign Grade/Marks");
            System.out.println("8. View Student Transcript");
            System.out.println("9. Import Students from CSV");
            System.out.println("10. Export Students to CSV");
            System.out.println("11. Import Courses from CSV");
            System.out.println("12. Export Courses to CSV");
            System.out.println("13. Backup Data Directory");
            System.out.println("14. Edit Student");
            System.out.println("15. Delete Student");
            System.out.println("16. Search Student by Name");
            System.out.println("17. Edit Course");
            System.out.println("18. Delete Course");
            System.out.println("19. Search Course by Title");
            System.out.println("0. Exit");
            System.out.print("Select: ");
            int choice = sc.nextInt();
            sc.nextLine(); 

            switch (choice) {
                case 0: running = false; break;
                case 1: addStudentMenu(); break;
                case 2: listStudentsMenu(); break;
                case 3: addCourseMenu(); break;
                case 4: listCoursesMenu(); break;
                case 5: enrollStudentMenu(); break;
                case 6: viewStudentEnrollmentsMenu(); break;
                case 7: assignGradeMenu(); break;
                case 8: viewTranscriptMenu(); break;
                case 9: importStudentsMenu(); break;
                case 10: exportStudentsMenu(); break;
                case 11: importCoursesMenu(); break;
                case 12: exportCoursesMenu(); break;
                case 13: backupDataMenu(); break;
                case 14: editStudentMenu(); break;
                case 15: deleteStudentMenu(); break;
                case 16: searchStudentMenu(); break;
                case 17: editCourseMenu(); break;
                case 18: deleteCourseMenu(); break;
                case 19: searchCourseMenu(); break;
                default: System.out.println("Invalid option.");
            }
        }
    }

    private void addStudentMenu() {
    System.out.print("Enter ID: ");
    String id = sc.nextLine();
    String regNo = sc.nextLine();
    System.out.print("Enter name: ");
    String name = sc.nextLine();
    System.out.print("Enter email: ");
    String email = sc.nextLine();

    Student s = new Student(id, regNo, name, email);
    try {
        studentService.addStudent(s);
        System.out.println("Student added.");
    } catch (edu.ccrm.exception.DuplicateStudentException e) {
        System.out.println(e.getMessage());
    }
    }


    private void listStudentsMenu() {
        System.out.println("=== All Students ===");
        for (Student s : studentService.listStudents()) {
            System.out.println(s.getProfile());
        }
    }

    private void addCourseMenu() {
    System.out.print("Enter code: ");
    String code = sc.nextLine();
    System.out.print("Enter title: ");
    String title = sc.nextLine();
    System.out.print("Enter credits (integer): ");
    int credits = sc.nextInt();
    sc.nextLine();
    System.out.print("Enter instructorId (optional): ");
    String instructorId = sc.nextLine();
    System.out.print("Enter semester (SPRING, SUMMER, FALL): ");
    String semStr = sc.nextLine().toUpperCase();
    Semester semester = Semester.valueOf(semStr);
    System.out.print("Enter department: ");
    String department = sc.nextLine();

    // Use the builder instead of constructor
    Course c = new Course.CourseBuilder(code)
        .title(title)
        .credits(credits)
        .instructorId(instructorId)
        .semester(semester)
        .department(department)
        .build();

        try {
        courseService.addCourse(c);
        System.out.println("Course added.");
        } catch (edu.ccrm.exception.DuplicateCourseException e) {
        System.out.println(e.getMessage());
        }

    }

    private void listCoursesMenu() {
        System.out.println("=== All Courses ===");
        for (Course c : courseService.listCourses()) {
            System.out.println(c);
        }
    }

    private void enrollStudentMenu() {
        System.out.print("Enter Student RegNo: ");
        String regNo = sc.nextLine();
        System.out.print("Enter Course Code: ");
        String courseCode = sc.nextLine();

        Student s = studentService.getStudent(regNo);
        Course c = courseService.listCourses().stream()
                .filter(course -> course.getCode().equals(courseCode))
                .findFirst().orElse(null);

        if (s == null) {
            System.out.println("Student not found: " + regNo);
        } else if (c == null) {
            System.out.println("Course not found: " + courseCode);
        } else {
            boolean enrolled = enrollmentService.enrollStudentInCourse(s, c);
            if (enrolled) {
                System.out.println("Enrollment successful!");
            } else {
                System.out.println("Enrollment failed.");
            }
        }
    }

    private void viewStudentEnrollmentsMenu() {
        System.out.print("Enter Student RegNo: ");
        String regNo = sc.nextLine();

        Student s = studentService.getStudent(regNo);
        if (s == null) {
            System.out.println("Student not found.");
        } else {
            System.out.println(s.getProfile());
        }
    }

    private void assignGradeMenu() {
    System.out.print("Enter Student RegNo: ");
    String regNo = sc.nextLine();
    System.out.print("Enter Course Code: ");
    String courseCode = sc.nextLine();
    System.out.print("Enter Marks (0-100): ");
    int marks = sc.nextInt(); sc.nextLine();

    Student s = studentService.getStudent(regNo);
    if (s == null) {
        System.out.println("Student not found: " + regNo);
        return;
    }
    if (!s.getEnrolledCourses().contains(courseCode)) {
        System.out.println("Student is not enrolled in this course.");
        return;
    }
    gradingService.assignMarks(s, courseCode, marks);
    System.out.println("Marks and grade assigned.");
    }

    private void viewTranscriptMenu() {
    System.out.print("Enter Student RegNo: ");
    String regNo = sc.nextLine();

    Student s = studentService.getStudent(regNo);
    if (s == null) {
        System.out.println("Student not found.");
    } else {
        System.out.println("=== Transcript for " + s.getFullName() + " ===");
        for (TranscriptEntry te : s.getTranscript().values()) {
            System.out.println(te);
        }
        double gpa = gradingService.computeGPA(s);
        System.out.printf("GPA: %.2f\n", gpa);
    }
    }

    private void importStudentsMenu() {
    System.out.print("Enter file path for CSV: ");
    String path = sc.nextLine();
    try {
        int count = importExportService.importStudents(path, studentService);
        System.out.println("Imported " + count + " students.");
    } catch (IOException e) {
        System.out.println("Failed to import students: " + e.getMessage());
    }
    }

    private void exportStudentsMenu() {
    System.out.print("Enter file path to save CSV: ");
    String path = sc.nextLine();
    try {
        importExportService.exportStudents(path, studentService);
        System.out.println("Exported students.");
    } catch (IOException e) {
        System.out.println("Failed to export students: " + e.getMessage());
    }
    }
    
    private void importCoursesMenu() {
    System.out.print("Enter file path for courses CSV: ");
    String path = sc.nextLine();
    try {
        int count = importExportService.importCourses(path, courseService);
        System.out.println("Imported " + count + " courses.");
    } catch (IOException e) {
        System.out.println("Failed to import courses: " + e.getMessage());
    }
    }

    private void exportCoursesMenu() {
    System.out.print("Enter file path to save courses CSV: ");
    String path = sc.nextLine();
    try {
        importExportService.exportCourses(path, courseService);
        System.out.println("Exported courses.");
    } catch (IOException e) {
        System.out.println("Failed to export courses: " + e.getMessage());
    }
    }

    private void backupDataMenu() {
    System.out.print("Enter source directory to backup: ");
    String source = sc.nextLine();
    System.out.print("Enter backup target directory: ");
    String dest = sc.nextLine();
    try {
        BackupUtility.backupDirectory(Paths.get(source), Paths.get(dest));
        System.out.println("Backup complete!");
    } catch (Exception e) {
        System.out.println("Backup failed: " + e.getMessage());
    }
    }

    private void editStudentMenu() {
    System.out.print("Enter RegNo: ");
    String regNo = sc.nextLine();
    System.out.print("Enter new name: ");
    String name = sc.nextLine();
    System.out.print("Enter new email: ");
    String email = sc.nextLine();
    boolean ok = studentService.editStudent(regNo, name, email);
    System.out.println(ok ? "Student updated." : "Student not found.");
    }

    private void deleteStudentMenu() {
    System.out.print("Enter RegNo: ");
    String regNo = sc.nextLine();
    boolean ok = studentService.deleteStudent(regNo);
    System.out.println(ok ? "Student deleted." : "Student not found.");
    }

    private void searchStudentMenu() {
    System.out.print("Enter name to search: ");
    String name = sc.nextLine();
    List<Student> results = studentService.searchStudentsByName(name);
    results.forEach(s -> System.out.println(s.getProfile()));
    }

    private void editCourseMenu() {
    System.out.print("Enter Course Code: ");
    String code = sc.nextLine();
    System.out.print("Enter new title: ");
    String title = sc.nextLine();
    System.out.print("Enter new credits: ");
    int credits = sc.nextInt(); sc.nextLine();
    boolean ok = courseService.editCourse(code, title, credits);
    System.out.println(ok ? "Course updated." : "Course not found.");
    }

    private void deleteCourseMenu() {
    System.out.print("Enter Course Code: ");
    String code = sc.nextLine();
    boolean ok = courseService.deleteCourse(code);
    System.out.println(ok ? "Course deleted." : "Course not found.");
    }

    private void searchCourseMenu() {
    System.out.print("Enter title to search: ");
    String title = sc.nextLine();
    List<Course> results = courseService.searchCoursesByTitle(title);
    results.forEach(c -> System.out.println(c));
    }
}