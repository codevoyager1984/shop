import { defineStore } from "pinia";
import { ref } from "vue";
import { service } from "/@/cool";

export const useAddress = defineStore("address", () => {
	const info = ref<Eps.UserAddressEntity>();

	function set(data: Eps.UserAddressEntity) {
		info.value = data;
	}

	function getDefault() {
		if (!info.value || info.value?.isDefault) {
			service.user.address.default().then((res) => {
				info.value = res;
			});
		}
	}

	return {
		info,
		set,
		getDefault,
	};
});

export * from "./shopping-cart";
export * from "./spec";
export * from "./order";
