package net.hackyourfuture.coursehub.repository;

import net.hackyourfuture.coursehub.data.Role;
import net.hackyourfuture.coursehub.data.StudentEntity;
import net.hackyourfuture.coursehub.data.UserAccountEntity;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Repository
public class StudentRepository {
    public static final RowMapper<StudentEntity> STUDENT_ROW_MAPPER = (rs, rowNum) -> new StudentEntity(
            rs.getInt("student_id"),
            rs.getString("first_name"),
            rs.getString("last_name"),
            rs.getString("email_address") // from user_account
            );
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final UserAccountRepository userAccountRepository;

    public StudentRepository(NamedParameterJdbcTemplate jdbcTemplate, UserAccountRepository userAccountRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.userAccountRepository = userAccountRepository;
    }

    public StudentEntity findById(Integer studentId) {
        String sql = "SELECT s.student_id, s.first_name, s.last_name, ua.email_address "
                + "FROM student s JOIN user_account ua ON s.student_id = ua.user_id "
                + "WHERE s.student_id = :studentId";
        return jdbcTemplate.queryForObject(sql, Map.of("studentId", studentId), STUDENT_ROW_MAPPER);
    }

    @Transactional
    public StudentEntity insertStudent(String firstName, String lastName, String emailAddress, String passwordHash) {
        UserAccountEntity userAccountEntity =
                userAccountRepository.insertUserAccount(emailAddress, passwordHash, Role.student);
        if (userAccountEntity == null) {
            throw new IllegalStateException("Something went wrong inserting user account for a new instructor");
        }
        String sql =
                "INSERT INTO student (student_id, first_name, last_name) VALUES (:studentId, :firstName, :lastName)";
        jdbcTemplate.update(
                sql,
                Map.of(
                        "studentId", userAccountEntity.userId(),
                        "firstName", firstName,
                        "lastName", lastName));
        // Return the joined entity
        return findById(userAccountEntity.userId());
    }
}
