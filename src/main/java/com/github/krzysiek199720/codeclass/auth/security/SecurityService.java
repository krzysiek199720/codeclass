package com.github.krzysiek199720.codeclass.auth.security;

import com.github.krzysiek199720.codeclass.auth.accesstoken.AccessToken;
import com.github.krzysiek199720.codeclass.auth.accesstoken.AccessTokenService;
import com.github.krzysiek199720.codeclass.auth.permission.Permission;
import com.github.krzysiek199720.codeclass.auth.permission.PermissionService;
import com.github.krzysiek199720.codeclass.auth.role.Role;
import com.github.krzysiek199720.codeclass.core.exceptions.exception.SessionExpiredException;
import com.github.krzysiek199720.codeclass.core.exceptions.exception.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Component
public class SecurityService {

    private final AccessTokenService accessTokenService;

    private final PermissionService permissionService;

    @Autowired
    public SecurityService(AccessTokenService accessTokenService, PermissionService permissionService) {
        this.accessTokenService = accessTokenService;
        this.permissionService = permissionService;
    }

    @Transactional
    public String getToken(){
        String authToken;
        try {
            HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
            authToken = request.getHeader("Authorization");
            if (authToken == null || authToken.isBlank())
                throw new UnauthorizedException("auth.token.notfound");
        }
        catch (Exception exc){
            throw new UnauthorizedException("auth.token.notfound");
        }
        return authToken;
    }

    @Transactional
    public void authenticate(String token){
        if( ! accessTokenService.isSessionActive(token))
            throw new SessionExpiredException();
    }

    @Transactional
    public boolean checkPermission(String token, String perm){
        AccessToken at = accessTokenService.getAccesstokenByToken(token);
        //only authentication required
        if(perm.isEmpty())
            return true;

        Permission permission = permissionService.getByValue(perm);

        Role role = at.getUser().getRole();

        return role.getPermissions().contains(permission);
    }

    @Transactional
    public boolean checkPermission(AccessToken at, String perm){
        //only authentication required
        if(perm.isEmpty())
            return true;

        Permission permission = permissionService.getByValue(perm);

        Role role = at.getUser().getRole();

        return role.getPermissions().contains(permission);
    }
}
