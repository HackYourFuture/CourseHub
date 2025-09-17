package net.hackyourfuture.coursehub.data;

public record UserAccountEntity(Integer userId, String username, String passwordHash, String emailAddress, Role role) {}
