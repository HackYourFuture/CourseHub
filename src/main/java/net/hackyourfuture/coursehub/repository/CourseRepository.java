package net.hackyourfuture.coursehub.repository;

import net.hackyourfuture.coursehub.data.CourseEntity;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CourseRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public CourseRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<CourseEntity> findAll() {
        String sql = "SELECT * FROM course";
        return jdbcTemplate.query(sql, (rs, rowNumber) -> {
            CourseEntity course = new CourseEntity();
            return course;
        });
    }
}
