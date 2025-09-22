package edu.ccrm.domain;

public class Course {
    private String code;
    private String title;
    private int credits;
    private String instructorId;
    private Semester semester;
    private String department;

    // Private constructor used by the Builder
    private Course(CourseBuilder builder) {
        this.code = builder.code;
        this.title = builder.title;
        this.credits = builder.credits;
        this.instructorId = builder.instructorId;
        this.semester = builder.semester;
        this.department = builder.department;
    }

    // Getters
    public String getCode() { return code; }
    public String getTitle() { return title; }
    public int getCredits() { return credits; }
    public String getInstructorId() { return instructorId; }
    public Semester getSemester() { return semester; }
    public String getDepartment() { return department; }

    @Override
    public String toString() {
        return String.format("Course[%s: %s, Credits: %d, Instructor: %s, Sem: %s, Dept: %s]",
            code, title, credits, instructorId, semester, department);
    }

    // --- Inner static Builder class ---
    public static class CourseBuilder {
        private String code;
        private String title;
        private int credits;
        private String instructorId;
        private Semester semester;
        private String department;

        public CourseBuilder(String code) { this.code = code; }
        public CourseBuilder title(String title) { this.title = title; return this; }
        public CourseBuilder credits(int credits) { this.credits = credits; return this; }
        public CourseBuilder instructorId(String instructorId) { this.instructorId = instructorId; return this; }
        public CourseBuilder semester(Semester semester) { this.semester = semester; return this; }
        public CourseBuilder department(String department) { this.department = department; return this; }
        public Course build() { return new Course(this); }
    }
}
