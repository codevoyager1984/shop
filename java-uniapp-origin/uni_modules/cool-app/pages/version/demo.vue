<template>
	<cl-page>
		<view class="page">
			<cl-text block :margin="[0, 0, 20, 0]">当前版本：{{ version?.num }}</cl-text>
			<cl-text block color="success"
				>最新版本：{{ version.updateInfo?.version || "-" }}</cl-text
			>

			<cl-button :margin="[20, 0, 0, 0]" @tap="check">点击测试</cl-button>

			<!-- 版本升级组件 -->
			<cl-version-upgrade :ref="setRefs('versionUpgrade')" />
		</view>
	</cl-page>
</template>

<script lang="ts" setup>
import { onReady } from "@dcloudio/uni-app";
import { useCool } from "/@/cool";
import { useVersion } from "../../hooks";

const { refs, setRefs } = useCool();
const version = useVersion();

function check() {
	refs.versionUpgrade?.check();
}

onReady(() => {
	check();
});
</script>

<style lang="scss" scoped>
.page {
	padding: 20rpx;
}
</style>
