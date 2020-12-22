package com.github.krzysiek199720.codeclass.course.coursedata;

import com.github.krzysiek199720.codeclass.auth.accesstoken.AccessToken;
import com.github.krzysiek199720.codeclass.auth.accesstoken.AccessTokenService;
import com.github.krzysiek199720.codeclass.auth.security.annotation.Secure;
import com.github.krzysiek199720.codeclass.auth.user.User;
import com.github.krzysiek199720.codeclass.core.controller.AbstractController;
import com.github.krzysiek199720.codeclass.core.exceptions.exception.NotFoundException;
import com.github.krzysiek199720.codeclass.core.exceptions.exception.UnauthorizedException;
import com.github.krzysiek199720.codeclass.core.exceptions.response.ErrorResponse;
import com.github.krzysiek199720.codeclass.course.course.CourseService;
import com.github.krzysiek199720.codeclass.course.coursedata.api.CourseDataApi;
import com.github.krzysiek199720.codeclass.course.coursedata.response.CourseDataResponse;
import com.github.krzysiek199720.codeclass.course.coursedata.parser.exception.response.CourseDataParserParseErrorResponse;
import com.github.krzysiek199720.codeclass.course.coursedata.parser.exception.response.CourseDataParserTokenizerErrorResponse;
import com.github.krzysiek199720.codeclass.course.coursedata.response.DataImageResponse;
import com.github.krzysiek199720.codeclass.course.coursegroup.CourseGroupService;
import com.github.krzysiek199720.codeclass.course.file.File;
import io.swagger.annotations.*;
import lombok.SneakyThrows;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.websocket.server.PathParam;
import java.io.IOException;
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
        List<CourseData> res = courseDataService.parseCourseData(api.getData());
        return okResponse(res);
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
            @ApiResponse(code = 200, message = "OK", response = CourseDataResponse.class, responseContainer = "List"),

            @ApiResponse(code = 401, message = "course.unauthorized", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "course.notfound", response = ErrorResponse.class),

            @ApiResponse(code = 401, message = "auth.token.notfound", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "auth.session.expired", response = ErrorResponse.class)
    })
    @ApiImplicitParam(name = "Authorization", value = "Authorization Token", required = false, allowEmptyValue = true
            , paramType = "header", dataTypeClass = String.class, example = "aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee")
    @GetMapping("/{id}/data")
    public ResponseEntity<List<CourseDataResponse>> getCourseData(@PathVariable Long id, @RequestHeader(value = "Authorization", required = false) String token){

        AccessToken at = null;
        try{
            at = accessTokenService.getAccesstokenByToken(token);
        } catch (NotFoundException ignored){}

        User user = courseGroupService.getUserByCourseId(id);

        if(courseService.isPublished(id)){
            return okResponse(courseDataService.getCourseData(id));
        }
        if(at == null)
            throw new UnauthorizedException("course.unauthorized");

        if(!user.equals(at.getUser()))
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

    @SneakyThrows
    @ApiOperation(value = "get_coursedata_image", notes = "Get coursedata image")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = byte[].class),

            @ApiResponse(code = 401, message = "course.unauthorized", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "course.notfound", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "course.data.image.notfound", response = ErrorResponse.class),

            @ApiResponse(code = 401, message = "auth.token.notfound", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "auth.session.expired", response = ErrorResponse.class)
    })
    @ApiImplicitParam(name = "Authorization", value = "Authorization Token", required = false, allowEmptyValue = true
            , paramType = "header", dataTypeClass = String.class, example = "aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee")
    @GetMapping("/{courseId}/data/image/{image}")
    public ResponseEntity<byte[]> getCourseDataImage(@PathVariable Long courseId, @PathVariable String image, @RequestHeader(value = "Authorization", required = false) String token){

        AccessToken at = null;
        try{
            at = accessTokenService.getAccesstokenByToken(token);
        } catch (NotFoundException ignored){}

        User user = courseGroupService.getUserByCourseId(courseId);

        if(!courseService.isPublished(courseId)){
            if(at == null)
                throw new UnauthorizedException("course.unauthorized");

            if(!user.equals(at.getUser()))
                throw new UnauthorizedException("course.unauthorized");
        }

        HttpHeaders headers = new HttpHeaders();
        Image media = courseDataService.getImage(courseId, image);
        headers.setCacheControl(CacheControl.noCache().getHeaderValue());
        headers.setContentType(media.getType());

        return new ResponseEntity<>(media.getData(), headers, HttpStatus.OK);
    }

    @ApiOperation(value = "get_coursedata_image_list", notes = "Get coursedata image list")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = DataImageResponse.class, responseContainer = "list"),

            @ApiResponse(code = 401, message = "course.unauthorized", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "course.notfound", response = ErrorResponse.class),

            @ApiResponse(code = 401, message = "auth.token.notfound", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "auth.session.expired", response = ErrorResponse.class)
    })
    @ApiImplicitParam(name = "Authorization", value = "Authorization Token", required = false, allowEmptyValue = true
            , paramType = "header", dataTypeClass = String.class, example = "aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee")
    @GetMapping("/{courseId}/data/image")
    public ResponseEntity<List<DataImageResponse>> getCourseDataImageList(@PathVariable Long courseId, @RequestHeader(value = "Authorization", required = false) String token){

        AccessToken at = null;
        try{
            at = accessTokenService.getAccesstokenByToken(token);
        } catch (NotFoundException ignored){}

        User user = courseGroupService.getUserByCourseId(courseId);

        if(!courseService.isPublished(courseId)){
            if(at == null)
                throw new UnauthorizedException("course.unauthorized");

            if(!user.equals(at.getUser()))
                throw new UnauthorizedException("course.unauthorized");
        }

        return okResponse(courseDataService.getImageList(courseId));
    }

    //    save
    @ApiOperation(value = "createDataImage", notes = "create data image")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = DataImageResponse.class),

            @ApiResponse(code = 401, message = "course.notfound", response = ErrorResponse.class),

            @ApiResponse(code = 401, message = "auth.token.notfound", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "course.file.unauthorized", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "auth.session.expired", response = ErrorResponse.class),
    })
    @ApiImplicitParam(name = "Authorization", value = "Authorization Token", required = true, allowEmptyValue = false
            , paramType = "header", dataTypeClass = String.class, example = "aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee")
    @Secure(value = "course.file.create", exceptionMessage = "course.file.unauthorized")
    @PostMapping("/{courseId}/data/image")
    public ResponseEntity<DataImageResponse> create(@PathVariable("courseId") Long courseId,
                                                    @RequestParam("image") String localId,
                                                    @RequestParam("file") MultipartFile file,
                                                    @RequestHeader(value = "Authorization") String token) throws IOException {

        AccessToken at = accessTokenService.getAccesstokenByToken(token);
        User userAuthor = courseGroupService.getUserByCourseId(courseId);

        if(!at.getUser().equals(userAuthor))
            throw new UnauthorizedException("course.data.unauthorized");

        return okResponse(new DataImageResponse(courseDataService.saveImage(courseId, localId, file)));
    }

    //    delete
    @ApiOperation(value = "deleteDataImage", notes = "delete data image")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Object.class),

            @ApiResponse(code = 401, message = "course.notfound", response = ErrorResponse.class),

            @ApiResponse(code = 401, message = "auth.token.notfound", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "course.file.unauthorized", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "auth.session.expired", response = ErrorResponse.class),
    })
    @ApiImplicitParam(name = "Authorization", value = "Authorization Token", required = true, allowEmptyValue = false
            , paramType = "header", dataTypeClass = String.class, example = "aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee")
    @Secure(value = "course.file.create", exceptionMessage = "course.file.unauthorized")
    @DeleteMapping("/{courseId}/data/image/{image}")
    public ResponseEntity<Object> delete(@PathVariable Long courseId, @PathVariable String image,
                                                    @RequestHeader(value = "Authorization") String token) throws IOException {

        AccessToken at = accessTokenService.getAccesstokenByToken(token);
        User userAuthor = courseGroupService.getUserByCourseId(courseId);

        if(!at.getUser().equals(userAuthor))
            throw new UnauthorizedException("course.data.unauthorized");

        courseDataService.deleteImage(courseId, image);

        return noContent();
    }

}
