package edu.ccrm.domain;

public class TranscriptEntry {
    private String courseCode;
    private int marks;
    private Grade grade;

    //transcript for grades
    public TranscriptEntry(String courseCode, int marks, Grade grade) {
        this.courseCode = courseCode;
        this.marks = marks;
        this.grade = grade;
    }

    public String getCourseCode() { return courseCode; }
    public int getMarks() { return marks; }
    public Grade getGrade() { return grade; }

    @Override
    public String toString() {
        return String.format("Course: %s, Marks: %d, Grade: %s", courseCode, marks, grade);
    }
}
