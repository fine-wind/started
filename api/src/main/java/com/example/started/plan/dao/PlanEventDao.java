package com.example.started.plan.dao;

import com.example.started.plan.entity.PlanEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 操作日志
 *
 * @since 1.0.0
 */
@Repository
public interface PlanEventDao extends JpaRepository<PlanEventEntity, String> {

    List<PlanEventEntity> findByDtBetween(Date lastDate, Date lastDate1);
    List<PlanEventEntity> findByDtEquals(Date dt);

}
