package com.github.krzysiek199720.codeclass.auth.role.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RoleApi {
    private String name;
    private Boolean isAdmin;
    private Set<Long> permissions;
}
