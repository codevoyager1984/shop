<template>
	<cl-page>
		<view class="page">
			<view class="list">
				<view class="item" v-for="item in list">
					<comment-item :item="item" />
				</view>

				<cl-empty text="暂无评论" icon="message" v-if="isEmpty(list)" />
			</view>
		</view>
	</cl-page>
</template>

<script lang="ts" setup>
import { onMounted } from "vue";
import { useCool, usePager } from "/@/cool";
import { isEmpty } from "lodash-es";
import CommentItem from "./components/comment-item.vue";

const { service, router } = useCool();
const { list, onRefresh } = usePager<Eps.GoodsCommentEntity>();

function refresh(params?: any) {
	const { next, data } = onRefresh(params);
	next(service.goods.comment.page(data));
}

onMounted(() => {
	refresh({
		goodsId: router.query.goodsId,
	});
});

defineExpose({
	refresh,
});
</script>

<style lang="scss" scoped>
.page {
	padding: 24rpx;

	.list {
		.item {
			background-color: #fff;
			padding: 24rpx;
			border-radius: 24rpx;
			margin-bottom: 24rpx;

			&:last-child {
				margin-bottom: 0;
			}
		}
	}
}
</style>
