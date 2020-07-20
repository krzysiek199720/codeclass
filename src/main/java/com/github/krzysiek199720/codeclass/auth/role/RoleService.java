package com.github.krzysiek199720.codeclass.auth.role;

import com.github.krzysiek199720.codeclass.auth.permission.Permission;
import com.github.krzysiek199720.codeclass.auth.permission.PermissionService;
import com.github.krzysiek199720.codeclass.auth.role.api.RoleApi;
import com.github.krzysiek199720.codeclass.auth.role.response.RoleNameDTO;
import com.github.krzysiek199720.codeclass.core.exceptions.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface RoleService {

    public List<Role> getAll();

    public Role getById(Long id);

    public List<RoleNameDTO> getAllNames();

    public Role save(Long id, RoleApi api);

    public void delete(Long id);

    public Role findByName(String name);
}
