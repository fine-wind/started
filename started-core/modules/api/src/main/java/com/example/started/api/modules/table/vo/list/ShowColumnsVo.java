package com.example.started.api.modules.table.vo.list;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ShowColumnsVo {
    private String key;
    private String dataKey;
    /**
     * 显示的标题
     */
    private String title;
    /**
     * 显示的宽度
     */
    private int width;
}
