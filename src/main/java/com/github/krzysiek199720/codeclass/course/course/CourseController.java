package com.github.krzysiek199720.codeclass.course.course;

import com.github.krzysiek199720.codeclass.auth.accesstoken.AccessToken;
import com.github.krzysiek199720.codeclass.auth.accesstoken.AccessTokenService;
import com.github.krzysiek199720.codeclass.auth.security.annotation.Secure;
import com.github.krzysiek199720.codeclass.auth.user.User;
import com.github.krzysiek199720.codeclass.core.controller.AbstractController;
import com.github.krzysiek199720.codeclass.core.exceptions.exception.UnauthorizedException;
import com.github.krzysiek199720.codeclass.core.exceptions.response.ErrorResponse;
import com.github.krzysiek199720.codeclass.course.course.coursedata.CourseData;
import com.github.krzysiek199720.codeclass.course.course.coursedata.CourseDataService;
import com.github.krzysiek199720.codeclass.course.course.coursedata.parser.exception.response.CourseDataParserParseErrorResponse;
import com.github.krzysiek199720.codeclass.course.course.coursedata.parser.exception.response.CourseDataParserTokenizerErrorResponse;
import com.github.krzysiek199720.codeclass.course.course.coursegroup.CourseGroupService;
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

//    save

//    update

//    delete

//    publish - on/off
    @ApiOperation(value = "coursedataSave", notes = "Save course data")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Course.class),

            @ApiResponse(code = 401, message = "course.unauthorized", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "course.notfound", response = ErrorResponse.class)
    })
    @ApiImplicitParam(name = "Authorization", value = "Authorization Token", required = true, allowEmptyValue = false
            , paramType = "header", dataTypeClass = String.class, example = "aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee")
    @Secure("")
    @PutMapping("/publish")
    public ResponseEntity<Course> publish(Long courseId, Boolean isPublished, @RequestHeader(value = "Authorization") String token){

    AccessToken at = accessTokenService.getAccesstokenByToken(token);
    User user = courseGroupService.getUserByCourseId(courseId);

    if(!user.equals(at.getUser()))
        throw new UnauthorizedException("course.unauthorized");

    return okResponse(courseService.publish(courseId, isPublished));
}



// -----COURSE DATA -----
    @ApiOperation(value = "coursedataCheck", notes = "Check course data")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = CourseData.class, responseContainer = "List"),
            @ApiResponse(code = 401, message = "course.data.tokenize.error", response = CourseDataParserTokenizerErrorResponse.class),
            @ApiResponse(code = 401, message = "course.data.parse.error", response = CourseDataParserParseErrorResponse.class)
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
            @ApiResponse(code = 401, message = "course.data.parse.error", response = CourseDataParserParseErrorResponse.class)
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
