package com.example.started.modules.table.vo.list;

import com.example.started.modules.table.entity.ShowTableFieldEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ShowColumnsVo {
    /**
     * @see ShowTableFieldEntity#getType()
     */
    private Integer type;
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
