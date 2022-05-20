package com.example.modules.sys.groupResources;

import com.baomidou.mybatisplus.annotation.TableName;
import com.example.common.data.bo.BaseBo;

public class SysGroupResourcesBo extends BaseBo {
    private static final long serialVersionUID = 1L;
    /**
     * 角色ID
     */
    private Long groupId;
    /**
     * 菜单ID
     */
    private Long resourcesId;
}
