package net.hackyourfuture.coursehub.repository;

import jakarta.annotation.Nullable;
import net.hackyourfuture.coursehub.data.Role;
import net.hackyourfuture.coursehub.data.UserAccountEntity;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Repository
public class UserAccountRepository {
    public static final RowMapper<UserAccountEntity> USER_ACCOUNT_ROW_MAPPER = (rs, rowNum) -> new UserAccountEntity(
            rs.getInt("user_id"),
            rs.getString("email_address"),
            rs.getString("password_hash"),
            Role.valueOf(rs.getString("role")),
            rs.getString("api_key"));
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public UserAccountRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public UserAccountEntity findByUserId(Integer userId) {
        String sql = "SELECT * FROM user_account WHERE user_id = :userId";
        return jdbcTemplate.queryForObject(sql, Map.of("userId", userId), USER_ACCOUNT_ROW_MAPPER);
    }

    @Nullable
    public UserAccountEntity findByEmailAddress(String emailAddress) {
        String sql = "SELECT * FROM user_account WHERE lower(email_address) = lower(:emailAddress)";
        try {
            return jdbcTemplate.queryForObject(sql, Map.of("emailAddress", emailAddress), USER_ACCOUNT_ROW_MAPPER);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Transactional
    public UserAccountEntity insertUserAccount(String emailAddress, String passwordHash, Role role) {
        Integer userId = findUserIdByEmail(emailAddress);
        if (userId != null) {
            throw new IllegalArgumentException(
                    "Another user with the email address '%s' already exists".formatted(emailAddress));
        }
        String userSql = "INSERT INTO user_account (email_address, password_hash, role) "
                + "VALUES (:emailAddress, :passwordHash, :role::role) "
                + "RETURNING user_id, email_address, password_hash, role, api_key";
        return jdbcTemplate.queryForObject(
                userSql,
                Map.of(
                        "emailAddress", emailAddress,
                        "passwordHash", passwordHash,
                        "role", role.name()),
                USER_ACCOUNT_ROW_MAPPER);
    }

    @Nullable
    public Integer findUserIdByEmail(String emailAddress) {
        String sql = "SELECT user_id FROM user_account WHERE lower(email_address) = lower(:emailAddress)";
        try {
            return jdbcTemplate.queryForObject(sql, Map.of("emailAddress", emailAddress), Integer.class);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    /**
     * Stores the given API key for the user with the given ID.
     *
     * @param userId the user ID
     * @param apiKey the API key to store
     * @return the updated UserAccountEntity
     */
    @Transactional
    public UserAccountEntity updateApiKey(Integer userId, String apiKey) {
        String sql = "UPDATE user_account SET api_key = :apiKey WHERE user_id = :userId " +
                     "RETURNING user_id, email_address, password_hash, role, api_key";
        return jdbcTemplate.queryForObject(
                sql,
                Map.of("userId", userId, "apiKey", apiKey),
                USER_ACCOUNT_ROW_MAPPER);
    }

    /**
     * Finds a user by API key.
     *
     * @param apiKey the API key
     * @return the UserAccountEntity, or null if not found
     */
    @Nullable
    public UserAccountEntity findByApiKey(String apiKey) {
        String sql = "SELECT * FROM user_account WHERE api_key = :apiKey";
        try {
            return jdbcTemplate.queryForObject(sql, Map.of("apiKey", apiKey), USER_ACCOUNT_ROW_MAPPER);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
