import dev from "./dev";
import prod from "./prod";

// 是否开发模式
export const isDev = import.meta.env.MODE === "development";

// 配置
export const config = {
	// 应用信息
	app: {
		// 应用名称
		name: "携带空间",
		// 应用描述
		desc: "能用钱解决的事，就不要客气",
		// 页面配置
		pages: {
			login: "/pages/user/login",
		},
		wx: {
			debug: false,
		},
	},

	// 调试
	test: {
		token: "",
		mock: false,
		eps: true,
	},

	// 忽略
	ignore: {
		token: [],
	},

	// 当前环境
	...(isDev ? dev : prod),
};

export * from "./proxy";
