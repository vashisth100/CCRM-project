package edu.ccrm.service;

import edu.ccrm.domain.Student;
import edu.ccrm.domain.Course;

public class EnrollmentService {
    public boolean enrollStudentInCourse(Student student, Course course) {
        if (student != null && course != null) {
            // Could add business rules (e.g., max credits) here
            student.enroll(course.getCode());
            return true;
        }
        return false;
    }

    public boolean unenrollStudentFromCourse(Student student, Course course) {
        if (student != null && course != null) {
            student.unenroll(course.getCode());
            return true;
        }
        return false;
    }
}
