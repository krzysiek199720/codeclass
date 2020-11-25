package com.github.krzysiek199720.codeclass.core.controller;

import com.github.krzysiek199720.codeclass.core.api.TimeResponse;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags={"Connection test"})

@RestController
public class MainController extends AbstractController {

    @GetMapping("")
    public ResponseEntity<TimeResponse> main() {

        return okResponse(new TimeResponse(System.currentTimeMillis()));
    }

}
