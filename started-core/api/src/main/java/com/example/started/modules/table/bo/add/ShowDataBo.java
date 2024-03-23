package com.example.started.modules.table.bo.add;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * 显示的查询条件
 *
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ShowDataBo implements Serializable {

    private String name;
    private String value;
    private List<ShowDataBo> child;
}
