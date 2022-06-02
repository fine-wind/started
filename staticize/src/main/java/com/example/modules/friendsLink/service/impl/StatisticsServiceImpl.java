package com.example.modules.friendsLink.service.impl;

import com.example.bo.StatisticsBo;
import org.springframework.stereotype.Service;
import com.example.common.data.service.impl.BaseServiceImpl;
import com.example.modules.dao.StatisticsDao;
import com.example.page.modules.page.entity.StatisticsEntity;
import com.example.service.StatisticsService;

/**
 * 验证码
 */
@Service
public class StatisticsServiceImpl extends BaseServiceImpl<StatisticsBo, StatisticsDao, StatisticsEntity> implements StatisticsService {

}
