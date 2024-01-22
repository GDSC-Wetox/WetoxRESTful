package dev.wetox.WetoxRESTful.exception;

public class RequestNotFoundException extends WetoxException {
    public RequestNotFoundException(){
        super(WetoxErorr.REQUEST_NOT_FOUND);
    }
}
