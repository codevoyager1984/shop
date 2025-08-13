import { config, type ModuleConfig } from "/@/cool";

export default (): ModuleConfig => {
	return {
		description: "客服聊天模块",
		options: {
			url: config.host + "/cs",
			socketUrl: "http://192.168.31.92:9092/cs",
		},
		demo: {
			label: "CS 客服聊天",
			path: "/uni_modules/cool-cs/pages/chat",
		},
	};
};
