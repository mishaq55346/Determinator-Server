package ru.mikhail.server.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (roleId != user.roleId) return false;
        if (!fio.equals(user.fio)) return false;
        if (!username.equals(user.username)) return false;
        if (!password.equals(user.password)) return false;
        if (!email.equals(user.email)) return false;
        if (groupName != null ? !groupName.equals(user.groupName) : user.groupName != null) return false;
        if (lastModifiedAt != null ? !lastModifiedAt.equals(user.lastModifiedAt) : user.lastModifiedAt != null)
            return false;
        return createdAt != null ? createdAt.equals(user.createdAt) : user.createdAt == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + fio.hashCode();
        result = 31 * result + username.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + email.hashCode();
        result = 31 * result + (int) (roleId ^ (roleId >>> 32));
        result = 31 * result + (groupName != null ? groupName.hashCode() : 0);
        result = 31 * result + (lastModifiedAt != null ? lastModifiedAt.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        return result;
    }
}
