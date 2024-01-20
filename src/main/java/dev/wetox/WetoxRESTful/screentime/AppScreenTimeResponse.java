package dev.wetox.WetoxRESTful.screentime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppScreenTimeResponse {
    private String appName;
    private String appCategory;
    private Double duration;
}
