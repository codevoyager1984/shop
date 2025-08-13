import { defineStore } from "pinia";
import { computed, ref, watch } from "vue";
import { storage } from "/@/cool";
import { uuid } from "/@/cool/utils";

// 购物车
export const useShoppingCart = defineStore("shopping-cart", () => {
	const list = ref<OrderGoods[]>(storage.get("shopping-cart.list") || []);

	// 购物车数量
	const num = computed(() => {
		return list.value.length;
	});

	// 数量+1
	function add(data: OrderGoods) {
		const d = list.value.find((e) => e.spec?.id == data.spec?.id);

		if (d) {
			// 判定库存
			d.count += data.count || 1;

			if (d.count > data.spec.stock!) {
				d.count = data.spec.stock || 1;
			}
		} else {
			list.value.push({
				...data,
				id: uuid(),
			});
		}
	}

	// 删除规格
	function del(id: string) {
		const i = list.value.findIndex((e) => e.id == id);

		if (i >= 0) {
			list.value.splice(i, 1);
		}
	}

	// 删除规格根据 specId
	function delBySpecId(id: number) {
		const i = list.value.findIndex((e) => e.spec?.id == id);

		if (i >= 0) {
			list.value.splice(i, 1);
		}
	}

	// 监听更新
	watch(
		list,
		(val) => {
			storage.set("shopping-cart.list", val);
		},
		{
			deep: true,
		},
	);

	return {
		list,
		num,
		add,
		del,
		delBySpecId,
	};
});
