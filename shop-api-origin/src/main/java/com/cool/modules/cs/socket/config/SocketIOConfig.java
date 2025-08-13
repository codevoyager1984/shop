package com.cool.modules.cs.socket.config;
import com.cool.modules.cs.socket.handler.SocketIOServerHandler;
import com.corundumstudio.socketio.SocketConfig;
import com.corundumstudio.socketio.SocketIONamespace;
import com.corundumstudio.socketio.SocketIOServer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class SocketIOConfig {

    @Value("${server.socket.host:0.0.0.0}")
    private String host;

    @Value("${server.socket.port:9092}")
    private Integer port;

    private final SocketIOServerHandler socketIOServerHandler;

    @Bean
    public SocketIOServer socketIOServer() {
        SocketConfig socketConfig = new SocketConfig();
        // 为了确保一个进程被关闭后，即使它还没有释放该端口，其他进程可以立刻使用该端口，而不是提示端口被占用，注意此项设置必须在socket还没有绑定到本地端口之前设置，否则会导致失效
        socketConfig.setReuseAddress(true);
        // 关闭Nagle算法，即关闭消息的ack确认
        socketConfig.setTcpNoDelay(true);
        // 如果消息发送到一半，关闭连接，-1会等到消息发送完毕再执行tcp的四次挥手。如果是0，则会直接关闭
        socketConfig.setSoLinger(-1);
        com.corundumstudio.socketio.Configuration configuration = getConfiguration(socketConfig);
        SocketIOServer socketIOServer = new SocketIOServer(configuration);
        // 注册 /cs 命名空间
        SocketIONamespace csNamespace = socketIOServer.addNamespace("/cs");
        // 添加事件监听器
        csNamespace.addListeners(socketIOServerHandler);
        // 启动SocketIOServer
        socketIOServer.start();
        log.info("------- SocketIOServer start finished ------server hostIp: {}", host);
        return socketIOServer;
    }

    private com.corundumstudio.socketio.Configuration getConfiguration(SocketConfig socketConfig) {
        com.corundumstudio.socketio.Configuration configuration = new com.corundumstudio.socketio.Configuration();
        configuration.setSocketConfig(socketConfig);
        // host在本地测试可以设置为localhost或者本机IP，在Linux服务器跑可换成服务器IP
        configuration.setHostname(host);
        configuration.setPort(port);
        // socket连接数大小（只监听一个端口，设置为1即可）
        configuration.setBossThreads(1);
        configuration.setWorkerThreads(100);
        // 允许自定义请求
        configuration.setAllowCustomRequests(true);
        // 协议升级超时时间（毫秒），默认10秒。HTTP握手升级为ws协议超时时间
        configuration.setUpgradeTimeout(1000000);
        // Ping消息超时时间（毫秒），默认60秒，这个时间间隔内没有接收到心跳则发送超时事件
        configuration.setPingTimeout(6000000);
        // Ping消息间隔（毫秒），默认25秒，客户端向服务器发送一条心跳消息间隔
        configuration.setPingInterval(25000);
        return configuration;
    }
}