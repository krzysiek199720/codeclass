package com.github.krzysiek199720.codeclass.auth.accesstoken.response;

import com.github.krzysiek199720.codeclass.auth.permission.Permission;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

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
    private Boolean isAdmin;
    private Set<Permission> permissions;
}
