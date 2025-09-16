package net.hackyourfuture.coursehub.repository;

import net.hackyourfuture.coursehub.data.StudentEntity;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class StudentRepository {
    public static final RowMapper<StudentEntity> STUDENT_ROW_MAPPER = (rs, rowNum) -> new StudentEntity(
            rs.getInt("student_id"),
            rs.getString("first_name"),
            rs.getString("last_name"),
            rs.getString("email")
    );
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public StudentRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public StudentEntity findById(Integer studentId) {
        String sql = "SELECT * FROM student WHERE student_id = :studentId";
        return jdbcTemplate.queryForObject(sql, Map.of("studentId", studentId), STUDENT_ROW_MAPPER);
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
