package com.github.krzysiek199720.codeclass.auth.user;

import com.github.krzysiek199720.codeclass.auth.accesstoken.AccessToken;
import com.github.krzysiek199720.codeclass.auth.accesstoken.AccessTokenService;
import com.github.krzysiek199720.codeclass.auth.security.SecurityService;
import com.github.krzysiek199720.codeclass.auth.security.annotation.Secure;
import com.github.krzysiek199720.codeclass.auth.user.api.ChangeEmailApi;
import com.github.krzysiek199720.codeclass.auth.user.api.ChangePasswordApi;
import com.github.krzysiek199720.codeclass.auth.user.api.SignUpApi;
import com.github.krzysiek199720.codeclass.auth.user.response.UserResponse;
import com.github.krzysiek199720.codeclass.core.controller.AbstractController;
import com.github.krzysiek199720.codeclass.core.exceptions.exception.SessionExpiredException;
import com.github.krzysiek199720.codeclass.core.exceptions.exception.UnauthorizedException;
import com.github.krzysiek199720.codeclass.core.exceptions.response.ErrorResponse;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Api(tags={"User"})

@RestController
@RequestMapping("/user")
public class UserController extends AbstractController {

    private final UserService userService;
    private final SecurityService securityService;
    private final AccessTokenService accessTokenService;

    @Autowired
    public UserController(UserService userService, SecurityService securityService, AccessTokenService accessTokenService) {
        this.userService = userService;
        this.securityService = securityService;
        this.accessTokenService = accessTokenService;
    }

