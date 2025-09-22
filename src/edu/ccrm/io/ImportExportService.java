package edu.ccrm.io;

import edu.ccrm.domain.*;
import edu.ccrm.service.*;

import java.nio.file.*;
import java.io.IOException;
import java.util.*;
import java.util.stream.*;

public class ImportExportService {

    // Imports students from a CSV with columns: id,regNo,fullName,email
    public int importStudents(String filePath, StudentService studentService) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filePath));
        int count = 0;
        for (String line : lines) {
            String[] parts = line.strip().split(",");
            if (parts.length >= 4) {
                Student s = new Student(parts[0], parts[1], parts[2], parts[3]);
                try {
                    studentService.addStudent(s);
                    count++;
                } catch (edu.ccrm.exception.DuplicateStudentException e) {
                    // You can optionally print:
                    // System.out.println("Duplicate student (skipped): " + s.getRegNo());
                }
            }
        }
        return count;
    }

    // Exports all current students as CSV
    public void exportStudents(String filePath, StudentService studentService) throws IOException {
        List<String> out = studentService.listStudents()
            .stream()
            .map(s -> s.getId() + "," + s.getRegNo() + "," + s.getFullName() + "," + s.getEmail())
            .collect(Collectors.toList());
        Files.write(Paths.get(filePath), out);
    }

    // Imports courses from CSV: code,title,credits,instructorId,semester,department
    public int importCourses(String filePath, CourseService courseService) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filePath));
        int count = 0;
        for (String line : lines) {
            String[] parts = line.strip().split(",");
            if (parts.length >= 6) {
                Course c = new Course.CourseBuilder(parts[0])
                    .title(parts[1])
                    .credits(Integer.parseInt(parts[2]))
                    .instructorId(parts[3])
                    .semester(Semester.valueOf(parts[4].toUpperCase()))
                    .department(parts[5])
                    .build();

                try {
                    courseService.addCourse(c);
                    count++;
                } catch (edu.ccrm.exception.DuplicateCourseException e) {
                    // You can optionally print:
                    // System.out.println("Duplicate course (skipped): " + c.getCode());
                }
            }
        }
        return count;
    }

    // Exports courses to CSV
    public void exportCourses(String filePath, CourseService courseService) throws IOException {
        List<String> out = courseService.listCourses()
            .stream()
            .map(c -> c.getCode() + "," + c.getTitle() + "," + c.getCredits() + "," +
                      c.getInstructorId() + "," + c.getSemester() + "," + c.getDepartment())
            .collect(Collectors.toList());
        Files.write(Paths.get(filePath), out);
    }
}
