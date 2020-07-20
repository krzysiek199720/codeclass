package com.github.krzysiek199720.codeclass.auth.role;

import com.github.krzysiek199720.codeclass.auth.role.response.RoleNameDTO;
import com.github.krzysiek199720.codeclass.core.db.DAO;

import java.util.List;

public interface RoleDAO extends DAO<Role>{

    public Role findByName(String name);

    public List<RoleNameDTO> findAllNames();
}
