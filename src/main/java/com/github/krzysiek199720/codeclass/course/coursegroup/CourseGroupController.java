package com.github.krzysiek199720.codeclass.course.coursegroup;

import com.github.krzysiek199720.codeclass.auth.accesstoken.AccessToken;
import com.github.krzysiek199720.codeclass.auth.accesstoken.AccessTokenService;
import com.github.krzysiek199720.codeclass.auth.security.annotation.Secure;
import com.github.krzysiek199720.codeclass.auth.user.User;
import com.github.krzysiek199720.codeclass.core.controller.AbstractController;
import com.github.krzysiek199720.codeclass.core.exceptions.exception.UnauthorizedException;
import com.github.krzysiek199720.codeclass.core.exceptions.response.ErrorResponse;
import com.github.krzysiek199720.codeclass.course.course.api.CourseUpdateApi;
import com.github.krzysiek199720.codeclass.course.course.response.CourseResponse;
import com.github.krzysiek199720.codeclass.course.coursegroup.api.CourseGroupSaveApi;
import com.github.krzysiek199720.codeclass.course.coursegroup.response.CourseGroupResponse;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/course/group")
public class CourseGroupController extends AbstractController {

    private final AccessTokenService accessTokenService;
    private final CourseGroupService courseGroupService;

    public CourseGroupController(AccessTokenService accessTokenService, CourseGroupService courseGroupService) {
        this.accessTokenService = accessTokenService;
        this.courseGroupService = courseGroupService;
    }

    //    Get
    @ApiOperation(value = "getCourseGroup", notes = "Get course group")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = CourseGroupResponse.class),

            @ApiResponse(code = 404, message = "course.notfound", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "course.unauthorized", response = ErrorResponse.class),
    })
    @ApiImplicitParam(name = "Authorization", value = "Authorization Token", required = false, allowEmptyValue = false
            , paramType = "header", dataTypeClass = String.class, example = "aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee")
    @GetMapping("/{id}")
    public ResponseEntity<CourseGroupResponse> get(@PathVariable Long id,
                                              @RequestHeader(value = "Authorization",required = false, defaultValue = "") String token){
        boolean isAuthor = false;

        if(!token.isBlank()){
            AccessToken at = accessTokenService.getAccesstokenByToken(token);
            User user = courseGroupService.getUserByCourseGroupId(id);

            if(user.equals(at.getUser()))
                isAuthor = true;
        }

        return okResponse(courseGroupService.getById(id, isAuthor));
    }

    //    save
    @ApiOperation(value = "createCourseGroup", notes = "create coursegroup")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = CourseGroupResponse.class),

            @ApiResponse(code = 404, message = "course.notfound", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "course.unauthorized", response = ErrorResponse.class),

            @ApiResponse(code = 401, message = "auth.token.notfound", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "auth.unauthorized", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "auth.session.expired", response = ErrorResponse.class),
    })
    @ApiImplicitParam(name = "Authorization", value = "Authorization Token", required = true, allowEmptyValue = false
            , paramType = "header", dataTypeClass = String.class, example = "aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee")
    @Secure("course.group.save")
    @PostMapping("/")
    public ResponseEntity<CourseGroupResponse> create(@RequestBody CourseGroupSaveApi api,
                                                 @RequestHeader(value = "Authorization") String token){

        AccessToken at = accessTokenService.getAccesstokenByToken(token);

        return okResponse(courseGroupService.createCourseGroup(api, at.getUser()));
    }

    //    update
    @ApiOperation(value = "updateCourseGroup", notes = "update coursegroup")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = CourseResponse.class),

            @ApiResponse(code = 404, message = "course.notfound", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "course.coursegroup.notfound", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "course.language.notfound", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "course.category.notfound", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "course.unauthorized", response = ErrorResponse.class),

            @ApiResponse(code = 401, message = "auth.token.notfound", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "auth.unauthorized", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "auth.session.expired", response = ErrorResponse.class),
    })
    @ApiImplicitParam(name = "Authorization", value = "Authorization Token", required = true, allowEmptyValue = false
            , paramType = "header", dataTypeClass = String.class, example = "aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee")
    @Secure("course.group.save")
    @PutMapping("/{id}")
    public ResponseEntity<CourseGroupResponse> update(@PathVariable Long id,
                                                 @RequestBody CourseGroupSaveApi api,
                                                 @RequestHeader(value = "Authorization") String token){

        AccessToken at = accessTokenService.getAccesstokenByToken(token);
        User userAuthor = courseGroupService.getUserByCourseGroupId(id);

        if(!userAuthor.equals(at.getUser()))
            throw new UnauthorizedException("course.group.save");

        return okResponse(courseGroupService.updateCourseGroup(id, api));
    }

    //    delete
    @ApiOperation(value = "deleteUser", notes = "Delete user")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "NO_CONTENT"),
            @ApiResponse(code = 400, message = "course.coursegroup.notfound", response = ErrorResponse.class),

            @ApiResponse(code = 401, message = "auth.token.notfound", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "auth.unauthorized", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "auth.session.expired", response = ErrorResponse.class),
    })
    @ApiImplicitParam(name = "Authorization", value = "Authorization Token", required = true, allowEmptyValue = false
            , paramType = "header", dataTypeClass = String.class, example = "aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee")
    @Secure("course.group.delete")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@RequestHeader(value = "Authorization") String token, @PathVariable Long id){

        AccessToken at = accessTokenService.getAccesstokenByToken(token);
        User userAuthor = courseGroupService.getUserByCourseGroupId(id);

        if(!userAuthor.equals(at.getUser()))
            throw new UnauthorizedException("course.delete");

        courseGroupService.delete(id);

        return noContent();
    }
}
