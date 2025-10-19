package net.hackyourfuture.coursehub.repository;

import net.hackyourfuture.coursehub.data.StudentEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

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

    @Test
    void cannotInsertTwoStudentsWithSameEmail() {
        var emailAddress = "bobb@example.com";
        var emailAddressDifferentCase = "Bobb@example.com";

        studentRepository.insertStudent("Bob", "de Bouwer", emailAddress, "password");
        assertThatThrownBy(() -> studentRepository.insertStudent("Bob", "de Bouwer", emailAddress, "password"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Another user with the email address 'bobb@example.com' already exists");
        assertThatThrownBy(() -> studentRepository.insertStudent("Bob", "de Bouwer", emailAddressDifferentCase, "password"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Another user with the email address 'Bobb@example.com' already exists");
    }
}
