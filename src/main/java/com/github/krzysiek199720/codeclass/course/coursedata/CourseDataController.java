package com.github.krzysiek199720.codeclass.course.coursedata;

import com.github.krzysiek199720.codeclass.auth.accesstoken.AccessToken;
import com.github.krzysiek199720.codeclass.auth.accesstoken.AccessTokenService;
import com.github.krzysiek199720.codeclass.auth.security.annotation.Secure;
import com.github.krzysiek199720.codeclass.auth.user.User;
import com.github.krzysiek199720.codeclass.core.controller.AbstractController;
import com.github.krzysiek199720.codeclass.core.exceptions.exception.UnauthorizedException;
import com.github.krzysiek199720.codeclass.core.exceptions.response.ErrorResponse;
import com.github.krzysiek199720.codeclass.course.course.CourseService;
import com.github.krzysiek199720.codeclass.course.coursedata.api.CourseDataApi;
import com.github.krzysiek199720.codeclass.course.coursedata.parser.exception.response.CourseDataParserParseErrorResponse;
import com.github.krzysiek199720.codeclass.course.coursedata.parser.exception.response.CourseDataParserTokenizerErrorResponse;
import com.github.krzysiek199720.codeclass.course.coursegroup.CourseGroupService;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags={"Course Data"})

@RestController
@RequestMapping("/course")
public class CourseDataController extends AbstractController {

    private final CourseDataService courseDataService;
    private final CourseService courseService;
    private final CourseGroupService courseGroupService;
    private final AccessTokenService accessTokenService;

    public CourseDataController(CourseDataService courseDataService, CourseService courseService, CourseGroupService courseGroupService, AccessTokenService accessTokenService) {
        this.courseDataService = courseDataService;
        this.courseService = courseService;
        this.courseGroupService = courseGroupService;
        this.accessTokenService = accessTokenService;
    }

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
    public ResponseEntity<List<CourseData>> checkCourseData(@RequestBody CourseDataApi api){
        return okResponse(courseDataService.parseCourseData(api.getData()));
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
    @Secure(value = "course.data.save", exceptionMessage = "course.unauthorized")
    @PostMapping("/{id}/data")
    public ResponseEntity<List<CourseData>> saveCourseData(@PathVariable Long id, @RequestBody CourseDataApi api, @RequestHeader(value = "Authorization") String token){

        AccessToken at = accessTokenService.getAccesstokenByToken(token);
        User user = courseGroupService.getUserByCourseId(id);

        if(!user.equals(at.getUser()))
            throw new UnauthorizedException("course.unauthorized");

        return okResponse(courseDataService.saveCourseData(id, api.getData()));
    }

    @ApiOperation(value = "get_coursedata", notes = "Get coursedata")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = CourseData.class, responseContainer = "List"),

            @ApiResponse(code = 401, message = "course.unauthorized", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "course.notfound", response = ErrorResponse.class),

            @ApiResponse(code = 401, message = "auth.token.notfound", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "auth.session.expired", response = ErrorResponse.class)
    })
    @ApiImplicitParam(name = "Authorization", value = "Authorization Token", required = false, allowEmptyValue = true
            , paramType = "header", dataTypeClass = String.class, example = "aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee")
    @GetMapping("/{id}/data")
    public ResponseEntity<List<CourseData>> getCourseData(@PathVariable Long id, @RequestHeader(value = "Authorization", required = false) String token){

        AccessToken at = accessTokenService.getAccesstokenByToken(token);
        User user = courseGroupService.getUserByCourseId(id);

        if(!user.equals(at.getUser()))
            if(!courseService.isPublished(id))
                throw new UnauthorizedException("course.unauthorized");

        return okResponse(courseDataService.getCourseData(id));
    }

    @ApiOperation(value = "get_coursedata", notes = "Get coursedata")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = String.class),

            @ApiResponse(code = 401, message = "course.unauthorized", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "course.notfound", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "course.data.raw.notfound", response = ErrorResponse.class),

            @ApiResponse(code = 401, message = "auth.token.notfound", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "auth.session.expired", response = ErrorResponse.class)
    })
    @ApiImplicitParam(name = "Authorization", value = "Authorization Token", required = false, allowEmptyValue = true
            , paramType = "header", dataTypeClass = String.class, example = "aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee")
    @GetMapping("/{id}/data/raw")
    public ResponseEntity<String> getCourseDataRaw(@PathVariable Long id, @RequestHeader(value = "Authorization") String token){

        AccessToken at = accessTokenService.getAccesstokenByToken(token);

        return okResponse(courseDataService.getCourseDataRaw(id, at.getUser()));
    }

    @ApiOperation(value = "get_coursedata_plain", notes = "Get coursedata lines as plain text")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = String.class, responseContainer = "List"),

            @ApiResponse(code = 401, message = "course.unauthorized", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "course.notfound", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "course.data.raw.notfound", response = ErrorResponse.class),

            @ApiResponse(code = 401, message = "auth.token.notfound", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "auth.session.expired", response = ErrorResponse.class)
    })
    @ApiImplicitParam(name = "Authorization", value = "Authorization Token", required = false, allowEmptyValue = true
            , paramType = "header", dataTypeClass = String.class, example = "aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee")
    @GetMapping("/data/{id}/plain")
    public ResponseEntity<List<String>> getCourseDataPlain(@PathVariable Long id, @RequestHeader(value = "Authorization") String token){

        AccessToken at = accessTokenService.getAccesstokenByToken(token);

        return okResponse(courseDataService.getCourseDataPlain(id, at.getUser()));
    }
}
