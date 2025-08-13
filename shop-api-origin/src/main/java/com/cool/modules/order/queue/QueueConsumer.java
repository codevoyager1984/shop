package com.cool.modules.order.queue;
import cn.hutool.json.JSONObject;
import com.cool.modules.order.enums.ActionEnum;
import com.cool.modules.order.service.OrderInfoService;
import jakarta.annotation.PostConstruct;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

/**
 * 队列消费
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class QueueConsumer {
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    private final RedissonClient redissonClient;
    private final OrderInfoService orderInfoService;

    @PostConstruct
    public void init() {
        executorService.submit(this::consumeMessages);
    }

    public void consumeMessages() {
        try {
            RBlockingQueue<JSONObject> queue = redissonClient.getBlockingQueue("myDelayedQueue");
            while (true) {
                JSONObject message = queue.take(); // 阻塞等待消息

                handler(message);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.info("Consumer was interrupted");
        }
    }

    private void handler(JSONObject message) {
        ActionEnum actionEnum = message.get("action", ActionEnum.class);
        Long orderId = message.get("orderId", Long.class);
        String orderNum = message.get("orderNum", String.class);
        try{
            switch (actionEnum) {
                // 关闭订单
                case TIMEOUT -> orderInfoService.close(null, orderId, "超时未支付");
                // 自动确认收货
                case CONFIRM -> orderInfoService.autoConfirm(orderId);
            }
            log.info("订单 {} 执行 {}", orderNum, actionEnum.getToDo());
        }catch (Exception e){
            log.error("handlerErr {} {}", orderNum, actionEnum.getToDo(), e);
        }
    }
}