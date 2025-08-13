<template>
	<view class="goods-info">
		<view class="banner">
			<swiper class="cl-banner" indicator-dots>
				<swiper-item v-for="(item, index) in banner" :key="index">
					<image class="cl-banner-item__image" mode="aspectFill" :src="item" />
				</swiper-item>
			</swiper>
		</view>

		<view class="content">
			<!-- 售价 -->
			<cl-row type="flex" justify="space-between" :padding="[20, 0, 20, 0]">
				<cl-skeleton
					:height="54"
					:loading="!info"
					:loading-style="{
						width: '200rpx',
					}"
				>
					<cl-text
						type="price"
						bold
						color="error"
						:size="50"
						:value="spec.info?.price || info?.price"
					/>
				</cl-skeleton>

				<cl-skeleton
					:loading-style="{
						height: '38rpx',
						width: '90rpx',
						borderRadius: '38rpx',
					}"
					:loading="!info"
				>
					<cl-tag round size="small">已售 {{ info?.sold || 0 }}</cl-tag>
				</cl-skeleton>
			</cl-row>

			<!-- 优惠券 -->
			<cl-row :margin="[0, 0, 20, 0]">
				<coupon-get />
			</cl-row>

			<!-- 标题 -->
			<cl-skeleton
				:margin="[0, 0, 20, 0]"
				:loading-style="{
					height: '48rpx',
				}"
				:loading="!info"
			>
				<cl-text bold block :line-height="1.5" :size="32">
					{{ info?.title }} {{ spec.info?.name }}
				</cl-text>
			</cl-skeleton>

			<!-- 其他 -->
			<view class="menu">
				<!-- 规格 -->
				<view
					class="item"
					@tap="
						spec.open({
							action: 'select',
							goods: info!,
						})
					"
				>
					<cl-icon class-name="shop-icon-sku" :size="52" :margin="[0, 10, 0, 0]" />
					<cl-text :ellipsis="1">
						{{ spec.text }}
					</cl-text>

					<text
						class="cl-icon-close-border"
						@tap.stop="spec.clear"
						v-if="!!spec.info"
					></text>
				</view>
			</view>
		</view>
	</view>
</template>

<script lang="ts" setup>
import { computed, type PropType } from "vue";
import { useUpload } from "/@/cool";
import { useSpec } from "/@/hooks";
import { isEmpty } from "lodash-es";
import CouponGet from "/@/components/coupon/get.vue";

const props = defineProps({
	info: Object as PropType<Eps.GoodsInfoEntity>,
});

const { resizeImage } = useUpload();
const spec = useSpec();

// 轮播图列表
const banner = computed(() => {
	// 规格图
	if (!isEmpty(spec.info?.images)) {
		return spec.info?.images;
	}

	// 商品示例图
	if (!isEmpty(props.info?.pics)) {
		return props.info?.pics;
	}

	// 主图
	return [props.info?.mainPic];
});
</script>

<style lang="scss" scoped>
.goods-info {
	margin-bottom: 24rpx;

	.banner {
		.cl-banner {
			height: 760rpx;

			.cl-banner-item__image {
				border-radius: 0;
			}
		}
	}

	.content {
		padding: 10rpx 30rpx;
		background-color: #fff;

		.menu {
			.item {
				display: flex;
				align-items: center;
				height: 80rpx;
				background-color: $cl-color-bg;
				padding: 0 16rpx;
				border-radius: 12rpx;
				margin-bottom: 20rpx;
				padding-right: 60rpx;
				position: relative;

				.cl-icon-close-border {
					position: absolute;
					right: 20rpx;
					color: $cl-color-info;
				}
			}
		}
	}
}
</style>
