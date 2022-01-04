package ru.glushets.meetroom.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorsController {

    @GetMapping("/error/403")
    public String getPageError403() {
        return "/error/error_403";
    }
}
