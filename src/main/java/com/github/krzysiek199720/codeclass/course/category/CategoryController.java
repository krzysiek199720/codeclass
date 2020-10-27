package com.github.krzysiek199720.codeclass.course.category;

import com.github.krzysiek199720.codeclass.auth.security.annotation.Secure;
import com.github.krzysiek199720.codeclass.core.controller.AbstractController;
import com.github.krzysiek199720.codeclass.core.exceptions.response.ErrorResponse;
import com.github.krzysiek199720.codeclass.course.category.api.CategorySaveApi;
import com.github.krzysiek199720.codeclass.course.language.Language;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags={"Category"})

@RestController
@RequestMapping("/course/category")
public class CategoryController extends AbstractController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @ApiOperation(value = "getCategory", notes = "Get category")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Category.class),

            @ApiResponse(code = 404, message = "course.category.notfound", response = ErrorResponse.class),
    })
    @GetMapping("/{id}")
    public ResponseEntity<Category> get(@PathVariable Long id){

        return okResponse(categoryService.getById(id));
    }

    @ApiOperation(value = "getAllCategories", notes = "Get all categories")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Category.class, responseContainer = "List"),
    })
    @GetMapping("/")
    public ResponseEntity<List<Category>> getAll(){
        return okResponse(categoryService.getAll());
    }

    //    save
    @ApiOperation(value = "createCategory", notes = "create category")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Language.class),

            @ApiResponse(code = 401, message = "course.category.unauthorized", response = ErrorResponse.class),

            @ApiResponse(code = 401, message = "auth.token.notfound", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "course.category.unauthorized", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "auth.session.expired", response = ErrorResponse.class),
    })
    @ApiImplicitParam(name = "Authorization", value = "Authorization Token", required = true, allowEmptyValue = false
            , paramType = "header", dataTypeClass = String.class, example = "aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee")
    @Secure(value = "course.category.create", exceptionMessage = "course.category.unauthorized")
    @PostMapping("/")
    public ResponseEntity<Category> create(@RequestBody CategorySaveApi api){

        return okResponse(categoryService.createCategory(api));
    }

    //    update
    @ApiOperation(value = "updateCategory", notes = "update category")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Language.class),
            @ApiResponse(code = 404, message = "course.category.notfound", response = ErrorResponse.class),

            @ApiResponse(code = 401, message = "auth.token.notfound", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "course.category.unauthorized", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "auth.session.expired", response = ErrorResponse.class),
    })
    @ApiImplicitParam(name = "Authorization", value = "Authorization Token", required = true, allowEmptyValue = false
            , paramType = "header", dataTypeClass = String.class, example = "aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee")
    @Secure(value = "course.category.update", exceptionMessage = "course.category.unauthorized")
    @PutMapping("/{id}")
    public ResponseEntity<Category> update(@PathVariable Long id,
                                           @RequestBody CategorySaveApi api){

        return okResponse(categoryService.updateCategory(id,api));
    }

    //    delete
    @ApiOperation(value = "deleteCategory", notes = "Delete category")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "NO_CONTENT"),
            @ApiResponse(code = 400, message = "course.category.notfound", response = ErrorResponse.class),

            @ApiResponse(code = 401, message = "auth.token.notfound", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "course.category.unauthorized", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "auth.session.expired", response = ErrorResponse.class),
    })
    @ApiImplicitParam(name = "Authorization", value = "Authorization Token", required = true, allowEmptyValue = false
            , paramType = "header", dataTypeClass = String.class, example = "aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee")
    @Secure(value = "course.category.delete", exceptionMessage = "course.category.unauthorized")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id){

        categoryService.deleteCategory(id);

        return noContent();
    }
}
