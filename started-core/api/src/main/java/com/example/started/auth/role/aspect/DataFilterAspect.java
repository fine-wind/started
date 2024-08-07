package com.example.started.auth.role.aspect;

import com.example.common.v0.annotation.DataFilter;
import com.example.common.v0.data.bo.BaseBo;
import com.example.common.v0.exception.ServerException;
import com.example.common.v0.exception.UniversalCode;
import com.example.common.v0.utils.StringUtil;
import com.example.started.auth.Se;
import com.example.started.auth.role.user.SecurityUserDetails;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;

/**
 * 数据过滤，切面处理类
 */
@Aspect
@Log4j2
@Component
public class DataFilterAspect {

    @Pointcut("@annotation(com.example.common.v0.annotation.DataFilter)")
    public void dataFilterCut() {

    }

    @Before("dataFilterCut()")
    public void dataFilter(JoinPoint point) {
        Object params = point.getArgs()[0];
        if (params instanceof BaseBo) {

            //todo 如果是超级管理员，则不进行数据过滤
            if (Se.SUPER_USER()) {
                return;
            }

            try {
                //否则进行数据过滤
                // BaseBo map = (BaseBo)params;
                // String sqlFilter = getSqlFilter(user, point);
                // map.put(Constant.SQL_FILTER, new DataScope(sqlFilter));
            } catch (Exception e) {
                log.error(e);
            }

            return;
        }

        throw new ServerException(UniversalCode.DATA_SCOPE_PARAMS_ERROR);
    }

    /**
     * 获取数据过滤的SQL
     */
    private String getSqlFilter(SecurityUserDetails user, JoinPoint point) throws Exception {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = point.getTarget().getClass().getDeclaredMethod(signature.getName(), signature.getParameterTypes());
        DataFilter dataFilter = method.getAnnotation(DataFilter.class);

        //获取表的别名
        String tableAlias = dataFilter.tableAlias();
        if (StringUtil.isNotBlank(tableAlias)) {
            tableAlias += ".";
        }

        StringBuilder sqlFilter = new StringBuilder();
        sqlFilter.append(" (");

        //部门ID列表
        List<Long> deptIdList = user.getDeptIdList();
        if (Objects.nonNull(deptIdList) && !deptIdList.isEmpty()) {
            sqlFilter.append(tableAlias).append(dataFilter.deptId());

            sqlFilter.append(" in(").append(StringUtil.join(deptIdList, ",")).append(")");
        }

        //查询本人数据
        if (Objects.nonNull(deptIdList) && !deptIdList.isEmpty()) {
            sqlFilter.append(" or ");
        }
        sqlFilter.append(tableAlias).append(dataFilter.userId()).append("=").append(user.getId());

        sqlFilter.append(")");

        return sqlFilter.toString();
    }
}
