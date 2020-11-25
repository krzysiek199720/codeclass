package com.github.krzysiek199720.codeclass.auth.permission;

import com.github.krzysiek199720.codeclass.core.db.DAO;

import java.util.List;
import java.util.Set;

public interface PermissionDAO extends DAO<Permission> {

    public Permission getByValue(String value);

    public Set<Permission> getAllIn(Set<Long> ids);

    public List<Permission> getAll();
    public List<Permission> getAllOrdered();

}
