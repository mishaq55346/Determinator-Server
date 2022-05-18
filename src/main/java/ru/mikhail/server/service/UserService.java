package ru.mikhail.server.service;

import ru.mikhail.server.model.UserDTO;

public interface UserService {
    UserDTO getUserInfo(String username);
}
