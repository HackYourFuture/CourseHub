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
 * This is a unit test for the CourseService class. We mock the repositories to return specific data and verify
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

        when(courseRepository.findAll())
                .thenReturn(List.of(
                        new CourseEntity(
                                1,
                                "Testing course",
                                "A course about testing",
                                1,
                                LocalDate.of(2026, Month.JANUARY, 15),
                                LocalDate.of(2026, Month.MARCH, 1),
                                30),
                        new CourseEntity(
                                2,
                                "Spring course",
                                "A course about using Spring Boot",
                                2,
                                LocalDate.of(2026, Month.MAY, 1),
                                LocalDate.of(2026, Month.SEPTEMBER, 1),
                                50)));
        when(instructorRepository.findById(1))
                .thenReturn(new InstructorEntity(1, "Alice", "Smith", "alice@example.com"));
        when(instructorRepository.findById(2)).thenReturn(new InstructorEntity(2, "Bob", "Johnson", "bob@example.com"));

        var courses = courseService.getAllCourses();

        assertThat(courses)
                .hasSize(2)
                .contains(new CourseDto(
                        "Testing course",
                        "A course about testing",
                        "Alice Smith",
                        LocalDate.of(2026, Month.JANUARY, 15),
                        LocalDate.of(2026, Month.MARCH, 1),
                        30))
                .contains(new CourseDto(
                        "Spring course",
                        "A course about using Spring Boot",
                        "Bob Johnson",
                        LocalDate.of(2026, Month.MAY, 1),
                        LocalDate.of(2026, Month.SEPTEMBER, 1),
                        50));
    }

    @Test
    void shouldFailIfCourseExistsWithoutAnInstructor() {
        CourseService courseService = new CourseService(courseRepository, instructorRepository);

        when(courseRepository.findAll())
                .thenReturn(List.of(new CourseEntity(
                        1,
                        "Testing course",
                        "A course about testing",
                        1,
                        LocalDate.of(2026, Month.JANUARY, 15),
                        LocalDate.of(2026, Month.MARCH, 1),
                        30)));
        when(instructorRepository.findById(1)).thenReturn(null);

        assertThatThrownBy(courseService::getAllCourses).hasMessage("Instructor with id 1 not found");
    }

    @Test
    void shouldFailIfRepositoryThrowsException() {
        CourseService courseService = new CourseService(courseRepository, instructorRepository);

        when(courseRepository.findAll()).thenThrow(new RuntimeException("Database is down"));

        assertThatThrownBy(courseService::getAllCourses).hasMessage("Database is down");
    }
}
