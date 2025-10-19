package net.hackyourfuture.coursehub.web;

import net.hackyourfuture.coursehub.service.CourseService;
import net.hackyourfuture.coursehub.web.model.CourseListResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public CourseListResponse getAllCourses() {
        return new CourseListResponse(courseService.getAllCourses());
    }
}
