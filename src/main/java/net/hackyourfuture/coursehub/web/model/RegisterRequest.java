package net.hackyourfuture.coursehub.web.model;

public record RegisterRequest(
        String firstName,
        String lastName,
        String emailAddress,
        String password
) {
}
