<template>
	<view class="goods-item" @tap="toDetail">
		<cl-image
			mode="aspectFill"
			background-color="#fff"
			:size="[350, '100%']"
			:radius="[16, 16, 0, 0]"
			:src="resizeImage(item.mainPic!, 360)"
		/>

		<view class="goods-item__det">
			<cl-text
				:value="item.title"
				block
				:margin="[0, 0, 16, 0]"
				:ellipsis="2"
				:line-height="1.4"
			/>

			<cl-row>
				<cl-text type="price" color="#333" :size="34" :value="item.price" bold />
				<cl-text
					color="info"
					:size="22"
					:margin="[0, 0, 0, 10]"
					:value="`${item.sold || 0}件已售`"
				/>
			</cl-row>
		</view>
	</view>
</template>

<script lang="ts" setup>
import type { PropType } from "vue";
import { useCool, useUpload } from "/@/cool";

const props = defineProps({
	item: {
		type: Object as PropType<Eps.GoodsInfoEntity>,
		default: () => ({}),
	},
});

const { router } = useCool();
const { resizeImage } = useUpload();

function toDetail() {
	router.push({
		path: "/pages/goods/detail",
		query: {
			id: props.item.id,
		},
	});
}
</script>

<style lang="scss" scoped>
.goods-item {
	position: relative;
	background-color: #ffffff;
	border-radius: 16rpx;
	margin-bottom: 20rpx;

	.goods-item__det {
		padding: 20rpx;
	}
}
</style>
