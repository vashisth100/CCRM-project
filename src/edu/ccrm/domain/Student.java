package edu.ccrm.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;


public class Student extends Person {
    private String regNo;
    private List<String> enrolledCourses; 
    
    private Map<String, TranscriptEntry> transcript = new HashMap<>();
 
   //student details
    public Student(String id, String regNo, String fullName, String email) {
        super(id, fullName, email);
        this.regNo = regNo;
        this.enrolledCourses = new ArrayList<>();
    }

    public String getRegNo() { return regNo; }
    public List<String> getEnrolledCourses() { return enrolledCourses; }

    public Map<String, TranscriptEntry> getTranscript() { return transcript; }

    public void addTranscriptEntry(String courseCode, int marks, Grade grade) {
        transcript.put(courseCode, new TranscriptEntry(courseCode, marks, grade));
    }

    public void enroll(String courseCode) {
        if (!enrolledCourses.contains(courseCode))
            enrolledCourses.add(courseCode);
    }
    public void unenroll(String courseCode) {
        enrolledCourses.remove(courseCode);
    }

    @Override
    public String getProfile() {
        return "Student [" + super.toString() + ", RegNo: " + regNo + ", Courses: " + enrolledCourses + "]";
    }
}
