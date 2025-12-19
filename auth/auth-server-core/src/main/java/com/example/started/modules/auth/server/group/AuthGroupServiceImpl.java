package com.example.started.modules.auth.server.group;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * demo table service
 */
@Service
public class AuthGroupServiceImpl extends ServiceImpl<AuthGroupMapper, AuthGroupEntity> implements AuthGroupService {
    public List<AuthGroupEntity> ins() {
        List<AuthGroupEntity> list = Arrays.asList();
        this.saveBatch(list);
        return list;
    }
}
