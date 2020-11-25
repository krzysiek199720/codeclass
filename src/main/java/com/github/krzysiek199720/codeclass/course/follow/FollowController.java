package com.github.krzysiek199720.codeclass.course.follow;

import com.github.krzysiek199720.codeclass.auth.accesstoken.AccessToken;
import com.github.krzysiek199720.codeclass.auth.accesstoken.AccessTokenService;
import com.github.krzysiek199720.codeclass.auth.security.annotation.Secure;
import com.github.krzysiek199720.codeclass.core.controller.AbstractController;
import com.github.krzysiek199720.codeclass.core.exceptions.response.ErrorResponse;
import com.github.krzysiek199720.codeclass.course.follow.response.FollowResponse;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags={"Follow"})

@RestController
@RequestMapping("/course")
public class FollowController extends AbstractController {

    private final FollowService followService;
    private final AccessTokenService accessTokenService;

    public FollowController(FollowService followService, AccessTokenService accessTokenService) {
        this.followService = followService;
        this.accessTokenService = accessTokenService;
    }


    @ApiOperation(value = "getFollows", notes = "Get all follows")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = FollowResponse.class, responseContainer = "List"),

            @ApiResponse(code = 401, message = "auth.token.notfound", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "course.follow.unauthorized", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "auth.session.expired", response = ErrorResponse.class),
    })
    @Secure(value = "", exceptionMessage = "course.follow.unauthorized")
    @GetMapping("/follow")
    public ResponseEntity<List<FollowResponse>> getByCourse(@RequestHeader(value = "Authorization") String token){
        AccessToken at = accessTokenService.getAccesstokenByToken(token);
        return okResponse(followService.getAll(at.getUser()));
    }

    //    save
    @ApiOperation(value = "saveFollowByCourse", notes = "save follow by course")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "NO_CONTENT"),

            @ApiResponse(code = 401, message = "course.group.notfound", response = ErrorResponse.class),

            @ApiResponse(code = 401, message = "auth.token.notfound", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "course.follow.unauthorized", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "auth.session.expired", response = ErrorResponse.class),
    })
    @ApiImplicitParam(name = "Authorization", value = "Authorization Token", required = true, allowEmptyValue = false
            , paramType = "header", dataTypeClass = String.class, example = "aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee")
    @Secure(value = "", exceptionMessage = "course.follow.unauthorized")
    @PutMapping("/{id}/follow")
    public ResponseEntity<Object> saveByCourse(@PathVariable("id") Long courseId, Boolean doFollow, @RequestHeader(value = "Authorization") String token){

        AccessToken at = accessTokenService.getAccesstokenByToken(token);

        followService.saveFollowByCourse(courseId, doFollow, at.getUser());

        return noContent();
    }

    @ApiOperation(value = "saveFollowByCourseGroup", notes = "save follow by course group")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "NO_CONTENT"),

            @ApiResponse(code = 401, message = "course.group.notfound", response = ErrorResponse.class),

            @ApiResponse(code = 401, message = "auth.token.notfound", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "course.follow.unauthorized", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "auth.session.expired", response = ErrorResponse.class),
    })
    @ApiImplicitParam(name = "Authorization", value = "Authorization Token", required = true, allowEmptyValue = false
            , paramType = "header", dataTypeClass = String.class, example = "aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee")
    @Secure(value = "", exceptionMessage = "course.follow.unauthorized")
    @PostMapping("/group/{id}/follow")
    public ResponseEntity<Object> saveByCourseGroup(@PathVariable("id") Long courseGroupId, Boolean doFollow, @RequestHeader(value = "Authorization") String token){

        AccessToken at = accessTokenService.getAccesstokenByToken(token);

        followService.saveFollowByCourseGroup(courseGroupId, doFollow, at.getUser());

        return noContent();
    }


}
