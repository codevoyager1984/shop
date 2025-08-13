<template>
	<view
		class="goods-group"
		:class="{
			'is-border': border,
		}"
	>
		<view class="goods" v-for="goods in list" :key="goods.id">
			<!-- 名称 -->
			<view
				class="name"
				@tap="
					router.push({
						path: '/pages/goods/detail',
						query: {
							id: goods.id,
						},
					})
				"
			>
				<cl-text :ellipsis="1" :value="goods.name" bold />
				<cl-icon name="arrow-right" :size="20" :margin="[0, 0, 0, 6]" />
			</view>

			<!-- 规格 -->
			<view class="specs">
				<view class="item" v-for="item in goods.children" :key="item.id">
					<view class="checkbox" v-if="showCheckbox">
						<cl-checkbox round v-model="item.checked" />
					</view>

					<goods-cover :size="140" :item="item.goodsInfo" :spec="item.spec" />

					<view class="det">
						<cl-row>
							<view class="spec" @tap="onSpec(item)">
								<cl-text :size="24" :ellipsis="1">{{ item.spec?.name }}</cl-text>
								<cl-icon
									name="arrow-bottom"
									:size="20"
									:margin="[0, 0, 0, 10]"
									v-if="specEdit"
								/>
							</view>
						</cl-row>

						<view class="flex1" />

						<cl-row type="flex" justify="space-between">
							<cl-text
								type="price"
								color="error"
								:size="36"
								:value="item.spec?.price"
							/>

							<slot name="count" :item="item">
								<cl-text color="info" v-if="readonly">x{{ item.count }}</cl-text>

								<cl-input-number
									v-model="item.count"
									:min="1"
									:max="item.spec?.stock"
									v-else
								/>
							</slot>
						</cl-row>
					</view>
				</view>
			</view>

			<slot name="goods" :goods="goods"></slot>
		</view>
	</view>
</template>

<script lang="ts" setup>
import { computed, type PropType } from "vue";
import { useCool } from "/@/cool";
import GoodsCover from "/@/components/goods/cover.vue";

interface Item {
	id: number;
	name: string;
	children: OrderGoods[];
	[key: string]: any;
}

const props = defineProps({
	// 商品数据
	data: {
		type: Array as PropType<OrderGoods[]>,
		default: () => [],
	},
	// 能否编辑规格
	specEdit: Boolean,
	// 显示多选框
	showCheckbox: Boolean,
	// 带边框
	border: Boolean,
	// 是否只读
	readonly: Boolean,
});

const emit = defineEmits(["spec"]);

const { router } = useCool();

const list = computed(() => {
	const arr: Item[] = [];

	props.data
		.filter((e) => !!e)
		.forEach((e) => {
			const d = arr.find((a) => a.id == e.goodsInfo.id);

			if (d) {
				d.children?.push(e);
			} else {
				arr.push({
					id: e.goodsInfo.id!,
					name: e.goodsInfo.title!,
					children: [e],
				});
			}
		});

	return arr;
});

function onSpec(item: OrderGoods) {
	if (props.specEdit) {
		emit("spec", item);
	}
}

defineExpose({
	list,
});
</script>

<style lang="scss" scoped>
.goods-group {
	.goods {
		background-color: #ffffff;
		border-radius: 24rpx;
		padding: 24rpx;
		margin-bottom: 24rpx;

		.name {
			display: flex;
			align-items: center;
			margin-bottom: 24rpx;
		}

		.specs {
			.item {
				display: flex;
				margin-bottom: 24rpx;

				.checkbox {
					display: flex;
					align-items: center;
					width: 34rpx;
					margin-right: 24rpx;

					.cl-checkbox {
						position: absolute;
					}
				}

				.det {
					display: flex;
					flex-direction: column;
					margin-left: 24rpx;
					padding: 10rpx 0;
					flex: 1;

					.spec {
						display: inline-flex;
						align-items: center;
						height: 46rpx;
						padding: 0 16rpx;
						border-radius: 10rpx;
						background-color: $cl-color-bg;
					}
				}

				&:last-child {
					margin-bottom: 0;
				}
			}
		}
	}

	&.is-border {
		.goods {
			border: $cl-border-width solid $cl-border-color;
		}
	}
}
</style>
