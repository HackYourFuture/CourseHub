package net.hackyourfuture.coursehub.web;

import net.hackyourfuture.coursehub.repository.CourseRepository;
import net.hackyourfuture.coursehub.web.model.CourseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CourseController {

    private final CourseRepository courseRepository;

    public CourseController(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @GetMapping("/courses")
    public List<CourseDto> getAllCourses() {
        return courseRepository.findAll()
                .stream()
                .map(c -> new CourseDto())
                .toList();
    }
}
