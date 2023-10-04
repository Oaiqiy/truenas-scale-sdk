package dev.oaiqiy.truenas.scale.sdk.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PoolDatasetDetailDto {

    private String id;

    private String type;

    private String name;

    private String pool;

    private Boolean encrypted;

    private List<PoolDatasetDetailDto> children;

    private Boolean locked;
}
