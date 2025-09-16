package net.hackyourfuture.coursehub.service;

import net.hackyourfuture.coursehub.repository.CourseRepository;
import net.hackyourfuture.coursehub.service.model.Course;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll()
                .stream()
                // TODO: add instructor name from repository
                .map(c -> new Course(c.name(), c.description(), "unknown"))
                .toList();
    }
}

