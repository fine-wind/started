package com.example.started.modules.auth.server.sys.menu.t;

import com.example.started.common.exception.AppException;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@AllArgsConstructor
public class NemuServiceImpl implements NemuService {

    private final MenuRepository menuRepository;

    @Override
    @Transactional
    public void delById(String id) {
        if (menuRepository.exists(Example.of(new MenuEntity().setPid(id)))) {
            throw new AppException("存在下级，不可删除");
        }
        menuRepository.deleteById(id);
    }

    @Override
    public void save(MenuEntity entity) {
        menuRepository.save(entity);
    }
}
