<template>
	<view class="tools-fn">
		<cl-row>
			<cl-col :span="6">
				<view class="item" @tap="chooseImage('album')">
					<view class="icon">
						<image src="/uni_modules/cool-cs/static/fn-pic.png" mode="aspectFit" />
					</view>
					<text>相册</text>
				</view>
			</cl-col>

			<cl-col :span="6">
				<view class="item" @tap="chooseImage('camera')">
					<view class="icon">
						<image src="/uni_modules/cool-cs/static/fn-camera.png" mode="aspectFit" />
					</view>
					<text>拍摄</text>
				</view>
			</cl-col>
		</cl-row>
	</view>
</template>

<script setup lang="ts">
import { useSocket, useTools } from "../../hooks";
import { useUpload } from "/@/cool";

const tools = useTools();
const { upload } = useUpload();
const socket = useSocket();

// 选择
function chooseImage(type: string) {
	uni.chooseImage({
		sourceType: [type],
		success(res) {
			(res.tempFiles as any[]).forEach(async (e) => {
				const url = await upload(e);

				socket.send({
					type: "image",
					data: url,
				});
			});
		},
	});
	tools.close();
}
</script>

<style lang="scss" scoped>
.tools-fn {
	padding: 20rpx;

	.item {
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: center;
		margin: 20rpx 0;

		.icon {
			height: 130rpx;
			width: 130rpx;
			background-color: #fff;
			border-radius: 16rpx;
			display: flex;
			align-items: center;
			justify-content: center;
			margin-bottom: 20rpx;

			image {
				height: 60rpx;
				width: 60rpx;
			}
		}

		text {
			font-size: 26rpx;
			color: #666;
		}
	}
}
</style>
