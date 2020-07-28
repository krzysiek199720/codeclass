package com.github.krzysiek199720.codeclass.core.exceptions.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NotFoundException extends RuntimeException {

    public NotFoundException(String message){ super(message);}

    public NotFoundException(Throwable cause){ super(cause);}

    public NotFoundException(String message, Throwable cause){ super(message, cause);}
}
