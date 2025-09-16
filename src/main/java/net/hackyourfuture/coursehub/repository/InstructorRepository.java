package net.hackyourfuture.coursehub.repository;

import net.hackyourfuture.coursehub.data.InstructorEntity;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class InstructorRepository {
    public static final RowMapper<InstructorEntity> INSTRUCTOR_ROW_MAPPER = (rs, rowNum) -> new InstructorEntity(
            rs.getLong("instructor_id"),
            rs.getString("first_name"),
            rs.getString("last_name"),
            rs.getString("email")
    );
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public InstructorRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public InstructorEntity findById(Long instructorId) {
        String sql = "SELECT * FROM instructor WHERE instructor_id = :instructorId";
        return jdbcTemplate.queryForObject(sql, Map.of("instructorId", instructorId), INSTRUCTOR_ROW_MAPPER);
    }

    public void insertInstructor(InstructorEntity instructor) {
        String sql = """
                     INSERT INTO instructor (first_name, last_name, email)
                     VALUES (:firstName, :lastName, :email)
                     """;
        jdbcTemplate.update(
                sql, Map.of(
                        "firstName", instructor.firstName(),
                        "lastName", instructor.lastName(),
                        "email", instructor.email()
                )
        );
    }
}
