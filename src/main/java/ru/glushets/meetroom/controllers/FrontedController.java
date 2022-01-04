package ru.glushets.meetroom.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class FrontedController {

    @GetMapping("/panel")
    public String getPanelMenuPage() {
        return "/admin/panel";
    }

    @GetMapping("/pageAdministration")
    public String getAdminPage() {
        return "/admin/pageAdministration";
    }
}
