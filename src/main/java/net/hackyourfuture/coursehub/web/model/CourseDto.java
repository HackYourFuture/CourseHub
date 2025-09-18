package net.hackyourfuture.coursehub.web.model;

import java.time.LocalDate;

public record CourseDto(
        Integer courseId,
        String name,
        String description,
        String instructor,
        LocalDate startDate,
        LocalDate endDate,
        Integer maxEnrollments) {}
