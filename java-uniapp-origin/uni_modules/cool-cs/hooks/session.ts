import { defineStore } from "pinia";
import { ref } from "vue";
import type { Cs } from "../types";

export const useSession = defineStore("cs.session", () => {
	// 详情
	const info = ref<Cs.Session>();

	// 设置会话
	function set(data: Cs.Session) {
		info.value = data;
	}

	// 清除会话
	function clear() {
		info.value = undefined;
	}

	return {
		info,
		set,
		clear,
	};
});
