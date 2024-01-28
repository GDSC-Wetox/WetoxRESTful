package dev.wetox.WetoxRESTful.exception;

import static dev.wetox.WetoxRESTful.exception.WetoxErorr.NOT_REQUEST_MYSELF;

public class NotRequestMyselfException extends WetoxException{

    public NotRequestMyselfException() {
        super(NOT_REQUEST_MYSELF);
    }
}
