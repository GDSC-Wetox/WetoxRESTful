package dev.wetox.WetoxRESTful.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SelectReqByIdResponse {
    private Long requestId;
    private RequestType RequestType;
}
