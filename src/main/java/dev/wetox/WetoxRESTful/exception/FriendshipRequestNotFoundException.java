package dev.wetox.WetoxRESTful.exception;

import static dev.wetox.WetoxRESTful.exception.WetoxErorr.FRIENDSHIP_REQUEST_NOT_FOUND;

public class FriendshipRequestNotFoundException extends WetoxException {
    public FriendshipRequestNotFoundException() {

        super(FRIENDSHIP_REQUEST_NOT_FOUND);
    }
}
