package com.example.started.modules.auth.server.sys.menu.t;

import com.baomidou.mybatisplus.extension.service.IService;

public interface NemuService extends IService<MenuEntity> {


    void delById(String id);
}
