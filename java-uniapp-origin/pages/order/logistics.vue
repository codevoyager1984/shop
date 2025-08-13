<template>
	<cl-page>
		<view class="page">
			<!-- 收货地址 -->
			<address-select disabled />

			<!-- 物流列表 -->
			<view class="card">
				<template v-if="logistics">
					<cl-row type="flex" justify="space-between" :margin="[10, 0, 20, 0]">
						<cl-text :size="24" color="info">
							{{ logistics.expName }} {{ logistics.number }}
						</cl-text>
						<cl-text :size="24" color="info" @tap="toCopy">复制</cl-text>
					</cl-row>

					<!-- 运送记录 -->
					<view class="record">
						<cl-timeline>
							<cl-timeline-item
								v-for="(item, index) in logistics.list"
								:key="index"
								:timestamp="item.time"
								:content="item.status"
							/>
						</cl-timeline>
					</view>
				</template>

				<cl-empty :fixed="false" :height="600" text="暂无物流信息" v-else />
			</view>
		</view>
	</cl-page>
</template>

<script lang="ts" setup>
import { onPullDownRefresh, onReady } from "@dcloudio/uni-app";
import { useCool } from "/@/cool";
import { ref } from "vue";
import { useUi } from "/$/cool-ui";
import AddressSelect from "/@/components/address/select.vue";

const { router, service } = useCool();
const ui = useUi();

// 订单详情
const info = ref<OrderInfo>();

// 物流信息
const logistics = ref();

// 获取订单详情
async function refresh() {
	const { orderId } = router.query;

	ui.showLoading();

	try {
		service.order.info
			.info({
				id: orderId,
			})
			.then((res) => {
				info.value = res;
			});

		await service.order.info
			.logistics({
				orderId,
			})
			.then((res) => {
				logistics.value = res;
			});
	} catch (err: any) {
		ui.showTips(err.message, () => {
			router.back();
		});
	}

	ui.hideLoading();
}

// 复制物流单号
function toCopy() {
	uni.setClipboardData({
		data: info.value?.logistics.num!,
	});
}

onPullDownRefresh(async () => {
	await refresh();
	uni.stopPullDownRefresh();
});

onReady(() => {
	refresh();
});
</script>

<style lang="scss" scoped>
.page {
	padding: 24rpx;

	.record {
		margin-top: 6rpx;
	}
}
</style>
