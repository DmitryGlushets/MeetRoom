package ru.glushets.meetroom.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.glushets.meetroom.models.Role;
import ru.glushets.meetroom.repositories.RolesRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RolesServiceImpl implements RolesService{

    private final RolesRepository rolesRepository;

    public List<Role> getAllRoles() {
        List<Role> rolesList = new ArrayList<>();
        rolesRepository.findAll().forEach(rolesList::add);
        rolesList.sort(Comparator.comparing(Role::getId));
        return rolesList;
    }

    public Role addRole(String name) {
        Role role = Role.builder()
                .name("ROLE_" + name.toUpperCase())
                .build();
        rolesRepository.save(role);
        return role;
    }

    public void deleteRole(Long roleId) {
        rolesRepository.deleteById(roleId);
    }

    public Role getRoleByName(String name) {
        return rolesRepository.findByName(name);
    }
}

