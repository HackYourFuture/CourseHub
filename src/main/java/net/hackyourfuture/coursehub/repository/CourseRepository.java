package net.hackyourfuture.coursehub.repository;

import net.hackyourfuture.coursehub.data.CourseEntity;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Repository
public class CourseRepository {
    public static final RowMapper<CourseEntity> COURSE_ROW_MAPPER = (rs, rowNumber) -> new CourseEntity(
            rs.getLong("course_id"),
            rs.getString("name"),
            rs.getString("description"),
            rs.getLong("instructor_id"),
            rs.getObject("start_date", LocalDate.class),
            rs.getObject("end_date", LocalDate.class)
    );
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public CourseRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<CourseEntity> findAll() {
        String sql = "SELECT * FROM course";
        return jdbcTemplate.query(sql, COURSE_ROW_MAPPER);
    }

    public CourseEntity findByCourseId(Long courseId) {
        String sql = "SELECT * FROM course WHERE course_id = :courseId";
        return jdbcTemplate.queryForObject(sql, Map.of("courseId", courseId), COURSE_ROW_MAPPER);
    }

    public void insertCourse(CourseEntity course) {
        String sql = """
                     INSERT INTO course (name, description, instructor_id, start_date, end_date)
                     VALUES (:name, :description, :instructorId, :startDate, :endDate)
                     """;
        jdbcTemplate.update(
                sql, Map.of(
                        "name", course.name(),
                        "description", course.description(),
                        "instructorId", course.instructorId(),
                        "startDate", course.startDate(),
                        "endDate", course.endDate()
                )
        );
    }
}
