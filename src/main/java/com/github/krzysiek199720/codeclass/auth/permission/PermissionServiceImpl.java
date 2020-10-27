package com.github.krzysiek199720.codeclass.auth.permission;

import com.github.krzysiek199720.codeclass.core.exceptions.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class PermissionServiceImpl implements PermissionService{

    private final PermissionDAO permissionDAO;

    @Autowired
    public PermissionServiceImpl(PermissionDAO permissionDAO) {
        this.permissionDAO = permissionDAO;
    }

    public Set<Permission> getAllIn(Set<Long> ids){
        return permissionDAO.getAllIn(ids);
    }

    public Permission getByValue(String value){
        Permission permission = permissionDAO.getByValue(value);

        if(permission == null)
            throw new NotFoundException("auth.permission.notfound");
        return permission;
    }

    @Transactional
    public List<Permission> getAll(){
        return permissionDAO.getAll();
    }
}
