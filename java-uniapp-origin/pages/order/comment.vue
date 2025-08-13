<template>
	<cl-page background-color="#fff">
		<view class="page">
			<view class="star">
				<cl-text block :margin="[0, 0, 24, 0]">{{ text }}</cl-text>
				<cl-rate v-model="form.starCount" />
			</view>

			<cl-textarea
				v-model="form.content"
				:height="300"
				count
				:radius="24"
				:border="false"
				:maxlength="300"
				:margin="[24, 0, 24, 0]"
				background-color="#f6f7fa"
				placeholder="请输入您的评论内容"
			/>

			<cl-upload :ref="setRefs('upload')" v-model="form.pics" multiple :size="[200, 200]" />
		</view>

		<cl-footer>
			<cl-button custom type="primary" :loading="loading" @tap="submit">提交</cl-button>
		</cl-footer>
	</cl-page>
</template>

<script lang="ts" setup>
import { computed, reactive, ref } from "vue";
import { useCool } from "/@/cool";
import { useUi } from "/$/cool-ui";

const { service, router, refs, setRefs } = useCool();
const ui = useUi();

const form = reactive({
	content: "",
	starCount: 5,
	pics: [],
});

// 提交中
const loading = ref(false);

// 评分文案
const text = computed(() => {
	return ["太差了！", "不好用！", "还可以！", "推荐！", "强力推荐！"][form.starCount - 1];
});

async function submit() {
	const { orderId, goodsId } = router.query;

	if (!form.content) {
		return ui.showToast("请输入评论内容");
	}

	if (refs.upload.check()) {
		return ui.showToast("图片上传中，请稍等");
	}

	loading.value = true;

	await service.goods.comment
		.submit({
			orderId,
			data: {
				orderId,
				goodsId,
				...form,
			},
		})
		.then(() => {
			ui.showTips("评价已提交，感谢您的反馈", () => {
				router.back();
			});
		})
		.catch((err) => {
			ui.showToast(err.message);
		});

	loading.value = false;
}
</script>

<style lang="scss" scoped>
.page {
	padding: 24rpx;

	.star {
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: center;
		padding: 24rpx 0;
	}
}
</style>
