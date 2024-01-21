package dev.wetox.WetoxRESTful.Friend;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Embeddable
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FriendId implements Serializable {

    @GeneratedValue
    private Long fromUserId;

    @GeneratedValue
    private Long toUserId;

}
