<template>
	<view class="comment-item">
		<view class="inner">
			<view class="user">
				<cl-avatar :size="70" :src="item.avatarUrl" />

				<cl-row :margin="[4, 0, 0, 20]">
					<cl-text :size="24" block :margin="[0, 0, 4, 0]">{{ item.nickName }}</cl-text>
					<cl-rate disabled :size="24" v-model="item.starCount" />
				</cl-row>
			</view>

			<cl-text
				:ellipsis="demo ? 2 : undefined"
				block
				:line-height="1.5"
				:margin="[20, 0, 0, 0]"
				>{{ item.content }}</cl-text
			>
		</view>

		<view class="pics" v-if="!isEmpty(item.pics)">
			<cl-image
				:size="120"
				:src="resizeImage(item.pics[0], 120)"
				:preview-list="item.pics"
				:radius="12"
			/>
			<text class="num" v-if="1 || item.pics.length > 1">+{{ item.pics.length }}</text>
		</view>
	</view>
</template>

<script lang="ts" setup>
import { type PropType } from "vue";
import { isEmpty } from "lodash-es";
import { useUpload } from "/@/cool";

defineProps({
	item: {
		type: Object as PropType<Eps.GoodsCommentEntity>,
		default: () => ({}),
	},
	demo: Boolean,
});

const { resizeImage } = useUpload();
</script>

<style lang="scss" scoped>
.comment-item {
	display: flex;

	.inner {
		flex: 1;

		.user {
			display: flex;
			align-items: center;
		}
	}

	.pics {
		position: relative;
		margin-left: 24rpx;

		.num {
			position: absolute;
			top: 84rpx;
			right: 10rpx;
			font-size: 20rpx;
			background-color: rgba(0, 0, 0, 0.6);
			color: #fff;
			padding: 4rpx;
			border-radius: 6rpx;
			letter-spacing: 1rpx;
			line-height: 1;
		}
	}
}
</style>
