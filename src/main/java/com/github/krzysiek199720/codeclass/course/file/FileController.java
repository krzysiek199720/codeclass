package com.github.krzysiek199720.codeclass.course.file;

import com.github.krzysiek199720.codeclass.auth.accesstoken.AccessToken;
import com.github.krzysiek199720.codeclass.auth.accesstoken.AccessTokenService;
import com.github.krzysiek199720.codeclass.auth.security.annotation.Secure;
import com.github.krzysiek199720.codeclass.auth.user.User;
import com.github.krzysiek199720.codeclass.core.controller.AbstractController;
import com.github.krzysiek199720.codeclass.core.exceptions.exception.NotFoundException;
import com.github.krzysiek199720.codeclass.core.exceptions.exception.UnauthorizedException;
import com.github.krzysiek199720.codeclass.core.exceptions.response.ErrorResponse;
import com.github.krzysiek199720.codeclass.course.coursegroup.CourseGroupService;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Api(tags={"File"})

@RestController
@RequestMapping("/course")
public class FileController extends AbstractController {

    private final AccessTokenService accessTokenService;
    private final CourseGroupService courseGroupService;
    private final FileService fileService;

    public FileController(AccessTokenService accessTokenService, CourseGroupService courseGroupService, FileService fileService) {
        this.accessTokenService = accessTokenService;
        this.courseGroupService = courseGroupService;
        this.fileService = fileService;
    }


    @ApiOperation(value = "getAllFilesByCourse", notes = "Get all files by course")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = File.class, responseContainer = "List"),
    })
    @GetMapping("/{id}/file")
    public ResponseEntity<List<File>> get(@PathVariable("id") Long courseId){
        return okResponse(fileService.getAllByCourse(courseId));
    }

    //    save
    @ApiOperation(value = "createLink", notes = "create link")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = File.class),

            @ApiResponse(code = 401, message = "course.notfound", response = ErrorResponse.class),

            @ApiResponse(code = 401, message = "auth.token.notfound", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "course.file.unauthorized", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "auth.session.expired", response = ErrorResponse.class),
    })
    @ApiImplicitParam(name = "Authorization", value = "Authorization Token", required = true, allowEmptyValue = false
            , paramType = "header", dataTypeClass = String.class, example = "aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee")
    @Secure(value = "course.file.create", exceptionMessage = "course.file.unauthorized")
    @PostMapping("/{id}/file")
    public ResponseEntity<File> create(@PathVariable("id") Long courseId,
                                       @RequestParam("name") String displayName,
                                       @RequestParam("file") MultipartFile file,
                                       @RequestHeader(value = "Authorization") String token) throws IOException {

        AccessToken at = accessTokenService.getAccesstokenByToken(token);
        User userAuthor = courseGroupService.getUserByCourseId(courseId);

        if(!at.getUser().equals(userAuthor))
            throw new UnauthorizedException("course.file.unauthorized");

        return okResponse(fileService.saveFile(courseId, displayName, file));
    }

    //    delete
    @ApiOperation(value = "deleteFile", notes = "Delete file")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "NO_CONTENT"),
            @ApiResponse(code = 400, message = "course.file.notfound", response = ErrorResponse.class),

            @ApiResponse(code = 401, message = "auth.token.notfound", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "course.file.unauthorized", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "auth.session.expired", response = ErrorResponse.class),
    })
    @ApiImplicitParam(name = "Authorization", value = "Authorization Token", required = true, allowEmptyValue = false
            , paramType = "header", dataTypeClass = String.class, example = "aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee")
    @Secure(value = "course.file.delete", exceptionMessage = "course.file.unauthorized")
    @DeleteMapping("/file/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id, @RequestHeader(value = "Authorization") String token){

        AccessToken at = accessTokenService.getAccesstokenByToken(token);
        User userAuthor = fileService.getUserByFile(id);

        if(userAuthor == null)
            throw new NotFoundException("course.file.notfound");

        if(!userAuthor.equals(at.getUser()))
            throw new UnauthorizedException("course.link.unauthorized");

        fileService.deleteFile(id);

        return noContent();
    }
}
