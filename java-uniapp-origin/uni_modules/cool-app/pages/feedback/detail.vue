<template>
	<cl-page>
		<view class="page">
			<view class="header">
				<cl-tag type="info">{{ dict.getLabel("feedbackType", info?.type) }}</cl-tag>
				<cl-text :value="info?.content" block :margin="[20, 0, 30, 0]" :line-height="1.5" />

				<view class="pics">
					<cl-image
						v-for="(item, index) in info?.images"
						:key="index"
						:src="item"
						:preview-list="info?.images"
						:size="180"
						:margin="[0, 20, 30, 0]"
					/>
				</view>

				<cl-row type="flex" justify="space-between">
					<cl-text :value="info?.createTime" color="info" block />

					<cl-text :value="`${info?.status == 1 ? '已解决' : '待解决'}`" />
				</cl-row>
			</view>

			<view class="container">
				<view class="card" v-if="info?.remark">
					<cl-text block bold value="回复内容" :margin="[0, 0, 20, 0]" :size="28" />

					<cl-text :value="info?.remark" :line-height="1.5" />
				</view>
			</view>
		</view>
	</cl-page>
</template>

<script lang="ts" setup>
import { ref } from "vue";
import { useCool, useStore } from "/@/cool";
import { onReady } from "@dcloudio/uni-app";
import { useUi } from "/$/cool-ui";

const { service, router } = useCool();
const ui = useUi();
const { dict } = useStore();

const info = ref<Eps.AppFeedbackEntity>();

async function refresh() {
	ui.showLoading();

	await service.app.feedback
		.info({
			id: router.query.id,
		})
		.then((res) => {
			info.value = res;
		});

	ui.hideLoading();
}

onReady(() => {
	refresh();
});
</script>

<style lang="scss" scoped>
.page {
	.header {
		background-color: #fff;
		padding: 24rpx;

		.pics {
			display: flex;
			flex-wrap: wrap;
		}
	}

	.container {
		padding: 24rpx;

		.card {
			border-radius: 16rpx;
			background-color: #fff;
			padding: 24rpx;
			margin-bottom: 24rpx;
		}
	}
}
</style>
