package com.github.krzysiek199720.codeclass.core.exceptions.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

    private HttpStatus status;
    private String value;
    private long time;

    public ErrorResponse(HttpStatus status, String value){
        this.status = status;
        this.value = value;
        this.time = System.currentTimeMillis();
    }

}
