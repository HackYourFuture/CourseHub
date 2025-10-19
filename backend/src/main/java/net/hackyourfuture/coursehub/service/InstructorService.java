package net.hackyourfuture.coursehub.service;

import net.hackyourfuture.coursehub.data.InstructorEntity;
import net.hackyourfuture.coursehub.repository.InstructorRepository;
import org.springframework.stereotype.Service;

@Service
public class InstructorService {
    private final InstructorRepository instructorRepository;

    public InstructorService(InstructorRepository instructorRepository) {
        this.instructorRepository = instructorRepository;
    }

    public String getFullInstructorName(Integer instructorId) {
        InstructorEntity instructor = instructorRepository.findById(instructorId);
        if (instructor == null) {
            throw new IllegalArgumentException("Instructor with id " + instructorId + " not found");
        }
        return instructor.firstName() + " " + instructor.lastName();
    }
}
