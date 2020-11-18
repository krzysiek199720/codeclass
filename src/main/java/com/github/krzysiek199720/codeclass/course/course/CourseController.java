package com.github.krzysiek199720.codeclass.course.course;

import com.github.krzysiek199720.codeclass.auth.accesstoken.AccessToken;
import com.github.krzysiek199720.codeclass.auth.accesstoken.AccessTokenService;
import com.github.krzysiek199720.codeclass.auth.security.annotation.Secure;
import com.github.krzysiek199720.codeclass.auth.user.User;
import com.github.krzysiek199720.codeclass.core.controller.AbstractController;
import com.github.krzysiek199720.codeclass.core.exceptions.exception.UnauthorizedException;
import com.github.krzysiek199720.codeclass.core.exceptions.response.ErrorResponse;
import com.github.krzysiek199720.codeclass.course.course.api.CourseCreateApi;
import com.github.krzysiek199720.codeclass.course.course.api.CourseUpdateApi;
import com.github.krzysiek199720.codeclass.course.course.response.CourseResponse;
import com.github.krzysiek199720.codeclass.course.coursedata.CourseDataService;
import com.github.krzysiek199720.codeclass.course.coursegroup.CourseGroupService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Api(tags={"Course"})

@RestController
@RequestMapping("/course")
public class CourseController extends AbstractController {

    private final CourseService courseService;
    private final CourseDataService courseDataService;
    private final AccessTokenService accessTokenService;

    private final CourseGroupService courseGroupService;

    @Autowired
    public CourseController(CourseService courseService, CourseDataService courseDataService, AccessTokenService accessTokenService, CourseGroupService courseGroupService) {
        this.courseService = courseService;
        this.courseDataService = courseDataService;
        this.accessTokenService = accessTokenService;
        this.courseGroupService = courseGroupService;
    }


//    Get
    @ApiOperation(value = "getCourse", notes = "Get course")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = CourseResponse.class),

            @ApiResponse(code = 404, message = "course.notfound", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "course.unauthorized", response = ErrorResponse.class),
    })
    @ApiImplicitParam(name = "Authorization", value = "Authorization Token", required = false, allowEmptyValue = false
            , paramType = "header", dataTypeClass = String.class, example = "aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee")
    @GetMapping("/{id}")
    public ResponseEntity<CourseResponse> get(@PathVariable Long id,
                                              @RequestHeader(value = "Authorization",required = false, defaultValue = "") String token){

        User user = null;
        if(!token.isBlank())
            user = accessTokenService.getAccesstokenByToken(token).getUser();

        return okResponse(courseService.getById(id, user));
    }

//    save
    @ApiOperation(value = "createCourse", notes = "Create course")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = CourseResponse.class),

            @ApiResponse(code = 404, message = "course.notfound", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "course.coursegroup.notfound", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "course.language.notfound", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "course.category.notfound", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "course.unauthorized", response = ErrorResponse.class),

            @ApiResponse(code = 401, message = "auth.token.notfound", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "course.unauthorized", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "auth.session.expired", response = ErrorResponse.class),
    })
    @ApiImplicitParam(name = "Authorization", value = "Authorization Token", required = true, allowEmptyValue = false
            , paramType = "header", dataTypeClass = String.class, example = "aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee")
    @Secure(value = "course.save", exceptionMessage = "course.unauthorized")
    @PostMapping("/")
    public ResponseEntity<CourseResponse> create(@RequestBody CourseCreateApi api,
                                              @RequestHeader(value = "Authorization") String token){

        AccessToken at = accessTokenService.getAccesstokenByToken(token);
        User userAuthor = courseGroupService.getUserByCourseGroupId(api.getCourseGroupId());

        if(!userAuthor.equals(at.getUser()))
            throw new UnauthorizedException("course.save");


        return okResponse(courseService.createCourse(api));
    }

//    update
    @ApiOperation(value = "updateCourse", notes = "Update course")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = CourseResponse.class),

            @ApiResponse(code = 404, message = "course.notfound", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "course.coursegroup.notfound", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "course.language.notfound", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "course.category.notfound", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "course.unauthorized", response = ErrorResponse.class),

            @ApiResponse(code = 401, message = "auth.token.notfound", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "course.unauthorized", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "auth.session.expired", response = ErrorResponse.class),
    })
    @ApiImplicitParam(name = "Authorization", value = "Authorization Token", required = true, allowEmptyValue = false
            , paramType = "header", dataTypeClass = String.class, example = "aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee")
    @Secure(value = "course.save", exceptionMessage = "course.unauthorized")
    @PutMapping("/{id}")
    public ResponseEntity<CourseResponse> update(@PathVariable Long id,
                                                 @RequestBody CourseUpdateApi api,
                                                 @RequestHeader(value = "Authorization") String token){

        AccessToken at = accessTokenService.getAccesstokenByToken(token);
        User userAuthor = courseGroupService.getUserByCourseId(id);

        if(!userAuthor.equals(at.getUser()))
            throw new UnauthorizedException("course.save");

        return okResponse(courseService.updateCourse(id, api));
    }

//    delete
    @ApiOperation(value = "deleteUser", notes = "Delete user")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "NO_CONTENT"),
            @ApiResponse(code = 400, message = "auth.user.notfound", response = ErrorResponse.class),

            @ApiResponse(code = 401, message = "auth.token.notfound", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "course.unauthorized", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "auth.session.expired", response = ErrorResponse.class),
    })
    @ApiImplicitParam(name = "Authorization", value = "Authorization Token", required = true, allowEmptyValue = false
            , paramType = "header", dataTypeClass = String.class, example = "aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee")
    @Secure(value = "course.delete", exceptionMessage = "course.unauthorized")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@RequestHeader(value = "Authorization") String token, @PathVariable Long id){

        AccessToken at = accessTokenService.getAccesstokenByToken(token);
        User userAuthor = courseGroupService.getUserByCourseId(id);

        if(!userAuthor.equals(at.getUser()))
            throw new UnauthorizedException("course.delete");

        courseService.delete(id);

        return noContent();
    }

//    publish - on/off
    @ApiOperation(value = "coursedataSave", notes = "Save course data")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = LocalDateTime.class),

            @ApiResponse(code = 401, message = "course.unauthorized", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "course.notfound", response = ErrorResponse.class),

            @ApiResponse(code = 401, message = "auth.token.notfound", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "auth.session.expired", response = ErrorResponse.class)
    })
    @ApiImplicitParam(name = "Authorization", value = "Authorization Token", required = true, allowEmptyValue = false
            , paramType = "header", dataTypeClass = String.class, example = "aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee")
    @Secure(value = "course.publish", exceptionMessage = "course.unauthorized")
    @PutMapping("/{id}/publish")
    public ResponseEntity<Object> publish(@PathVariable Long id, Boolean isPublished, @RequestHeader(value = "Authorization") String token){

        AccessToken at = accessTokenService.getAccesstokenByToken(token);

        LocalDateTime isPublishedRes = courseService.publish(id, at.getUser(), isPublished);

        return okResponse(isPublishedRes);
    }

}
