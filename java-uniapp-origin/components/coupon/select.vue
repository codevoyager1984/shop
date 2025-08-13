<template>
	<view class="coupon-select__inner" @tap="open">
		<template v-if="checked">
			<cl-text :size="28" color="error">-</cl-text>
			<cl-text :size="28" type="price" color="error" :value="checked.amount" />
		</template>

		<cl-text color="info" v-else>选择优惠券</cl-text>
	</view>

	<cl-popup
		v-model="visible"
		direction="bottom"
		:border-radius="[32, 32, 0, 0]"
		:padding="0"
		title="选择优惠券"
		show-close-btn
	>
		<view class="coupon-select">
			<scroll-view class="scroller" scroll-y>
				<view class="list">
					<view class="item" v-for="item in checkList" :key="item.id" @tap="select(item)">
						<coupon-item
							:item="item"
							:checked="checked?.id == item.id"
							:disabled="item.disabled"
						/>
					</view>
				</view>

				<cl-empty :fixed="false" :margin="[-40, 0, 0, 0]" v-if="isEmpty(list)" />
			</scroll-view>
		</view>
	</cl-popup>
</template>

<script lang="ts" setup>
import { computed, ref } from "vue";
import { useCool } from "/@/cool";
import { isEmpty } from "lodash-es";
import CouponItem from "./item/index.vue";
import { useUi } from "/$/cool-ui";

const props = defineProps({
	totalAmount: {
		type: Number,
		default: 0,
	},
});

const emit = defineEmits(["success"]);

const { service } = useCool();
const ui = useUi();

// 是否可见
const visible = ref(false);

// 选中值
const checked = ref<CouponInfo>();

// 优惠券列表
const list = ref<CouponInfo[]>();

// 待选列表
const checkList = computed(() => {
	return list.value?.map((e) => {
		let disabled = true;

		switch (e.type) {
			case 0:
				if (props.totalAmount >= e.condition?.fullAmount!) {
					disabled = false;
				}
				break;
		}

		return {
			...e,
			disabled,
		};
	});
});

// 打开弹窗
function open() {
	visible.value = true;

	// 获取未使用的优惠券
	service.market.coupon.user.page({ page: 1, size: 100, status: 0 }).then((res) => {
		list.value = res.list;
	});
}

// 关闭弹窗
function close() {
	visible.value = false;
}

// 选择
function select(item: CouponInfo) {
	if (item.disabled) {
		ui.showToast("优惠券不可用");
	} else {
		if (checked.value?.id == item.id) {
			checked.value = undefined;
		} else {
			checked.value = item;
		}

		close();
	}
}

defineExpose({
	open,
	close,
	checked,
});
</script>

<style lang="scss" scoped>
.coupon-select {
	height: 60vh;

	.scroller {
		height: 100%;

		.list {
			padding: 0 32rpx;
		}
	}
}
</style>
