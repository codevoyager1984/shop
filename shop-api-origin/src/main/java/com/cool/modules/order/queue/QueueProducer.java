package com.cool.modules.order.queue;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.cool.modules.order.entity.OrderInfoEntity;
import com.cool.modules.order.enums.ActionEnum;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RQueue;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

/**
 * 队列生产者
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class QueueProducer {
    private final RedissonClient redissonClient;

    public void enqueueDelayedMessage(OrderInfoEntity orderInfoEntity, ActionEnum actionEnum, long delayInSeconds) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.set("orderId", orderInfoEntity.getId());
        jsonObject.set("orderNum", orderInfoEntity.getOrderNum());
        jsonObject.set("action", actionEnum);
        try{
            RQueue<JSONObject> queue = redissonClient.getQueue("myDelayedQueue");
            RDelayedQueue<JSONObject> delayedQueue = redissonClient.getDelayedQueue(queue);
            delayedQueue.offer(jsonObject, delayInSeconds, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("enqueueDelayedMessageErr {}", JSONUtil.toJsonStr(jsonObject), e);
        }
    }
}