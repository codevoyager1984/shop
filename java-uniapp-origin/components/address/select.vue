<template>
	<view class="select-address" @tap="open()">
		<address-item :item="data || address.info">
			<template #icon>
				<text class="cl-icon-arrow-right" v-if="!disabled"></text>
			</template>
		</address-item>
	</view>

	<cl-popup
		v-model="visible"
		direction="bottom"
		:border-radius="[32, 32, 0, 0]"
		:padding="0"
		show-close-btn
	>
		<view class="select-address__popup">
			<cl-text :size="30" block bold :margin="[30, 0, 30, 30]">选择地址</cl-text>

			<cl-loading-mask :loading="loading">
				<scroll-view scroll-y class="scroller">
					<view class="list">
						<view
							class="item"
							v-for="item in list"
							:key="item.id"
							:class="{
								'is-active': item.id == address.info?.id,
							}"
							@tap="select(item)"
						>
							<address-item :item="item" background-color="#f7f7f7">
								<template #icon>
									<text class="cl-icon-check-border"></text>
								</template>
							</address-item>
						</view>
					</view>

					<cl-empty icon="address" :fixed="false" text="暂无地址" v-if="isEmpty(list)" />
				</scroll-view>
			</cl-loading-mask>

			<cl-footer :fixed="false" :vt="[visible]">
				<cl-button custom type="primary" @tap="router.push('/pages/user/address-edit')"
					>添加收货地址</cl-button
				>
			</cl-footer>
		</view>
	</cl-popup>
</template>

<script lang="ts" setup>
import { ref } from "vue";
import { useAddress } from "/@/hooks";
import { useCool } from "/@/cool";
import { onShow } from "@dcloudio/uni-app";
import AddressItem from "./item.vue";
import { isEmpty } from "lodash-es";

const props = defineProps({
	disabled: Boolean,
	data: Object,
});

const { service, router } = useCool();
const address = useAddress();

const visible = ref(false);
const loading = ref(false);

// 地址列表
const list = ref<Eps.UserAddressEntity[]>([]);

// 获取地址
async function refresh() {
	loading.value = true;

	await service.user.address.list().then((res) => {
		list.value = res;
	});

	loading.value = false;
}

function select(item: Eps.UserAddressEntity) {
	address.set(item);
	close();
}

function open() {
	if (!props.disabled) {
		visible.value = true;
	}
}

function close() {
	visible.value = false;
}

onShow(() => {
	refresh();
	address.getDefault();
});

defineExpose({
	open,
	close,
});
</script>

<style lang="scss" scoped>
.select-address {
	margin-bottom: 24rpx;

	&__popup {
		.scroller {
			height: 50vh;

			.list {
				padding: 0 32rpx;

				.item {
					border: 2rpx solid #f7f7f7;
					border-radius: 24rpx;
					margin-bottom: 24rpx;

					.cl-icon-check-border {
						display: none;
						color: $cl-color-primary;
						font-size: 46rpx;
					}

					&.is-active {
						border-color: $cl-color-primary;

						.cl-icon-check-border {
							display: block;
						}
					}

					&:last-child {
						margin-bottom: 0;
					}
				}
			}
		}
	}
}
</style>
