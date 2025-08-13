<template>
	<cl-popup
		v-model="visible"
		direction="bottom"
		:border-radius="[32, 32, 0, 0]"
		:padding="0"
		title="请选择取消订单原因（必选）"
		show-close-btn
	>
		<view class="order-cancel">
			<scroll-view class="scroller" scroll-y>
				<view class="list">
					<cl-radio-group v-model="remark" fill>
						<cl-radio
							v-for="(item, index) in dict.get('orderCancelReason')"
							:key="index"
							:label="item.label"
							:height="80"
						>
							{{ item.label }}
						</cl-radio>
					</cl-radio-group>
				</view>
			</scroll-view>

			<cl-footer :fixed="false" :vt="[visible]">
				<cl-button
					custom
					type="primary"
					:loading="loading"
					:disabled="!remark"
					@tap="submit"
				>
					提交
				</cl-button>
			</cl-footer>
		</view>
	</cl-popup>
</template>

<script lang="ts" setup>
import { ref } from "vue";
import { useCool, useStore } from "/@/cool";
import { useUi } from "/$/cool-ui";

const emit = defineEmits(["success"]);

const { service } = useCool();
const { dict } = useStore();
const ui = useUi();

// 是否可见
const visible = ref(false);

// 取消中
const loading = ref(false);

// 备注
const remark = ref();

// 订单id
const orderId = ref();

// 打开弹窗
function open(data: OrderInfo) {
	visible.value = true;
	remark.value = "";
	orderId.value = data.id;
}

// 关闭弹窗
function close() {
	visible.value = false;
}

// 提交表单
async function submit() {
	loading.value = true;

	await service.order.info
		.cancel({
			orderId: orderId.value,
			remark: remark.value,
		})
		.then(() => {
			ui.showToast("订单取消成功");
			emit("success");
			close();
		})
		.catch((err) => {
			ui.showToast(err.message);
		});

	loading.value = false;
}

defineExpose({
	open,
	close,
});
</script>

<style lang="scss" scoped>
.order-cancel {
	.scroller {
		max-height: 60vh;

		.list {
			padding: 0 32rpx;
		}
	}
}
</style>
