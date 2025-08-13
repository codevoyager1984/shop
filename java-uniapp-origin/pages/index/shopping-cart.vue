<template>
	<cl-page status-bar-background="#f6f7fa">
		<view class="page">
			<cl-sticky>
				<cl-topbar
					title="购物车"
					:border="false"
					:show-back="false"
					background-color="#f6f7fa"
				>
					<template #prepend>
						<cl-row :margin="[0, 0, 0, 30]" v-if="!isEmpty(spCart.list)">
							<cl-text
								value="完成"
								@tap="op.command('delete-confirm')"
								v-if="op.mode == 'delete'"
							/>

							<cl-text value="管理" @tap="op.change('delete')" v-else />
						</cl-row>
					</template>
				</cl-topbar>
			</cl-sticky>

			<view class="list">
				<template v-if="!isEmpty(spCart.list)">
					<goods-group :data="spCart.list" spec-edit show-checkbox @spec="toEdit">
						<template #count="{ item }">
							<cl-input-number
								v-model="item.count"
								:min="0"
								:max="item.spec?.stock"
								@change="onStockChange(item)"
							/>
						</template>
					</goods-group>
				</template>

				<cl-empty
					icon="spopping-cart"
					text="购物车竟然是空的"
					height="calc(100% - 120rpx)"
					v-else
				>
					<cl-button round type="primary" @tap="router.push('/pages/goods/list')"
						>去逛逛</cl-button
					>
				</cl-empty>
			</view>

			<!-- 底部操作按钮 -->
			<cl-footer
				:flex="false"
				:bottom="120"
				:height="104"
				padding="20rpx 24rpx"
				v-if="!isEmpty(spCart.list)"
			>
				<cl-row type="flex">
					<cl-checkbox round :model-value="selectAll" @change="onSelectAllChange">
						全选
					</cl-checkbox>

					<view class="flex1" />

					<template v-if="op.mode == 'settle'">
						<cl-text value="合计：" />
						<cl-text type="price" color="error" :value="settleAmount" :size="40" />

						<cl-button round type="primary" :margin="[0, 0, 0, 20]" @tap="toSettle"
							>去结算</cl-button
						>
					</template>

					<template v-else-if="op.mode == 'delete'">
						<cl-button round @tap="toDel()">删除</cl-button>
					</template>
				</cl-row>
			</cl-footer>
		</view>

		<!-- 商品规格 -->
		<goods-spec />

		<tabbar />
	</cl-page>
</template>

<script lang="ts" setup>
import { computed, reactive } from "vue";
import { useShoppingCart, useSpec } from "/@/hooks";
import { assign, isEmpty } from "lodash-es";
import { useCool } from "/@/cool";
import { useUi } from "/$/cool-ui";
import GoodsSpec from "/@/components/goods/spec.vue";
import GoodsGroup from "/@/components/goods/group.vue";
import Tabbar from "./components/tabbar.vue";

type OpMode = "settle" | "delete";

const { router } = useCool();
const spCart = useShoppingCart();
const spec = useSpec();
const ui = useUi();

// 操作
const op = reactive({
	mode: "settle" as OpMode,

	change(key: OpMode) {
		op.mode = key;
	},

	command(key: string) {
		switch (key) {
			case "delete-confirm":
				op.done();
				break;
		}
	},

	done() {
		op.mode = "settle";
	},
});

// 结算金额
const settleAmount = computed(() => {
	return spCart.list
		.filter((e) => e.checked)
		.reduce((a, b) => {
			return a + b.count * (b.spec.price || 0);
		}, 0);
});

// 全选开关
const selectAll = computed(() => {
	return !isEmpty(spCart.list) && !spCart.list.find((e) => !e.checked);
});

// 监听全选切换
function onSelectAllChange(val?: boolean) {
	spCart.list.forEach((e) => {
		e.checked = val;
	});
}

// 编辑规格
function toEdit(item: OrderGoods) {
	spec.open({
		action: "edit",
		goods: item.goodsInfo,
		specId: item.spec.id,
		count: item.count,
		callback(action) {
			if (action == "edit") {
				// 同个规格
				if (spec.info?.id == item.spec.id) {
					assign(item, spec.info);
					assign(item.goodsInfo, spec.goods);
					item.count = spec.num;
				}
				// 不同规格
				else {
					// 移除当前
					spCart.del(item.id!);

					// 添加新规格
					spCart.add({
						count: spec.num,
						spec: spec.info!,
						goodsInfo: spec.goods!,
					});
				}
			}
		},
	});
}

// 删除商品
function toDel() {
	const ids = spCart.list.filter((e) => e.checked).map((e) => e.id);

	if (isEmpty(ids)) {
		ui.showToast("请先选择商品");
	} else {
		ui.showConfirm({
			title: "提示",
			message: "确定删除选中商品吗？",
			callback(action) {
				if (action == "confirm") {
					ids.forEach((id) => {
						spCart.del(id!);
					});
				}
			},
		});
	}
}

// 去结算
function toSettle() {
	const buyList = spCart.list.filter((e) => e.checked);

	if (isEmpty(buyList)) {
		ui.showToast("请先选择商品");
	} else {
		router.push({
			path: "/pages/order/submit",
			params: {
				buyList,
			},
		});
	}
}

// 监听库存变化
function onStockChange(item: OrderGoods) {
	if (item.count == 0) {
		ui.showConfirm({
			title: "提示",
			message: "确定删除该商品吗？",
			callback(action) {
				if (action == "confirm") {
					spCart.del(item.id!);
				} else {
					item.count = 1;
				}
			},
		});
	}
}
</script>

<style lang="scss" scoped>
.list {
	padding: 0 24rpx;
}
</style>
