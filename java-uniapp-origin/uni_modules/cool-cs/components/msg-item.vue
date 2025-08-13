<template>
	<view
		class="msg-item"
		:class="[
			isR ? 'r' : 'l',
			{
				'is-animation': item.isAnimation,
			},
		]"
	>
		<view class="avatar">
			<cl-avatar shape="square" :size="80" :src="item.avatarUrl"></cl-avatar>
		</view>

		<view class="det">
			<view class="h" v-if="item.nickName">
				<text class="name">{{ item.nickName }}</text>
			</view>

			<view class="content">
				<view class="text" v-if="item.content.type == 'text'">
					<text>{{ item.content.data }}</text>
				</view>

				<view class="emoji" v-else-if="item.content.type == 'emoji'">
					<image
						:src="`/uni_modules/cool-cs/static/emoji/${item.content.data}`"
						mode="aspectFit"
					/>
				</view>

				<view class="image" v-if="item.content.type == 'image'">
					<cl-image
						:src="item.content.data"
						mode="heightFix"
						:size="[200, 'auto']"
						:custom-style="{
							'text-align': isR ? 'right' : 'left',
						}"
						:preview-list="[item.content.data]"
					/>
				</view>
			</view>
		</view>
	</view>
</template>

<script setup lang="ts">
import type { Cs } from "../types";
import { computed, onMounted, type PropType } from "vue";

const props = defineProps({
	item: {
		type: Object as PropType<Cs.Msg>,
		default: () => ({}),
	},
});

const isR = computed(() => !props.item.type);

onMounted(() => {
	// 清除动画
	setTimeout(() => {
		props.item.isAnimation = false;
	}, 500);
});
</script>

<style lang="scss" scoped>
.msg-item {
	display: flex;
	margin-bottom: 40rpx;

	.avatar {
		flex-shrink: 0;
	}

	.det {
		flex: 1;
		margin: 0 20rpx;

		.h {
			display: flex;
			align-items: center;
			margin: 8rpx 0 16rpx 0;

			.name {
				font-size: 24rpx;
			}
		}

		.content {
			display: inline-block;

			.emoji {
				padding: 18rpx;

				image {
					display: block;
					height: 44rpx;
					width: 44rpx;
				}
			}

			.text {
				display: flex;
				align-items: center;
				font-size: 28rpx;
				letter-spacing: 2rpx;
				padding: 16rpx 24rpx;
				word-break: break-all;
				word-wrap: break-word;
				min-height: 80rpx;
				box-sizing: border-box;
			}

			.emoji,
			.text {
				background-color: #fff;
				border-radius: 0 16rpx 16rpx 16rpx;
			}
		}
	}

	&.r {
		flex-direction: row-reverse;
		text-align: right;

		.h,
		.content {
			flex-direction: row-reverse;
		}

		.content {
			.text,
			.emoji {
				border-radius: 16rpx 0 16rpx 16rpx;
				background-color: $cl-color-primary;
				color: #fff;
			}

			.image {
				display: flex;
				justify-content: flex-end;
			}

			.text {
				text-align: left;
			}
		}
	}

	&.is-animation {
		&.l {
			animation: fadeInLeft 0.5s ease both;
		}

		&.r {
			animation: fadeInRight 0.5s ease both;
		}
	}

	@keyframes fadeInRight {
		from {
			opacity: 0;
			transform: translate3d(100%, 0, 0);
		}

		to {
			opacity: 1;
			transform: translate3d(0, 0, 0);
		}
	}

	@keyframes fadeInLeft {
		from {
			opacity: 0;
			transform: translate3d(-100%, 0, 0);
		}

		to {
			opacity: 1;
			transform: translate3d(0, 0, 0);
		}
	}
}
</style>
