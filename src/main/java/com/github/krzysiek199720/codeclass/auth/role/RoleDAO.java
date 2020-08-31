package com.github.krzysiek199720.codeclass.auth.role;

import com.github.krzysiek199720.codeclass.auth.role.response.RoleNameDTO;
import com.github.krzysiek199720.codeclass.core.db.DAO;

import java.util.List;

public interface RoleDAO extends DAO<Role>{

    public Role findByName(String name);

    public Role getDefaultRole();

    public List<RoleNameDTO> findAllNames();

    public List<Role> getAll();
}
