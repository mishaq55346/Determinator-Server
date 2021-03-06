package ru.mikhail.server.authentication;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import ru.mikhail.server.config.PasswordEncoderBean;
import ru.mikhail.server.model.Role;
import ru.mikhail.server.model.User;
import ru.mikhail.server.repository.RoleRepository;
import ru.mikhail.server.repository.UserRepository;

import javax.annotation.PostConstruct;

@Component
public class DatabaseAuthenticationProvider implements AuthenticationProvider {
    private transient final Logger logger = Logger.getLogger(this.getClass());

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoderBean passwordEncoder;

    public DatabaseAuthenticationProvider(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoderBean passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    private void init(){
        logger.info("AuthenticationProvider instantiated.");
    }

    @Override
    public boolean authenticate(String username, String password) {
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            logger.error("Empty credentials");
            return false;
        }
        User user = userRepository.findByUsername(username);
        if (user == null) {
            logger.error("User not found");
            return false;
        }
        if (!passwordEncoder.matches(password, user.getPassword())) {
            logger.error("Bad credentials");
            return false;
        }
        return true;
    }

    @Override
    public Role getRole(String username) {
        if (username == null || username.isEmpty()) {
            logger.error("Empty credentials");
            return null;
        }
        User user = userRepository.findByUsername(username);
        if (user == null) {
            logger.error("User not found");
            return null;
        }
        return roleRepository.getById(user.getRoleId());
    }
}
