package com.github.demo.domain.user.admin.repository;

import com.github.demo.constant.TestProfile;
import com.github.demo.domain.base.Email;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles(TestProfile.TEST)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(AdminRepository.class)
class AdminRepositoryTest {

    @Autowired
    AdminRepository adminRepository;

    @ParameterizedTest
    @ValueSource(strings = {"81f14942-3e9b-4144-a1dc-b683fefacb8e"})
    void findById(String param) {
        var admin = this.adminRepository.findById(param);
        Assertions.assertEquals(UUID.fromString(param), admin.getId());
    }

    @ParameterizedTest
    @ValueSource(strings = {"test@test.com"})
    void findByEmail(String param) {
        var admin = this.adminRepository.findByEmail(param);
        Assertions.assertEquals(param, admin.getEmail().getValue());
    }

    @Test
    void existByEmail() {
        var string = this.adminRepository.existByEmail("test@test.com");
        var object = this.adminRepository.existByEmail(Email.of("test@test.com"));
        Assertions.assertTrue(string);
        Assertions.assertTrue(object);
    }
}
