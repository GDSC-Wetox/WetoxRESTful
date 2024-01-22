package dev.wetox.WetoxRESTful.request;


public class RequestResponse {
    public static SelectReqByIdResponse selectReqByIdResponse(Request request) {
        return SelectReqByIdResponse.builder()
                .requestId(request.getId())
                .RequestType(request.getRequestType())
                .build();
    }
}
