package ru.mikhail.server.authentication;

import ru.mikhail.server.model.Role;

public interface AuthenticationProvider {
    boolean authenticate(String username, String password);

    Role getRole(String username);
}
