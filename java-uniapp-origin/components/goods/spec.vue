<template>
	<cl-popup
		v-model="spec.visible"
		direction="bottom"
		:border-radius="[32, 32, 0, 0]"
		:padding="0"
		show-close-btn
		@open="onOpen"
	>
		<view class="goods-spec">
			<view class="goods">
				<goods-cover :item="spec.goods" :spec="spec.info" :link="false" :size="160" />

				<view class="det">
					<cl-row :margin="[0, 0, 16, 0]">
						<cl-text
							:value="spec.info?.price || spec.goods?.price"
							type="price"
							color="error"
							:size="40"
							bold
						/>
						<cl-text
							color="info"
							:size="24"
							:margin="[0, 0, 0, 30]"
							:value="`库存：${stock}`"
							v-if="spec.info"
						/>
					</cl-row>

					<cl-text
						:ellipsis="2"
						:line-height="1.2"
						:color="spec.info ? 'primary' : 'info'"
						:value="spec.text"
					/>
				</view>
			</view>

			<cl-row type="flex" justify="space-between" :margin="[0, 24, 16, 24]">
				<cl-text bold>规格分类（{{ list.length + 1 }}）</cl-text>

				<cl-icon
					:size="40"
					:class-name="`${isGrid ? 'shop-icon-list' : 'shop-icon-list2'}`"
					@tap="isGrid = !isGrid"
				/>
			</cl-row>

			<!-- 规格列表 -->
			<cl-loading-mask :loading="loading">
				<scroll-view scroll-y class="scroller">
					<view
						class="list"
						:class="{
							'is-grid': isGrid,
						}"
					>
						<cl-row :gutter="20">
							<cl-col
								v-for="(item, label) in list"
								:key="label"
								:span="isGrid ? 8 : 24"
							>
								<view
									class="item"
									:class="{
										'is-active': spec.info?.id == item.id,
										'is-disabled': !item.stock,
									}"
									@tap="select(item)"
								>
									<view class="cover">
										<goods-cover
											:item="spec.goods"
											:spec="item"
											:link="false"
											:radius="isGrid ? 24 : 12"
											:zoom="isGrid"
										/>
									</view>

									<view class="name">
										<text>{{ item.name }}</text>
									</view>
								</view>
							</cl-col>
						</cl-row>

						<cl-list-item
							label="数量"
							:arrow-icon="false"
							:margin="[0, -10, 0, -10]"
							v-if="spec.info"
						>
							<cl-input-number v-model="spec.num" :min="1" :max="stock" />
						</cl-list-item>
					</view>
				</scroll-view>
			</cl-loading-mask>

			<!-- 底部按钮 -->
			<cl-footer :fixed="false" :vt="[spec.action]">
				<!-- 加入购物车 -->
				<template v-if="spec.action == 'spCart'">
					<cl-button custom type="primary" :disabled="disabled" @tap="toAdd"
						>确定</cl-button
					>
				</template>

				<!-- 选规格的时候 -->
				<template v-else-if="spec.action == 'select'">
					<cl-button custom :disabled="disabled" @tap="toAdd">加入购物车</cl-button>
					<cl-button custom type="primary" :disabled="disabled" @tap="toBuy"
						>立即购买</cl-button
					>
				</template>

				<!-- 立即购买 -->
				<template v-else-if="spec.action == 'buy'">
					<cl-button custom type="primary" :disabled="disabled" @tap="toBuy"
						>立即购买</cl-button
					>
				</template>

				<!-- 编辑 -->
				<template v-else-if="spec.action == 'edit'">
					<cl-button custom type="primary" :disabled="disabled" @tap="toEdit"
						>确定</cl-button
					>
				</template>
			</cl-footer>
		</view>
	</cl-popup>
</template>

<script lang="ts" setup>
import { isEmpty } from "lodash-es";
import { computed, nextTick, onUnmounted, ref } from "vue";
import { useCool } from "/@/cool";
import { useSpec } from "/@/hooks";
import { useShoppingCart } from "/@/hooks";
import { useUi } from "/$/cool-ui";
import GoodsCover from "/@/components/goods/cover.vue";

const { service, router } = useCool();
const spec = useSpec();
const spCart = useShoppingCart();
const ui = useUi();

// 列表是否格子布局
const isGrid = ref(true);

