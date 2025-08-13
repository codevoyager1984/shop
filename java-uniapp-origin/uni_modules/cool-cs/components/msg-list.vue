<template>
	<view class="msg-list">
		<view
			v-for="(d, n) in message.list"
			:key="d.id"
			class="group"
			:class="{
				'is-show': message.list.length - n <= pagination.page,
			}"
		>
			<block v-for="item in d.data" :key="item.id">
				<!-- 日期 -->
				<view class="date" v-if="item.date">
					<text>{{ item.date }}</text>
				</view>

				<!-- 列表项 -->
				<msg-item :item="item" />
			</block>
		</view>
	</view>
</template>

<script setup lang="ts">
import { reactive, getCurrentInstance, nextTick, ref } from "vue";
import { useMessage, useScroller } from "../hooks";
import MsgItem from "./msg-item.vue";

const message = useMessage();
const { proxy }: any = getCurrentInstance();
const scroller = useScroller();

// 分页
const pagination = reactive({
	page: 1,
});

// 加载中
const loading = ref(false);

// 滚动到顶部
async function scrollToUpper() {
	if (loading.value) {
		return false;
	}

	loading.value = true;

	// 没有更多了
	if (pagination.page > message.list.length) {
		return false;
	}

	// 计算当前的高度
	const h1 = await getHeight();

	// 预加载下一页
	await message.refresh({
		page: pagination.page + 1,
	});

	// 下一页
	pagination.page += 1;

	// 延迟
	await nextTick();

	// 计算页数增加后的高度
	const h2 = await getHeight();

	// 滚动到原来的位置
	scroller.scrollTo(h2 - h1, false);

	// 加载完成
	loading.value = false;
}

// 获取高度
function getHeight(): Promise<number> {
	return new Promise((resolve) => {
		uni.createSelectorQuery()
			.in(proxy)
			.select(".msg-list")
			.boundingClientRect((rect) => {
				resolve(rect?.height || 0);
			})
			.exec();
	});
}

// 监听滚动
function onScroll(scrollTop: number) {
	if (scrollTop < 5) {
		scrollToUpper();
	}
}

defineExpose({
	onScroll,
});
</script>

<style lang="scss" scoped>
.msg-list {
	padding: 24rpx;

	.group {
		opacity: 0;
		height: 0;
		overflow: hidden;
		transition: opacity 0.3s;

		&.is-show {
			height: auto;
			opacity: 1;
		}
	}

	.date {
		margin: 20rpx;
		text-align: center;

		text {
			background-color: #c9c9c9;
			padding: 10rpx;
			border-radius: 8rpx;
			color: #fff;
			font-size: 24rpx;
		}
	}
}
</style>
