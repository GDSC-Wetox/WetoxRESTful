package dev.wetox.WetoxRESTful.exception;

import static dev.wetox.WetoxRESTful.exception.WetoxErorr.SCREEN_TIME_NOT_FOUND;

public class ScreenTimeNotFoundException extends WetoxException {
    public ScreenTimeNotFoundException() {
        super(SCREEN_TIME_NOT_FOUND);
    }
}
