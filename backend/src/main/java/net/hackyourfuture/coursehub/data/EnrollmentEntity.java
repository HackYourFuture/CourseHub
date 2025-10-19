package net.hackyourfuture.coursehub.data;

import java.time.LocalDate;

public record EnrollmentEntity(
        Integer enrollmentId,
        Integer studentId,
        Integer courseId,
        LocalDate enrollmentDate
) {
}
