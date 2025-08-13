import { getUrlParam, storage } from "/@/cool/utils";
import { proxy } from "./proxy";

export default {
	// 根地址
	host: proxy["/dev/"].target,
	socketUrl: "http://127.0.0.1:9092",
	// 请求地址
	get baseUrl() {
		let proxy = getUrlParam("proxy");

		if (proxy) {
			storage.set("proxy", proxy);
		} else {
			proxy = storage.get("proxy") || "dev";
		}

		return `/${proxy}`;
	}
};
