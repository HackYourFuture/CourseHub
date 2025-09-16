package net.hackyourfuture.coursehub.data;

public record StudentEntity(
        Long studentId,
        String firstName,
        String lastName,
        String email
) {
}
