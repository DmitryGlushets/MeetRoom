package ru.glushets.meetroom.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.glushets.meetroom.MeetRoomApplicationTests;
import ru.glushets.meetroom.forms.UsersForm;
import ru.glushets.meetroom.models.User;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UsersServiceImplTest extends MeetRoomApplicationTests {

    @Autowired
    private UsersServiceImpl usersService;

    @Autowired
    private MeetingsService meetingsServiceImpl;

    private UsersForm getUserFormTest() {
        UsersForm usersFormTest = new UsersForm();
        usersFormTest.setFirstname("test1");
        usersFormTest.setLastname("test2");
        usersFormTest.setUsername("test3");
        usersFormTest.setPassword("test4");
        usersFormTest.setRole("ROLE_USER");
        return usersFormTest;
    }

    @Test
    void testGetAllUsers() {
        List<User> userListTest = usersService.getAllUsers();
        assertNotNull(userListTest);
    }

    @Test
    @Transactional
    User testAddUser() {
        User userCreate = usersService.addUser(getUserFormTest());
        assertEquals("test1", userCreate.getFirstname());
        assertEquals("test2", userCreate.getLastname());
        assertEquals("test3", userCreate.getUsername());
        return userCreate;
    }

    @Test
    @Transactional
    void testDeleteUser() {
        User userCreate = usersService.addUser(getUserFormTest());
        assertNotNull(userCreate);
        usersService.deleteUser(userCreate.getId(), meetingsServiceImpl);
        userCreate = usersService.getUserById(userCreate.getId());
        assertNull(userCreate);
    }

    @Test
    @Transactional
    void testGetFirstnameAdnLastname() {
        User userCreate = usersService.addUser(getUserFormTest());
        assertEquals("test1 test2", usersService.getFirstnameAndLastname(userCreate.getId()));
        assertEquals("-", usersService.getFirstnameAndLastname(999L));
    }

    @Test
    @Transactional
    void testLoadUserByUsername() {
        User userCreate = usersService.addUser(getUserFormTest());
        User user = (User) usersService.loadUserByUsername(userCreate.getUsername());
        assertEquals(user.getFirstname(), userCreate.getFirstname());
        assertEquals(user.getLastname(), userCreate.getLastname());
        assertEquals(user.getUsername(), userCreate.getUsername());
        assertEquals(user.getPassword(), userCreate.getPassword());
        assertEquals(user.getRoles(), userCreate.getRoles());
        assertThrows(UsernameNotFoundException.class, () -> usersService.loadUserByUsername("Noname"));
    }
}
