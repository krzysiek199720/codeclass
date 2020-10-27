package com.github.krzysiek199720.codeclass.course.comment;

import com.github.krzysiek199720.codeclass.auth.accesstoken.AccessToken;
import com.github.krzysiek199720.codeclass.auth.accesstoken.AccessTokenService;
import com.github.krzysiek199720.codeclass.auth.security.annotation.Secure;
import com.github.krzysiek199720.codeclass.core.controller.AbstractController;
import com.github.krzysiek199720.codeclass.core.exceptions.response.ErrorResponse;
import com.github.krzysiek199720.codeclass.course.comment.response.CommentResponse;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<List<CommentResponse>> getByCourse(@PathVariable("id") Long courseId, @RequestHeader(value = "Authorization") String token){
        AccessToken at = accessTokenService.getAccesstokenByToken(token);

        return okResponse(commentService.getAllComments(courseId, at.getUser() == null ? null : at.getUser()));
    }

    //    save
    @ApiOperation(value = "saveQuiz", notes = "save quiz")
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

    //    delete
//    @ApiOperation(value = "deleteQuiz", notes = "Delete quiz")
//    @ApiResponses(value = {
//            @ApiResponse(code = 204, message = "NO_CONTENT"),
//            @ApiResponse(code = 400, message = "course.quiz.notfound", response = ErrorResponse.class),
//
//            @ApiResponse(code = 401, message = "auth.token.notfound", response = ErrorResponse.class),
//            @ApiResponse(code = 401, message = "course.quiz.unauthorized", response = ErrorResponse.class),
//            @ApiResponse(code = 401, message = "auth.session.expired", response = ErrorResponse.class),
//    })
//    @ApiImplicitParam(name = "Authorization", value = "Authorization Token", required = true, allowEmptyValue = false
//            , paramType = "header", dataTypeClass = String.class, example = "aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee")
//    @Secure(value = "course.link.delete", exceptionMessage = "course.link.unauthorized")
//    @DeleteMapping()
//    public ResponseEntity<Object> delete(@PathVariable("id") Long courseId, @RequestHeader(value = "Authorization") String token){
//
//        AccessToken at = accessTokenService.getAccesstokenByToken(token);
//        User userAuthor = courseGroupService.getUserByCourseId(courseId);
//
//        if(!userAuthor.equals(at.getUser()))
//            throw new UnauthorizedException("course.quiz.unauthorized");
//
//        quizService.deleteQuiz(courseId);
//
//        return noContent();
//    }

    //score
//    @ApiOperation(value = "saveQuizScore", notes = "save quiz score")
//    @ApiResponses(value = {
//            @ApiResponse(code = 204, message = "NO_CONTENT"),
//
//            @ApiResponse(code = 401, message = "course.notfound", response = ErrorResponse.class),
//
//            @ApiResponse(code = 401, message = "auth.token.notfound", response = ErrorResponse.class),
//            @ApiResponse(code = 401, message = "course.quiz.unauthorized", response = ErrorResponse.class),
//            @ApiResponse(code = 401, message = "auth.session.expired", response = ErrorResponse.class),
//    })
//    @ApiImplicitParam(name = "Authorization", value = "Authorization Token", required = true, allowEmptyValue = false
//            , paramType = "header", dataTypeClass = String.class, example = "aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee")
//    @PostMapping("/score")
//    public ResponseEntity<Object> saveScore(@PathVariable("id") Long courseId, Integer score, @RequestHeader(value = "Authorization") String token){
//
//        AccessToken at = accessTokenService.getAccesstokenByToken(token);
//
//        quizService.setScore(courseId, score, at.getUser());
//
//        return noContent();
//    }

//    @ApiOperation(value = "getQuizScore", notes = "get quiz score")
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "OK", response = Integer.class),
//
//            @ApiResponse(code = 401, message = "course.notfound", response = ErrorResponse.class),
//
//            @ApiResponse(code = 401, message = "auth.token.notfound", response = ErrorResponse.class),
//            @ApiResponse(code = 401, message = "course.quiz.unauthorized", response = ErrorResponse.class),
//            @ApiResponse(code = 401, message = "auth.session.expired", response = ErrorResponse.class),
//    })
//    @ApiImplicitParam(name = "Authorization", value = "Authorization Token", required = true, allowEmptyValue = false
//            , paramType = "header", dataTypeClass = String.class, example = "aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee")
//    @GetMapping("/score")
//    public ResponseEntity<Integer> getScore(@PathVariable("id") Long courseId, @RequestHeader(value = "Authorization") String token){
//
//        AccessToken at = accessTokenService.getAccesstokenByToken(token);
//
//        return okResponse(quizService.getScore(courseId, at.getUser()));
//    }



}
