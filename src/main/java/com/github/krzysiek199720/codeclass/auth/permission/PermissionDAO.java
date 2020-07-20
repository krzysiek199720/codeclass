package com.github.krzysiek199720.codeclass.auth.permission;

import com.github.krzysiek199720.codeclass.core.db.DAO;

import java.util.Set;

public interface PermissionDAO extends DAO<Permission> {

    public Permission getByValue(String value);

    public Set<Permission> getAllIn(Set<Long> ids);

}
