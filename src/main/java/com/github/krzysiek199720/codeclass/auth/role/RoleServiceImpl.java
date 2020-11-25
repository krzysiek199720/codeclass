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

@Slf4j
@Service
public class RoleServiceImpl implements RoleService{

    private final RoleDAO roleDAO;
    private final PermissionService permissionService;

    @Autowired
    public RoleServiceImpl(RoleDAO roleDAO, PermissionService permissionService) {
        this.roleDAO = roleDAO;
        this.permissionService = permissionService;
    }

    @Transactional
    public List<Role> getAll(){
        return roleDAO.getAll();
    }

    @Transactional
    public Role getById(Long id){
        Role role = roleDAO.getById(id);
        if(role == null)
            throw new NotFoundException("auth.role.notfound");
        return role;
    }

    @Transactional
    public List<RoleNameDTO> getAllNames(){
        return roleDAO.findAllNames();
    }

    @Transactional
    public Role save(Long id, RoleApi api){
        Role role;
        if(id == null){
            role = new Role();
            role.setId(null);
            role.setCreatedAt(LocalDateTime.now());
            role.setIsAdmin(api.getIsAdmin());
        } else{
            role = getById(id);
            if(role == null)
                throw new NotFoundException("auth.role.notfound");
        }

        Set<Permission> permissions;
        if(api.getPermissions() == null || api.getPermissions().isEmpty())
            permissions = new HashSet<>();
        else{
            permissions = permissionService.getAllIn(api.getPermissions());
            if(permissions.size() != api.getPermissions().size())
                throw new NotFoundException("auth.permission.notfound");
        }

        role.setName(api.getName());
        role.setIsAdmin(api.getIsAdmin());
        role.setPermissions(permissions);
        role.setModifiedAt(role.getCreatedAt());

        return roleDAO.save(role);
    }

    @Transactional
    public void delete(Long id){
        Role role = roleDAO.getById(id);
        if(role == null)
            throw new NotFoundException("auth.role.notfound");
        roleDAO.delete(role);
    }

    public Role findByName(String name){
        return roleDAO.findByName(name);
    }
}
