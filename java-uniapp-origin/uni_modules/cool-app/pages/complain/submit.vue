<template>
	<cl-page>
		<cl-topbar title="填写您的投诉">
			<template #append>
				<cl-text value="投诉历史" :margin="[0, 24, 0, 24]" @tap="router.push('./list')" />
			</template>
		</cl-topbar>

		<view class="page">
			<cl-form :ref="setRefs('form')" v-model="form" :rules="rules" label-position="top">
				<cl-form-item label="投诉类型" prop="type" required>
					<cl-radio-group v-model="form.type">
						<view v-for="(item, index) in dict.get('complainType')" :key="index">
							<cl-radio :label="item.value">{{ item.label }}</cl-radio>
						</view>
					</cl-radio-group>
				</cl-form-item>

				<cl-form-item label="投诉内容" prop="content">
					<cl-textarea
						v-model="form.content"
						placeholder="请描述存在的问题，如：涉及的假货、违规广告是什么，哪里有明显的虚假夸张宣传，如何引导非法渠道问诊开药等。~"
						:height="200"
						count
						:maxlength="300"
					/>
				</cl-form-item>

				<cl-form-item label="图片材料提交" prop="images">
					<cl-upload v-model="form.images" multiple :limit="6" />
					<cl-text
						block
						value="上传照片 (最多6张)"
						color="info"
						:size="24"
						:margin="[24, 0, 0, 0]"
					/>
				</cl-form-item>

				<cl-form-item label="联系方式" prop="contact" label-position="top">
					<cl-input
						v-model="form.contact"
						placeholder="QQ/微信号，手机号（选填）"
						:maxlength="50"
					/>
				</cl-form-item>
			</cl-form>
		</view>

		<cl-footer border>
			<cl-button custom type="primary" :loading="saving" @tap="submit">提交</cl-button>
		</cl-footer>
	</cl-page>
</template>

<script lang="ts" setup>
import { useCool, useStore } from "/@/cool";
import { reactive, ref } from "vue";
import { useUi } from "/$/cool-ui";

const { service, router, refs, setRefs } = useCool();
const { dict } = useStore();
const ui = useUi();

const form = ref<Eps.AppComplainEntity>({
	images: [],
	contact: "",
	type: 0,
	content: "",
});

const rules = reactive({
	content: [
		{
			required: true,
			message: "请填写投诉内容",
		},
	],
});

const saving = ref(false);

function submit() {
	refs.form.validate(async (valid: boolean) => {
		if (valid) {
			saving.value = true;

			await service.app.complain
				.submit(form.value)
				.then(() => {
					ui.showTips("提交成功", () => {
						router.back();
					});
				})
				.catch((err) => {
					ui.showTips(err.message);
				});

			saving.value = false;
		}
	});
}
</script>

<style lang="scss" scoped>
.page {
	padding: 24rpx;

	:deep(.cl-form-item) {
		background-color: #fff;
		border-radius: 16rpx;
		padding: 0 30rpx 30rpx 30rpx;
		margin-bottom: 24rpx;
	}
}
</style>
