<template>
	<view
		class="coupon-item"
		:class="{
			'is-disabled': info.useStatus == 1 || disabled,
		}"
	>
		<image :src="Bg" class="bg" />

		<view class="a">
			<text class="amount">{{ info.amount }}</text>
			<text class="doc">{{ doc }}</text>
		</view>

		<view class="b">
			<view class="top">
				<view class="text">
					<cl-text :ellipsis="1" bold :size="30" :margin="[0, 0, 10, 0]">{{
						info.title
					}}</cl-text>
					<cl-text :ellipsis="1" color="info" :size="24">{{ info.description }}</cl-text>
				</view>

				<slot name="btn"></slot>
			</view>

			<view class="bottom">
				<text class="time">有效期：{{ info.time }}</text>
			</view>
		</view>

		<view class="status" v-if="info.useStatus == 1">已使用</view>
		<view class="status" v-if="checked">使用中</view>
	</view>
</template>

<script lang="ts" setup>
import { computed, type PropType } from "vue";
import Bg from "./bg.png";
import dayjs from "dayjs";

const props = defineProps({
	item: {
		type: Object as PropType<CouponInfo>,
		default: () => ({}),
	},
	checked: Boolean,
	disabled: Boolean,
});

// 详情
const info = computed(() => {
	return {
		...props.item,
		amount: Math.floor(props.item.amount!),
		useStatus: props.item.useStatus,
		time:
			dayjs(props.item.startTime).format("YYYY-MM-DD") +
			" ~ " +
			dayjs(props.item.endTime).format("YYYY-MM-DD"),
	};
});

// 使用说明
const doc = computed(() => {
	const { type, condition } = props.item || {};

	switch (type) {
		case 0:
			return `满${condition.fullAmount}可用`;
	}
});
</script>

<style lang="scss" scoped>
.coupon-item {
	display: flex;
	height: 200rpx;
	position: relative;
	margin-bottom: 24rpx;
	overflow: hidden;

	.bg {
		height: 100%;
		width: 100%;
		position: absolute;
	}

	.status {
		transform: rotate(45deg);
		height: 46rpx;
		width: 200rpx;
		background-color: #2b2e3d;
		position: absolute;
		right: -60rpx;
		top: 20rpx;
		display: flex;
		align-items: center;
		justify-content: center;
		color: #fff;
		font-size: 22rpx;
		line-height: 46rpx;
	}

	.a {
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: center;
		position: relative;
		color: #fff;
		height: 100%;
		width: 172rpx;
		margin-left: 10rpx;
		padding: 0 20rpx;
		box-sizing: border-box;

		.amount {
			font-size: 50rpx;
			font-weight: 500;
			margin-bottom: 10rpx;
			line-height: 1;

			&::before {
				content: "￥";
				font-size: 38rpx;
				position: relative;
				top: -1rpx;
			}
		}

		.doc {
			font-size: 22rpx;
			color: #ddd;
		}
	}

	.b {
		display: flex;
		flex-direction: column;
		flex: 1;
		position: relative;
		padding: 0 20rpx;

		.top {
			display: flex;
			align-items: center;
			flex: 1;
		}

		.bottom {
			display: flex;
			align-items: center;
			border-top: 1rpx solid #ddd;
			height: 54rpx;

			.time {
				font-size: 22rpx;
				color: #999;
			}
		}
	}

	&.is-disabled {
		opacity: 0.4;
	}
}
</style>
