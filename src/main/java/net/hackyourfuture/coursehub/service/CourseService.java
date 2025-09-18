package net.hackyourfuture.coursehub.service;

import net.hackyourfuture.coursehub.repository.CourseRepository;
import net.hackyourfuture.coursehub.web.model.CourseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    private final CourseRepository courseRepository;
    private final InstructorService instructorService;

    public CourseService(CourseRepository courseRepository, InstructorService instructorService) {
        this.courseRepository = courseRepository;
        this.instructorService = instructorService;
    }

    public List<CourseDto> getCoursesForStudent(Integer studentId) {

        return courseRepository.findAllByStudentId(studentId).stream()
                .map(c -> new CourseDto(
                        c.courseId(),
                        c.name(),
                        c.description(),
                        instructorService.getFullInstructorName(c.instructorId()),
                        c.startDate(),
                        c.endDate(),
                        c.maxEnrollments()))
                .toList();
    }

    public List<CourseDto> getAllCourses() {
        return courseRepository.findAll().stream()
                .map(c -> new CourseDto(
                        c.courseId(),
                        c.name(),
                        c.description(),
                        instructorService.getFullInstructorName(c.instructorId()),
                        c.startDate(),
                        c.endDate(),
                        c.maxEnrollments()))
                .toList();
    }
}
