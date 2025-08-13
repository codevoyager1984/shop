<template>
	<cl-page fullscreen background-color="#fff">
		<view class="page">
			<view class="container">
				<scroll-view
					:scroll-top="scroller.top"
					scroll-y
					:scroll-with-animation="scroller.animation"
					class="scroller"
					@tap="tools.close()"
					@scroll="onScroll"
				>
					<msg-list :ref="setRefs('msgList')" />
				</scroll-view>
			</view>

			<view class="footer">
				<view class="wrap">
					<!-- 输入 -->
					<view class="inner">
						<cl-input
							v-model="content"
							round
							:height="76"
							placeholder="输入你想要咨询的问题"
							:confirm-hold="true"
							:adjust-position="true"
							:cursor-spacing="10"
							auto-blur
							@focus="onFocus"
							@confirm="onConfirm"
						/>
					</view>

					<image src="/uni_modules/cool-cs/static/emoji.png" @tap="tools.open('emoji')" />
					<image src="/uni_modules/cool-cs/static/plus.png" @tap="tools.open('fn')" />
				</view>

				<!-- 工具 -->
				<tools />
			</view>
		</view>
	</cl-page>
</template>

<script setup lang="ts">
import { useSocket, useTools, useScroller, useSession, useMessage } from "../hooks";
import { onReady } from "@dcloudio/uni-app";
import { useCool } from "/@/cool";
import { ref } from "vue";
import Tools from "../components/tools/index.vue";
import MsgList from "../components/msg-list.vue";

const { service, refs, setRefs } = useCool();
const socket = useSocket();
const session = useSession();
const message = useMessage();
const scroller = useScroller();
const tools = useTools();

// 咨询内容
const content = ref("");

// 监听滚动
function onScroll(e: { detail: { scrollTop: number } }) {
	refs.msgList?.onScroll(e.detail.scrollTop);
}

// 聚焦
function onFocus() {
	scroller.scrollToBottom();
}

// 确认
async function onConfirm() {
	const data = content.value;

	if (data) {
		content.value = "";

		socket.send({
			type: "text",
			data,
		});
	}
}

onReady(() => {
	service.cs.session.create().then(async (res) => {
		// 设置会话
		session.set(res);

		// 连接 socket
		socket.connect();

		// 获取消息列表
		await message.refresh({
			sessionId: res.id,
		});

		// 滚动到底
		scroller.scrollToBottom(false);
	});
});
</script>

<style lang="scss" scoped>
.page {
	display: flex;
	flex-direction: column;
	height: 100%;

	.container {
		flex: 1;
		overflow: hidden;
		background-color: $cl-color-bg;

		.scroller {
			height: 100%;
		}
	}

	.footer {
		.wrap {
			display: flex;
			align-items: center;
			padding: 24rpx 36rpx;
			background-color: #fff;

			.inner {
				flex: 1;
			}

			image {
				height: 46rpx;
				width: 46rpx;
				margin-left: 24rpx;
			}
		}
	}
}
</style>
