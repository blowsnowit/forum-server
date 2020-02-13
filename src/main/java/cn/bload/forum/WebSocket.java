package cn.bload.forum;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

/**
 * @author 作者 : blownsow
 * @version 版本: 1.0
 * @date 创建时间 : 2020/2/12 17:44
 * @describe 类描述:
 */
@ServerEndpoint("/websocket/{userId}")
public class WebSocket {
    private Session session;

    private static CopyOnWriteArraySet<WebSocket> webSockets = new CopyOnWriteArraySet<>();
    private static Map<Integer,Session> sessionPool = new HashMap<>();

    @OnOpen
    private void onOpen(Session session, @PathParam(value="userId")Integer userId) {
        this.session = session;
        webSockets.add(this);
        sessionPool.put(userId, session);

        System.out.println(userId + "【websocket消息】有新的连接，总数为:" + webSockets.size());
        this.onInitUser(userId);
    }

    @OnClose
    private void onClose() {
        webSockets.remove(this);
        System.out.println("【websocket消息】连接断开，总数为:"+webSockets.size());
    }

    /**
     * 用户初始化
     */
    private void onInitUser(Integer userId){

    }

    // 此为广播消息
    public void pushAllMessage(String message) {
        for(WebSocket webSocket : webSockets) {
            try {
                webSocket.session.getAsyncRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // 此为单点消息
    public void pushOneMessage(Integer userId, String message) {
        Session session = sessionPool.get(userId);
        if (session != null) {
            try {
                session.getAsyncRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
