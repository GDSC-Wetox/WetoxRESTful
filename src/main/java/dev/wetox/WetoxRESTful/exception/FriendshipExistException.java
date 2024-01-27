package dev.wetox.WetoxRESTful.exception;

import static dev.wetox.WetoxRESTful.exception.WetoxErorr.FRIENDSHIP_EXIST;

public class FriendshipExistException extends WetoxException{
    public FriendshipExistException() {

        super(FRIENDSHIP_EXIST);
    }
}
