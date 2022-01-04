package ru.glushets.meetroom.services;

import org.springframework.stereotype.Service;
import ru.glushets.meetroom.forms.UsersForm;
import ru.glushets.meetroom.models.User;

import java.util.List;

@Service
public interface UsersService {

    List<User> getAllUsers();

    User addUser(UsersForm form);

    void deleteUser(Long userId, MeetingsService meetingsServiceImpl);

    User getUserById(Long userId);

    void updateUser(Long userId, UsersForm form);

    String getUserName();
}