    @ApiOperation(value = "getUser", notes = "Get user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = UserResponse.class),
            @ApiResponse(code = 404, message = "user.notfound", response = ErrorResponse.class),

            @ApiResponse(code = 401, message = "auth.token.notfound", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "auth.unauthorized", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "auth.session.expired", response = ErrorResponse.class),
    })
    @ApiImplicitParam(name = "Authorization", value = "Authorization Token", required = true, allowEmptyValue = false
            , paramType = "header", dataTypeClass = String.class, example = "aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee")
    @GetMapping("/{id}")
    public ResponseEntity<User> get(@PathVariable Long id, @RequestHeader(value = "Authorization") String token){

        AccessToken at = accessTokenService.getAccesstokenByToken(token);
        if(!accessTokenService.isSessionActive(at))
            throw new SessionExpiredException();;

        if(!at.getUser().getId().equals(id))
            if(!securityService.checkPermission(at, "user.get"))
                throw new UnauthorizedException("auth.unauthorized");

        User ret = userService.getById(id);
        return okResponse(ret);
    }

    @ApiOperation(value = "getUsers", notes = "Get users")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = UserResponse.class, responseContainer = "List"),

            @ApiResponse(code = 401, message = "auth.token.notfound", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "auth.unauthorized", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "auth.session.expired", response = ErrorResponse.class),
    })
    @ApiImplicitParam(name = "Authorization", value = "Authorization Token", required = true, allowEmptyValue = false
            , paramType = "header", dataTypeClass = String.class, example = "aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee")
    @GetMapping("")

    @Secure("user.get")
    public ResponseEntity<List<UserResponse>> getAll(@RequestParam String searchQuery,
                                                     @RequestParam(required = false) Long roleId){

        List<UserResponse> ret = userService.getAll(searchQuery, roleId).stream().map(User::toUserResponse).collect(Collectors.toList());
        return okResponse(ret);
    }

    @ApiOperation(value = "signUp", notes = "Sign up")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "CREATED", response = UserResponse.class),
            @ApiResponse(code = 400, message = "auth.user.email.taken", response = ErrorResponse.class),
    })
    @PostMapping("")
    public ResponseEntity<UserResponse> create(@Valid @RequestBody SignUpApi api){
        User user = userService.signUp(api);

        return createdResponse(user.toUserResponse());
    }

    @ApiOperation(value = "updateUsersRole", notes = "Update users role")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = UserResponse.class),
            @ApiResponse(code = 404, message = "auth.user.notfound", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "auth.role.notfound", response = ErrorResponse.class),

            @ApiResponse(code = 401, message = "auth.token.notfound", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "auth.unauthorized", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "auth.session.expired", response = ErrorResponse.class),
    })
    @ApiImplicitParam(name = "Authorization", value = "Authorization Token", required = true, allowEmptyValue = false
            , paramType = "header", dataTypeClass = String.class, example = "aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee")
    @PutMapping("/{userId}/role")

    @Secure("user.role.update")
    public ResponseEntity<UserResponse> updateRole(@PathVariable Long userId, @RequestBody Long roleId){

        User user = userService.changeRole(userId, roleId);
        return okResponse(user.toUserResponse());
    }

    @ApiOperation(value = "updateUsersPassword", notes = "Update users password")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "NO_CONTENT", response = UserResponse.class),
            @ApiResponse(code = 404, message = "user.notfound", response = ErrorResponse.class),
            @ApiResponse(code = 400, message = "auth.user.unauthorized", response = ErrorResponse.class),

            @ApiResponse(code = 401, message = "auth.token.notfound", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "auth.unauthorized", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "auth.session.expired", response = ErrorResponse.class),
    })
    @ApiImplicitParam(name = "Authorization", value = "Authorization Token", required = true, allowEmptyValue = false
            , paramType = "header", dataTypeClass = String.class, example = "aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee")
    @PutMapping("/{userId}/password")
    public ResponseEntity<Object> updatePassword(@RequestHeader(value = "Authorization") String token,
                                                       @PathVariable Long userId, @RequestBody ChangePasswordApi api){

        AccessToken at = accessTokenService.getAccesstokenByToken(token);
        if(!accessTokenService.isSessionActive(at))
            throw new SessionExpiredException();

        if(!at.getUser().getId().equals(userId))
            throw new UnauthorizedException("auth.unauthorized");

        userService.changePassword(userId, api);

        return noContent();
    }

    @ApiOperation(value = "updateUsersEmail", notes = "Update users email")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = UserResponse.class),
            @ApiResponse(code = 400, message = "auth.user.email.taken", response = ErrorResponse.class),
            @ApiResponse(code = 400, message = "auth.user.notfound", response = ErrorResponse.class),

            @ApiResponse(code = 401, message = "auth.token.notfound", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "auth.unauthorized", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "auth.session.expired", response = ErrorResponse.class),
    })
    @ApiImplicitParam(name = "Authorization", value = "Authorization Token", required = true, allowEmptyValue = false
            , paramType = "header", dataTypeClass = String.class, example = "aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee")
    @PutMapping("/{userId}/email")
    public ResponseEntity<UserResponse> updateEmail(@RequestHeader(value = "Authorization") String token,
                                                    @PathVariable Long userId, @RequestBody ChangeEmailApi api){

        AccessToken at = accessTokenService.getAccesstokenByToken(token);
        if(!accessTokenService.isSessionActive(at))
            throw new SessionExpiredException();

        if(!at.getUser().getId().equals(userId))
            throw new UnauthorizedException("auth.unauthorized");

        User user = userService.changeEmail(userId, api);

        return okResponse(user.toUserResponse());
    }

    @ApiOperation(value = "deleteUser", notes = "Delete user")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "NO_CONTENT"),
            @ApiResponse(code = 400, message = "auth.user.notfound", response = ErrorResponse.class),

            @ApiResponse(code = 401, message = "auth.token.notfound", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "auth.unauthorized", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "auth.session.expired", response = ErrorResponse.class),
    })
    @ApiImplicitParam(name = "Authorization", value = "Authorization Token", required = true, allowEmptyValue = false
            , paramType = "header", dataTypeClass = String.class, example = "aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee")
    @DeleteMapping("/{userId}")
    public ResponseEntity<Object> delete(@RequestHeader(value = "Authorization") String token, @PathVariable Long userId){

        AccessToken at = accessTokenService.getAccesstokenByToken(token);
        if(!accessTokenService.isSessionActive(at))
            throw new SessionExpiredException();

        if(!at.getUser().getId().equals(userId))
            if(!securityService.checkPermission(at, "user.delete"))
                throw new UnauthorizedException("auth.unauthorized");

        userService.delete(userId);

        return noContent();
    }
}
