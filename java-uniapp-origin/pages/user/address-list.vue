<template>
	<cl-page>
		<view class="page">
			<view class="list">
				<view class="item" v-for="(item, index) in list" :key="item.id">
					<cl-list-item swipe="right" :arrow-icon="false" :radius="16">
						<template #menu>
							<cl-button type="error" @tap="del(item.id!, index)">删除</cl-button>
						</template>

						<address-item :item="item">
							<template #icon>
								<cl-icon name="edit" :size="34" @tap="edit(item.id)" />
							</template>
						</address-item>
					</cl-list-item>
				</view>
			</view>

			<cl-empty icon="address" v-if="isEmpty(list)" />
		</view>

		<cl-footer>
			<cl-button custom type="primary" @tap="edit()">添加收货地址</cl-button>
		</cl-footer>
	</cl-page>
</template>

<script lang="ts" setup>
import { onReady, onShow } from "@dcloudio/uni-app";
import { useCool, usePager } from "/@/cool";
import { isEmpty } from "lodash-es";
import { useUi } from "/$/cool-ui";
import AddressItem from "/@/components/address/item.vue";

const { service, router } = useCool();
const { list, onRefresh } = usePager<Eps.UserAddressEntity>();
const ui = useUi();

// 刷新
function refresh(params?: any, loading?: boolean) {
	const { data, next } = onRefresh(params, { loading });
	next(service.user.address.page(data));
}

// 添加、编辑
function edit(id?: number) {
	router.push({
		path: "/pages/user/address-edit",
		query: {
			id,
		},
	});
}

// 删除
function del(id: number, index: number) {
	ui.showConfirm({
		title: "提示",
		message: "确定删除该地址吗？",
		callback(action) {
			if (action == "confirm") {
				service.user.address
					.delete({
						ids: [id],
					})
					.then(() => {
						list.value.splice(index, 1);
						ui.showToast("删除成功");
					})
					.catch((err) => {
						ui.showToast(err.message);
					});
			}
		},
	});
}

onShow(() => {
	if (ui.loaded) {
		refresh({}, false);
	}
});

onReady(() => {
	refresh();
});

defineExpose({
	refresh,
});
</script>

<style lang="scss" scoped>
.page {
	padding: 24rpx;

	.list {
		.item {
			margin-bottom: 24rpx;

			address-item {
				flex: 1;
			}

			:deep(.address-item) {
				padding: 14rpx 4rpx;
			}

			&:last-child {
				margin-bottom: 0;
			}
		}
	}
}
</style>
