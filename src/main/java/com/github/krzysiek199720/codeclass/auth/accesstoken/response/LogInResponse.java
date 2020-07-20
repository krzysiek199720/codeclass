package com.github.krzysiek199720.codeclass.auth.accesstoken.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LogInResponse {

    private String token;
    private LocalDateTime expires;
    private Long userId;
    private String email;
    private String firstName;
    private String lastName;
    private Long roleId;
    private String roleName;
}
