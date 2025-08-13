<template>
	<view class="banner">
		<cl-banner :list="list" type="card" :height="300" @select="toPath">
			<template #item="{ item }">
				<cl-skeleton
					height="100%"
					:loading="!item.pic"
					:loading-style="{
						borderRadius: '24rpx',
					}"
				>
					<image mode="aspectFill" :src="item.pic" />
				</cl-skeleton>
			</template>
		</cl-banner>
	</view>
</template>

<script lang="ts" setup>
import { onShow } from "@dcloudio/uni-app";
import { ref } from "vue";
import { useCool } from "/@/cool";

const { service, router } = useCool();

const list = ref<Eps.InfoBannerEntity[]>([{}, {}, {}]);

function refresh() {
	service.info.banner.list({ order: "sortNum", sort: "desc" }).then((res) => {
		list.value = res;
	});
}

function toPath(index: number) {
	const { path } = list.value[index];

	if (path) {
		router.push(path);
	}
}

onShow(() => {
	refresh();
});
</script>

<style lang="scss" scoped>
.banner {
	margin: 32rpx 0;

	image {
		height: 100%;
		width: 100%;
		border-radius: 24rpx;
	}
}
</style>
