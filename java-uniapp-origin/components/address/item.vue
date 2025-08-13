<template>
	<view
		class="address-item"
		:style="{
			backgroundColor,
		}"
	>
		<view class="address-item__inner">
			<template v-if="item">
				<cl-text block color="info" :size="24"
					>{{ item?.province }}{{ item?.city }}{{ item?.district }}</cl-text
				>
				<cl-text block bold :size="30" :line-height="1.2" :margin="[14, 0, 14, 0]">{{
					item?.address
				}}</cl-text>

				<cl-row type="flex">
					<cl-text>{{ item?.contact }}</cl-text>
					<cl-text type="phone" :margin="[0, 20, 0, 100]" :value="item?.phone" />
					<cl-tag size="small" type="primary" round v-if="item?.isDefault"
						>默认地址</cl-tag
					>
				</cl-row>
			</template>

			<template v-else>
				<cl-text block bold :size="30" :margin="[6, 0, 14, 0]">选择地址</cl-text>
				<cl-text color="info" :size="24">设置默认地址后优先使用</cl-text>
			</template>
		</view>

		<view class="address-item__icon">
			<slot name="icon"> </slot>
		</view>
	</view>
</template>

<script lang="ts" setup>
import type { PropType } from "vue";

const props = defineProps({
	item: Object as PropType<Eps.UserAddressEntity>,
	backgroundColor: {
		type: String,
		default: "#ffffff",
	},
});
</script>

<style lang="scss" scoped>
.address-item {
	display: flex;
	box-sizing: border-box;
	width: 100%;
	border-radius: 24rpx;
	padding: 24rpx;

	&__inner {
		flex: 1;
	}

	&__icon {
		display: flex;
		align-items: center;
		justify-content: center;
		height: initial;
		font-size: 32rpx;
		margin-left: 24rpx;
	}
}
</style>
