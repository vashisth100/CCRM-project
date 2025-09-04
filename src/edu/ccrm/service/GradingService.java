package edu.ccrm.service;

import edu.ccrm.domain.*;

public class GradingService {
    public static Grade calculateGrade(int marks) {
        if (marks >= 90) return Grade.S;
        else if (marks >= 80) return Grade.A;
        else if (marks >= 70) return Grade.B;
        else if (marks >= 60) return Grade.C;
        else if (marks >= 50) return Grade.D;
        else if (marks >= 40) return Grade.E;
        else return Grade.F;
    }

    public void assignMarks(Student s, String courseCode, int marks) {
        Grade grade = calculateGrade(marks);
        s.addTranscriptEntry(courseCode, marks, grade);
    }

    public double computeGPA(Student s) {
        if (s.getTranscript().isEmpty()) return 0.0;
        double totalPoints = 0;
        int courses = 0;
        for (TranscriptEntry te : s.getTranscript().values()) {
            totalPoints += te.getGrade().getPoints();
            courses++;
        }
        return (courses == 0) ? 0.0 : totalPoints / courses;
    }
}
