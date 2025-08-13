<template>
	<view class="goods-comment">
		<cl-row type="flex" justify="space-between" :height="90">
			<cl-text :size="28" bold :value="`商品评论（${total}）`"></cl-text>

			<cl-text :size="24" color="info" @tap="toAll">
				全部
				<text class="cl-icon-arrow-right"></text>
			</cl-text>
		</cl-row>

		<view class="list">
			<view class="item" v-for="item in list" :key="item.id" @tap="toAll">
				<comment-item :item="item" demo />
			</view>

			<cl-empty
				text="暂无评论"
				icon="message"
				:icon-size="200"
				:fixed="false"
				:padding="[30, 0, 130, 0]"
				v-if="isEmpty(list)"
			/>
		</view>
	</view>
</template>

<script lang="ts" setup>
import { onMounted, ref, type PropType } from "vue";
import { useCool } from "/@/cool";
import { isEmpty } from "lodash-es";
import CommentItem from "./comment-item.vue";

const props = defineProps({
	info: Object as PropType<Eps.GoodsInfoEntity>,
});

const { service, router } = useCool();

const list = ref<Eps.GoodsCommentEntity[]>([]);
const total = ref(0);

function refresh() {
	service.goods.comment
		.page({
			goodsId: router.query.id,
			page: 1,
			size: 2,
			order: "createTime",
			sort: "desc",
		})
		.then((res) => {
			list.value = res.list;
			total.value = res.pagination.total || 0;
		});
}

function toAll() {
	router.push({
		path: "/pages/goods/comment",
		query: {
			goodsId: router.query.id,
		},
	});
}

onMounted(() => {
	refresh();
});
</script>

<style lang="scss" scoped>
.goods-comment {
	position: relative;
	background-color: #fff;
	padding: 0 30rpx 24rpx 30rpx;
	margin-bottom: 24rpx;

	.list {
		.item {
			margin-bottom: 24rpx;

			&:last-child {
				margin-bottom: 0;
			}
		}
	}
}
</style>
