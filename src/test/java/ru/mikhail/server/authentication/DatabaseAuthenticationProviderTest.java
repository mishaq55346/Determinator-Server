package ru.mikhail.server.authentication;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.mikhail.server.config.PasswordEncoderBean;
import ru.mikhail.server.model.User;
import ru.mikhail.server.repository.RoleRepository;
import ru.mikhail.server.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DatabaseAuthenticationProviderTest {

    DatabaseAuthenticationProvider databaseAuthenticationProvider;

    UserRepository userRepository;
    RoleRepository roleRepository;
    PasswordEncoderBean passwordEncoderBean;
    User goodUser;

    @BeforeEach
    void setUp() {
        goodUser = mock(User.class);
        when(goodUser.getPassword()).thenReturn("good password");

        userRepository = mock(UserRepository.class);
        when(userRepository.findByUsername("good username")).thenReturn(goodUser);
        when(userRepository.findByUsername("bad username")).thenReturn(goodUser);

        roleRepository = mock(RoleRepository.class);
        passwordEncoderBean = mock(PasswordEncoderBean.class);
        when(passwordEncoderBean.encodePassword(anyString())).thenAnswer(i -> i.getArgument(0));
        when(passwordEncoderBean.matches("bad password", "good password")).thenReturn(false);
        when(passwordEncoderBean.matches("good password", "good password")).thenReturn(true);




        databaseAuthenticationProvider =
                new DatabaseAuthenticationProvider(userRepository, roleRepository, passwordEncoderBean);
    }

    @Test
    void authenticateTest() {
        boolean goodAuthentication = databaseAuthenticationProvider
                .authenticate("good username", "good password");
        assertTrue(goodAuthentication);

        boolean badUsernameAuthentication = databaseAuthenticationProvider
                .authenticate("bad username", "bad password");
        assertFalse(badUsernameAuthentication);

        boolean badPasswordAuthentication = databaseAuthenticationProvider
                .authenticate("good username", "bad password");
        assertFalse(badPasswordAuthentication);
    }
}