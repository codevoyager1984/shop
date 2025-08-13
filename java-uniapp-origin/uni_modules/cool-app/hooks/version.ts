import { defineStore } from "pinia";
import { ref } from "vue";
import { service } from "/@/cool";
import { useUi } from "/$/cool-ui";
import { compareVersions, getVersion } from "../utils";
import { isAndroid } from "/@/cool/utils";

// 版本信息
export function useVersion() {
	const ui = useUi();

	const store = defineStore("app.version", () => {
		// 版本号
		const num = ref();

		// 是否能升级
		const isUpgrade = ref(false);

		// 检测状态
		const loading = ref(false);

		// 下载进度
		const progress = ref();

		// 新版本信息
		const updateInfo = ref<Eps.AppVersionEntity>();

		let req: any;
		let task: UniApp.DownloadTask | null;

		// 检测更新
		async function check() {
			// #ifdef APP
			loading.value = true;

			await req;

			await service.app.version
				.check({
					version: num.value,
					type: isAndroid ? 0 : 1,
				})
				.then((res) => {
					if (res) {
						console.log("[cool-app] 新版本信息", res);
						updateInfo.value = res;
						isUpgrade.value = compareVersions(res?.version, num.value) === 1;
					}
				})
				.catch((err) => {
					ui.showToast(err.message);
				});

			loading.value = false;
			// #endif
		}

		// 更新资源包
		function update(isConfirm: boolean = true, cb?: () => void) {
			const { url, forceUpdate, version, description, hotUpdate } = updateInfo.value || {};

			function next() {
				if (!isUpgrade.value) {
					return false;
				}

				if (!updateInfo.value) {
					return ui.showToast("未检测到版本信息");
				}

				if (!url) {
					return ui.showToast(hotUpdate ? "资源包错误" : "APP下载地址错误");
				}

				if (hotUpdate) {
					ui.showLoading({
						text: "下载资源包",
						border: true,
					});

					task = uni.downloadFile({
						url,
						success(res) {
							if (cb) {
								cb();
							}

							ui.hideLoading();

							if (res.statusCode == 200) {
								plus.runtime.install(
									res.tempFilePath,
									{ force: !!forceUpdate },
									() => {
										num.value = version;
										ui.showToast("更新成功，重启中");
										plus.runtime.restart();
									},
								);
							} else {
								ui.showToast("下载资源包失败");
							}
						},
						complete() {
							ui.hideLoading();
						},
					});

					task.onProgressUpdate((res) => {
						progress.value = res.progress;
					});
				} else {
					plus.runtime.openURL(url);
				}
			}

			if (isUpgrade.value) {
				if (isConfirm) {
					ui.showConfirm({
						title: "检查到新版本",
						message: description,
						confirmButtonText: "立即更新",
						callback(action) {
							if (action == "confirm") {
								next();
							}
						},
					});
				} else {
					next();
				}
			} else {
				ui.showToast("当前已是最新版本");
			}
		}

		// 取消下载
		function cancel() {
			task?.abort();
		}

		// 清空
		function clear() {
			progress.value = 0;
		}

		// 获取版本号
		function get() {
			req = getVersion().then((res) => {
				num.value = res;
			});
		}

		get();

		return {
			num,
			progress,
			updateInfo,
			isUpgrade,
			loading,
			check,
			update,
			get,
			clear,
			cancel,
		};
	});

	return store();
}
