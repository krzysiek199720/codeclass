package com.github.krzysiek199720.codeclass.auth.user.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChangeEmailApi {
    private String email;
    private String password;
}
