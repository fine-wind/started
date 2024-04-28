package com.example.started.modules.table.vo.list;

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
public class PaginationVo {
    protected long current;
    protected long size;
    protected long total;
}
