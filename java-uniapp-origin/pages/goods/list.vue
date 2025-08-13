<template>
	<cl-page>
		<view class="page">
			<cl-sticky>
				<!-- 搜索栏 -->
				<cl-topbar :border="false" :show-back="false" :ref="setRefs('topbar')" with-mp>
					<cl-search
						v-model="keyWord"
						placeholder="搜索商品名称"
						@search="refresh({ page: 1 })"
					>
						<template #prepend>
							<cl-icon name="arrow-left" size="22px" @tap="refs.topbar?.back" />
						</template>
					</cl-search>
				</cl-topbar>

				<!-- 筛选栏 -->
				<filter-bar @search="refresh" />
			</cl-sticky>

			<!-- 商品列表 -->
			<view class="list">
				<cl-waterfall :ref="setRefs('list')" :column="2">
					<template #default="{ list }">
						<cl-waterfall-column v-for="(columns, index) in list" :key="index">
							<goods-item :item="item" v-for="item in columns" :key="item.id" />
						</cl-waterfall-column>
					</template>
				</cl-waterfall>
			</view>
		</view>
	</cl-page>
</template>

<script lang="ts" setup>
import { ref } from "vue";
import { onReady } from "@dcloudio/uni-app";
import { useCool, usePager } from "/@/cool";
import GoodsItem from "/@/components/goods/item.vue";
import FilterBar from "./components/filter-bar.vue";

const { router, service, refs, setRefs } = useCool();
const { onRefresh, onData } = usePager();

const keyWord = ref("");

// 获取列表
function refresh(params?: any) {
	const { data, next } = onRefresh(params);

	onData((list) => {
		if (data.page == 1) {
			refs.list.refresh(list);
		} else {
			refs.list.append(list);
		}
	});

	return next(
		service.goods.info.page({
			...data,
			keyWord: keyWord.value,
		}),
	);
}

onReady(() => {
	keyWord.value = router.query.keyWord || "";
	refresh({ order: "sortNum", sort: "desc", ...router.query });
});

defineExpose({
	refresh,
});
</script>

<style lang="scss" scoped>
.page {
	.list {
		padding: 24rpx 14rpx;
	}
}
</style>
