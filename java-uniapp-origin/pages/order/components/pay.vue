<template>
	<cl-popup
		v-model="visible"
		direction="bottom"
		:border-radius="[32, 32, 0, 0]"
		:padding="0"
		title="支付"
		show-close-btn
	>
		<view class="order-pay">
			<cl-row :margin="[16, 0, 32, 32]">
				<cl-text :size="22" color="info">合计费用</cl-text>
				<cl-text :size="46" bold :margin="[0, 6, 0, 6]">{{ paidAmount }}</cl-text>
				<cl-text :size="22" color="info">元</cl-text>
			</cl-row>

			<view class="list">
				<cl-radio-group fill v-model="type">
					<cl-radio
						v-for="item in order.payTypes"
						:key="item.key"
						:label="item.key"
						:height="80"
					>
						{{ item.label }}
						<image class="icon" mode="aspectFill" :src="item.icon" />
					</cl-radio>
				</cl-radio-group>
			</view>

			<cl-footer :fixed="false" :vt="[visible]">
				<cl-button custom type="primary" @tap="toPay">
					确认支付{{ paidAmount }}元
				</cl-button>
			</cl-footer>
		</view>
	</cl-popup>
</template>

<script lang="ts" setup>
import { computed, ref } from "vue";
import { useOrder } from "/@/hooks";
import { useUi } from "/$/cool-ui";

const emit = defineEmits(["success"]);

const ui = useUi();
const order = useOrder();

// 是否可见
const visible = ref(false);

// 支付方式
const type = ref();

// 订单详情
const info = ref<OrderInfo>();

// 支付金额
const paidAmount = computed(() => {
	return (Number(info.value?.price) - Number(info.value?.discountPrice)).toFixed(2);
});

// 打开弹窗
function open(data: OrderInfo) {
	visible.value = true;
	info.value = data;
	type.value = order.payTypes[0].key;
}

// 关闭弹窗
function close() {
	visible.value = false;
}

// 提交支付
function toPay() {
	close();

	order
		.toPay(info.value?.id!, type.value)
		.then(() => {
			emit("success");
		})
		.catch((err) => {
			ui.showToast(err.message);
		});
}

defineExpose({
	open,
	close,
});
</script>

<style lang="scss" scoped>
.order-pay {
	.list {
		padding: 0 32rpx 32rpx 32rpx;
	}

	.icon {
		height: 56rpx;
		width: 56rpx;
		position: absolute;
		right: 0;
		top: calc(50% - 28rpx);
	}
}
</style>