// 加载规格中
const loading = ref(false);

// 规格列表
const list = ref<Eps.GoodsSpecEntity[]>([]);

// 库存数，减去购物车数量
const stock = computed(() => {
	const a = spec.info?.stock || 0;
	const b = spCart.list.find((e) => e.spec?.id == spec.info?.id)?.count || 0;

	if (spec.action == "edit") {
		return a;
	}

	let d = a - b;

	if (d < 0) {
		d = 0;
	}

	return d;
});

// 是否能下单
const disabled = computed(() => {
	return !stock.value;
});

// 获取规格列表
async function refresh(params?: any) {
	loading.value = true;

	await service.goods.spec
		.list({
			goodsId: spec.goods?.id,
			order: "sortNum",
			sort: "desc",
			...params,
		})
		.then((res) => {
			list.value = res.map((e) => {
				let arr = e.images || [];

				if (isEmpty(arr)) {
					arr = [spec.goods?.mainPic];
				}

				return {
					...e,
					cover: arr[0],
				};
			});

			// 设置规格
			spec.set(list.value.find((e) => e.id == spec.specId));
		});

	loading.value = false;
}

// 监听打开
function onOpen() {
	refresh();
}

// 选择规格
function select(item: Eps.GoodsSpecEntity) {
	if (spec.info?.id == item.id) {
		spec.set();
	} else {
		spec.set(item);
	}

	nextTick(() => {
		spec.setNum(1);
	});
}

// 购买
function toBuy() {
	spec.close();

	router.push({
		path: "/pages/order/submit",
		params: {
			buyList: [
				{
					spec: spec.info!,
					goodsInfo: spec.goods!,
					count: spec.num,
				},
			],
		},
	});
}

// 编辑
function toEdit() {
	spec.emit("edit");
	spec.close();
}

// 加入购物车
function toAdd() {
	spCart.add({
		count: spec.num,
		spec: spec.info!,
		goodsInfo: spec.goods!,
	});

	ui.showToast("添加购物车成功");
	spec.close();
}

onUnmounted(() => {
	spec.clear();
	spec.close();
});

defineExpose({
	refresh,
});
</script>

<style lang="scss" scoped>
.goods-spec {
	.goods {
		display: flex;
		padding: 24rpx;

		.det {
			flex: 1;
			padding: 24rpx 24rpx 0 24rpx;
		}
	}

	.scroller {
		height: 50vh;

		.list {
			padding: 0 24rpx;

			.item {
				display: flex;
				align-items: center;
				margin-bottom: 16rpx;
				border-radius: 12rpx;
				border: 2rpx solid #eeeeee;
				padding: 12rpx;

				.cover {
					height: 80rpx;
					width: 80rpx;
					flex-shrink: 0;
					position: relative;
				}

				.name {
					display: flex;
					align-items: center;
					padding: 0 16rpx;
					font-size: 24rpx;
					line-height: 1.4;
					flex: 1;

					text {
						word-break: break-all;
						overflow: hidden;
						text-overflow: ellipsis;
						display: -webkit-box;
						-webkit-box-orient: vertical;
						-webkit-line-clamp: 2;
					}
				}

				&.is-active {
					border-color: $cl-color-primary;
				}

				&.is-disabled {
					.cover {
						&::after {
							display: flex;
							align-items: center;
							justify-content: center;
							height: 100rpx;
							width: 100rpx;
							content: "无货";
							color: #fff;
							background-color: rgba(50, 50, 50, 0.6);
							border-radius: 100%;
							position: absolute;
							left: calc(50% - 50rpx);
							top: calc(50% - 50rpx);
							font-size: 26rpx;
							border: 1rpx solid currentColor;
							box-sizing: border-box;
							transform: scale(0.6);
						}
					}

					.name {
						color: $cl-color-disabled;
					}
				}
			}

			&.is-grid {
				.item {
					display: flex;
					flex-direction: column;
					align-items: center;
					justify-content: center;
					width: 100%;
					border-radius: 28rpx;
					padding: 0;
					margin-bottom: 24rpx;

					.cover {
						height: 216rpx;
						width: 100%;

						&::after {
							transform: scale(1);
						}
					}

					.name {
						flex: none;
						height: 70rpx;
						line-height: 1.2;
					}
				}
			}
		}
	}
}
</style>
