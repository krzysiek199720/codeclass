package com.github.krzysiek199720.codeclass.auth.role;

import com.github.krzysiek199720.codeclass.auth.role.api.RoleApi;
import com.github.krzysiek199720.codeclass.auth.role.response.RoleNameDTO;

import java.util.List;

public interface RoleService {

    public List<Role> getAll();

    public Role getById(Long id);

    public List<RoleNameDTO> getAllNames();

    public Role save(Long id, RoleApi api);

    public void delete(Long id);

    public Role findByName(String name);
}
