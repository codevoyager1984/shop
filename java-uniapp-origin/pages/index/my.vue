<template>
	<cl-page status-bar-background="#2b2e3d">
		<view class="page-my">
			<view class="bg"></view>

			<view class="container">
				<view class="info">
					<!-- 头像 -->
					<view class="avatar">
						<cl-avatar :size="100" :src="user.info?.avatarUrl" />
					</view>

					<view class="det">
						<!-- 昵称 -->
						<cl-text :size="36" bold block color="#fff" @tap="toLogin">
							{{ user.info?.nickName || "-" }}
						</cl-text>

						<!-- 手机号 -->
						<button
							class="phone"
							:open-type="user.info?.phone ? '' : 'getPhoneNumber'"
							@getphonenumber="toBindPhone"
						>
							<cl-text color="info">手机号：</cl-text>
							<cl-text color="info">
								{{ user.info?.phone || "未绑定，点击获取" }}
							</cl-text>
						</button>
					</view>

					<!-- 设置 -->
					<!-- #ifndef MP-WEIXIN -->
					<text class="cl-icon-set" @tap="router.push('/pages/user/set')"></text>
					<!-- #endif -->
				</view>

				<!-- 我的订单 -->
				<view class="order">
					<view class="label">
						<cl-text :size="28" bold>我的订单</cl-text>

						<view class="more" @tap="order.toList()">
							<cl-text color="info" :size="24"> 全部 </cl-text>
							<cl-icon color="info" name="arrow-right"></cl-icon>
						</view>
					</view>

					<view class="status">
						<view
							class="item"
							v-for="(item, index) in order.types"
							:key="index"
							@tap="order.toList(item.value)"
						>
							<cl-badge
								:value="item.number"
								:max="99"
								:margin="14"
								type="error"
								plain
							>
								<cl-icon :class-name="item.icon" :size="70" color="primary" />
							</cl-badge>

							<cl-text color="info" :size="24" :margin="[10, 0, 0, 0]">
								{{ item.label }}
							</cl-text>
						</view>
					</view>
				</view>

				<!-- 菜单 -->
				<cl-list :radius="24" :border="false">
					<cl-list-item label="我的优惠券" @tap="router.push('/pages/market/coupon')">
						<template #icon>
							<cl-icon class-name="shop-icon-coupon" :size="50" />
						</template>
					</cl-list-item>

					<cl-list-item
						label="在线客服"
						@tap="router.push('/uni_modules/cool-cs/pages/chat')"
					>
						<template #icon>
							<cl-icon class-name="shop-icon-custom" :size="50" />
						</template>
					</cl-list-item>

					<cl-list-item label="收货地址" @tap="router.push('/pages/user/address-list')">
						<template #icon>
							<cl-icon class-name="shop-icon-location" :size="50" />
						</template>
					</cl-list-item>
				</cl-list>

				<!-- 设置 -->
				<cl-list :radius="24" :border="false">
					<cl-list-item label="设置" @tap="router.push('/pages/user/set')">
						<template #icon>
							<cl-icon class-name="shop-icon-set" :size="50" />
						</template>
					</cl-list-item>
				</cl-list>
			</view>
		</view>

		<tabbar />
	</cl-page>
</template>

<script lang="ts" setup>
import { reactive } from "vue";
import { useCool, useStore, useWx } from "/@/cool";
import { useUi } from "/$/cool-ui";
import { onShow } from "@dcloudio/uni-app";
import Tabbar from "./components/tabbar.vue";

const { service, router } = useCool();
const { user } = useStore();
const ui = useUi();
const wx = useWx();

// 订单
const order = reactive({
	types: [
		{
			label: "待付款",
			icon: "shop-icon-order-paid",
			value: 0,
			number: 0,
		},
		{
			label: "待发货",
			icon: "shop-icon-order-not-shipped",
			value: 1,
			number: 0,
		},
		{
			label: "待收货",
			icon: "shop-icon-order-received",
			value: 2,
			number: 0,
		},
		{
			label: "待评价",
			icon: "shop-icon-order-comment",
			value: 3,
			number: 0,
		},
		{
			label: "退款/售后",
			icon: "shop-icon-order-refund",
			value: "5,6",
			number: 0,
		},
	],

	// 查看订单
	toList(status?: string | number) {
		router.push({
			path: "/pages/order/list",
			query: {
				status,
			},
		});
	},

	// 获取订单统计
	refresh() {
		service.order.info.userCount().then((res) => {
			order.types[0].number = res["待付款"];
			order.types[1].number = res["待发货"];
			order.types[2].number = res["待收货"];
			order.types[3].number = res["待评价"];
			order.types[4].number = Number(res["退款中"]) + Number(res["已退款"]);
		});
	},
});

// 去登录
function toLogin() {
	if (!user.token) {
		user.logout();
	}
}

// 绑定手机号
function toBindPhone(e: { detail: any }) {
	service.user.info
		.miniPhone({
			...e.detail,
			code: wx.code.value,
		})
		.then((phone) => {
			ui.showToast("手机号绑定成功");

			user.update({
				phone,
			});
		})
		.catch((err) => {
			ui.showToast(err.message);
		});
}

onShow(() => {
	order.refresh();
	toLogin();
});
</script>

<style lang="scss" scoped>
.page-my {
	position: relative;
	overflow: hidden;

	.bg {
		position: absolute;
		left: 0;
		top: 0;
		height: 700rpx;
		width: 100%;
		background: linear-gradient(to bottom, #2b2e3d, rgba(#2b2e3d, 0.8), #f6f7fa);
	}

	.container {
		padding: 0 24rpx;
		position: relative;

		.info {
			display: flex;
			align-items: center;
			position: relative;
			padding: 40rpx 10rpx;

			.avatar {
				margin-right: 30rpx;
			}

			.phone {
				display: inline-block;
				border: 0;
				padding: 0;
				margin: 0;
				line-height: normal;
				font-size: 24rpx;
				margin-top: 14rpx;
				background-color: transparent;

				&::after {
					border: 0;
				}
			}

			.cl-icon-set {
				position: absolute;
				right: 10rpx;
				top: 34rpx;
				font-size: 40rpx;
				color: #fff;
			}
		}

		.order {
			padding: 24rpx;
			margin-bottom: 24rpx;
			border-radius: 24rpx;
			background-color: #ffffff;

			.label {
				display: flex;
				justify-content: space-between;
				margin-bottom: 12rpx;

				.more {
					display: flex;
					align-items: center;
				}
			}

			.status {
				display: flex;
				padding: 24rpx 0;

				.item {
					display: flex;
					flex-direction: column;
					align-items: center;
					justify-content: center;
					flex: 1;
					position: relative;
				}
			}
		}
	}
}
</style>
