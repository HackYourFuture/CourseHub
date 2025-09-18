package net.hackyourfuture.coursehub.web;

import jakarta.validation.constraints.Positive;
import net.hackyourfuture.coursehub.service.CourseService;
import net.hackyourfuture.coursehub.web.model.CourseListResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final CourseService courseService;

    public StudentController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/{studentId}/courses")
    public CourseListResponse getCoursesForStudent(@PathVariable @Positive Integer studentId) {
        var courses = courseService.getCoursesForStudent(studentId);
        return new CourseListResponse(courses);
    }
}
