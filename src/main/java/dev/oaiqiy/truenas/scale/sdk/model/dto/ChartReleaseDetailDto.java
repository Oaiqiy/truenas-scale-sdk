package dev.oaiqiy.truenas.scale.sdk.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChartReleaseDetailDto {

    private String name;

    private String namespace;

    private String id;

    private String status;
}
