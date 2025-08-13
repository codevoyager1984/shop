<template>
	<cl-page>
		<view class="page">
			<cl-sticky>
				<cl-tabs
					v-model="tabs.active"
					un-color="#999"
					:list="tabs.list"
					fill
					@change="tabs.onChange"
				/>
			</cl-sticky>

			<view class="list">
				<view
					class="item"
					v-for="item in list"
					:key="item.id"
					@tap="
						router.push({
							path: '/pages/order/detail',
							query: {
								id: item.id,
							},
						})
					"
				>
					<!-- 订单号、状态 -->
					<cl-row type="flex" justify="space-between" :margin="[0, 0, 32, 0]">
						<cl-text :size="28" bold>{{ item.orderNum }}</cl-text>
						<order-status-tag :item="item" />
					</cl-row>

					<!-- 退款、关闭原因 -->
					<view class="reason" v-if="item.closeRemark || item.refund?.refuseReason">
						<cl-text prefix-icon="cl-icon-warning-border" :size="24" :ellipsis="1">
							{{ item.closeRemark || item.refund?.refuseReason }}
						</cl-text>
					</view>

					<!-- 商品图片 -->
					<view class="pics">
						<scroll-view scroll-x class="scroller">
							<view class="inner">
								<cl-image
									v-for="url in item.pics"
									:key="url"
									:src="resizeImage(url, 200)"
									:size="[140, 140]"
									:radius="24"
									:margin="[0, 16, 0, 0]"
								/>
							</view>
						</scroll-view>

						<view class="cover">
							<cl-text
								type="price"
								:size="30"
								:value="item.price! - (item.discountPrice || 0)"
								block
								bold
								:margin="[0, 0, 10, 0]"
							/>
							<cl-text :size="22" color="info">共 {{ item.pics.length }} 件</cl-text>
						</view>
					</view>

					<!-- 操作按钮 -->
					<order-op-btns :info="item" @command="refs.orderOp?.open" />
				</view>

				<cl-empty icon="order" text="暂无订单" v-if="isEmpty(list)" />
			</view>
		</view>

		<!-- 订单操作 -->
		<order-op :ref="setRefs('orderOp')" @success="refresh()" />
	</cl-page>
</template>

<script lang="ts" setup>
import { onReady, onShow } from "@dcloudio/uni-app";
import { useCool, usePager, useUpload } from "/@/cool";
import { reactive } from "vue";
import { isEmpty } from "lodash-es";
import { useUi } from "/$/cool-ui";
import OrderOp from "./components/op.vue";
import OrderOpBtns from "./components/op-btns.vue";
import OrderStatusTag from "./components/status-tag.vue";

const { service, router, refs, setRefs } = useCool();
const { resizeImage } = useUpload();
const ui = useUi();
const { list, onRefresh, onData } = usePager<OrderInfo>();

// 状态选项卡
const tabs = reactive({
	active: "0",

	list: [
		{
			label: "全部",
		},
		{
			label: "待付款",
			value: "0",
		},
		{
			label: "待发货",
			value: "1",
		},
		{
			label: "待收货",
			value: "2",
		},
		{
			label: "售后/退款",
			value: "5,6",
		},
		{
			label: "待评价",
			value: "3",
		},
		{
			label: "已完成",
			value: "4",
		},
	],

	onChange() {
		refresh({
			page: 1,
		});
	},
});

// 获取订单列表
function refresh(params?: any, loading?: boolean) {
	const { data, next } = onRefresh(params, { loading });
	next(
		service.order.info.page({
			status: tabs.active?.split(","),
			...data,
		}),
	);
}

onData((list) => {
	list.forEach((e) => {
		e.pics = (e.goodsList || []).map((a) => {
			let arr = a.spec.images || [];

			if (isEmpty(arr)) {
				arr = [a.goodsInfo?.mainPic];
			}

			return arr[0];
		});
	});
});

onReady(() => {
	tabs.active = router.query.status || undefined;
	refresh();
});

onShow(() => {
	if (ui.loaded) {
		refresh({}, false);
	}
});

defineExpose({
	refresh,
});
</script>

<style lang="scss" scoped>
.page {
	.list {
		padding: 24rpx;

		.item {
			background-color: #fff;
			padding: 32rpx;
			border-radius: 24rpx;
			margin-bottom: 24rpx;

			.reason {
				display: flex;
				align-items: center;
				border-radius: 16rpx;
				background-color: $cl-color-bg;
				padding: 16rpx;
				margin-bottom: 32rpx;
			}

			.pics {
				height: 140rpx;
				margin-bottom: 32rpx;
				overflow: hidden;
				position: relative;

				.scroller {
					height: 160rpx;

					.inner {
						display: flex;

						&::after {
							content: "";
							display: block;
							width: 144rpx;
							flex-shrink: 0;
						}
					}
				}

				.cover {
					display: flex;
					flex-direction: column;
					justify-content: center;
					align-items: flex-end;
					position: absolute;
					right: 0;
					top: 0;
					height: 100%;
					width: 160rpx;
					background-color: rgba(255, 255, 255, 0.95);
				}
			}

			&:last-child {
				margin-bottom: 0;
			}
		}
	}
}
</style>
