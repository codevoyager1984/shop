<template>
	<view class="goods-detail">
		<cl-row type="flex" :height="90" :padding="[0, 30, 0, 30]">
			<cl-text :size="28" bold value="商品详情"></cl-text>
		</cl-row>

		<view class="mp-html" v-if="info?.content">
			<mp-html :content="info?.content"></mp-html>
		</view>

		<cl-empty
			icon="comm"
			text="暂无描述"
			:icon-size="200"
			:fixed="false"
			:padding="[30, 0, 130, 0]"
			v-else
		/>
	</view>
</template>

<script lang="ts" setup>
import { type PropType } from "vue";

defineProps({
	info: Object as PropType<Eps.GoodsInfoEntity>,
});
</script>

<style lang="scss" scoped>
.goods-detail {
	position: relative;
	background-color: #fff;
	margin-bottom: 24rpx;
}
</style>
