<template>
	<cl-page>
		<view class="page">
			<!-- 搜索 -->
			<cl-sticky>
				<view class="search-bar" @tap="toSearch">
					<view class="search-bar__inner">
						<cl-icon name="search" :margin="[0, 12, 0, 0]"> </cl-icon>
						<cl-text value="搜索商品名称"></cl-text>
					</view>
				</view>
			</cl-sticky>

			<!-- 轮播图 -->
			<banner />

			<!-- 热门分类 -->
			<hot-category />

			<!-- 优惠券活动 -->
			<coupon-activity />

			<!-- 热门推荐 -->
			<view class="hot">
				<view class="hot__header">
					<text class="hot__title">热门推荐</text>
					<text class="hot__desc">TOP PICKS</text>
				</view>

				<view class="hot__container">
					<cl-waterfall :ref="setRefs('list')" :column="2">
						<template #default="{ list }">
							<cl-waterfall-column v-for="(columns, index) in list" :key="index">
								<goods-item :item="item" v-for="item in columns" :key="item.id" />
							</cl-waterfall-column>
						</template>

						<template #empty>
							<cl-empty :fixed="false" :height="500" />
						</template>
					</cl-waterfall>
				</view>
			</view>

			<!-- 版本检测 -->
			<cl-version-upgrade :ref="setRefs('versionUpgrade')" />
		</view>

		<tabbar />
	</cl-page>
</template>

<script lang="ts" setup>
import { router, useCool, usePager } from "/@/cool";
import { onReady, onShareAppMessage } from "@dcloudio/uni-app";
import Tabbar from "./components/tabbar.vue";
import GoodsItem from "/@/components/goods/item.vue";
import CouponActivity from "./components/coupon-activity.vue";
import HotCategory from "./components/hot-category.vue";
import Banner from "./components/banner.vue";

const { service, refs, setRefs } = useCool();
const { onRefresh, onData } = usePager();

// 获取热门推荐
function refresh(params?: any) {
	const { data, next } = onRefresh(params, { loading: false });

	onData((list) => {
		if (data.page == 1) {
			refs.list.refresh(list);
		} else {
			refs.list.append(list);
		}
	});

	return next(service.goods.info.page(data));
}

// 商品搜索
function toSearch() {
	router.push("/pages/goods/search");
}

onReady(() => {
	refresh({ order: "sortNum", sort: "desc" });

	// #ifdef APP
	refs.versionUpgrade?.check();
	// #endif
});

onShareAppMessage(() => {
	return {
		title: "能用钱解决的事，就不要客气",
		path: "/pages/index/home",
	};
});

defineExpose({
	refresh,
});
</script>

<style lang="scss" scoped>
.page {
	.search-bar {
		background-color: #fff;
		padding: 24rpx;

		&__inner {
			display: flex;
			align-items: center;
			height: 70rpx;
			width: 100%;
			border-radius: 70rpx;
			background-color: #f7f7f7;
			box-sizing: border-box;
			padding: 0 24rpx;
		}
	}

	.hot {
		&__header {
			display: flex;
			flex-direction: column;
			align-items: center;
			margin: 40rpx 0;
		}

		&__title {
			font-size: 32rpx;
			margin-bottom: 10rpx;
		}

		&__desc {
			font-size: 22rpx;
			color: #999;
			letter-spacing: 5rpx;
		}

		&__container {
			padding: 0 14rpx;
		}
	}
}
</style>
