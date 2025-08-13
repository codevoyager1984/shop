<template>
	<cl-page>
		<view class="page">
			<!-- 收货地址 -->
			<address-select />

			<!-- 商品列表 -->
			<goods-group :data="list" />

			<view class="card">
				<text class="label">订单备注</text>

				<cl-textarea
					v-model="form.remark"
					placeholder="选填，付款后商家可见"
					background-color="#f7f7f7"
					:border="false"
					:margin="[8, 0, 10, 0]"
					count
				/>
			</view>

			<view class="card">
				<text class="label">价格明细</text>

				<cl-list-item :label="`商品总价（共${total}件商品）`" :arrow-icon="false">
					<template #icon>
						<cl-icon class-name="shop-icon-q-order" :size="40"></cl-icon>
					</template>

					<cl-text :size="28" type="price" :value="totalAmount"></cl-text>
				</cl-list-item>

				<cl-list-item label="优惠券">
					<template #icon>
						<cl-icon class-name="shop-icon-coupon" :size="40"></cl-icon>
					</template>

					<coupon-select :total-amount="totalAmount" :ref="setRefs('couponSelect')" />
				</cl-list-item>

				<cl-list-item label="合计" :arrow-icon="false">
					<template #icon>
						<cl-icon class-name="shop-icon-q-pay" :size="40"></cl-icon>
					</template>

					<cl-text :size="32" type="price" :value="paidAmount"></cl-text>
				</cl-list-item>
			</view>

			<view class="card">
				<text class="label">支付方式</text>

				<cl-radio-group fill v-model="form.payType">
					<cl-radio
						v-for="item in order.payTypes"
						:key="item.key"
						:label="item.key"
						:height="80"
					>
						{{ item.label }}
						<image class="pay-icon" mode="aspectFill" :src="item.icon" />
					</cl-radio>
				</cl-radio-group>
			</view>
		</view>

		<cl-footer :flex="false" border :padding="24">
			<cl-row type="flex" justify="space-between">
				<cl-text type="price" color="error" :size="50" :value="paidAmount" />
				<cl-button type="primary" :width="200" :loading="loading" @tap="submit"
					>提交订单</cl-button
				>
			</cl-row>
		</cl-footer>
	</cl-page>
</template>

<script lang="ts" setup>
import { onReady } from "@dcloudio/uni-app";
import { useCool } from "/@/cool";
import { computed, reactive, ref } from "vue";
import { useAddress, useShoppingCart } from "/@/hooks";
import { useUi } from "/$/cool-ui";
import AddressSelect from "/@/components/address/select.vue";
import CouponSelect from "/@/components/coupon/select.vue";
import GoodsGroup from "/@/components/goods/group.vue";
import { useOrder } from "/@/hooks";

const { router, service, refs, setRefs } = useCool();
const address = useAddress();
const ui = useUi();
const spCart = useShoppingCart();
const order = useOrder();

// 表单
const form = reactive({
	payType: "wxpay" as "wxpay" | "alipay",
	remark: "",
});

// 提交中
const loading = ref(false);

// 购买列表
const list = ref<OrderGoods[]>([]);

// 商品总数
const total = computed(() => {
	return list.value.reduce((a, b) => {
		return a + b.count;
	}, 0);
});

// 总金额
const totalAmount = computed(() => {
	return list.value.reduce((a, b) => {
		return a + (b.spec.price || 0) * b.count;
	}, 0);
});

// 实付金额
const paidAmount = computed(() => {
	return totalAmount.value - (refs.couponSelect?.checked?.amount || 0);
});

// 提交
async function submit() {
	if (!address.info?.id) {
		return ui.showToast("请选择收货地址");
	}

	loading.value = true;

	await service.order.info
		.create({
			data: {
				remark: form.remark,
				goodsList: list.value.map((e) => {
					return {
						goodsInfo: e.goodsInfo,
						spec: e.spec,
						count: e.count,
						goodsId: e.goodsInfo.id,
					};
				}),
				couponId: refs.couponSelect?.checked?.id,
				addressId: address.info?.id,
				title: "购买商品",
			},
		})
		.then(async (res) => {
			// 删除购物车数据
			list.value.forEach((e) => {
				spCart.delBySpecId(e.spec.id!);
			});

			// 跳转订单详情
			function next() {
				router.push({
					path: "/pages/order/detail",
					query: {
						id: res.id,
					},
					mode: "redirectTo",
				});
			}

			await order
				.toPay(res.id)
				.then(() => {
					next();
				})
				.catch((err) => {
					ui.showTips(err.message, () => {
						next();
					});
				});
		})
		.catch((err) => {
			ui.showTips(err.message, () => {
				router.push({
					path: "/pages/order/list",
					mode: "redirectTo",
				});
			});
		});

	loading.value = false;
}

onReady(() => {
	list.value = router.params.buyList;
});
</script>

<style lang="scss" scoped>
.page {
	padding: 24rpx;

	.pay-icon {
		height: 56rpx;
		width: 56rpx;
		position: absolute;
		right: 0;
		top: calc(50% - 28rpx);
	}
}
</style>
