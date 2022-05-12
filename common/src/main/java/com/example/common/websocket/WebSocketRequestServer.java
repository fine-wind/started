package com.example.common.websocket;

import com.alibaba.fastjson.JSON;
import com.example.common.utils.Result;
//import com.example.admin.sys.controller.SystemController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;

import java.util.Map;
import java.util.Objects;

/**
 * todo 接口控制
 */
@Slf4j
@Component
@Service
public class WebSocketRequestServer {
//    @Autowired
//    private SystemController systemController;

    public Result<?> run(String path, String msg) {
        Map param = JSON.parseObject(msg, Map.class);
        if (Objects.isNull(path)) {
            path = "";
        }
        switch (path) {
            case "/sys/info":
//                return systemController.info();
            case "b":
            case "c":
        }
        return new Result<>().error("未找到地址");
    }


}
