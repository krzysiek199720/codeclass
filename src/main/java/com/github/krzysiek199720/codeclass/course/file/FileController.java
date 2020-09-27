package com.github.krzysiek199720.codeclass.course.file;

import com.github.krzysiek199720.codeclass.auth.accesstoken.AccessToken;
import com.github.krzysiek199720.codeclass.auth.accesstoken.AccessTokenService;
import com.github.krzysiek199720.codeclass.auth.security.annotation.Secure;
import com.github.krzysiek199720.codeclass.auth.user.User;
import com.github.krzysiek199720.codeclass.core.controller.AbstractController;
import com.github.krzysiek199720.codeclass.core.exceptions.exception.UnauthorizedException;
import com.github.krzysiek199720.codeclass.core.exceptions.response.ErrorResponse;
import com.github.krzysiek199720.codeclass.course.coursegroup.CourseGroupService;
import com.github.krzysiek199720.codeclass.course.link.Link;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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


//    @ApiOperation(value = "getAllLinksByCourse", notes = "Get all links by course")
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "OK", response = Link.class, responseContainer = "List"),
//    })
//    @GetMapping("/{id}/link")
//    public ResponseEntity<List<Link>> get(@PathVariable("id") Long courseId){
//        return okResponse(linkService.getAllByCourse(courseId));
//    }

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
//    @ApiOperation(value = "deleteLink", notes = "Delete link")
//    @ApiResponses(value = {
//            @ApiResponse(code = 204, message = "NO_CONTENT"),
//            @ApiResponse(code = 400, message = "course.link.notfound", response = ErrorResponse.class),
//
//            @ApiResponse(code = 401, message = "auth.token.notfound", response = ErrorResponse.class),
//            @ApiResponse(code = 401, message = "course.link.unauthorized", response = ErrorResponse.class),
//            @ApiResponse(code = 401, message = "auth.session.expired", response = ErrorResponse.class),
//    })
//    @ApiImplicitParam(name = "Authorization", value = "Authorization Token", required = true, allowEmptyValue = false
//            , paramType = "header", dataTypeClass = String.class, example = "aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee")
//    @Secure(value = "course.link.delete", exceptionMessage = "course.link.unauthorized")
//    @DeleteMapping("/link/{id}")
//    public ResponseEntity<Object> delete(@PathVariable Long id, @RequestHeader(value = "Authorization") String token){
//
//        AccessToken at = accessTokenService.getAccesstokenByToken(token);
//        User userAuthor = linkService.getUserByLink(id);
//
//        if(userAuthor == null)
//            throw new NotFoundException("course.link.notfound");
//
//        if(!userAuthor.equals(at.getUser()))
//            throw new UnauthorizedException("course.link.unauthorized");
//
//        linkService.deleteLink(id);
//
//        return noContent();
//    }
}
