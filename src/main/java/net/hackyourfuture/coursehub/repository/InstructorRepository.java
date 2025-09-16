package net.hackyourfuture.coursehub.repository;

import net.hackyourfuture.coursehub.data.InstructorEntity;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class InstructorRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public InstructorRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public InstructorEntity findById(Long instructorId) {
        String sql = "SELECT * FROM instructor WHERE instructor_id = :instructorId";
        return jdbcTemplate.queryForObject(
                sql,
                Map.of("instructorId", instructorId),
                (rs, rowNum) -> new InstructorEntity(
                        rs.getLong("instructor_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email")
                )
        );
    }
}
