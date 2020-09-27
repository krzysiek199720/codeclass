package com.github.krzysiek199720.codeclass.course.link;

import com.github.krzysiek199720.codeclass.auth.accesstoken.AccessToken;
import com.github.krzysiek199720.codeclass.auth.accesstoken.AccessTokenService;
import com.github.krzysiek199720.codeclass.auth.security.annotation.Secure;
import com.github.krzysiek199720.codeclass.auth.user.User;
import com.github.krzysiek199720.codeclass.core.controller.AbstractController;
import com.github.krzysiek199720.codeclass.core.exceptions.exception.NotFoundException;
import com.github.krzysiek199720.codeclass.core.exceptions.exception.UnauthorizedException;
import com.github.krzysiek199720.codeclass.core.exceptions.response.ErrorResponse;
import com.github.krzysiek199720.codeclass.course.coursegroup.CourseGroupService;
import com.github.krzysiek199720.codeclass.course.link.api.LinkSaveApi;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags={"Link"})

@RestController
@RequestMapping("/course")
public class LinkController extends AbstractController {

    private final LinkService linkService;
    private final AccessTokenService accessTokenService;
    private final CourseGroupService courseGroupService;

    public LinkController(LinkService linkService, AccessTokenService accessTokenService, CourseGroupService courseGroupService) {
        this.linkService = linkService;
        this.accessTokenService = accessTokenService;
        this.courseGroupService = courseGroupService;
    }


    @ApiOperation(value = "getAllLinksByCourse", notes = "Get all links by course")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Link.class, responseContainer = "List"),
    })
    @GetMapping("/{id}/link")
    public ResponseEntity<List<Link>> getByCourse(@PathVariable("id") Long courseId){
        return okResponse(linkService.getAllByCourse(courseId));
    }

    //    save
    @ApiOperation(value = "createLink", notes = "create link")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Link.class),

            @ApiResponse(code = 401, message = "course.notfound", response = ErrorResponse.class),

            @ApiResponse(code = 401, message = "auth.token.notfound", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "course.link.unauthorized", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "auth.session.expired", response = ErrorResponse.class),
    })
    @ApiImplicitParam(name = "Authorization", value = "Authorization Token", required = true, allowEmptyValue = false
            , paramType = "header", dataTypeClass = String.class, example = "aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee")
    @Secure(value = "course.link.create", exceptionMessage = "course.link.unauthorized")
    @PostMapping("/{id}/link")
    public ResponseEntity<Link> create(@PathVariable("id") Long courseId, @RequestBody LinkSaveApi api, @RequestHeader(value = "Authorization") String token){

        AccessToken at = accessTokenService.getAccesstokenByToken(token);
        User userAuthor = courseGroupService.getUserByCourseId(courseId);

        if(!userAuthor.equals(at.getUser()))
            throw new UnauthorizedException("course.link.unauthorized");

        return okResponse(linkService.createLink(courseId, api));
    }

    //    update
    @ApiOperation(value = "updateLink", notes = "update link")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Link.class),
            @ApiResponse(code = 404, message = "course.link.notfound", response = ErrorResponse.class),

            @ApiResponse(code = 401, message = "auth.token.notfound", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "course.link.unauthorized", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "auth.session.expired", response = ErrorResponse.class),
    })
    @ApiImplicitParam(name = "Authorization", value = "Authorization Token", required = true, allowEmptyValue = false
            , paramType = "header", dataTypeClass = String.class, example = "aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee")
    @Secure(value = "course.link.update", exceptionMessage = "course.link.unauthorized")
    @PutMapping("/link/{id}")
    public ResponseEntity<Link> update(@PathVariable Long id, @RequestBody LinkSaveApi api, @RequestHeader(value = "Authorization") String token){

        AccessToken at = accessTokenService.getAccesstokenByToken(token);
        User userAuthor = linkService.getUserByLink(id);

        if(userAuthor == null)
            throw new NotFoundException("course.link.notfound");

        if(!userAuthor.equals(at.getUser()))
            throw new UnauthorizedException("course.link.unauthorized");

        return okResponse(linkService.updateLink(id,api));
    }

    //    delete
    @ApiOperation(value = "deleteLink", notes = "Delete link")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "NO_CONTENT"),
            @ApiResponse(code = 400, message = "course.link.notfound", response = ErrorResponse.class),

            @ApiResponse(code = 401, message = "auth.token.notfound", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "course.link.unauthorized", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "auth.session.expired", response = ErrorResponse.class),
    })
    @ApiImplicitParam(name = "Authorization", value = "Authorization Token", required = true, allowEmptyValue = false
            , paramType = "header", dataTypeClass = String.class, example = "aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee")
    @Secure(value = "course.link.delete", exceptionMessage = "course.link.unauthorized")
    @DeleteMapping("/link/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id, @RequestHeader(value = "Authorization") String token){

        AccessToken at = accessTokenService.getAccesstokenByToken(token);
        User userAuthor = linkService.getUserByLink(id);

        if(userAuthor == null)
            throw new NotFoundException("course.link.notfound");

        if(!userAuthor.equals(at.getUser()))
            throw new UnauthorizedException("course.link.unauthorized");

        linkService.deleteLink(id);

        return noContent();
    }
}
