package ru.mikhail.server.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
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
}
