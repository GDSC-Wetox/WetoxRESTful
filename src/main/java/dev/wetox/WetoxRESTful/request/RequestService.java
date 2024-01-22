package dev.wetox.WetoxRESTful.request;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class RequestService {
    private final RequestRepository RequestRepository;

    public SelectReqByIdResponse selectReqById(Long id) {
    }
}

