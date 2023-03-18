package com.example.common.v0.data.bo;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.example.common.v0.constant.Constant;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 公共Bo
 */
@Data
@Deprecated
@Accessors(chain = true)
@ApiModel(value = "公共Bo")
public class BaseBo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    private Long id;
    /**
     * 创建者
     */
    private Long creator;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 更新者
     */
    private Long updater;
    /**
     * 更新时间
     */
    private Date updateDate;

    /**
     * 删除标志
     */
    @TableLogic(value = Constant.SysDel.DEL_NO, delval = Constant.SysDel.DEL_YES)
    private String delFlag;
    /**
     * 分页
     */
    PageBo page = new PageBo();

}
