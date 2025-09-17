package net.hackyourfuture.coursehub.repository;

import net.hackyourfuture.coursehub.data.StudentEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class StudentRepositoryTest {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Test
    void insertAndFindStudentById() {
        StudentEntity studentEntity = studentRepository.insertStudent("Stas", "Seldin", "stas@example.com", "password");
        Assertions.assertThat(studentEntity).isNotNull();
        Assertions.assertThat(studentRepository.findById(studentEntity.studentId()))
                .isEqualTo(studentEntity);
    }

    @Test
    void insertAndFindUserAccountByEmailAddress() {
        String emailAddress = "bobb@example.com";
        StudentEntity studentEntity = studentRepository.insertStudent("Bob", "de Bouwer", emailAddress, "password");
        Assertions.assertThat(studentEntity).isNotNull();
        Assertions.assertThat(userAccountRepository.findUserIdByEmail(emailAddress))
                .isEqualTo(studentEntity.studentId());
    }
}
