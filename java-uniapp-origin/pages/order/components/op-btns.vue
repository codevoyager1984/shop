<template>
	<cl-row type="flex" justify="end">
		<template v-if="info?.status == 0">
			<cl-button @tap.stop="command('cancel')">取消订单</cl-button>
			<cl-button type="primary" @tap.stop="command('pay')">立即支付</cl-button>
		</template>

		<template v-if="[2, 3, 4, 5, 6].includes(info.status!)">
			<cl-button @tap.stop="command('logistics')">查看物流</cl-button>
		</template>

		<template v-if="[1, 2].includes(info.status!)">
			<cl-button @tap.stop="command('refund')"> 售后\退款 </cl-button>
		</template>

		<template v-if="info.status == 2">
			<cl-button type="primary" @tap.stop="command('confirm')">确认收货</cl-button>
		</template>

		<template v-if="[3, 4].includes(info.status!)">
			<cl-button type="primary" @tap.stop="command('comment')">评价</cl-button>
		</template>
	</cl-row>
</template>

<script lang="ts" setup>
import type { PropType } from "vue";

const props = defineProps({
	info: {
		type: Object as PropType<OrderInfo>,
		default: () => ({}),
	},
});

const emit = defineEmits(["command"]);

function command(key: string) {
	emit("command", key, props.info);
}
</script>
