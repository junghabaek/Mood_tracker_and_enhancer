package com.moodTracker.domain.users;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@Rollback(true)
@ExtendWith(SpringExtension.class)
//@DataJpaTest
@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private Users firstUser;
    private Users secondUser;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        firstUser = Users.builder().
                email("test@test.com").
                picture("picture1").
                name("tester1").
                role(Role.GUEST).
                build();

        secondUser = Users.builder().
                email("test2@test.com").
                picture("picture2").
                name("tester2").
                role(Role.GUEST).
                build();

        userRepository.save(firstUser);
        userRepository.save(secondUser);
    }

    @Test
    void findByEmail() {
        Users founduser = userRepository.findByEmail("test@test.com").get();

        assertEquals("tester1", founduser.getName());
        assertEquals("picture1", founduser.getPicture());
    }

}