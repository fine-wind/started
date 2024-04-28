package com.example.started.modules.table.vo.list;

import com.example.started.modules.table.entity.ShowTableFieldEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 按钮
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ShowOpVo {
    /**
     * 显示的标题
     */
    private String title;
}
