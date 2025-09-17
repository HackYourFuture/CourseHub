package net.hackyourfuture.coursehub.repository;

import net.hackyourfuture.coursehub.data.Role;
import net.hackyourfuture.coursehub.data.UserAccountEntity;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class UserAccountRepository {
    public static final RowMapper<UserAccountEntity> USER_ACCOUNT_ROW_MAPPER = (rs, rowNum) -> new UserAccountEntity(
            rs.getInt("user_id"),
            rs.getString("username"),
            rs.getString("password_hash"),
            rs.getString("email_address"),
            rs.getObject("role", Role.class));
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public UserAccountRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public UserAccountEntity findByUserId(Integer userId) {
        String sql = "SELECT * FROM user_account WHERE user_id = :useId";
        return jdbcTemplate.queryForObject(sql, Map.of("userId", userId), USER_ACCOUNT_ROW_MAPPER);
    }

    public UserAccountEntity findByEmailAddress(String emailAddress) {
        String sql = "SELECT * FROM user_account WHERE email_address = :emailAddress";
        return jdbcTemplate.queryForObject(sql, Map.of("emailAddress", emailAddress), USER_ACCOUNT_ROW_MAPPER);
    }

    public void save(UserAccountEntity user) {
        String sql =
                "INSERT INTO user_account (password_hash, email_address, role) VALUES (:passwordHash, :emailAddress, :role)";
        jdbcTemplate.update(
                sql,
                Map.of(
                        "passwordHash", user.passwordHash(),
                        "emailAddress", user.emailAddress(),
                        "role", user.role()));
    }
}
