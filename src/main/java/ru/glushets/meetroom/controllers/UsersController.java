package ru.glushets.meetroom.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.glushets.meetroom.forms.UsersForm;
import ru.glushets.meetroom.models.Role;
import ru.glushets.meetroom.models.User;
import ru.glushets.meetroom.services.MeetingsService;
import ru.glushets.meetroom.services.RolesService;
import ru.glushets.meetroom.services.UsersService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UsersController {

    private final UsersService usersServiceImpl;

    private final RolesService rolesServiceImpl;

    private final MeetingsService meetingsServiceImpl;

    @GetMapping("/usersPage")
    public String getUsersPage(Model model) {
        List<User> users = usersServiceImpl.getAllUsers();
        List<Role> roles = rolesServiceImpl.getAllRoles();
        model.addAttribute("users", users);
        model.addAttribute("roles", roles);
        return "/users/usersPage";
    }

    @PostMapping("/usersPage")
    public String addUser(UsersForm form) {
        usersServiceImpl.addUser(form);
        return "redirect:/usersPage";
    }

    @PostMapping("/usersPage/{user-id}/delete")
    public String deleteUser(@PathVariable("user-id") Long userId) {
        usersServiceImpl.deleteUser(userId, meetingsServiceImpl);
        return "redirect:/usersPage";
    }

    @GetMapping("/user/{user-id}")
    public String getUserPage(Model model, @PathVariable("user-id") Long userId) {
        User user = usersServiceImpl.getUserById(userId);
        List<Role> roles = rolesServiceImpl.getAllRoles();
        model.addAttribute(roles);
        model.addAttribute("user", user);
        return "/users/user";
    }

    @PostMapping("/user/{user-id}/update")
    public String update(@PathVariable("user-id") Long userId, UsersForm form) {
        usersServiceImpl.updateUser(userId, form);
        return "redirect:/usersPage";
    }
}
