package com.example.common.websocket.service;

import com.alibaba.fastjson.JSON;
import com.example.common.asyn.CachedThreadPool;
import com.example.common.utils.Result;
import com.example.common.websocket.data.SockedBody;
import com.example.common.websocket.data.WebSocketData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.websocket.Session;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * todo 聊天的业务处理
 */
@Slf4j
@Service
public class ChatService {

//    @Autowired
//    RedisUtils redisUtils;
    private static final String roomK = "sys:room";

    private void roomAdd(String room, String room1) {
//        redisUtils.hashSet(roomK, room, room1);
    }

    private String getRoom(String room) {
//        return redisUtils.hashGet(roomK, room);
        return null;
    }

    private boolean hasRoom(String room) {
//        return redisUtils.hashHasKey(roomK, room);
        return false;
    }

    private void getRoomUsersAdd(String room, String username) {
        String k = "sys:room_user:" + room;
//        redisUtils.hashSet(k, username, "");
    }

    private Set<String> getRoomUsersList(String room) {
        String k = "sys:room_user:" + room;
//        Map<String, Object> sets = redisUtils.hashGetAll(k);
//        Set<String> res = new HashSet<>(sets.size());
//        sets.forEach((kk, v) -> res.add(String.valueOf(kk)));
//        return res;
        return null;
    }

    private void addMessage(String room, SockedBody obj) {
        String k = "sys:room_message:" + room;
//        redisUtils.listLeftPush(k, obj);
    }

    private SockedBody getMessage(String room) {
        String k = "sys:room_message:" + room;
//        return redisUtils.listRightPop(k);
        return null;
    }

    public void onMessage(Session sender, SockedBody requestBody) {


        if (!this.hasRoom(requestBody.getTo())) {
            this.roomAdd(requestBody.getTo(), requestBody.getTo());
            sendMessageToRoomThread(requestBody.getTo());
        }
        this.getRoomUsersAdd(requestBody.getTo(), requestBody.getFrom());

        this.addMessage(requestBody.getTo(), requestBody);

        // WebSocketData webSocketData = users.get(data.getTo());
        // if (Objects.isNull(webSocketData)) {
        //     sendMessage(sender, new Result<>().error(Constant.UniversalCode.UNPROCESSABLE_ENTITY, "此用户未登录或未注册"));
        // } else {
        //     sendMessage(webSocketData.getSession(), new Result<>().ok(data.getMsg()));
        // }
        SockedBody responseBody = new SockedBody();
        responseBody.setFrom("msg");
        responseBody.setTo("msg");
        responseBody.setType("text");
        responseBody.setMsg("你说的是啥意思啊");
        Result<?> result = new Result<>().ok(responseBody);
        result.setId(requestBody.getId());

        sendMessage(sender, result);
    }

    /**
     * 向客户端发送消息
     *
     * @param session 客户的session
     * @param result  消息
     */
    public void sendMessage(Session session, Result<?> result) {

        CachedThreadPool.submit(() -> {
            String msg = JSON.toJSONString(result);
            log.info("ws send message -> {}", msg);
            try {
                session.getBasicRemote().sendText(msg);
            } catch (IOException e) {
                log.error("send message error，" + e.getMessage(), e);
            }
        });
    }

    private void sendMessageToRoomThread(String room) {

        CachedThreadPool.scheduleAtFixedRate(() -> {
            while (true) {
                log.debug("检查是否有最新消息：{}", room);
                if (!this.hasRoom(room)) {
                    return;
                }
                SockedBody requestBody = this.getMessage(room);
                if (requestBody == null) {
                    return;
                }
                Set<String> roomUsersList = this.getRoomUsersList(room);
                for (String username : roomUsersList) {
                    if (username.equals(requestBody.getFrom())) {
                        continue;
                    }
                    Result<?> result = new Result<>().ok(requestBody);
                    String msg = JSON.toJSONString(result);
                    log.info("ws send message {}", msg);
                    WebSocketData webSocketData = WsService.users.get(username);
                    if (webSocketData == null) {
                        continue;
                    }
                    Session session = webSocketData.getSession();
                    try {
                        session.getBasicRemote().sendText(msg);
                    } catch (IOException e) {
                        log.error("send message error，" + e.getMessage(), e);
                    }
                }
            }
        }, 0, 3000, TimeUnit.MILLISECONDS);
    }

}
