package ru.mikhail.server.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String fio;
    private String username;
    private String password;
    private String email;
    @Column(name = "role_id")
    private long roleId;
    @Column(name = "group_name")
    private String groupName;
    @Column(name = "last_modified_at")
    private Date lastModifiedAt;
    @Column(name = "created_at")
    private Date createdAt;
}
