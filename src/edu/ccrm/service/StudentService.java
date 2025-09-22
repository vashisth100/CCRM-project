package edu.ccrm.service;

import edu.ccrm.domain.Student;
import edu.ccrm.exception.DuplicateStudentException;
import java.util.*;

public class StudentService {
    private Map<String, Student> students = new HashMap<>();

    // Adds a new student. Throws if regNo already exists
    public void addStudent(Student s) throws DuplicateStudentException {
        if (students.containsKey(s.getRegNo()))
            throw new DuplicateStudentException("Student already exists with regNo: " + s.getRegNo());
        students.put(s.getRegNo(), s);
    }

    // Returns the student object for the given regNo (null if not present)
    public Student getStudent(String regNo) {
        return students.get(regNo);
    }

    // Returns all students as a List
    public List<Student> listStudents() {
        return new ArrayList<>(students.values());
    }

    // Optional: Edit Student info
    public boolean editStudent(String regNo, String newName, String newEmail) {
        Student s = students.get(regNo);
        if (s != null) {
            
            return true;
        } else {
            return false;
        }
    }

    // Optional: Delete Student by regNo
    public boolean deleteStudent(String regNo) {
        return students.remove(regNo) != null;
    }

    // Optional: Search by name
    public List<Student> searchStudentsByName(String name) {
        List<Student> results = new ArrayList<>();
        for (Student s : students.values()) {
            if (s.getFullName().toLowerCase().contains(name.toLowerCase()))
                results.add(s);
        }
        return results;
    }
}
