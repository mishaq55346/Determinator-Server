package ru.mikhail.server.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mikhail.server.model.User;
import ru.mikhail.server.model.UserDTO;
import ru.mikhail.server.repository.RoleRepository;
import ru.mikhail.server.repository.UserRepository;

@Service
@Log4j2
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public UserDTO getUserInfo(String username) {
        User user = userRepository.findByUsername(username);
        return UserDTO.fromUser(user, roleRepository.getById(user.getRoleId()));
    }
}
