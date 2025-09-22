package edu.ccrm.service;

import edu.ccrm.domain.Course;
import edu.ccrm.exception.DuplicateCourseException;
import java.util.*;

public class CourseService {
    private Map<String, Course> courses = new HashMap<>();

    // Adds a new course. Throws if code already exists
    public void addCourse(Course c) throws DuplicateCourseException {
        if (courses.containsKey(c.getCode()))
            throw new DuplicateCourseException("Course already exists with code: " + c.getCode());
        courses.put(c.getCode(), c);
    }

    // Returns all courses as a List
    public List<Course> listCourses() {
        return new ArrayList<>(courses.values());
    }

    // Returns the course object for the given code (null if not present)
    public Course getCourse(String code) {
        return courses.get(code);
    }

    // Optional: Edit Course info
    public boolean editCourse(String code, String newTitle, int newCredits) {
        Course c = courses.get(code);
        if (c != null) {
            return true;
        } else {
            return false;
        }
    }

    // Optional: Delete Course by code
    public boolean deleteCourse(String code) {
        return courses.remove(code) != null;
    }

    // Optional: Search by title
    public List<Course> searchCoursesByTitle(String title) {
        List<Course> results = new ArrayList<>();
        for (Course c : courses.values()) {
            if (c.getTitle().toLowerCase().contains(title.toLowerCase()))
                results.add(c);
        }
        return results;
    }
}
