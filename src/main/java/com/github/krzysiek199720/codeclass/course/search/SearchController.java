package com.github.krzysiek199720.codeclass.course.search;

import com.github.krzysiek199720.codeclass.auth.accesstoken.AccessToken;
import com.github.krzysiek199720.codeclass.auth.accesstoken.AccessTokenService;
import com.github.krzysiek199720.codeclass.core.controller.AbstractController;
import com.github.krzysiek199720.codeclass.course.course.CourseComplexity;
import com.github.krzysiek199720.codeclass.course.search.response.SearchResponse;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags={"Search"})

@RestController
@RequestMapping("/search")
public class SearchController extends AbstractController {

    private final SearchService searchService;
    private final AccessTokenService accessTokenService;

    public SearchController(SearchService searchService, AccessTokenService accessTokenService) {
        this.searchService = searchService;
        this.accessTokenService = accessTokenService;
    }

    @ApiOperation(value = "search", notes = "Search course group")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = SearchResponse.class, responseContainer = "List"),
    })
    @ApiImplicitParam(name = "Authorization", value = "Authorization Token", required = false, allowEmptyValue = false
            , paramType = "header", dataTypeClass = String.class, example = "aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee")
    @GetMapping()
    public ResponseEntity<List<SearchResponse>> getByCourse(@RequestHeader(value = "Authorization", required = false) String token,
                                                            @RequestParam(required = false) String searchQuery,
                                                            @RequestParam(required = false)List<CourseComplexity> complexities){
        AccessToken at = accessTokenService.getAccesstokenByToken(token);

        return okResponse(searchService.search(searchQuery, complexities, at == null ? null : at.getUser()));
    }




}
