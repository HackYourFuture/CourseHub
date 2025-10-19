package net.hackyourfuture.coursehub.data;

public record UserAccountEntity(Integer userId, String emailAddress, String passwordHash, Role role, String apiKey) {}
