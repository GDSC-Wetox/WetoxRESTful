package dev.wetox.WetoxRESTful.exception;

import static dev.wetox.WetoxRESTful.exception.WetoxErorr.OIDC_INVALID_PUBLIC_ID;

public class OIDCInvalidPublicKeyIdException extends WetoxException {
    public OIDCInvalidPublicKeyIdException() {
        super(OIDC_INVALID_PUBLIC_ID);
    }
}
