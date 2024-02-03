package dev.wetox.WetoxRESTful.exception;

import static dev.wetox.WetoxRESTful.exception.WetoxErorr.USER_NOT_FOUND;

public class UserNotFoundException extends WetoxException {
    public UserNotFoundException() {
        super(USER_NOT_FOUND);
    }
}
