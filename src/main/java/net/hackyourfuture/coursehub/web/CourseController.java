package net.hackyourfuture.coursehub.web;

import net.hackyourfuture.coursehub.service.CourseService;
import net.hackyourfuture.coursehub.web.model.CourseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/courses")
    public List<CourseDto> getAllCourses() {
        return courseService.getAllCourses()
                .stream()
                .map(c -> new CourseDto(c.name(), c.description(), c.instructor()))
                .toList();
    }
}
