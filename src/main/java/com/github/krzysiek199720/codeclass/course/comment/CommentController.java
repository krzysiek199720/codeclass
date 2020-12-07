package com.github.krzysiek199720.codeclass.course.comment;

import com.github.krzysiek199720.codeclass.auth.accesstoken.AccessToken;
import com.github.krzysiek199720.codeclass.auth.accesstoken.AccessTokenService;
import com.github.krzysiek199720.codeclass.auth.security.annotation.Secure;
import com.github.krzysiek199720.codeclass.core.controller.AbstractController;
import com.github.krzysiek199720.codeclass.core.exceptions.exception.NotFoundException;
import com.github.krzysiek199720.codeclass.core.exceptions.response.ErrorResponse;
import com.github.krzysiek199720.codeclass.course.comment.response.CommentResponse;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags={"Comment"})

@RestController
@RequestMapping("/course/{id}/comment")
public class CommentController extends AbstractController {

    private final AccessTokenService accessTokenService;
    private final CommentService commentService;

    @Autowired
    public CommentController(AccessTokenService accessTokenService, CommentService commentService) {
        this.accessTokenService = accessTokenService;
        this.commentService = commentService;
    }

    @ApiOperation(value = "getComments", notes = "Get comments")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = CommentResponse.class, responseContainer = "List"),

            @ApiResponse(code = 401, message = "course.notfound", response = ErrorResponse.class),

            @ApiResponse(code = 401, message = "course.course.unauthorized", response = ErrorResponse.class),
    })
    @ApiImplicitParam(name = "Authorization", value = "Authorization Token", required = false, allowEmptyValue = false
            , paramType = "header", dataTypeClass = String.class, example = "aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee")
    @GetMapping()
    public ResponseEntity<List<CommentResponse>> getByCourse(@PathVariable("id") Long courseId, @RequestHeader(value = "Authorization", required = false) String token){
        AccessToken at;
        try{
            at = accessTokenService.getAccesstokenByToken(token);
        }catch(NotFoundException ignored) {
            at = null;
        }

        return okResponse(commentService.getAllComments(courseId, at == null ? null : at.getUser()));
    }

    //    save
    @ApiOperation(value = "saveComment", notes = "save comment")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = CommentResponse.class),

            @ApiResponse(code = 401, message = "course.notfound", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "course.comment.notfound", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "course.coursedata.notfound", response = ErrorResponse.class),

            @ApiResponse(code = 401, message = "auth.token.notfound", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "course.comment.unauthorized", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "auth.session.expired", response = ErrorResponse.class),
    })
    @ApiImplicitParam(name = "Authorization", value = "Authorization Token", required = true, allowEmptyValue = false
            , paramType = "header", dataTypeClass = String.class, example = "aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee")
    @Secure(value = "", exceptionMessage = "course.comment.unauthorized")
    @PostMapping()
    public ResponseEntity<CommentResponse> create(@PathVariable("id") Long courseId, @RequestBody CommentSaveApi api, @RequestHeader(value = "Authorization") String token){

        AccessToken at = accessTokenService.getAccesstokenByToken(token);

        return okResponse(commentService.saveComment(courseId, api, at.getUser()));
    }

}
