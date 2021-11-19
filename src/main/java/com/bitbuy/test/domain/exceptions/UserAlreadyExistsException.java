package com.bitbuy.test.domain.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserAlreadyExistsException extends Exception {
    private final String userName;
}
