package dev.wetox.WetoxRESTful.exception;

import static dev.wetox.WetoxRESTful.exception.WetoxErorr.MEMBER_NOT_FOUND;

public class MemberNotFoundException extends WetoxException {
    public MemberNotFoundException() {
        super(MEMBER_NOT_FOUND);
    }
}
