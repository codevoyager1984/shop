import type { ModuleConfig } from "/@/cool";

export default (): ModuleConfig => {
	return {
		description: "APP应用管理模块：版本升级、意见反馈、投诉建议、套餐设置",

		// #ifdef APP
		demo: {
			label: "Version 版本升级",
			path: "/uni_modules/cool-app/pages/version/demo",
		},
		// #endif
	};
};
