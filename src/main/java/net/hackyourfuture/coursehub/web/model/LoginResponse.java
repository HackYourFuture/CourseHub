package net.hackyourfuture.coursehub.web.model;

public record LoginResponse(Integer userId, Boolean success, String errorMessage) {}
