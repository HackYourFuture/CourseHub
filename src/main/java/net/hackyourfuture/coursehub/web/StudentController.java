package net.hackyourfuture.coursehub.web;

import jakarta.validation.constraints.Positive;
import net.hackyourfuture.coursehub.data.AuthenticatedUser;
import net.hackyourfuture.coursehub.service.CourseService;
import net.hackyourfuture.coursehub.web.model.CourseListResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final CourseService courseService;

    public StudentController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/{studentId}/courses")
    public CourseListResponse getCoursesForStudent(
            @PathVariable @Positive Integer studentId, @AuthenticationPrincipal AuthenticatedUser user) {
        if (!user.getUserId().equals(studentId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        var courses = courseService.getCoursesForStudent(studentId);
        return new CourseListResponse(courses);
    }
}
