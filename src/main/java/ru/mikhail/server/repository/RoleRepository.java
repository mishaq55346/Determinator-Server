package ru.mikhail.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mikhail.server.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
