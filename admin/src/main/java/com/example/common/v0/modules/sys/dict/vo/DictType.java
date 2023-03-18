package com.example.common.v0.modules.sys.dict.vo;

import com.example.common.v0.data.dto.DictData;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 字典类型
 */
@Data
public class DictType implements Serializable {
    @JsonIgnore
    private Long id;
    private String dictType;
    private List<DictData> dataList;
}
