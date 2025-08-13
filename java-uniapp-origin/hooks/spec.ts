import { defineStore } from "pinia";
import { computed, ref } from "vue";

type Action = "select" | "spCart" | "buy" | "edit";

export const useSpec = defineStore("goods.spec", () => {
	const visible = ref(false);

	// 打开类型
	const action = ref<Action>("select");

	// 商品信息
	const goods = ref<Eps.GoodsInfoEntity>();

	// 已选规格信息
	const info = ref<Eps.GoodsSpecEntity>();

	// 已选数量
	const num = ref(1);

	// 指定规格id
	const specId = ref();

	// 回调函数
	let cb: ((action: Action) => void) | undefined;

	// 选中文案
	const text = computed(() => {
		return info.value ? `已选：${info.value.name}` : "选择规格";
	});

	// 打开弹窗
	function open(options: {
		action: Action;
		goods: Eps.GoodsInfoEntity;
		spec?: Eps.GoodsSpecEntity;
		specId?: number;
		count?: number;
		item?: OrderGoods;
		callback?: typeof cb;
	}) {
		if (!options.goods) {
			return false;
		}

		visible.value = true;
		action.value = options.action || "select";
		goods.value = options.goods;
		specId.value = options.specId || options.spec?.id || specId.value;

		if (options.spec) {
			info.value = options.spec;
		}

		cb = options.callback;

		if (options.action == "edit") {
			num.value = options.count || 0;
		}
	}

	// 关闭弹窗
	function close() {
		visible.value = false;
	}

	// 设置选中规格
	function set(data?: Eps.GoodsSpecEntity) {
		info.value = data;
		setId(data?.id!);
	}

	// 设置规格id
	function setId(id: number) {
		specId.value = id;
	}

	// 设置数量
	function setNum(val?: number) {
		num.value = val || 0;
	}

	// 回调
	function emit(action: Action) {
		if (cb) {
			cb(action);
		}
	}

	// 清空选项
	function clear() {
		num.value = 1;
		info.value = undefined;
		goods.value = undefined;
		specId.value = undefined;
		cb = undefined;
	}

	return {
		visible,
		action,
		goods,
		info,
		emit,
		num,
		setNum,
		specId,
		text,
		open,
		close,
		set,
		setId,
		clear,
	};
});
