package com.example.started.modules.staticize.service.impl;

import com.example.common.v0.data.service.impl.BaseServiceImpl;
import com.example.started.modules.staticize.bo.StatisticsBo;
import com.example.started.modules.staticize.dao.StatisticsDao;
import com.example.started.modules.staticize.entity.StatisticsEntity;
import com.example.started.modules.staticize.service.StatisticsService;
import org.springframework.stereotype.Service;

/**
 * 验证码
 */
@Service
public class StatisticsServiceImpl extends BaseServiceImpl<StatisticsBo, StatisticsDao, StatisticsEntity> implements StatisticsService {

}
