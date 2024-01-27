package dev.wetox.WetoxRESTful.screentime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryScreenTimeResponse {
    private Category category;
    private Long duration;

    public CategoryScreenTimeResponse(CategoryScreenTime categoryScreenTime) {
        this.category = categoryScreenTime.getCategory();
        this.duration = categoryScreenTime.getDuration();
    }
}
