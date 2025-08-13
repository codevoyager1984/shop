<template>
	<cl-page>
		<view class="page">
			<cl-sticky>
				<cl-topbar :border="false">
					<cl-tabs
						v-model="tabs.active"
						:list="tabs.list"
						justify="center"
						un-color="#777"
						@change="tabs.onChange"
						v-if="tabs.scrollTop >= 200"
					/>

					<view class="cl-topbar__text" v-else>
						<text class="cl-topbar__title">商品详情</text>
					</view>
				</cl-topbar>
			</cl-sticky>

			<!-- 商品信息 -->
			<goods-info :info="info" />

			<!-- 商品评论 -->
			<goods-comment :info="info" />

			<!-- 商品详情 -->
			<goods-detail :info="info" />

			<!-- 商品规格 -->
			<goods-spec :ref="setRefs('goodsSpec')" />

			<cl-footer border :flex="false" :padding="0">
				<view class="footer">
					<view class="icons">
						<view class="item" @tap="toCs">
							<cl-icon
								class-name="shop-icon-message"
								:size="56"
								:margin="[0, 0, 8, 0]"
							/>
							<cl-text :size="22" value="客服" />
						</view>

						<view class="item" @tap="router.push('/pages/index/shopping-cart')">
							<cl-badge :value="spCart.num" :margin="[10, 6, 0, 0]">
								<cl-icon
									class-name="shop-icon-q-order"
									:size="56"
									:margin="[0, 0, 8, 0]"
								/>
							</cl-badge>
							<cl-text :size="22" value="购物车" />
						</view>
					</view>

					<cl-button :disabled="!info" @tap="openSpec('spCart')">加入购物车</cl-button>
					<cl-button :disabled="!info" type="primary" @tap="openSpec('buy')"
						>立即购买</cl-button
					>
				</view>
			</cl-footer>
		</view>
	</cl-page>
</template>

<script lang="ts" setup>
import { reactive, ref } from "vue";
import { useCool } from "/@/cool";
import { onPageScroll, onReady } from "@dcloudio/uni-app";
import { last } from "lodash-es";
import { useShoppingCart, useSpec } from "/@/hooks";
import GoodsInfo from "./components/goods-info.vue";
import GoodsComment from "./components/goods-comment.vue";
import GoodsDetail from "./components/goods-detail.vue";
import GoodsSpec from "/@/components/goods/spec.vue";

const { service, router, refs, setRefs } = useCool();
const spec = useSpec();
const spCart = useShoppingCart();
const { statusBarHeight = 0 } = uni.getSystemInfoSync();

// 顶部选项栏
const tabs = reactive({
	scrollTop: 0,
	active: "info",

	list: [
		{
			label: "信息",
			value: "info",
			top: 0,
		},
		{
			label: "评论",
			value: "comment",
			top: 0,
		},
		{
			label: "详情",
			value: "detail",
			top: 0,
		},
	],

	getSelector(name: string) {
		let selector = `.goods-${name}`;

		// #ifdef MP
		selector = ".page >>> " + selector;
		// #endif

		return selector;
	},

	async getTop() {
		return Promise.all(
			tabs.list.map((e) => {
				return new Promise((resolve) => {
					uni.createSelectorQuery()
						.select(tabs.getSelector(e.value))
						.boundingClientRect((res) => {
							e.top = res.top || 0 - 44;
							resolve(true);
						})
						.exec();
				});
			}),
		);
	},

	lock: null as any,

	async onScroll(top: number) {
		tabs.scrollTop = top;

		if (!tabs.lock) {
			if (!last(tabs.list)?.top) {
				await tabs.getTop();
			}

			const d = last(tabs.list.filter((e) => top + 44 >= e.top));

			if (d) {
				tabs.active = d.value;
			}
		}
	},

	onChange(name: string) {
		if (tabs.lock) {
			clearTimeout(tabs.lock);
		}

		uni.createSelectorQuery()
			.select(tabs.getSelector(name))
			.boundingClientRect((res) => {
				uni.pageScrollTo({
					scrollTop: (res.top || 0) - statusBarHeight - 44 + tabs.scrollTop,
					duration: 200,
				});

				tabs.lock = setTimeout(() => {
					tabs.lock = null;
				}, 500);
			})
			.exec();
	},
});

// 商品详情
const info = ref<Eps.GoodsInfoEntity>();

// 获取详情
async function refresh() {
	await service.goods.info
		.info({
			id: router.query.id,
		})
		.then((res) => {
			info.value = res;
		});
}

// 联系客服
function toCs() {
	router.push({
		path: "/uni_modules/cool-cs/pages/chat",
		query: {
			goodsId: info.value?.id,
		},
	});
}

// 打开规格
function openSpec(action: any) {
	spec.open({
		action,
		goods: info.value!,
	});
}

onReady(async () => {
	const { id, specId } = router.query || {};

	// 清空规格信息
	spec.clear();

	// 获取商品信息
	await refresh();

	// 当带入指定规格参数 specId 时
	if (specId) {
		// 设置规格
		spec.setId(specId);

		// 获取列表
		refs.goodsSpec.refresh({
			goodsId: id,
		});
	}
});

onPageScroll((e) => {
	tabs.onScroll(e.scrollTop);
});
</script>

<style lang="scss" scoped>
.page {
	.footer {
		display: flex;
		align-items: center;
		padding: 24rpx 24rpx 24rpx 0;

		.icons {
			display: flex;
			flex: 1;

			.item {
				display: flex;
				flex-direction: column;
				align-items: center;
				justify-content: center;
				width: 120rpx;
			}
		}
	}
}
</style>
