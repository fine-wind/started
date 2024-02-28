package com.example.started.api.modules.sys.dto.region;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * 省
 */
@ApiModel(value = "省")
@Data
@EqualsAndHashCode(callSuper = true)
public class RegionProvince extends Region {
    @ApiModelProperty(value = "市列表")
    private List<Region> cities = new ArrayList<>();
}
