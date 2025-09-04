package edu.ccrm.service;

import edu.ccrm.domain.Student;
import java.util.*;

public class StudentService {
    // Map of regNo (or id) to Student object
    private Map<String, Student> studentMap = new HashMap<>();

    public void addStudent(Student student) {
        if (studentMap.containsKey(student.getRegNo())) {
            System.out.println("Student already exists: " + student.getRegNo());
        } else {
            studentMap.put(student.getRegNo(), student);
            System.out.println("Student added: " + student.getRegNo());
        }
    }

    public Student getStudent(String regNo) {
        return studentMap.get(regNo);
    }

    public List<Student> listStudents() {
        return new ArrayList<>(studentMap.values());
    }

    public void deactivateStudent(String regNo) {
        Student s = studentMap.get(regNo);
        if (s != null) {
            s.setActive(false);
            System.out.println("Student deactivated: " + regNo);
        }
    }
}
