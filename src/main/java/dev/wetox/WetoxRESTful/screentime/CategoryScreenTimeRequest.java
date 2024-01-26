package dev.wetox.WetoxRESTful.screentime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryScreenTimeRequest {
    private Category category;
    private Double duration;
}
