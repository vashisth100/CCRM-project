package edu.ccrm.cli;

import edu.ccrm.service.StudentService;
import edu.ccrm.domain.Student;
import edu.ccrm.service.CourseService;
import edu.ccrm.domain.Course;
import edu.ccrm.domain.Semester;
import edu.ccrm.service.EnrollmentService;
import edu.ccrm.service.GradingService;
import edu.ccrm.domain.TranscriptEntry;


import java.util.Scanner;

public class CLIApplication {
    private StudentService studentService = new StudentService();
    private CourseService courseService = new CourseService();
    private EnrollmentService enrollmentService = new EnrollmentService();
    private GradingService gradingService = new GradingService();

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
                default: System.out.println("Invalid option.");
            }
        }
    }

    private void addStudentMenu() {
        System.out.print("Enter ID: ");
        String id = sc.nextLine();
        System.out.print("Enter RegNo: ");
        String regNo = sc.nextLine();
        System.out.print("Enter name: ");
        String name = sc.nextLine();
        System.out.print("Enter email: ");
        String email = sc.nextLine();

        Student s = new Student(id, regNo, name, email);
        studentService.addStudent(s);
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
        sc.nextLine(); // Consume leftover newline
        System.out.print("Enter instructorId (optional): ");
        String instructorId = sc.nextLine();
        System.out.print("Enter semester (SPRING, SUMMER, FALL): ");
        String semStr = sc.nextLine().toUpperCase();
        Semester semester = Semester.valueOf(semStr);
        System.out.print("Enter department: ");
        String department = sc.nextLine();

        Course c = new Course(code, title, credits, instructorId, semester, department);
        courseService.addCourse(c);
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
    System.out.print("Enter Marks (0–100): ");
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

}
