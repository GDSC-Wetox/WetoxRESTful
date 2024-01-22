package dev.wetox.WetoxRESTful.request;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.support.RequestPartServletServerHttpRequest;

@RequiredArgsConstructor
@RestController
@RequestMapping("/request")
public class RequestController {

    private final RequestService requestService;

    @GetMapping("{userId}")
    public SelectReqByIdResponse selectReqById(@PathVariable Long userId){
        return requestService.selectReqById(userId);
    }
}
