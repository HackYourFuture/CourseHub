package net.hackyourfuture.coursehub.repository;

import net.hackyourfuture.coursehub.data.InstructorEntity;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Repository
public class InstructorRepository {
    public static final RowMapper<InstructorEntity> INSTRUCTOR_ROW_MAPPER = (rs, rowNum) -> new InstructorEntity(
            rs.getInt("instructor_id"),
            rs.getString("first_name"),
            rs.getString("last_name"),
            rs.getString("email_address"));
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final UserAccountRepository userAccountRepository;

    public InstructorRepository(NamedParameterJdbcTemplate jdbcTemplate, UserAccountRepository userAccountRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.userAccountRepository = userAccountRepository;
    }

    public InstructorEntity findById(Integer instructorId) {
        try {
            String sql = "SELECT i.instructor_id, i.first_name, i.last_name, ua.email_address "
                    + "FROM instructor i JOIN user_account ua ON i.instructor_id = ua.user_id "
                    + "WHERE i.instructor_id = :instructorId";
            return jdbcTemplate.queryForObject(sql, Map.of("instructorId", instructorId), INSTRUCTOR_ROW_MAPPER);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Transactional
    public InstructorEntity insertInstructor(
            String firstName, String lastName, String emailAddress, String passwordHash) {
        if (firstName == null || firstName.isBlank()) {
            throw new IllegalArgumentException("firstName must not be empty");
        }
        if (lastName == null || lastName.isBlank()) {
            throw new IllegalArgumentException("lastName must not be empty");
        }
        if (emailAddress == null || emailAddress.isBlank()) {
            throw new IllegalArgumentException("emailAddress must not be empty");
        }
        if (passwordHash == null || passwordHash.isBlank()) {
            throw new IllegalArgumentException("passwordHash must not be empty");
        }
        Integer userId = userAccountRepository.findUserIdByEmail(emailAddress);
        if (userId != null) {
            throw new IllegalArgumentException(
                    "Another user with the email address '%s' already exists".formatted(emailAddress));
        }
        // Insert into user_account first
        String userSql =
                "INSERT INTO user_account (email_address, password_hash, role) VALUES (:emailAddress, :passwordHash, :role::role) RETURNING user_id";
        userId = jdbcTemplate.queryForObject(
                userSql,
                Map.of(
                        "emailAddress", emailAddress,
                        "passwordHash", passwordHash,
                        "role", "instructor"),
                Integer.class);
        if (userId == null) {
            throw new IllegalStateException("Something went wrong inserting user account for a new instructor");
        }
        // Insert into the instructor table
        String instructorSql =
                "INSERT INTO instructor (instructor_id, first_name, last_name) VALUES (:instructorId, :firstName, :lastName)";
        jdbcTemplate.update(
                instructorSql,
                Map.of(
                        "instructorId", userId,
                        "firstName", firstName,
                        "lastName", lastName));
        // Return the joined entity
        return findById(userId);
    }
}
