package com.github.krzysiek199720.codeclass.core.controller;

import com.github.krzysiek199720.codeclass.core.api.TimeResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController extends AbstractController {

    @GetMapping("/")
    public ResponseEntity<TimeResponse> main() {

        return okResponse(new TimeResponse(System.currentTimeMillis()));
    }

}
