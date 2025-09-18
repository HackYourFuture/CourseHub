package net.hackyourfuture.coursehub.web;

import net.hackyourfuture.coursehub.service.CourseService;
import net.hackyourfuture.coursehub.web.model.CourseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("students")
public class StudentController {

    private final CourseService courseService;

    public StudentController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/{studentId}/courses")
    public List<CourseDto> getCoursesForStudent(Integer studentId) {
        return courseService.getCoursesForStudent(studentId);
    }
}
