<template>
	<view class="order-op">
		<order-cancel :ref="setRefs('cancel')" @success="onSuccess" />
		<order-pay :ref="setRefs('pay')" @success="onSuccess" />
		<order-comment :ref="setRefs('comment')" @success="onSuccess" />
	</view>
</template>

<script lang="ts" setup>
import { useUi } from "/$/cool-ui";
import { useCool } from "/@/cool";
import OrderCancel from "./cancel.vue";
import OrderPay from "./pay.vue";
import OrderComment from "./comment.vue";

const emit = defineEmits(["success"]);

const { refs, setRefs, router, service } = useCool();
const ui = useUi();

function open(key: string, data: OrderInfo) {
	const orderId = data.id;

	if (refs[key]) {
		refs[key].open(data);
	} else {
		switch (key) {
			case "confirm":
				ui.showConfirm({
					title: "确认已收到货",
					message: "确认收货后无法发起退款等售后申请，请谨慎操作",
					async beforeClose(action, { done, hideLoading, showLoading }) {
						if (action == "confirm") {
							showLoading();

							await service.order.info
								.confirm({
									orderId,
								})
								.then(() => {
									ui.showToast("确认收货成功");
									onSuccess();
								})
								.catch((err) => {
									ui.showToast(err.message);
								});

							hideLoading();
						}

						done();
					},
				});
				break;

			default:
				router.push({
					path: `/pages/order/${key}`,
					query: {
						orderId,
					},
				});
				break;
		}
	}
}

function close(key: string) {
	refs[key].close();
}

function onSuccess(data?: any) {
	emit("success", data);
}

defineExpose({
	open,
	close,
});
</script>
