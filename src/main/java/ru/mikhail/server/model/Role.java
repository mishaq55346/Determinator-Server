package ru.mikhail.server.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "university_code")
    private String universityCode;
    @Column(name = "university_name")
    private String universityName;

    public Role(String role) {
        universityCode = role;
    }

    public Role() {
    }
}
