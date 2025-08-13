<template>
	<cl-text :size="24" :color="status?.color || 'primary'">{{ status?.label }}</cl-text>
</template>

<script lang="ts" setup>
import { computed, type PropType } from "vue";
import { OrderStatus, RefundStatus } from "../dict";

const props = defineProps({
	item: {
		type: Object as PropType<OrderInfo>,
		default: () => ({}),
	},
});

const status = computed(() => {
	if (props.item.refund) {
		return RefundStatus.find((e) => e.value == props.item.refund?.status);
	}

	return OrderStatus.find((e) => e.value == props.item.status);
});
</script>
