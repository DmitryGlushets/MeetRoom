package ru.glushets.meetroom.forms;

import lombok.Data;

@Data
public class UsersForm {

    private String username;

    private String firstname;

    private String lastname;

    private String password;

    private String role;
}
