package com.example.started.modules.table.bo;

import com.example.common.v0.data.bo.PageBo;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 显示的查询条件
 *
 * @since 1.0.0
 */
@Data
public class ShowQueryBo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 查询条件
     */
    private List<?> query;
    /**
     * 分页
     */
    private PageBo page = new PageBo();
    /**
     * 排序条件
     */
    private List<?> sort;
}
