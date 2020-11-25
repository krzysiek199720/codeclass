package com.github.krzysiek199720.codeclass.auth.permission.response;

import com.github.krzysiek199720.codeclass.auth.permission.Permission;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PermissionResponse {
    private String groupName;
    private List<Permission> permissions;
}
