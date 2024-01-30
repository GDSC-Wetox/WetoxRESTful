package dev.wetox.WetoxRESTful.exception;

import static dev.wetox.WetoxRESTful.exception.WetoxErorr.PROFILE_IMAGE_IO;

public class ProfileImageIOException extends WetoxException {
    public ProfileImageIOException() {
        super(PROFILE_IMAGE_IO);
    }
}
