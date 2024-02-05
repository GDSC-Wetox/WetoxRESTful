package dev.wetox.WetoxRESTful.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDuplicatedConfirmResponse {
    private boolean existed;

    public static UserDuplicatedConfirmResponse build(boolean present) {
        UserDuplicatedConfirmResponse response = new UserDuplicatedConfirmResponse();
        response.setExisted(present);
        return response;
    }
}
