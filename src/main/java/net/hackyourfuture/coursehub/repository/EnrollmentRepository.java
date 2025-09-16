package net.hackyourfuture.coursehub.repository;

import net.hackyourfuture.coursehub.data.EnrollmentEntity;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Repository
public class EnrollmentRepository {
    public static final RowMapper<EnrollmentEntity> ENROLLMENT_ROW_MAPPER = (rs, rowNum) -> new EnrollmentEntity(
            rs.getLong("enrollment_id"),
            rs.getLong("course_id"),
            rs.getLong("student_id"),
            rs.getObject("enrollment_date", LocalDate.class)
    );
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public EnrollmentRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<EnrollmentEntity> findEnrollmentsByCourseId(Long courseId) {
        String sql = "SELECT * FROM enrollment WHERE course_id = :courseId";
        return jdbcTemplate.query(sql, Map.of("courseId", courseId), ENROLLMENT_ROW_MAPPER);
    }

    public List<EnrollmentEntity> findEnrollmentsByStudentId(Long studentId) {
        String sql = "SELECT * FROM enrollment WHERE student_id = :studentId";
        return jdbcTemplate.query(sql, Map.of("studentId", studentId), ENROLLMENT_ROW_MAPPER);
    }
}
