package net.hackyourfuture.coursehub.data;

import java.time.LocalDate;

public record CourseEntity(
        Long courseId,
        String name,
        String description,
        Long instructorId,
        LocalDate startDate,
        LocalDate endDate
) {
}
