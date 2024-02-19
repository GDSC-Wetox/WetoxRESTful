package dev.wetox.WetoxRESTful.exception;

import static dev.wetox.WetoxRESTful.exception.WetoxErorr.OIDC_INVALID_HEADER;

public class OIDCInvalidHeaderException extends WetoxException {
    public OIDCInvalidHeaderException() {
        super(OIDC_INVALID_HEADER);
    }
}
