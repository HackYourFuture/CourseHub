package net.hackyourfuture.coursehub.data;

public record InstructorEntity(
        Long instructorId,
        String firstName,
        String lastName,
        String email
) {
}
