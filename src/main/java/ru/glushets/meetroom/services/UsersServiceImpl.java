package ru.glushets.meetroom.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.glushets.meetroom.forms.UsersForm;
import ru.glushets.meetroom.models.Meeting;
import ru.glushets.meetroom.models.Role;
import ru.glushets.meetroom.models.User;
import ru.glushets.meetroom.repositories.UsersRepository;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UserDetailsService, UsersService {

    private final UsersRepository usersRepository;
    private final RolesServiceImpl rolesService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public User addUser(UsersForm form) {
        User user = User.builder()
                .username(form.getUsername())
                .firstname(form.getFirstname())
                .lastname(form.getLastname())
                .build();
        user.setRoles(Collections.singleton(rolesService.getRoleByName(checkRoleIfNullReturnUser(form.getRole()))));
        user.setPassword(bCryptPasswordEncoder.encode(form.getPassword()));
        usersRepository.save(user);
        return user;
    }

    public void deleteUser(Long id, MeetingsService meetingsService) {
        List<Meeting> meetingList = meetingsService.getAllMeetingUser(getUserById(id));
        for (Meeting meeting: meetingList){
            meetingsService.deleteMeeting(meeting.getId());
        }
        usersRepository.deleteById(id);
    }

    public List<User> getAllUsers() {
        return (List<User>) usersRepository.findAll();
    }

    public User getUserById(Long id) {
        return usersRepository.findById(id).orElse(null);
    }

    public void updateUser(Long id, UsersForm form) {
        User user = getUserById(id);
        user.setUsername(form.getUsername());
        user.setFirstname(form.getFirstname());
        user.setLastname(form.getLastname());
        user.setPassword(bCryptPasswordEncoder.encode(form.getPassword()));
        usersRepository.save(user);
    }


    public Long getUserId() {
        try {
            return usersRepository.findByUsername(getUserName()).getId();
        } catch (NullPointerException e) {
            return -1L;
        }
    }

    public String getUserName() {
        try {
            return SecurityContextHolder.getContext().getAuthentication().getName();
        } catch (NullPointerException e) {
            return "";
        }
    }

    public String getFirstnameAndLastname(Long id) {
        User user = usersRepository.findById(id).orElse(null);
        if (user == null) {
            return "-";
        }
        return user.getFirstname() + " " + user.getLastname();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = usersRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }

    private String checkRoleIfNullReturnUser(String nameRole) {
        Role role = rolesService.getRoleByName(nameRole);
        if (role == null) {
            return "ROLE_USER";
        } else {
            return nameRole;
        }
    }
}
