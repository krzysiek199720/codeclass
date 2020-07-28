package com.github.krzysiek199720.codeclass.auth.security;

import com.github.krzysiek199720.codeclass.auth.security.annotation.Secure;
import com.github.krzysiek199720.codeclass.core.exceptions.exception.UnauthorizedException;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(0)
public class Security {

    private final SecurityService securityService;

    @Autowired
    public Security(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Before("@annotation(secure)")
    public void checkSecurity(Secure secure){

        //tmp TODO remove
        System.out.println("\n\nSecure\n\n");


        String permissionVal = secure.value();
        String authToken = securityService.getToken();

        // Authenticate
        // it throws if not authentiacted
        securityService.authenticate(authToken);

        //check permission
        if(securityService.checkPermission(authToken, permissionVal))
            return;
        throw new UnauthorizedException("auth.unauthorized");
    }
}
