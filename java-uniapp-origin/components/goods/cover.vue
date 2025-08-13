<template>
	<cl-image
		:src="resizeImage(current, 300)"
		:radius="radius"
		:size="size"
		:background-color="backgroundColor"
		@tap="toGoods"
	>
		<view class="zoom" @tap.stop="preview()" v-if="zoom">
			<text class="shop-icon-zoom"></text>
		</view>
	</cl-image>
</template>

<script lang="ts" setup>
import { isEmpty } from "lodash-es";
import { computed, type PropType } from "vue";
import { useCool, useUpload } from "/@/cool";

const props = defineProps({
	item: {
		type: Object as PropType<Eps.GoodsInfoEntity>,
		default: () => ({}),
	},
	spec: {
		type: Object as PropType<Eps.GoodsSpecEntity>,
		default: () => ({}),
	},
	size: [Number, String, Array],
	radius: {
		type: [Number, String],
		default: 24,
	},
	backgroundColor: String,
	link: {
		type: Boolean,
		default: true,
	},
	zoom: Boolean,
});

const { router } = useCool();
const { resizeImage } = useUpload();

const urls = computed(() => {
	if (props.spec) {
		const arr = props.spec.images || [];

		if (!isEmpty(arr)) {
			return arr;
		}
	}

	return [props.item.mainPic];
});

const current = computed(() => urls.value[0]);

// 商品详情
function toGoods() {
	if (props.link) {
		router.push({
			path: "/pages/goods/detail",
			query: {
				id: props.item.id,
				specId: props.spec?.id,
			},
		});
	}
}

// 大图预览
function preview() {
	uni.previewImage({
		urls: urls.value,
		current: current.value,
	});
}
</script>

<style lang="scss" scoped>
.zoom {
	display: inline-flex;
	align-items: center;
	justify-content: center;
	position: absolute;
	right: 8rpx;
	top: 8rpx;
	height: 30rpx;
	width: 30rpx;
	background-color: rgba(60, 60, 60, 0.7);
	color: #fff;
	font-size: 18rpx;
	border-radius: 100%;
}
</style>
