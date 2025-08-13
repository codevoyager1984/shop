import { createApp } from "vue";
import App from "./App.vue";
import { bootstrap } from "./cool";

const app = createApp(App);

// 启动
bootstrap(app)
	.then(() => {
		app.mount("#app");

		// 加载图表
		import("echarts");
	})
	.catch((err) => {
		console.error("携帯空間 启动失败", err);
	});
