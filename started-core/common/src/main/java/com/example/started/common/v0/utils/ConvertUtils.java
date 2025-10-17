package com.example.started.common.v0.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 转换工具类
 */
public class ConvertUtils {
    private static final Logger logger = LoggerFactory.getLogger(ConvertUtils.class);

    public static <D> D sourceToTarget(Object source, Class<D> target) {
        if (source == null) {
            return null;
        }
        D targetObject = null;
        try {
            targetObject = target.newInstance();
            BeanUtils.copyProperties(source, targetObject);
        } catch (Exception e) {
            logger.error("convert error ", e);
        }

        return targetObject;
    }

    public static <D> List<D> sourceToTarget(Collection<?> sourceList, Class<D> target) {
        if (sourceList == null) {
            return null;
        }

        List<D> targetList = new ArrayList<>(sourceList.size());
        try {
            for (Object source : sourceList) {
                D targetObject = target.newInstance();
                BeanUtils.copyProperties(source, targetObject);
                targetList.add(targetObject);
            }
        } catch (Exception e) {
            logger.error("convert error ", e);
        }

        return targetList;
    }
}
