package net.hackyourfuture.coursehub.service;

import net.hackyourfuture.coursehub.data.InstructorEntity;
import net.hackyourfuture.coursehub.repository.CourseRepository;
import net.hackyourfuture.coursehub.repository.InstructorRepository;
import net.hackyourfuture.coursehub.web.model.CourseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    private final CourseRepository courseRepository;
    private final InstructorRepository instructorRepository;

    public CourseService(CourseRepository courseRepository, InstructorRepository instructorRepository) {
        this.courseRepository = courseRepository;
        this.instructorRepository = instructorRepository;
    }

    public List<CourseDto> getAllCourses() {
        return courseRepository.findAll().stream()
                .map(c -> {
                    InstructorEntity instructor = instructorRepository.findById(c.instructorId());
                    String instructorName = instructor.firstName() + " " + instructor.lastName();
                    return new CourseDto(
                            c.name(), c.description(), instructorName, c.startDate(), c.endDate(), c.maxEnrollments());
                })
                .toList();
    }
}