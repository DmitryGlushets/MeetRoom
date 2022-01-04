package ru.glushets.meetroom.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.glushets.meetroom.MeetRoomApplicationTests;
import ru.glushets.meetroom.models.Role;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RolesServiceImplTest extends MeetRoomApplicationTests {

    @Autowired
    private RolesServiceImpl rolesServiceImpl;

    @Test
    void testGetAllRoles() {
        List<Role> roleList = rolesServiceImpl.getAllRoles();
        assertNotNull(roleList);
    }

    @Test
    @Transactional
    void testAddRole() {
        Role roleCreate = rolesServiceImpl.addRole("TEST");
        assertNotNull(roleCreate);
    }

    @Test
    @Transactional
    void testDeleteRole() {
        rolesServiceImpl.addRole("TEST");
        Role roleCreate = rolesServiceImpl.getRoleByName("ROLE_TEST");
        assertNotNull(roleCreate);
        rolesServiceImpl.deleteRole(roleCreate.getId());
        Role roleDelete = rolesServiceImpl.getRoleByName("ROLE_TEST");
        assertNull(roleDelete);
    }

    @Test
    @Transactional
    void testGetRoleByName(){
        Role roleCreate = rolesServiceImpl.addRole("TEST");
        Role role = rolesServiceImpl.getRoleByName("ROLE_TEST");
        assertEquals(role.getId(), roleCreate.getId());
        assertEquals(role.getName(), roleCreate.getName());
    }
}