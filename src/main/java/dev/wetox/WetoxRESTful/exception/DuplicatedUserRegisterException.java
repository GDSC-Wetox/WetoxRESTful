package dev.wetox.WetoxRESTful.exception;

import static dev.wetox.WetoxRESTful.exception.WetoxErorr.DUPLICATED_USER_REGISTER;

public class DuplicatedUserRegisterException extends WetoxException {
    public DuplicatedUserRegisterException() {
        super(DUPLICATED_USER_REGISTER);
    }
}
