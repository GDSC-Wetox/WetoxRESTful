package dev.wetox.WetoxRESTful.exception;

import static dev.wetox.WetoxRESTful.exception.WetoxErorr.OIDC_VALIDATION_FAIL;

public class OIDCValidationFailException extends WetoxException {
    public OIDCValidationFailException() {
        super(OIDC_VALIDATION_FAIL);
    }
}
