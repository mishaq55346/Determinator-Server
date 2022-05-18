package ru.mikhail.server.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO {
    private String fio;
    private String email;
    private String universityName;
    private String groupName;

    public static UserDTO fromUser(User user, Role role){
        return new UserDTO(
                user.getFio(),
                user.getEmail(),
                role.getUniversityName(),
                user.getGroupName()
        );
    }
}