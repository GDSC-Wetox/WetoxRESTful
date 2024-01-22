package dev.wetox.WetoxRESTful.request;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Request {

    @Id
    @Column(name = "request_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private RequestType requestType;

}
