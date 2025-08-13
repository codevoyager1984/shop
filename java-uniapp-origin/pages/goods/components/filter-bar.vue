<template>
	<view class="filter-bar">
		<cl-filter-bar v-model="flt.form" @change="flt.onChange">
			<cl-filter-item label="综合" prop="score" type="switch" />
			<cl-filter-item label="销量" prop="sold" type="order" />
			<cl-filter-item label="价格" prop="price" type="order" />

			<view class="cl-filter-item" @tap="adv.open">
				<text>筛选</text>
				<cl-icon class-name="shop-icon-filter" :size="36" />
			</view>
		</cl-filter-bar>
	</view>

	<!-- 侧边筛选 -->
	<cl-popup v-model="adv.visible" direction="right" :size="600" :padding="0">
		<view class="filter-adv">
			<!-- 筛选内容 -->
			<scroll-view scroll-y class="filter-adv__container">
				<view class="filter-adv__list">
					<!-- 价格 -->
					<view class="filter-adv__item">
						<text class="label">价格区间</text>

						<cl-row type="flex" justify="space-between">
							<cl-input
								v-model="adv.form.minPrice"
								placeholder="最低价"
								type="number"
								round
								align="center"
								@confirm="toSearch"
							/>

							<cl-icon name="minus" :margin="[0, 20, 0, 20]" />

							<cl-input
								v-model="adv.form.maxPrice"
								placeholder="最高价"
								type="number"
								round
								align="center"
								@confirm="toSearch"
							/>
						</cl-row>
					</view>

					<!-- 分类 -->
					<view class="filter-adv__item">
						<cl-loading-mask :loading="type.loading">
							<view class="category">
								<!-- 一级 -->
								<template v-for="m1 in type.list">
									<view class="m1" :key="m1.id" v-if="m1.children">
										<text class="label">
											{{ m1.name }}
										</text>

										<view class="m1-list">
											<!-- 二级 -->
											<view
												class="m2"
												v-for="m2 in m1.children"
												:key="m2.id"
												:class="{
													'is-active': adv.form.typeId.includes(m2.id!),
												}"
												@tap="type.select(m2)"
											>
												{{ m2.name }}
											</view>
										</view>
									</view>
								</template>
							</view>
						</cl-loading-mask>
					</view>
				</view>
			</scroll-view>

			<!-- 操作按钮 -->
			<view class="filter-adv__footer">
				<view class="inner">
					<button class="reset" @tap="adv.reset">重置</button>
					<button class="confirm" @tap="adv.confirm">确定</button>
				</view>
			</view>
		</view>
	</cl-popup>
</template>

<script lang="ts" setup>
import { cloneDeep, isEmpty } from "lodash-es";
import { useCool } from "/@/cool";
import { deepTree } from "/@/cool/utils";
import { onMounted, reactive } from "vue";

interface Type extends Eps.GoodsTypeEntity {
	children?: Eps.GoodsTypeEntity[];
}

const emit = defineEmits(["search"]);

const { service, router } = useCool();

// 高级搜索
const adv = reactive({
	visible: false,

	form: {
		minPrice: "",
		maxPrice: "",
		typeId: [] as number[],
	},
	_form: null as any,

	open() {
		adv.visible = true;

		// 默认表单
		if (!adv._form) {
			adv._form = cloneDeep(adv.form);
		}

		// 获取分类
		type.refresh();
	},

	close() {
		adv.visible = false;
	},

	reset() {
		adv.form = cloneDeep(adv._form);
		adv.confirm();
	},

	confirm() {
		adv.close();
		toSearch();
	},
});

// 过滤栏
const flt = reactive({
	form: {
		sold: "",
		price: "desc",
		score: false,
	},

	onChange({ prop, value }: { prop: "sold" | "price" | "score"; value: never }) {
		flt.form.sold = "";
		flt.form.price = "";
		flt.form.score = false;
		flt.form[prop] = value;

		switch (prop) {
			case "score":
				toSearch({
					order: undefined,
					sort: undefined,
				});
				break;

			case "price":
			case "sold":
				toSearch({
					order: value ? prop : undefined,
					sort: value || undefined,
				});
				break;
		}
	},
});

// 商品分类
const type = reactive({
	list: [] as Type[],
	loading: false,

	async refresh() {
		type.loading = true;

		await service.goods.type.list().then((res) => {
			type.list = deepTree(res);
		});

		type.loading = false;
	},

	select(item: Type) {
		const i = adv.form.typeId.indexOf(item.id!);

		if (i >= 0) {
			adv.form.typeId.splice(i, 1);
		} else {
			adv.form.typeId.push(item.id!);
		}
	},
});

// 搜索
function toSearch(params?: any) {
	const { minPrice, maxPrice, typeId } = adv.form;

	const data = {
		page: 1,
		minPrice: minPrice === "" ? undefined : minPrice,
		maxPrice: maxPrice === "" ? undefined : maxPrice,
		typeId: isEmpty(typeId) ? undefined : typeId,
		...params,
	};

	emit("search", data);
}

onMounted(() => {
	const { typeId } = router.query;

	if (typeId) {
		adv.form.typeId = typeId.split(",").map(Number);
	}
});
</script>

<style lang="scss" scoped>
.filter-adv {
	background-color: $cl-color-bg;
	height: 100%;

	&__container {
		height: calc(100% - 100rpx);
	}

	&__list {
		padding: 24rpx;
	}

	&__item {
		background-color: #fff;
		margin-bottom: 24rpx;
		padding: 24rpx;
		border-radius: 24rpx;

		.label {
			display: block;
			font-size: 26rpx;
			margin-bottom: 24rpx;
			line-height: 1;
		}

		.category {
			min-height: 200rpx;

			.m1 {
				margin-bottom: 30rpx;

				&-name {
					display: flex;
					align-items: center;
					height: 80rpx;
					font-size: 28rpx;
				}

				&-list {
					width: 100%;
					line-height: normal;
					border: 2rpx solid #f7f7f7;
					padding: 5rpx 20rpx;
					box-sizing: border-box;
					border-radius: 24rpx;
				}

				&:last-child {
					margin-bottom: 0;
				}

				.m2 {
					display: inline-flex;
					justify-content: center;
					align-items: center;
					border-radius: 10rpx;
					margin: 10rpx 20rpx 10rpx 0;
					padding: 0 25rpx;
					font-size: 22rpx;
					height: 50rpx;
					text-align: center;
					border-radius: 50rpx;
					color: #444;
					overflow: hidden;
					text-overflow: ellipsis;
					white-space: nowrap;
					border: 2rpx solid #f7f7f7;
					background-color: #f7f7f7;

					&.is-active {
						color: $cl-color-primary;
						border-color: $cl-color-primary;
						background-color: rgba($cl-color-primary, 0.1);
					}
				}
			}
		}

		&:last-child {
			margin-bottom: 0;
		}
	}

	&__footer {
		display: flex;
		align-items: center;
		justify-content: center;
		height: 100rpx;
		padding: 0 30rpx;
		background-color: #fff;

		.inner {
			display: inline-flex;
			border: 2rpx solid $cl-color-primary;
			background-color: $cl-color-primary;
			border-radius: 80rpx;
			overflow: hidden;
			flex: 1;

			button {
				height: 64rpx;
				line-height: 64rpx;
				padding: 0;
				margin: 0;
				font-size: 28rpx;
				flex: 1;
				background-color: transparent;
				color: #fff;

				&::after {
					border: 0;
				}

				&.reset {
					color: $cl-color-primary;
					background-color: #fff;
					border-radius: 80rpx 0 80rpx 80rpx;
				}
			}
		}
	}
}
</style>
