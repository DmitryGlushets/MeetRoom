package ru.glushets.meetroom.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.glushets.meetroom.models.Role;
import ru.glushets.meetroom.services.RolesService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class RolesController {

    private final RolesService rolesServiceImpl;

    @GetMapping("/roles")
    public String getRolesPage(Model model) {
        List<Role> roles = rolesServiceImpl.getAllRoles();
        model.addAttribute("roles", roles);
        return "/users/roles";
    }

    @PostMapping("/roles")
    public String addRole(@RequestParam("name") String name) {
        rolesServiceImpl.addRole(name);
        return "redirect:/roles";
    }

    @PostMapping("/roles/{roles-id}/delete")
    public String deleteRole(@PathVariable("roles-id") Long roleId) {
        rolesServiceImpl.deleteRole(roleId);
        return "redirect:/roles";
    }
}
