<template>
	<view class="coupon-activity" @tap="toGet" v-if="info">
		<view class="a">
			<view class="text">
				<text class="name">{{ info?.title }}</text>
				<text class="desc">{{ info?.description }}</text>
			</view>

			<text class="tag" v-if="info">点击领券</text>
		</view>

		<view class="b">
			<template v-if="info">
				<text class="amount">{{ info?.amount || 0 }}</text>
				<text class="doc">
					{{ doc }}
				</text>
			</template>
		</view>

		<view class="c"></view>

		<text class="d">* 更多精选，尽在携带空间！</text>
	</view>
</template>

<script lang="ts" setup>
import { onShow } from "@dcloudio/uni-app";
import { computed, ref } from "vue";
import { useCool } from "/@/cool";
import { useUi } from "/$/cool-ui";

const { service } = useCool();
const ui = useUi();

// 优惠券信息
const info = ref<CouponInfo>();

// 使用说明
const doc = computed(() => {
	const { type, condition } = info.value || {};

	switch (type) {
		case 0:
			return `满${condition?.fullAmount}可用`;
	}
});

// 获取优惠券
function refresh() {
	service.market.coupon.info
		.page({ size: 1, page: 1, order: "createTime", sort: "asc" })
		.then((res) => {
			info.value = res.list[0];
			info.value.amount = Math.floor(info.value.amount!);
		});
}

// 领取
function toGet() {
	service.market.coupon.user
		.receive({
			couponId: info.value?.id,
		})
		.then(() => {
			ui.showToast("领取成功");
		})
		.catch((err) => {
			ui.showToast(err.message);
		});
}

onShow(() => {
	refresh();
});
</script>

<style lang="scss" scoped>
.coupon-activity {
	display: flex;
	position: relative;
	height: 160rpx;
	letter-spacing: 1rpx;
	margin: 50rpx 0 100rpx 0;

	.a {
		background: linear-gradient(140deg, rgba(#2b2e3d, 0.7), #2b2e3d 60%);
		height: 140rpx;
		width: calc(100% - 250rpx);
		border-radius: 12rpx;
		position: absolute;
		left: 24rpx;
		bottom: 1rpx;
		z-index: 2;
		color: #fff;
		display: flex;
		align-items: center;
		justify-content: space-between;
		padding: 0 24rpx;
		box-sizing: border-box;

		.name {
			display: block;
			font-size: 28rpx;
			font-weight: 500;
		}

		.desc {
			font-size: 20rpx;
			color: #ccc;
		}

		.tag {
			padding: 2rpx 10rpx;
			border-radius: 4rpx;
			background-color: #eb10ab;
			color: #fff;
			font-size: 20rpx;
			margin-right: 16rpx;
		}
	}

	.b {
		height: 160rpx;
		width: 220rpx;
		background-color: #e2e2e2;
		box-sizing: border-box;
		position: absolute;
		right: 24rpx;
		bottom: 0;
		border-radius: 12rpx;
		z-index: 3;
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: center;

		.amount {
			font-size: 60rpx;
			font-weight: bold;
			line-height: 1;
			color: #2b2e3d;

			&::after {
				content: "元";
				font-size: 48rpx;
				position: relative;
				top: -4rpx;
			}
		}

		.doc {
			font-size: 22rpx;
			color: #666;
		}
	}

	.c {
		content: "";
		display: block;
		height: 40rpx;
		width: 40rpx;
		background-color: #868686;
		border-radius: 40rpx;
		z-index: 1;
		position: absolute;
		right: 216rpx;
		top: 2rpx;
	}

	.d {
		font-size: 22rpx;
		font-weight: 500;
		position: absolute;
		bottom: -46rpx;
		left: 30rpx;
	}
}
</style>
