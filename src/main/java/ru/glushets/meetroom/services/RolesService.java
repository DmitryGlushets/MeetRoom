package ru.glushets.meetroom.services;

import org.springframework.stereotype.Service;
import ru.glushets.meetroom.models.Role;

import java.util.List;

@Service
public interface RolesService {

    List<Role> getAllRoles();

    Role addRole(String name);

    void deleteRole(Long roleId);
}
