<template>
	<cl-popup
		v-model="visible"
		:padding="0"
		background-color="transparent"
		:close-on-click-modal="!version.updateInfo?.forceUpdate"
		@close="onClose"
		@closed="onClosed"
	>
		<view class="cl-version-upgrade">
			<image src="/uni_modules/cool-app/static/bg.png" class="bg" mode="widthFix" />

			<view class="wrap">
				<cl-text :size="32" bold block>
					发现新版本 v{{ version.updateInfo?.version }}
				</cl-text>

				<text class="desc">{{ version.updateInfo?.description }}</text>

				<cl-button
					round
					type="primary"
					:height="80"
					:width="300"
					:font-size="30"
					:margin="[0, 0, 50, 0]"
					@tap="next()"
				>
					{{ version.progress ? `下载中 ${version.progress || 0}%` : "立即更新" }}
				</cl-button>

				<cl-text
					color="primary"
					v-if="!version.updateInfo?.forceUpdate"
					:margin="[-10, 0, 50, 0]"
					@tap="close"
				>
					稍后再说
				</cl-text>
			</view>
		</view>
	</cl-popup>
</template>

<script setup lang="ts">
import { ref } from "vue";
import { useVersion } from "../../hooks";

const version = useVersion();

const visible = ref(false);

function close() {
	visible.value = false;
}

function onClose() {
	version.cancel();
}

function onClosed() {
	version.clear();
}

async function check() {
	await version.check();

	if (version.isUpgrade) {
		visible.value = true;
	}
}

function next() {
	version.update(false, () => {
		close();
	});
}

defineExpose({
	check,
});
</script>

<style lang="scss" scoped>
.cl-version-upgrade {
	border-radius: 26rpx;
	background-color: #fff;
	position: relative;
	width: 600rpx;
	padding-top: 260rpx;

	.bg {
		display: block;
		position: absolute;
		top: -70rpx;
		left: 0;
		width: 100%;
		display: block;
	}

	.wrap {
		position: relative;
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: center;

		.desc {
			display: block;
			font-size: 26rpx;
			line-height: 1.4;
			margin: 30rpx 0 50rpx 0;
			padding: 0 50rpx;
			width: 100%;
			box-sizing: border-box;
		}
	}
}
</style>
