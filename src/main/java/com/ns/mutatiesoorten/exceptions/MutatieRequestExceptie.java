package com.ns.mutatiesoorten.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MutatieRequestExceptie extends RuntimeException {
    public MutatieRequestExceptie(String message) {
        super(message);
    }
}

