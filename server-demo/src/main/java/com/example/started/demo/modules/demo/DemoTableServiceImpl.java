package com.example.started.demo.modules.demo;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * demo table service
 */
@Service
public class DemoTableServiceImpl extends ServiceImpl<DemoTableMapper, DemoTableEntity> implements DemoTableService {
    public List<DemoTableEntity> ins() {
        List<DemoTableEntity> list = Arrays.asList(new DemoTableEntity(), new DemoTableEntity());
        this.saveBatch(list);
        return list;
    }
}
