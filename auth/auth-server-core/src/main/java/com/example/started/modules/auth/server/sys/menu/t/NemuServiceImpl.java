package com.example.started.modules.auth.server.sys.menu.t;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.started.common.exception.AppException;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@AllArgsConstructor
public class NemuServiceImpl extends ServiceImpl<MenuMapper, MenuEntity> implements NemuService {


    @Override
    @Transactional
    public void delById(String id) {
        if (baseMapper.exists(new LambdaQueryWrapper<MenuEntity>().eq(MenuEntity::getPid, id))) {
            throw new AppException("存在下级，不可删除");
        }
        baseMapper.deleteById(id);
    }
}
