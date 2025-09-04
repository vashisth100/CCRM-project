package edu.ccrm.domain;

public class Course {
    private String code;
    private String title;
    private int credits;
    private String instructorId;
    private Semester semester;
    private String department;

    public Course(String code, String title, int credits, String instructorId, Semester semester, String department) {
        this.code = code;
        this.title = title;
        this.credits = credits;
        this.instructorId = instructorId;
        this.semester = semester;
        this.department = department;
    }

    //course details

    public String getCode() { return code; }
    public String getTitle() { return title; }
    public int getCredits() { return credits; }
    public String getInstructorId() { return instructorId; }
    public Semester getSemester() { return semester; }
    public String getDepartment() { return department; }

    public void setTitle(String title) { this.title = title; }
    public void setCredits(int credits) { this.credits = credits; }
    public void setInstructorId(String instructorId) { this.instructorId = instructorId; }
    public void setSemester(Semester semester) { this.semester = semester; }
    public void setDepartment(String department) { this.department = department; }

    @Override
    public String toString() {
        return String.format("Course [%s: %s, Credits: %d, Instructor: %s, Semester: %s, Dept: %s]",
            code, title, credits, instructorId, semester, department);
    }
}
