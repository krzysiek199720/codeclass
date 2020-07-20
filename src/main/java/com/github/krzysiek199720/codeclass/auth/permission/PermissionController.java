package com.github.krzysiek199720.codeclass.auth.permission;

import com.github.krzysiek199720.codeclass.auth.security.annotation.Secure;
import com.github.krzysiek199720.codeclass.core.controller.AbstractController;
import com.github.krzysiek199720.codeclass.core.exceptions.response.ErrorResponse;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/auth/permissions")
public class PermissionController extends AbstractController {

    private final PermissionService permissionService;

    @Autowired
    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @ApiOperation(value = "GetAllPermissions", notes = "Get all permissions")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Permission.class, responseContainer = "List"),

            @ApiResponse(code = 401, message = "auth.token.notfound", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "auth.unauthorized", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "auth.session.expired", response = ErrorResponse.class),
    })
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, allowEmptyValue = false
            , paramType = "header", dataTypeClass = String.class, example = "aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee")
    @GetMapping("/")
    @Secure("auth.permission.get")
    public ResponseEntity<List<Permission>> getAllPermissions(){
        return okResponse(permissionService.getAll());
    }
}
