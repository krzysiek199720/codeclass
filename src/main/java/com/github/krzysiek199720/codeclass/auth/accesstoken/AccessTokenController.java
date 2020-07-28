package com.github.krzysiek199720.codeclass.auth.accesstoken;

import com.github.krzysiek199720.codeclass.auth.accesstoken.api.LogInApi;
import com.github.krzysiek199720.codeclass.auth.accesstoken.response.LogInResponse;
import com.github.krzysiek199720.codeclass.core.controller.AbstractController;
import com.github.krzysiek199720.codeclass.core.exceptions.response.ErrorResponse;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags={"Authentication"})

@RestController
@RequestMapping("/auth")
public class AccessTokenController extends AbstractController {

    @Autowired
    private AccessTokenService accessTokenService;


    @ApiOperation(value = "logIn", notes = "Log in")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = LogInResponse.class),
            @ApiResponse(code = 401, message = "auth.bad_credentials", response = ErrorResponse.class)
    })
    @PostMapping("/login")
    public LogInResponse logIn(@RequestBody LogInApi api){
        return accessTokenService.logIn(api);
    }


    @ApiOperation(value = "logOut", notes = "Log out")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "NO_CONTENT"),
            @ApiResponse(code = 404, message = "auth.token.notfound", response = ErrorResponse.class)
    })
    @ApiImplicitParam(name = "Authorization", value = "Authorization Token", required = true, allowEmptyValue = false
            , paramType = "header", dataTypeClass = String.class, example = "aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee")
    @GetMapping("/logout")
    public ResponseEntity<Object> logOut(@RequestHeader(value = "Authorization") String token){
        accessTokenService.logOut(token);
        return noContent();
    }

    @ApiOperation(value = "logOut", notes = "Log out")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "NO_CONTENT"),
            @ApiResponse(code = 404, message = "auth.token.notfound", response = ErrorResponse.class)
    })
    @ApiImplicitParam(name = "Authorization", value = "Authorization Token", required = true, allowEmptyValue = false
            , paramType = "header", dataTypeClass = String.class, example = "aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee")
    @GetMapping("/logout/all")
    public ResponseEntity<Object> logOutAll(@RequestHeader(value = "Authorization") String token){
        accessTokenService.logOutAll(token);
        return noContent();
    }
}
