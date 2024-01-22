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
    private String name;
    private String category;
    private Double duration;

    public AppScreenTimeResponse(AppScreenTime appScreenTime) {
        this.name = appScreenTime.getName();
        this.category = appScreenTime.getCategory();
        this.duration = appScreenTime.getDuration();
    }
}
