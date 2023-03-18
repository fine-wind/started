package com.example.common.v1.modules;

/**
 * 转义业务对象时实现此接口
 */
public interface ITranslateVo {

    /**
     * 获取params 的key
     *
     * @return params的一个key
     */
    String getParamsKey();

    /**
     * 获取元素的值
     *
     * @param primaryKeyID 主键id
     * @return 元素
     */
    Object getParamsValue(String primaryKeyID);
}
