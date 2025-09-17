package net.hackyourfuture.coursehub.web;

import net.hackyourfuture.coursehub.service.CourseService;
import net.hackyourfuture.coursehub.web.model.CourseDto;
import net.hackyourfuture.coursehub.web.model.CourseListResponse;
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
    public CourseListResponse getAllCourses() {
        return new CourseListResponse(courseService.getAllCourses());
    }
}
