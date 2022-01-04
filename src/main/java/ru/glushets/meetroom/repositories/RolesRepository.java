package ru.glushets.meetroom.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.glushets.meetroom.models.Role;

@Repository
public interface RolesRepository extends CrudRepository<Role, Long> {

    Role findByName(String name);
}
