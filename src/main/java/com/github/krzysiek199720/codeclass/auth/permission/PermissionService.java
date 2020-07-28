package com.github.krzysiek199720.codeclass.auth.permission;

import java.util.List;
import java.util.Set;

public interface PermissionService {

    public Set<Permission> getAllIn(Set<Long> ids);

    public Permission getByValue(String value);

    public List<Permission> getAll();
}
