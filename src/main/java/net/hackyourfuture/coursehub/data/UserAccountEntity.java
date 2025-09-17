package net.hackyourfuture.coursehub.data;

public record UserAccountEntity(Integer userId, String passwordHash, String emailAddress, Role role) {}
