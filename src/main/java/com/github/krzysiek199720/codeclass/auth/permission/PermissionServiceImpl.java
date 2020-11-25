package com.github.krzysiek199720.codeclass.auth.permission;

import com.github.krzysiek199720.codeclass.auth.permission.response.PermissionResponse;
import com.github.krzysiek199720.codeclass.core.exceptions.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    public List<PermissionResponse> getAll(){
        List<Permission> permissionList = permissionDAO.getAllOrdered();
        List<PermissionResponse> response = new ArrayList<>();

        boolean start = true;
        PermissionResponse prevRes = null;
        String prevGroup = null;
        for(Permission p : permissionList){
            if(p.getGroup().equals(prevGroup)){
                prevRes.getPermissions().add(p);
                continue;
            }
            if(!start)
                response.add(prevRes);
            else
                start = false;

            prevRes = new PermissionResponse();
            prevGroup = p.getGroup();

            prevRes.setGroupName(p.getGroup());
            prevRes.setPermissions(new ArrayList<>());
            prevRes.getPermissions().add(p);
        }
        return response;
    }
}
