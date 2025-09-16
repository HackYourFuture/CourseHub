package net.hackyourfuture.coursehub.repository;

import net.hackyourfuture.coursehub.data.StudentEntity;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class StudentRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public StudentRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public StudentEntity findById(Long studentId) {
        String sql = "SELECT * FROM student WHERE student_id = :studentId";
        return jdbcTemplate.queryForObject(
                sql,
                Map.of("studentId", studentId),
                (rs, rowNum) -> new StudentEntity(
                        rs.getLong("student_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email")
                )
        );
    }

    public void insertStudent(StudentEntity student) {
        String sql = """
                     INSERT INTO student (first_name, last_name, email)
                     VALUES (:firstName, :lastName, :email)
                     """;
        jdbcTemplate.update(
                sql, Map.of(
                        "firstName", student.firstName(),
                        "lastName", student.lastName(),
                        "email", student.email()
                )
        );
    }
}
