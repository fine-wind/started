package com.example.started.demo.modules.demo;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * demo table service
 */
@Service
@AllArgsConstructor
public class DemoTableServiceImpl implements DemoTableService {

    private DemoTableRepository demoTableRepository;


    public void test() {
        List<DemoTableEntity> list = Arrays.asList(new DemoTableEntity(), new DemoTableEntity());
        demoTableRepository.saveAllAndFlush(list);
        for (DemoTableEntity in : list) {
            in.setB(22);
        }
        demoTableRepository.saveAllAndFlush(list);
        Set<String> collect = list.stream().map(DemoTableEntity::getId).collect(Collectors.toSet());
        demoTableRepository.deleteAllByIdInBatch(collect);
    }
}
