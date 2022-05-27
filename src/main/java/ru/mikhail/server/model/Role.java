package ru.mikhail.server.model;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return getId() == role.getId() && getUniversityCode().equals(role.getUniversityCode()) && getUniversityName().equals(role.getUniversityName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUniversityCode(), getUniversityName());
    }
}
