package com.chodaton.fizzbuzz.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NO_CONTENT)
public class RequestNotFound extends RuntimeException {
    public RequestNotFound(){
        super("Aucune requête fizz buzz n'a encore été effectuée ! ");
    }
}
