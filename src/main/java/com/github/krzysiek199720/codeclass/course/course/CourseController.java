package com.github.krzysiek199720.codeclass.course.course;

import com.github.krzysiek199720.codeclass.auth.accesstoken.AccessTokenService;
import com.github.krzysiek199720.codeclass.auth.accesstoken.api.LogInApi;
import com.github.krzysiek199720.codeclass.auth.accesstoken.response.LogInResponse;
import com.github.krzysiek199720.codeclass.auth.security.annotation.Secure;
import com.github.krzysiek199720.codeclass.core.controller.AbstractController;
import com.github.krzysiek199720.codeclass.core.exceptions.response.ErrorResponse;
import com.github.krzysiek199720.codeclass.course.course.coursedata.CourseData;
import com.github.krzysiek199720.codeclass.course.course.coursedata.CourseDataService;
import com.github.krzysiek199720.codeclass.course.course.coursedata.parser.exception.response.CourseDataParserParseErrorResponse;
import com.github.krzysiek199720.codeclass.course.course.coursedata.parser.exception.response.CourseDataParserTokenizerErrorResponse;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController extends AbstractController {

    private final CourseDataService courseDataService;

    @Autowired
    public CourseController(CourseDataService courseDataService) {
        this.courseDataService = courseDataService;
    }


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
    public List<CourseData> checkCourseData(String input){
        List<CourseData> res = courseDataService.parseCourseData(input);
        return res;
    }

}
