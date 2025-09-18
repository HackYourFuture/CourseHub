package net.hackyourfuture.coursehub.service;

import net.hackyourfuture.coursehub.data.CourseEntity;
import net.hackyourfuture.coursehub.repository.CourseRepository;
import net.hackyourfuture.coursehub.repository.InstructorRepository;
import net.hackyourfuture.coursehub.web.model.CourseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * The difference between this integration test and the unit test is that here we use the repositories connected
 * to a real postgres database (needs to be running with docker compose), while in the unit test we mock repositories
 * to return mock data specified in the test.
 *
 * <p>The advantage of this integration test is that we test a more complete flow, so that if the repository or
 * the database schema changes, this test will likely catch it. The disadvantage is that the test is slower,
 * it requires a running database (we use docker compose for that), and we need to insert test data into the database
 * before executing a test. Also, we need to make sure not to pollute the database with test data,
 * so we use @Transactional to rollback any changes after each test.
 */
// start the full application listening on a random port
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// rollback any changes after the test is completed
@Transactional
class CourseServiceIntegrationTest {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private InstructorRepository instructorRepository;

    @Autowired
    private CourseService courseService;

    @BeforeEach
    void setUp() {
        var instructor1 =
                instructorRepository.insertInstructor("Artur", "Havliukovskyi", "artur@example.com", "passwordHash");
        var instructor2 =
                instructorRepository.insertInstructor("Breus", "Blaauwendraad", "breus@example.com", "passwordHash");

        courseRepository.insertCourse(new CourseEntity(
                null,
                "Test Course 1",
                "Description 1",
                instructor1.instructorId(),
                LocalDate.now(),
                LocalDate.now().plusDays(30),
                30));

        courseRepository.insertCourse(new CourseEntity(
                null,
                "Test Course 2",
                "Description 2",
                instructor2.instructorId(),
                LocalDate.now(),
                LocalDate.now().plusDays(60),
                50));
    }

    @Test
    void shouldReturnCourses() {
        var allCourses = courseRepository.findAll();
        var course1 = allCourses.stream()
                .filter(c -> "Test Course 1".equals(c.name()))
                .findFirst()
                .orElseThrow();
        var course2 = allCourses.stream()
                .filter(c -> "Test Course 2".equals(c.name()))
                .findFirst()
                .orElseThrow();

        var courses = courseService.getAllCourses();

        assertThat(courses)
                .contains(new CourseDto(
                        course1.courseId(),
                        "Test Course 1",
                        "Description 1",
                        "Artur Havliukovskyi",
                        course1.startDate(),
                        course1.endDate(),
                        30))
                .contains(new CourseDto(
                        course2.courseId(),
                        "Test Course 2",
                        "Description 2",
                        "Breus Blaauwendraad",
                        course2.startDate(),
                        course2.endDate(),
                        50));
    }
}
