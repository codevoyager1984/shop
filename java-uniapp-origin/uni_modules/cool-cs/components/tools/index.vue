<template>
	<view
		class="cs-tools"
		:style="{
			height: tools.visible ? height : '0rpx',
		}"
	>
		<scroll-view scroll-y class="scroller">
			<emoji v-show="tools.mode == 'emoji'" />
			<fn v-show="tools.mode == 'fn'" />
		</scroll-view>
	</view>
</template>

<script setup lang="ts">
import { useTools } from "../../hooks";
import Emoji from "./emoji.vue";
import Fn from "./fn.vue";

defineProps({
	height: {
		type: String,
		default: "540rpx",
	},
});

const tools = useTools();
</script>

<style lang="scss" scoped>
.cs-tools {
	transition: height 0.3s;
	overflow: hidden;

	.scroller {
		height: 100%;
		background-color: #f7f7f7;
	}
}
</style>
