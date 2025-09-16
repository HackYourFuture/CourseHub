package net.hackyourfuture.coursehub.web;

import net.hackyourfuture.coursehub.web.model.CourseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CourseController {

    @GetMapping("/courses")
    public List<CourseDto> getAllCourses() {
        return List.of();
    }
}
