package com.github.krzysiek199720.codeclass.course.course;

import com.github.krzysiek199720.codeclass.auth.accesstoken.AccessToken;
import com.github.krzysiek199720.codeclass.auth.accesstoken.AccessTokenService;
import com.github.krzysiek199720.codeclass.auth.security.annotation.Secure;
import com.github.krzysiek199720.codeclass.auth.user.User;
import com.github.krzysiek199720.codeclass.auth.user.response.UserResponse;
import com.github.krzysiek199720.codeclass.core.controller.AbstractController;
import com.github.krzysiek199720.codeclass.core.exceptions.exception.SessionExpiredException;
import com.github.krzysiek199720.codeclass.core.exceptions.exception.UnauthorizedException;
import com.github.krzysiek199720.codeclass.core.exceptions.response.ErrorResponse;
import com.github.krzysiek199720.codeclass.course.course.api.CourseCreateApi;
import com.github.krzysiek199720.codeclass.course.course.api.CourseUpdateApi;
import com.github.krzysiek199720.codeclass.course.course.coursedata.CourseData;
import com.github.krzysiek199720.codeclass.course.course.coursedata.CourseDataService;
import com.github.krzysiek199720.codeclass.course.course.coursedata.parser.exception.response.CourseDataParserParseErrorResponse;
import com.github.krzysiek199720.codeclass.course.course.coursedata.parser.exception.response.CourseDataParserTokenizerErrorResponse;
import com.github.krzysiek199720.codeclass.course.course.coursegroup.CourseGroupService;
import com.github.krzysiek199720.codeclass.course.course.response.CourseResponse;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        boolean isAuthor = false;

        if(!token.isBlank()){
            AccessToken at = accessTokenService.getAccesstokenByToken(token);
            User user = courseGroupService.getUserByCourseId(id);

            if(user.equals(at.getUser()))
                isAuthor = true;
        }

        return okResponse(courseService.getById(id, isAuthor));
    }

//    save
    @ApiOperation(value = "getCourse", notes = "Get course")
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
    @ApiImplicitParam(name = "Authorization", value = "Authorization Token", required = false, allowEmptyValue = false
            , paramType = "header", dataTypeClass = String.class, example = "aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee")
    @Secure("course.save")
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
    @ApiOperation(value = "getCourse", notes = "Get course")
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
    @ApiImplicitParam(name = "Authorization", value = "Authorization Token", required = false, allowEmptyValue = false
            , paramType = "header", dataTypeClass = String.class, example = "aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee")
    @Secure("course.save")
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
            @ApiResponse(code = 401, message = "auth.unauthorized", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "auth.session.expired", response = ErrorResponse.class),
    })
    @ApiImplicitParam(name = "Authorization", value = "Authorization Token", required = true, allowEmptyValue = false
            , paramType = "header", dataTypeClass = String.class, example = "aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee")
    @Secure("course.delete")
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
            @ApiResponse(code = 204, message = "NO_CONTENT"),

            @ApiResponse(code = 401, message = "course.unauthorized", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "course.notfound", response = ErrorResponse.class),

            @ApiResponse(code = 401, message = "auth.token.notfound", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "auth.session.expired", response = ErrorResponse.class)
    })
    @ApiImplicitParam(name = "Authorization", value = "Authorization Token", required = true, allowEmptyValue = false
            , paramType = "header", dataTypeClass = String.class, example = "aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee")
    @Secure("")
    @PutMapping("/publish")
    public ResponseEntity<Object> publish(Long courseId, Boolean isPublished, @RequestHeader(value = "Authorization") String token){

        AccessToken at = accessTokenService.getAccesstokenByToken(token);
        User user = courseGroupService.getUserByCourseId(courseId);

        if(!user.equals(at.getUser()))
            throw new UnauthorizedException("course.unauthorized");

        courseService.publish(courseId, isPublished);

        return noContent();
    }



// -----COURSE DATA -----
    @ApiOperation(value = "coursedataCheck", notes = "Check course data")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = CourseData.class, responseContainer = "List"),
            @ApiResponse(code = 401, message = "course.data.tokenize.error", response = CourseDataParserTokenizerErrorResponse.class),
            @ApiResponse(code = 401, message = "course.data.parse.error", response = CourseDataParserParseErrorResponse.class),

            @ApiResponse(code = 401, message = "auth.token.notfound", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "auth.session.expired", response = ErrorResponse.class)
    })
    @ApiImplicitParam(name = "Authorization", value = "Authorization Token", required = true, allowEmptyValue = false
            , paramType = "header", dataTypeClass = String.class, example = "aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee")
    @Secure("")
    @PostMapping("/data/check")
    public ResponseEntity<List<CourseData>> checkCourseData(String input){
        return okResponse(courseDataService.parseCourseData(input));
    }

    @ApiOperation(value = "coursedataSave", notes = "Save course data")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = CourseData.class, responseContainer = "List"),

            @ApiResponse(code = 401, message = "course.unauthorized", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "course.notfound", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "course.data.tokenize.error", response = CourseDataParserTokenizerErrorResponse.class),
            @ApiResponse(code = 401, message = "course.data.parse.error", response = CourseDataParserParseErrorResponse.class),

            @ApiResponse(code = 401, message = "auth.token.notfound", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "auth.session.expired", response = ErrorResponse.class)
    })
    @ApiImplicitParam(name = "Authorization", value = "Authorization Token", required = true, allowEmptyValue = false
            , paramType = "header", dataTypeClass = String.class, example = "aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee")
    @Secure("")
    @PostMapping("/data")
    public ResponseEntity<List<CourseData>> saveCourseData(Long courseId, String input, @RequestHeader(value = "Authorization") String token){

        AccessToken at = accessTokenService.getAccesstokenByToken(token);
        User user = courseGroupService.getUserByCourseId(courseId);

        if(!user.equals(at.getUser()))
            throw new UnauthorizedException("course.unauthorized");

        return okResponse(courseDataService.saveCourseData(courseId, input));
    }
// END-----COURSE DATA -----

}
