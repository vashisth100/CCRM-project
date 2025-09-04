package edu.ccrm.service;

import edu.ccrm.domain.Course;
import java.util.*;

public class CourseService {
    private Map<String, Course> courseMap = new HashMap<>();

    public void addCourse(Course course) {
        if (courseMap.containsKey(course.getCode())) {
            System.out.println("Course already exists: " + course.getCode());
        } else {
            courseMap.put(course.getCode(), course);
            System.out.println("Course added: " + course.getCode());
        }
    }

    public List<Course> listCourses() {
        return new ArrayList<>(courseMap.values());
    }
}
