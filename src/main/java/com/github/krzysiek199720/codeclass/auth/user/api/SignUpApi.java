package com.github.krzysiek199720.codeclass.auth.user.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SignUpApi {
    private String email;
    private String firstName;
    private String lastName;
    private String password;
}
