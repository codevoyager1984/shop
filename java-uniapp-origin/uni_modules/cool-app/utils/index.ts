import { isAndroid, uuid } from "/@/cool/utils";

// 版本比较
export function compareVersions(v1: string, v2: string) {
	const arr1 = v1.split(".").map(Number);
	const arr2 = v2.split(".").map(Number);
	const maxLength = Math.max(arr1.length, arr2.length);

	for (let i = 0; i < maxLength; i++) {
		const num1 = arr1[i] || 0;
		const num2 = arr2[i] || 0;

		if (num1 > num2) return 1;
		if (num2 > num1) return -1;
	}

	return 0;
}

// 获取版本号
export function getVersion(): Promise<string> {
	return new Promise((resolve) => {
		// #ifdef APP
		const packVersion: any = plus.runtime.version;
		const appid: any = plus.runtime.appid;
		plus.runtime.getProperty(appid, function (widgetInfo) {
			resolve(
				String(packVersion) > String(widgetInfo.version) ? packVersion : widgetInfo.version,
			);
		});
		// #endif

		// #ifndef APP
		resolve("1.0.0");
		// #endif
	});
}

// 获取 deviceId
export function getDeviceId(): string {
	// #ifdef APP
	return plus.device.uuid!;
	// #endif

	// #ifndef APP
	return uuid();
	// #endif
}

// 获取 oaid
export function getOAID() {
	return new Promise((resolve) => {
		plus.device.getOAID({
			success(res) {
				resolve(res.oaid);
			},
			fail() {
				resolve(0);
			},
		});
	});
}

// 获取 androidId
export function getAndroidId() {
	if (isAndroid) {
		const Settings = plus.android.importClass("android.provider.Settings");
		const main = plus.android.runtimeMainActivity();
		// @ts-ignore
		return Settings.Secure.getString(main.getContentResolver(), Settings.Secure.ANDROID_ID);
	}
}

// 获取 mac
export function getMac() {
	if (isAndroid) {
		const net = plus.android.importClass("java.net.NetworkInterface");
		// @ts-ignore
		const wl0 = net.getByName("wlan0");
		const macByte = wl0.getHardwareAddress();
		let str = "";
		for (let i = 0; i < macByte.length; i++) {
			let tmp = "";
			let num = macByte[i];
			if (num < 0) {
				tmp = (255 + num + 1).toString(16);
			} else {
				tmp = num.toString(16);
			}
			if (tmp.length == 1) {
				tmp = "0" + tmp;
			}
			str += tmp;
		}
		return str;
	}
}
