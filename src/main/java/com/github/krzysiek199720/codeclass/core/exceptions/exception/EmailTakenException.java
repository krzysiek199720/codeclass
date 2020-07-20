package com.github.krzysiek199720.codeclass.core.exceptions.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class EmailTakenException extends RuntimeException {

    public EmailTakenException(String message){ super(message);}
}
