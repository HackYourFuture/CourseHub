package net.hackyourfuture.coursehub.repository;

import net.hackyourfuture.coursehub.data.InstructorEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class InstructorRepositoryTest {
    @Autowired
    private InstructorRepository instructorRepository;

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Test
    void insertAndFindInstructorById() {
        InstructorEntity instructorEntity =
                instructorRepository.insertInstructor("Alice", "Anderson", "alice@example.com", "password");
        Assertions.assertThat(instructorEntity).isNotNull();
        Assertions.assertThat(instructorRepository.findById(instructorEntity.instructorId()))
                .isEqualTo(instructorEntity);
    }

    @Test
    void insertAndFindUserAccountByEmailAddress() {
        String emailAddress = "bob@example.com";
        InstructorEntity instructorEntity =
                instructorRepository.insertInstructor("Bob", "Builder", emailAddress, "password");
        Assertions.assertThat(instructorEntity).isNotNull();
        Assertions.assertThat(userAccountRepository.findUserIdByEmail(emailAddress))
                .isEqualTo(instructorEntity.instructorId());
    }

    @Test
    void insertInstructorWithDuplicateEmailThrowsException() {
        String email = "duplicate@example.com";
        instructorRepository.insertInstructor("John", "Doe", email, "password");
        Assertions.assertThatThrownBy(() -> instructorRepository.insertInstructor("Jane", "Smith", email, "password"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("already exists");
    }

    @Test
    void findNonExistentInstructorReturnsException() {
        Integer nonExistentId = -9999;
        Assertions.assertThat(instructorRepository.findById(nonExistentId)).isNull();
    }
}