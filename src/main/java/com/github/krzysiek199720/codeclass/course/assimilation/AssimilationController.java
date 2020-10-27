package com.github.krzysiek199720.codeclass.course.assimilation;

import com.github.krzysiek199720.codeclass.auth.accesstoken.AccessToken;
import com.github.krzysiek199720.codeclass.auth.accesstoken.AccessTokenService;
import com.github.krzysiek199720.codeclass.auth.security.annotation.Secure;
import com.github.krzysiek199720.codeclass.core.controller.AbstractController;
import com.github.krzysiek199720.codeclass.core.exceptions.response.ErrorResponse;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags={"Assimilation"})

@RestController
@RequestMapping("/course")
public class AssimilationController extends AbstractController {

    private final AccessTokenService accessTokenService;
    private final AssimilationService assimilationService;

    public AssimilationController(AccessTokenService accessTokenService, AssimilationService assimilationService) {
        this.accessTokenService = accessTokenService;
        this.assimilationService = assimilationService;
    }


    //    get
    @ApiOperation(value = "getAssimilation", notes = "Get assimilation")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = AssimilationValue.class),

            @ApiResponse(code = 401, message = "course.assimilation.notfound", response = ErrorResponse.class),

            @ApiResponse(code = 401, message = "auth.token.notfound", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "course.assimilation.unauthorized", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "auth.session.expired", response = ErrorResponse.class),
    })
    @ApiImplicitParam(name = "Authorization", value = "Authorization Token", required = true, allowEmptyValue = false
            , paramType = "header", dataTypeClass = String.class, example = "aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee")
    @Secure(value = "", exceptionMessage = "course.assimilation.unauthorized")
    @GetMapping("/{id}/assimilation")
    public ResponseEntity<AssimilationValue> get(@PathVariable("id") Long courseId, @RequestHeader(value = "Authorization") String token){

        AccessToken at = accessTokenService.getAccesstokenByToken(token);

        Assimilation assimilation = assimilationService.getAssimilation(courseId, at.getUser());

        return okResponse( assimilation == null ? AssimilationValue.NO : assimilation.getValue());
    }

    //    update
    @ApiOperation(value = "updateAssimilation", notes = "update assimilation")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = AssimilationValue.class),

            @ApiResponse(code = 401, message = "course.notfound", response = ErrorResponse.class),

            @ApiResponse(code = 401, message = "auth.token.notfound", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "course.assimilation.unauthorized", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "auth.session.expired", response = ErrorResponse.class),
    })
    @ApiImplicitParam(name = "Authorization", value = "Authorization Token", required = true, allowEmptyValue = false
            , paramType = "header", dataTypeClass = String.class, example = "aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee")
    @Secure(value = "", exceptionMessage = "course.assimilation.unauthorized")
    @PutMapping("/{id}/assimilation")
    public ResponseEntity<AssimilationValue> create(@PathVariable("id") Long courseId, @RequestBody AssimilationValue value, @RequestHeader(value = "Authorization") String token){

        AccessToken at = accessTokenService.getAccesstokenByToken(token);

        Assimilation assimilation = assimilationService.updateAssimilation(courseId, value, at.getUser());

        return okResponse(assimilation.getValue());
    }

}
