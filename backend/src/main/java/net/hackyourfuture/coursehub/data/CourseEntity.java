package net.hackyourfuture.coursehub.data;

import java.time.LocalDate;

public record CourseEntity(
        Integer courseId,
        String name,
        String description,
        Integer instructorId,
        LocalDate startDate,
        LocalDate endDate,
        Integer maxEnrollments) {}
