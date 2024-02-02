package dev.wetox.WetoxRESTful.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDuplicatedConfirmRequest {
    private String nickname;
}
