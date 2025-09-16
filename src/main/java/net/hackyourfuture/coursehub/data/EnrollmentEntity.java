package net.hackyourfuture.coursehub.data;

import java.time.LocalDate;

public record EnrollmentEntity(
        Long enrollmentId,
        Long studentId,
        Long courseId,
        LocalDate enrollmentDate
) {
}
