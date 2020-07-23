package com.github.krzysiek199720.codeclass.auth.role;

import com.github.krzysiek199720.codeclass.auth.role.api.RoleApi;
import com.github.krzysiek199720.codeclass.auth.security.annotation.Secure;
import com.github.krzysiek199720.codeclass.core.controller.AbstractController;
import com.github.krzysiek199720.codeclass.core.exceptions.response.ErrorResponse;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags={"Role"})

@RestController
@RequestMapping("/auth/role")
public class RoleController extends AbstractController {

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @ApiOperation(value = "getRole", notes = "Get role")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Role.class),
            @ApiResponse(code = 404, message = "auth.role.notfound", response = ErrorResponse.class),

            @ApiResponse(code = 401, message = "auth.token.notfound", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "auth.unauthorized", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "auth.session.expired", response = ErrorResponse.class),
    })
    @ApiImplicitParam(name = "Authorization", value = "Authorization Token", required = true, allowEmptyValue = false
            , paramType = "header", dataTypeClass = String.class, example = "aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee")
    @GetMapping("/{id}")
    @Secure("role.get")
    public ResponseEntity<Role> get(@PathVariable Long id){

        return okResponse(roleService.getById(id));
    }

    @ApiOperation(value = "getRole", notes = "Get role")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Role.class, responseContainer = "List"),

            @ApiResponse(code = 401, message = "auth.token.notfound", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "auth.unauthorized", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "auth.session.expired", response = ErrorResponse.class),
    })
    @ApiImplicitParam(name = "Authorization", value = "Authorization Token", required = true, allowEmptyValue = false
            , paramType = "header", dataTypeClass = String.class, example = "aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee")
    @GetMapping("/")
    @Secure("role.get")
    public ResponseEntity<List<Role>> getAll(){

        return okResponse(roleService.getAll());
    }

    @ApiOperation(value = "createRole", notes = "Create role")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "CREATED", response = Role.class),
            @ApiResponse(code = 404, message = "auth.role.notfound", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "auth.permission.notfound", response = ErrorResponse.class),

            @ApiResponse(code = 401, message = "auth.token.notfound", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "auth.unauthorized", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "auth.session.expired", response = ErrorResponse.class),
    })
    @ApiImplicitParam(name = "Authorization", value = "Authorization Token", required = true, allowEmptyValue = false
            , paramType = "header", dataTypeClass = String.class, example = "aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee")
    @PostMapping("/")
    @Secure("role.create")
    public ResponseEntity<Role> create(@RequestBody RoleApi api){

        Role role = roleService.save(null, api);

        return createdResponse(role);
    }

    @ApiOperation(value = "updateRole", notes = "Update role")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Role.class),
            @ApiResponse(code = 404, message = "auth.role.notfound", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "auth.permission.notfound", response = ErrorResponse.class),

            @ApiResponse(code = 401, message = "auth.token.notfound", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "auth.unauthorized", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "auth.session.expired", response = ErrorResponse.class),
    })
    @ApiImplicitParam(name = "Authorization", value = "Authorization Token", required = true, allowEmptyValue = false
            , paramType = "header", dataTypeClass = String.class, example = "aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee")
    @PutMapping("/{id}")
    @Secure("role.update")
    public ResponseEntity<Role> update(@PathVariable Long id, @RequestBody RoleApi api){

        return okResponse(roleService.save(id, api));
    }

    @ApiOperation(value = "deleteRole", notes = "Delete role")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "NO_CONTENT"),
            @ApiResponse(code = 404, message = "auth.role.notfound", response = ErrorResponse.class),

            @ApiResponse(code = 401, message = "auth.token.notfound", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "auth.unauthorized", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "auth.session.expired", response = ErrorResponse.class),
    })
    @ApiImplicitParam(name = "Authorization", value = "Authorization Token", required = true, allowEmptyValue = false
            , paramType = "header", dataTypeClass = String.class, example = "aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee")
    @DeleteMapping("/{id}")
    @Secure("role.delete")
    public ResponseEntity<Object> delete(@PathVariable Long id){

        roleService.delete(id);

        return noContent();
    }
}
