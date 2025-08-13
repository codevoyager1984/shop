import { defineStore } from "pinia";
import { computed, ref } from "vue";
import type { Cs } from "../types";
import dayjs from "dayjs";
import { service, useStore } from "/@/cool";
import { last } from "lodash-es";
import { useSession } from "./session";
import { uuid } from "/@/cool/utils";
import { useScroller } from "./tools";
import { dateFormatter } from "../utils";

export const useMessage = defineStore("cs.message", () => {
	const session = useSession();
	const scroller = useScroller();
	const { user } = useStore();

	// 加载中
	const loading = ref(false);

	// 所有页
	const data = ref<{ id: string; data: Cs.Msg[] }[]>([]);

	// 列表
	const list = computed(() => {
		data.value.forEach((e) => {
			if (e.data[0]) {
				let date = e.data[0].createTime;

				if (date) {
					e.data.forEach((a, i) => {
						const d = dateFormatter(a.createTime);

						if (i == 0) {
							a.date = d;
						}

						if (dayjs(a.createTime).subtract(10, "minute").isAfter(dayjs(date))) {
							a.date = d;
							date = a.createTime;
						}
					});
				}
			}
		});

		return data.value;
	});

	// 追加消息
	function append(item: Cs.Msg) {
		const list = last(data.value)?.data;

		if (list) {
			list.push({
				sessionId: session.info?.id,
				type: 0,
				nickName: user.info?.nickName,
				avatarUrl: user.info?.avatarUrl,
				...item,
				isAnimation: true,
			});

			scroller.scrollToBottom();
		}
	}

	// 清除消息
	function clear() {
		data.value = [];
	}

	// 读消息
	function read(id: number) {
		service.cs.msg.read({
			msgIds: [id],
		});
	}

	// 获取消息
	async function refresh(params?: any) {
		loading.value = true;

		if (params.page == 1) {
			data.value = [];
		}

		await service.cs.msg
			.page({
				sessionId: session.info?.id,
				order: "createTime",
				sort: "desc",
				...params,
			})
			.then((res) => {
				res.list.forEach((e) => {
					if (e.type == 1) {
						e.avatarUrl = e.adminUserHeadImg;
						e.nickName = e.adminUserName;
					}
				});

				data.value.unshift({
					id: uuid(),
					data: res.list.reverse() as any[],
				});
			});

		loading.value = false;
	}

	return {
		data,
		list,
		loading,
		append,
		clear,
		read,
		refresh,
	};
});
