package com.cool.modules.cs.socket;

import com.corundumstudio.socketio.SocketIOClient;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

@Component
public class SocketIOSessionManager {
    // 使用 ConcurrentHashMap 存储客户端会话信息
    private static final Map<String, SocketIOClient> clientSessions = new ConcurrentHashMap<>();

    // 添加客户端会话信息
    public void addSession(String sessionId, SocketIOClient client) {
        clientSessions.put(sessionId, client);
    }

    // 根据 sessionId 获取客户端
    public SocketIOClient getSession(String sessionId) {
        return clientSessions.get(sessionId);
    }

    // 删除客户端会话信息
    public void removeSession(String sessionId) {
        clientSessions.remove(sessionId);
    }

    // 获取所有的客户端会话
    public Map<String, SocketIOClient> getAllSessions() {
        return clientSessions;
    }
}
