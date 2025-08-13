import { defineStore } from "pinia";
import { nextTick, ref } from "vue";
import { throttle } from "lodash-es";

export const useTools = defineStore("cs.tools", () => {
	const visible = ref(false);
	const mode = ref("");

	// 打开
	function open(m: string) {
		if (visible.value && mode.value === m) {
			return close();
		}

		visible.value = true;
		mode.value = m;
	}

	// 关闭
	function close() {
		visible.value = false;
	}

	return {
		visible,
		mode,
		open,
		close,
	};
});

export const useScroller = defineStore("cs.scroller", () => {
	// 滚动距离
	const top = ref(0);

	// 滚动动画
	const animation = ref(true);

	let n = 0;

	// 滚动到指定位置
	const scrollTo = throttle((scrollTop: number, smooth: boolean = true) => {
		nextTick().then(() => {
			top.value = scrollTop;

			animation.value = smooth;
		});
	}, 500);

	// 滚动到底部
	const scrollToBottom = (smooth?: boolean) => {
		scrollTo(100000 + n++, smooth);
	};

	return {
		animation,
		top,
		scrollToBottom,
		scrollTo,
	};
});
