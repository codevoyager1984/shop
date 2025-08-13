<template>
	<cl-page>
		<cl-sticky>
			<cl-topbar>
				<cl-row type="flex" justify="center">
					<cl-icon :class-name="status?.icon" :size="60" :margin="[0, 6, 0, 0]" />
					<cl-text :size="28">{{ status?.desc }}</cl-text>
				</cl-row>
			</cl-topbar>
		</cl-sticky>

		<view class="page">
			<!-- 收货地址 -->
			<address-select :data="info?.address" disabled />

			<!-- 商品列表 -->
			<goods-group :data="info?.goodsList" readonly />

			<view class="card">
				<text class="label">价格明细</text>

				<cl-list-item
					:label="`商品总价（共${info?.goodsList?.length || 0}件商品）`"
					:arrow-icon="false"
				>
					<template #icon>
						<cl-icon class-name="shop-icon-q-order" :size="40" />
					</template>

					<cl-text :size="28" type="price" :value="info?.price" />
				</cl-list-item>

				<cl-list-item
					label="优惠金额"
					:arrow-icon="false"
					@tap="showDiscount = !showDiscount"
				>
					<template #icon>
						<cl-icon class-name="shop-icon-coupon" :size="40" />
					</template>

					<cl-text :size="28" color="error">-</cl-text>
					<cl-text :size="28" type="price" color="error" :value="info?.discountPrice" />

					<cl-icon
						name="arrow-bottom"
						:margin="[0, 0, 0, 10]"
						:size="24"
						v-if="info?.discountSource"
					/>
				</cl-list-item>

				<!-- 优惠信息 -->
				<view class="discount-info" v-if="showDiscount && info?.discountSource">
					{{ info?.discountSource?.info.title }}：满
					{{ info?.discountSource?.info.condition?.fullAmount }} 元减
					{{ info?.discountSource?.info.amount }} 元
				</view>

				<cl-list-item label="实付金额" :arrow-icon="false">
					<template #icon>
						<cl-icon class-name="shop-icon-q-pay" :size="40" />
					</template>

					<cl-text :size="32" type="price" :value="paidAmount" />
				</cl-list-item>
			</view>

			<view class="card" v-if="info?.refund">
				<text class="label">售后/退款</text>

				<cl-list-item label="退款金额" :arrow-icon="false">
					<cl-text
						:size="32"
						type="price"
						color="error"
						:value="info?.refund?.amount || 0"
					/>
				</cl-list-item>

				<cl-list-item label="退款状态" :arrow-icon="false">
					{{ dict.getLabel(RefundStatus, info.refund.status) }}
				</cl-list-item>

				<cl-list-item label="申请原因" :arrow-icon="false">
					{{ info.refund.reason }}
				</cl-list-item>

				<template v-if="info.refund.status == 2">
					<cl-list-item label="拒绝原因" :arrow-icon="false" />

					<cl-text block color="error" :margin="[0, 0, 20, 0]" :line-height="1.4">{{
						info.refund.refuseReason
					}}</cl-text>
				</template>
			</view>

			<view class="card">
				<text class="label">订单信息</text>

				<cl-list-item label="订单编号" :arrow-icon="false">
					<cl-text copy>{{ info?.orderNum }} </cl-text>

					<cl-button
						size="small"
						type="primary"
						:height="40"
						:width="70"
						:font-size="22"
						:margin="[0, 0, 0, 10]"
						@tap="toCopy"
						>复制</cl-button
					>
				</cl-list-item>

				<cl-list-item label="支付方式" :arrow-icon="false">
					{{ dict.getLabel(PayType, info?.payType) }}
				</cl-list-item>

				<cl-list-item label="支付时间" :arrow-icon="false" v-if="info?.payType">
					{{ info?.payTime }}
				</cl-list-item>

				<cl-list-item label="下单时间" :arrow-icon="false">
					{{ info?.createTime }}
				</cl-list-item>

				<cl-list-item label="订单备注" :arrow-icon="false">
					{{ info?.remark || "暂无备注" }}
				</cl-list-item>
			</view>
		</view>

		<!-- 底部操作按钮 -->
		<cl-footer :flex="false" border :vt="[info?.status]" v-if="info">
			<order-op-btns :info="info" @command="refs.orderOp?.open" />
		</cl-footer>

		<!-- 订单操作 -->
		<order-op :ref="setRefs('orderOp')" @success="refresh()" />
	</cl-page>
</template>

<script lang="ts" setup>
import { onPullDownRefresh, onReady, onShow } from "@dcloudio/uni-app";
import { useCool, useStore } from "/@/cool";
import { computed, ref } from "vue";
import { useUi } from "/$/cool-ui";
import { OrderStatus, PayType, RefundStatus } from "./dict";
import AddressSelect from "/@/components/address/select.vue";
import GoodsGroup from "/@/components/goods/group.vue";
import OrderOp from "./components/op.vue";
import OrderOpBtns from "./components/op-btns.vue";

const { router, service, refs, setRefs } = useCool();
const { dict } = useStore();
const ui = useUi();

// 显示优惠信息
const showDiscount = ref(false);

// 订单详情
const info = ref<OrderInfo>();

// 订单状态
const status = computed(() => {
	return OrderStatus.find((e) => e.value == info.value?.status);
});

// 实付金额
const paidAmount = computed(() => {
	return Number(info.value?.price || 0) - Number(info.value?.discountPrice || 0);
});

// 获取订单详情
async function refresh() {
	ui.showLoading();

	await service.order.info
		.info({
			id: router.query.id,
		})
		.then((res) => {
			info.value = res;
		})
		.catch((err) => {
			ui.showTips(err.message, () => {
				router.back();
			});
		});

	ui.hideLoading();
}

// 复制订单号
function toCopy() {
	uni.setClipboardData({
		data: info.value?.orderNum!,
	});
}

onPullDownRefresh(async () => {
	await refresh();
	uni.stopPullDownRefresh();
});

onReady(() => {
	refresh();
});

onShow(() => {
	if (ui.loaded) {
		refresh();
	}
});
</script>

<style lang="scss" scoped>
.page {
	padding: 24rpx;

	.discount-info {
		background-color: $cl-color-bg;
		color: #444;
		padding: 12rpx;
		border-radius: 12rpx;
		font-size: 24rpx;
		text-align: right;
	}
}
</style>
