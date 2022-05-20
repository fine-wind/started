package com.example.util;

import com.example.common.utils.NumberUtils;
import com.example.common.utils.UrlUtil;
import com.example.modules.param.service.impl.SysParamsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

/**
 * 项目参数配置类
 * <p>
 * email xingii@outlook.com
 * datetime 2020/6/2 17:00
 *
 * @author 行星
 */
@Slf4j
@Service
public class ProjectParam {

    private final SysParamsServiceImpl paramsService;

    @Autowired
    public ProjectParam(SysParamsServiceImpl paramsService) {
        this.paramsService = paramsService;
    }

    /**
     * 设置基本属性
     *
     * @param model model
     */
    public void base(Model model) {
        model.addAttribute("appSettings", paramsService.getAppSettings());
        String random = NumberUtils.getRandom(3);
        model.addAttribute("random", random);
        model.addAttribute("random_int", NumberUtils.getRandom());
        model.addAttribute("random_url", UrlUtil.randomUrl());
    }

}
