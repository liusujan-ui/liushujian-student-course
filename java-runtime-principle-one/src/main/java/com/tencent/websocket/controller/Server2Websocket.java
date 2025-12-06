package com.tencent.websocket.controller;

import org.apache.logging.log4j.spi.CopyOnWrite;
import org.springframework.stereotype.Component;

import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author 观自在
 * @description
 * @date 2025-12-06 23:47
 */

@Component
@ServerEndpoint(value = "/websocket/{id}")
public class Server2Websocket {

    private Session session;

    private static CopyOnWriteArrayList<Session> websockets = new CopyOnWriteArrayList<Session>();


    @OnOpen  //事件机制
    public void onopen(Session session) {
        this.session = session;
        websockets.add(session);
        System.out.println("当前的连接数量为："+websockets.size());
    }

    // 接收消息
    @OnMessage
    public void onMessage(String msg, Session session) {
        System.out.println(msg);
        try {
            sendMessage(msg,session);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //推送消息
    public void sendMessage(String msg, Session session) {
        for (Session s : websockets) {
            if (!s.getId().equals(session.getId())) {
                s.getAsyncRemote().sendText(msg);
            }
        }
    }


}
