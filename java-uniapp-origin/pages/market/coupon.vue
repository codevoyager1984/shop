<template>
	<cl-page background-color="#fff">
		<view class="page">
			<view class="list">
				<coupon-item v-for="item in list" :key="item.id" :item="item" />
			</view>

			<cl-empty icon="coupon" v-if="isEmpty(list)" />
		</view>
	</cl-page>
</template>

<script lang="ts" setup>
import { onReady } from "@dcloudio/uni-app";
import { useCool, usePager } from "/@/cool";
import CouponItem from "/@/components/coupon/item/index.vue";
import { isEmpty } from "lodash-es";

const { service } = useCool();
const { list, onRefresh } = usePager();

function refresh(params?: any) {
	const { data, next } = onRefresh(params);
	next(service.market.coupon.user.page(data));
}

onReady(() => {
	refresh();
});

defineExpose({
	refresh,
});
</script>

<style lang="scss" scoped>
.page {
	padding: 24rpx;
}
</style>
