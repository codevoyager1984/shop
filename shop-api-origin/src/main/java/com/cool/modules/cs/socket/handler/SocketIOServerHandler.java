package com.cool.modules.cs.socket.handler;
import cn.hutool.core.convert.Convert;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.jwt.JWT;
import com.cool.core.security.jwt.JwtTokenUtil;
import com.cool.modules.cs.service.CsConnService;
import com.cool.modules.cs.service.CsMsgService;
import com.cool.modules.cs.socket.SocketIOSessionManager;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SocketIOServerHandler {

    private final SocketIOSessionManager socketIOSessionManager;

    private final JwtTokenUtil jwtTokenUtil;

    private final CsMsgService csMsgService;

    private final CsConnService csConnService;
    /**
     * 建立连接 客户端创建socket连接的时候，调用此事件
     *
     * @param client 客户端的SocketIO
     */
    @OnConnect
    public void onConnect(SocketIOClient client) {
        // 验证token
        Object authToken = client.getHandshakeData().getAuthToken();
        JSONObject jsonObject = JSONUtil.parseObj(authToken);
        String token = jsonObject.get("token", String.class);
        boolean isAdmin = jsonObject.get("isAdmin", Boolean.class);
        JWT jwt = jwtTokenUtil.getTokenInfo(token);
        if (!jwtTokenUtil.validateToken(token)) {
            client.sendEvent("sys", "连接失败");
            return;
        }
        String sessionId = client.getSessionId().toString();
        csConnService.binding(isAdmin, Convert.toLong(jwt.getPayload("userId")), sessionId);
        socketIOSessionManager.addSession(sessionId, client);
        log.info("socket连接建立成功, sessionId = {}", sessionId);
        client.sendEvent("message", "连接成功");
    }

    /**
     * 关闭连接  前端调用socket.disconnect()时触发改事件
     *
     * @param client 客户端的SocketIO
     */
    @OnDisconnect
    public void onDisconnect(SocketIOClient client) throws Exception {
        UUID sessionId = client.getSessionId();
        socketIOSessionManager.removeSession(sessionId.toString());
        log.info("socket连接关闭成功,关闭连接信息: sessionId = {}", client.getSessionId());
    }

    /**
     * 自定义事件，前端socket.emit('send', content)的时候，触发此事件
     */
    @OnEvent("send")
    public void send(SocketIOClient client, JSONObject jsonObject) throws Exception {
        Object authToken = socketIOSessionManager.getSession(
            client.getSessionId().toString()).getHandshakeData().getAuthToken();
        JSONObject tokenJsonObj = JSONUtil.parseObj(authToken);
        String token = tokenJsonObj.get("token", String.class);
        JWT jwt = jwtTokenUtil.getTokenInfo(token);
        String userId = jwt.getPayload("userId").toString();
        jsonObject.set("userId", userId);
        jsonObject.set("isAdmin", tokenJsonObj.get("isAdmin"));
        csMsgService.send(jsonObject);
    }
}
