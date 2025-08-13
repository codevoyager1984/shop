<template>
	<view class="coupon-get__btns">
		<cl-skeleton
			v-for="item in list"
			:key="item.id"
			:margin="[0, 10, 0, 0]"
			:height="46"
			:loading-style="{
				width: '150rpx',
			}"
			:loading="loading"
		>
			<view class="item" @tap="open">
				<text class="shop-icon-coupon"></text>
				<text>{{ item.text }}</text>
			</view>
		</cl-skeleton>
	</view>

	<cl-popup
		v-model="visible"
		direction="bottom"
		:border-radius="[32, 32, 0, 0]"
		:padding="0"
		title="领取优惠券"
		show-close-btn
	>
		<view class="coupon-get">
			<scroll-view class="scroller" scroll-y>
				<view class="list">
					<coupon-item :item="item" v-for="item in list" :key="item.id">
						<template #btn>
							<cl-button
								type="primary"
								:height="46"
								:width="116"
								:font-size="22"
								:disabled="!!item.userId"
								size="small"
								:margin="[0, 0, 0, 20]"
								@tap="toGet(item)"
							>
								{{ item.userId ? "已领取" : "立即领取" }}
							</cl-button>
						</template>
					</coupon-item>
				</view>

				<cl-empty :fixed="false" :margin="[-40, 0, 0, 0]" v-if="isEmpty(list)" />
			</scroll-view>
		</view>
	</cl-popup>
</template>

<script lang="ts" setup>
import { onMounted, ref } from "vue";
import { useCool } from "/@/cool";
import { useUi } from "/$/cool-ui";
import CouponItem from "./item/index.vue";
import { isEmpty } from "lodash-es";

const emit = defineEmits(["success"]);

const { service } = useCool();
const ui = useUi();

// 是否可见
const visible = ref(false);

// 获取中
const loading = ref(false);

// 优惠券列表
const list = ref<CouponInfo[]>([{}, {}]);

// 获取列表
async function refresh() {
	await service.market.coupon.info.page({ page: 1, size: 100 }).then((res) => {
		list.value = res.list.map((e) => {
			switch (e.type) {
				case 0:
					e.text = `满${e.condition?.fullAmount}减${Math.floor(e.amount!)}元`;
					break;
				default:
					e.text = "";
					break;
			}

			return e;
		});
	});
}

// 打开弹窗
function open() {
	visible.value = true;
	refresh();
}

// 关闭弹窗
function close() {
	visible.value = false;
}

// 领券
async function toGet(item: CouponInfo) {
	item.userId = true;

	service.market.coupon.user
		.receive({
			couponId: item.id,
		})
		.then(() => {
			ui.showToast("领取成功");
		})
		.catch((err) => {
			ui.showToast(err.message);
			item.userId = null;
		});
}

onMounted(async () => {
	loading.value = true;
	await refresh();
	loading.value = false;
});

defineExpose({
	open,
	close,
});
</script>

<style lang="scss" scoped>
.coupon-get {
	&__btns {
		display: flex;
		flex-wrap: wrap;

		.item {
			display: inline-flex;
			align-items: center;
			padding: 0 6rpx;
			height: 100%;
			border: 2rpx solid $cl-color-primary;
			box-sizing: border-box;
			border-radius: 8rpx;
			font-size: 22rpx;
			line-height: 1;

			.shop-icon-coupon {
				font-size: 36rpx;
				margin-right: 2rpx;
			}
		}
	}

	.scroller {
		height: 60vh;

		.list {
			padding: 0 32rpx;
		}
	}
}
</style>
