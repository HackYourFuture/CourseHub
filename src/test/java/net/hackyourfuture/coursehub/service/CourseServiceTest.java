package net.hackyourfuture.coursehub.service;

import net.hackyourfuture.coursehub.data.CourseEntity;
import net.hackyourfuture.coursehub.data.InstructorEntity;
import net.hackyourfuture.coursehub.repository.CourseRepository;
import net.hackyourfuture.coursehub.repository.InstructorRepository;
import net.hackyourfuture.coursehub.web.model.CourseDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

/**
 * This is a unit test for the CourseService class. We mock the repositories to return specific data, and verify
 * that the CourseService class combines the data correctly.
 */
@ExtendWith(MockitoExtension.class)
class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;
    @Mock
    private InstructorRepository instructorRepository;

    @Test
    void shouldReturnCoursesWithInstructorNames() {
        CourseService courseService = new CourseService(courseRepository, instructorRepository);

        when(courseRepository.findAll()).thenReturn(List.of(
            new CourseEntity(1L, "Testing course", "A course about testing", 1L, LocalDate.of(2026, Month.JANUARY, 15), LocalDate.of(2026, Month.MARCH, 1)),
            new CourseEntity(2L, "Spring course", "A course about using Spring Boot", 2L, LocalDate.of(2026, Month.MAY, 1), LocalDate.of(2026, Month.SEPTEMBER, 1))
        ));
        when(instructorRepository.findById(1L))
                .thenReturn(new InstructorEntity(1L, "Alice", "Smith", "alice1@example.com"));
        when(instructorRepository.findById(2L))
                .thenReturn(new InstructorEntity(1L, "Bob", "Johnson", "bob2@example.com"));

        var courses = courseService.getAllCourses();

        assertThat(courses)
                .hasSize(2)
                .contains(new CourseDto("Testing course", "A course about testing", "Alice Smith"))
                .contains(new CourseDto("Spring course", "A course about using Spring Boot", "Bob Johnson"));
    }

    @Test
    void shouldFailIfCourseExistsWithoutAnInstructor() {
        CourseService courseService = new CourseService(courseRepository, instructorRepository);

        when(courseRepository.findAll()).thenReturn(List.of(
                new CourseEntity(1L, "Testing course", "A course about testing", 1L, LocalDate.of(2026, Month.JANUARY, 15), LocalDate.of(2026, Month.MARCH, 1))
        ));
        when(instructorRepository.findById(1L)).thenReturn(null);

        assertThatThrownBy(courseService::getAllCourses)
                .hasMessage("Instructor with id 1 not found");
    }

    @Test
    void shouldFailIfRepositoryThrowsException() {
        CourseService courseService = new CourseService(courseRepository, instructorRepository);

        when(courseRepository.findAll()).thenThrow(new RuntimeException("Database is down"));

        assertThatThrownBy(courseService::getAllCourses)
                .hasMessage("Database is down");
    }
}
