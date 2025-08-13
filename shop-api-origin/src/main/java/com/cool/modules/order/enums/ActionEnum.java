package com.cool.modules.order.enums;

import lombok.Getter;

@Getter
public enum ActionEnum {
    // 超时未支付
    TIMEOUT(0, "关闭订单"),

    // 自动确认收货
    CONFIRM(1, "自动确认收货");

    private final int value;
    private final String toDo;

    ActionEnum(int value, String toDo) {
        this.value = value;
        this.toDo = toDo;
    }

    public static ActionEnum fromValue(int value) {
        for (ActionEnum action : ActionEnum.values()) {
            if (action.getValue() == value) {
                return action;
            }
        }
        throw new IllegalArgumentException("No matching Action for value " + value);
    }
}
