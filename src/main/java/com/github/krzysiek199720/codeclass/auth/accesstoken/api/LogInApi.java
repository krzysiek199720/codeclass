package com.github.krzysiek199720.codeclass.auth.accesstoken.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LogInApi {

    private String email;

    private String password;
}
