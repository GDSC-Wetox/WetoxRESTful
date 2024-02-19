package dev.wetox.WetoxRESTful.exception;

import static dev.wetox.WetoxRESTful.exception.WetoxErorr.USER_REGISTER_DUPLICATE;

public class UserRegisterDuplicateException extends WetoxException {
    public UserRegisterDuplicateException() {
        super(USER_REGISTER_DUPLICATE);
    }
}
