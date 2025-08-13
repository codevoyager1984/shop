<template>
	<cl-page background-color="#fff" fullscreen>
		<view class="page-category">
			<view class="menu-a">
				<scroll-view class="scroller" scroll-y>
					<view class="list">
						<view
							class="item"
							v-for="(item, index) in list"
							:key="index"
							:class="{
								'is-active': index == active,
								'is-prev': index == active - 1,
								'is-next': index == active + 1,
							}"
							@tap="toView(index)"
						>
							{{ item.name }}
						</view>

						<view class="flex1"></view>
					</view>
				</scroll-view>
			</view>

			<view class="menu-b">
				<scroll-view
					class="scroller"
					scroll-y
					:scroll-into-view="view"
					scroll-with-animation
					enable-back-to-top
					@scroll="onScroll"
				>
					<view
						class="group"
						v-for="(group, index) in list"
						:key="group.id"
						:id="`group-${index}`"
					>
						<view class="label">
							<cl-divider background-color="#ffffff">{{ group.name }}</cl-divider>
						</view>

						<view class="list">
							<cl-row>
								<cl-col :span="8" v-for="item in group.children" :key="item.id">
									<view class="item" @tap="toSearch(item)">
										<cl-image
											mode="aspectFit"
											:size="120"
											:radius="24"
											:src="resizeImage(item.pic, 200)"
											:margin="[0, 0, 20, 0]"
										/>
										<cl-text :size="24" :ellipsis="1" :value="item.name" />
									</view>
								</cl-col>
							</cl-row>
						</view>
					</view>
				</scroll-view>
			</view>
		</view>

		<tabbar />
	</cl-page>
</template>

<script lang="ts" setup>
import Tabbar from "./components/tabbar.vue";
import { useCool, useUpload } from "/@/cool";
import { useUi } from "/$/cool-ui";
import { deepTree } from "/@/cool/utils";
import { ref } from "vue";
import { onPullDownRefresh, onReady } from "@dcloudio/uni-app";

const { service, router } = useCool();
const { resizeImage } = useUpload();
const ui = useUi();

const list = ref<Eps.GoodsTypeEntity[]>();
const view = ref("");
const active = ref(0);

// 获取分类
async function refresh() {
	ui.showLoading();

	await service.goods.type.list().then((res) => {
		list.value = deepTree(res);

		list.value.reduce((a, b) => {
			b.top = uni.upx2px(Math.ceil((b.children || []).length / 3) * 224 + 80) + a;
			return b.top;
		}, 0);
	});

	ui.hideLoading();
}

// 滚动时的锁
let lock: any;
function clearLock() {
	if (lock) {
		clearTimeout(lock);
		lock = null;
	}
}

// 监听滚动
function onScroll(e: { detail: { scrollTop: number } }) {
	if (!lock) {
		list.value?.find((a, i) => {
			if (a.top >= e.detail.scrollTop) {
				active.value = i;
				return true;
			}
		});
	}
}

// 滚动到指定分类
function toView(index: number) {
	clearLock();

	active.value = index;
	view.value = `group-${index}`;

	lock = setTimeout(() => {
		clearLock();
	}, 500);
}

// 搜索
function toSearch(item: Eps.GoodsTypeEntity) {
	router.push({
		path: "/pages/goods/list",
		query: {
			typeId: item.id,
		},
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
.page-category {
	display: flex;
	height: calc(100% - 120rpx);

	.scroller {
		height: 100%;
	}

	.menu-a {
		width: 180rpx;
		flex-shrink: 0;
		overflow: hidden;
		border-radius: 0 24rpx 0 0;

		.list {
			display: flex;
			flex-direction: column;
			height: 100%;

			.flex1 {
				background-color: #f7f7f7;
				flex: 1;
			}
		}

		.item {
			display: flex;
			align-items: center;
			justify-content: center;
			text-align: center;
			padding: 30rpx 0;
			font-size: 26rpx;
			position: relative;
			color: $cl-color-placeholder;
			background-color: #f7f7f7;

			&.is-active {
				background-color: #fff;
				color: $cl-color-primary;
				font-weight: 500;

				&::after {
					content: "";
					height: 34rpx;
					width: 6rpx;
					background-color: currentColor;
					border-radius: 10rpx;
					position: absolute;
					left: 0;
					top: calc(50% - 17rpx);
				}

				& + .flex1 {
					border-top-right-radius: 24rpx;
				}
			}

			&.is-prev {
				border-bottom-right-radius: 24rpx;
			}

			&.is-next {
				border-top-right-radius: 24rpx;
			}
		}
	}

	.menu-b {
		flex: 1;

		.group {
			.label {
				padding: 0 30rpx;
			}

			.list {
				.item {
					display: flex;
					flex-direction: column;
					align-items: center;
					padding: 10rpx;
					margin-bottom: 40rpx;
				}
			}
		}
	}
}
</style>
