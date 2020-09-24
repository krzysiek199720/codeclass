package com.github.krzysiek199720.codeclass.course.language;

import com.github.krzysiek199720.codeclass.auth.security.annotation.Secure;
import com.github.krzysiek199720.codeclass.core.controller.AbstractController;
import com.github.krzysiek199720.codeclass.core.exceptions.response.ErrorResponse;
import com.github.krzysiek199720.codeclass.course.language.api.LanguageSaveApi;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/course/language")
public class LanguageController extends AbstractController {

    private final LanguageService languageService;

    public LanguageController(LanguageService languageService) {
        this.languageService = languageService;
    }

    @ApiOperation(value = "getLanguage", notes = "Get language")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Language.class),

            @ApiResponse(code = 404, message = "course.language.notfound", response = ErrorResponse.class),
    })
    @GetMapping("/{id}")
    public ResponseEntity<Language> get(@PathVariable Long id){

        return okResponse(languageService.getById(id));
    }

    @ApiOperation(value = "getAllLanguages", notes = "Get all languages")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Language.class, responseContainer = "List"),
    })
    @GetMapping("/")
    public ResponseEntity<List<Language>> get(){
        return okResponse(languageService.getAll());
    }

    //    save
    @ApiOperation(value = "createLanguage", notes = "create language")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Language.class),

            @ApiResponse(code = 401, message = "course.language.unauthorized", response = ErrorResponse.class),

            @ApiResponse(code = 401, message = "auth.token.notfound", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "course.language.unauthorized", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "auth.session.expired", response = ErrorResponse.class),
    })
    @ApiImplicitParam(name = "Authorization", value = "Authorization Token", required = true, allowEmptyValue = false
            , paramType = "header", dataTypeClass = String.class, example = "aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee")
    @Secure(value = "course.language.create", exceptionMessage = "course.language.unauthorized")
    @PostMapping("/")
    public ResponseEntity<Language> create(@RequestBody LanguageSaveApi api){

        return okResponse(languageService.createLanguage(api));
    }

    //    update
    @ApiOperation(value = "updateLanguage", notes = "update language")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Language.class),
            @ApiResponse(code = 404, message = "course.language.notfound", response = ErrorResponse.class),

            @ApiResponse(code = 401, message = "auth.token.notfound", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "course.language.unauthorized", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "auth.session.expired", response = ErrorResponse.class),
    })
    @ApiImplicitParam(name = "Authorization", value = "Authorization Token", required = true, allowEmptyValue = false
            , paramType = "header", dataTypeClass = String.class, example = "aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee")
    @Secure(value = "course.language.update", exceptionMessage = "course.language.unauthorized")
    @PutMapping("/{id}")
    public ResponseEntity<Language> update(@PathVariable Long id,
                                                      @RequestBody LanguageSaveApi api){

        return okResponse(languageService.updateLanguage(id,api));
    }

    //    delete
    @ApiOperation(value = "deleteLanguage", notes = "Delete language")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "NO_CONTENT"),
            @ApiResponse(code = 400, message = "course.language.notfound", response = ErrorResponse.class),

            @ApiResponse(code = 401, message = "auth.token.notfound", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "course.language.unauthorized", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "auth.session.expired", response = ErrorResponse.class),
    })
    @ApiImplicitParam(name = "Authorization", value = "Authorization Token", required = true, allowEmptyValue = false
            , paramType = "header", dataTypeClass = String.class, example = "aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee")
    @Secure(value = "course.language.delete", exceptionMessage = "course.language.unauthorized")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id){

        languageService.deleteLanguage(id);

        return noContent();
    }
}
