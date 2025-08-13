<template>
	<cl-popup
		v-model="visible"
		direction="bottom"
		:border-radius="[32, 32, 0, 0]"
		:padding="0"
		title="商品评价"
		show-close-btn
	>
		<view class="order-comment">
			<view class="list">
				<view class="item" v-for="(item, index) in list" :key="index">
					<cl-image
						:src="resizeImage(item.mainPic!, 200)"
						:size="[140, 140]"
						:radius="24"
					/>

					<cl-row :margin="[0, 0, 0, 32]">
						<cl-text
							block
							bold
							:size="28"
							:margin="[0, 0, 10, 0]"
							:line-height="1.2"
							:ellipsis="2"
						>
							{{ item.title }}
						</cl-text>

						<cl-button disabled type="info" size="small" v-if="item.isComment">
							已评价
						</cl-button>

						<cl-button type="primary" size="small" @tap="toComment(item)" v-else>
							去评价
						</cl-button>
					</cl-row>
				</view>
			</view>
		</view>
	</cl-popup>
</template>

<script lang="ts" setup>
import { computed, nextTick, ref } from "vue";
import { useCool, useUpload } from "/@/cool";
import { uniqBy } from "lodash-es";

const emit = defineEmits(["success"]);

const { router } = useCool();
const { resizeImage } = useUpload();

// 是否可见
const visible = ref(false);

// 订单详情
const info = ref<OrderInfo>();

// 商品列表
const list = computed(() => {
	return uniqBy(info.value?.goodsList, "goodsId").map((e) => {
		return {
			isComment: e.isComment,
			...e?.goodsInfo,
		};
	});
});

// 打开弹窗
function open(data: OrderInfo) {
	info.value = data;

	nextTick(() => {
		// 只有一个的时候直接去提交页面
		if (list.value.length == 1) {
			toComment(list.value[0]);
		} else {
			visible.value = true;
		}
	});
}

// 关闭弹窗
function close() {
	visible.value = false;
}

// 去评价
function toComment(item: Eps.GoodsInfoEntity) {
	close();

	router.push({
		path: "/pages/order/comment",
		query: {
			goodsId: item.id,
			orderId: info.value?.id,
		},
	});
}

defineExpose({
	open,
	close,
});
</script>

<style lang="scss" scoped>
.order-comment {
	.list {
		padding: 0 32rpx;

		.item {
			display: flex;
			align-items: center;
			margin-bottom: 32rpx;
		}
	}
}
</style>
