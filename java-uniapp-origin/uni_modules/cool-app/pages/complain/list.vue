<template>
	<cl-page>
		<view class="page">
			<view class="item" v-for="item in list" :key="item.id" @tap="toDetail(item)">
				<cl-card :label="item.status ? '已处理' : '核实中'" more>
					<cl-text
						:value="item.content"
						:line-height="1.4"
						block
						:size="28"
						:ellipsis="3"
					/>
					<cl-text
						:value="item.createTime"
						block
						:margin="[20, 0, 0, 0]"
						color="info"
						:size="24"
					/>
				</cl-card>
			</view>

			<cl-empty v-if="list.length == 0" />
		</view>

		<cl-footer>
			<cl-button type="primary" custom @tap="router.push('./submit')">我要投诉</cl-button>
		</cl-footer>
	</cl-page>
</template>

<script setup lang="ts">
import { onShow } from "@dcloudio/uni-app";
import { useCool, usePager } from "/@/cool";

const { service, router } = useCool();
const { onRefresh, list } = usePager();

function refresh(params?: any) {
	const { data, next } = onRefresh(params);
	next(service.app.complain.page(data));
}

function toDetail(item: Eps.AppComplainEntity) {
	router.push({
		path: "./detail",
		query: {
			id: item.id,
		},
	});
}

onShow(() => {
	refresh();
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
			border-radius: 16rpx;
			margin-bottom: 24rpx;
			padding: 30rpx;
		}
	}
}
</style>
