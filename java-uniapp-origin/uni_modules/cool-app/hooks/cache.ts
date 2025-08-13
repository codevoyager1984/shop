import { onShow } from "@dcloudio/uni-app";
import { defineStore } from "pinia";
import { ref } from "vue";

// 缓存
export const useCache = defineStore("app.cache", () => {
	// 缓存大小
	const size = ref("0KB");

	// 获取缓存
	function get() {
		// #ifdef APP
		// @ts-ignore
		plus.cache.calculate(function (s: number) {
			//size是多少个字节单位是b
			if (s < 1024) {
				size.value = s + "B";
			} else if (s / 1024 >= 1 && s / 1024 / 1024 < 1) {
				size.value = Math.floor((s / 1024) * 100) / 100 + "KB";
			} else if (s / 1024 / 1024 >= 1) {
				size.value = Math.floor((s / 1024 / 1024) * 100) / 100 + "M";
			}
		});
		// #endif
	}

	// 清空缓存
	function clear() {
		// #ifdef APP
		// @ts-ignore
		plus.cache.clear(function () {
			get();
		});
		// #endif
	}

	onShow(() => {
		get();
	});

	return {
		size,
		get,
		clear,
	};
});
