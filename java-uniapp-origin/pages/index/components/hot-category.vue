<template>
	<view class="hot-types">
		<scroll-view scroll-x class="scroller">
			<view class="inner">
				<view class="item" v-for="item in list" :key="item.id" @tap="toPath(item)">
					<cl-skeleton
						:height="120"
						:width="120"
						:margin="[0, 0, 20, 0]"
						:loading="!item.id"
						:loading-style="{
							borderRadius: '100%',
						}"
					>
						<cl-image :radius="120" :src="item.pic" background-color="#fff" />
					</cl-skeleton>

					<cl-skeleton
						:height="28"
						:loading="!item.id"
						:loading-style="{
							width: '90rpx',
							borderRadius: '6rpx',
						}"
					>
						<cl-text :size="24" align="center" block>{{ item.name }}</cl-text>
					</cl-skeleton>
				</view>
			</view>
		</scroll-view>
	</view>
</template>

<script lang="ts" setup>
import { onShow } from "@dcloudio/uni-app";
import { ref } from "vue";
import { useCool } from "/@/cool";
import { deepTree } from "/@/cool/utils";

interface Item extends Eps.GoodsTypeEntity {
	children?: Item[];
}

const { service, router } = useCool();

const list = ref<Item[]>([{}, {}, {}, {}, {}]);

function refresh() {
	service.goods.type.list({ order: "sortNum", sort: "desc" }).then((res) => {
		list.value = deepTree(res);
	});
}

function toPath(item: Item) {
	if (item.id) {
		router.push({
			path: "/pages/goods/list",
			query: {
				typeId: item.children?.map((e) => e.id).join(","),
			},
		});
	}
}

onShow(() => {
	refresh();
});
</script>

<style lang="scss" scoped>
.hot-types {
	height: 180rpx;
	overflow: hidden;
	margin: 50rpx 0 50rpx 0;

	.scroller {
		height: 200rpx;

		.inner {
			display: flex;
			align-items: center;

			.item {
				display: flex;
				flex-direction: column;
				align-items: center;
				justify-content: center;
				padding: 0 24rpx;
			}
		}
	}
}
</style>
