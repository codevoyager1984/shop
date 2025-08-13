import { router, useStore } from "/@/cool";

const ignoreToken = [
	"/pages/index/home",
	"/pages/index/category",
	"/pages/index/shopping-cart",
	"/pages/goods/detail",
	"/pages/goods/search",
	"/pages/goods/list",
	"/pages/user/login",
	"/pages/user/captcha",
	"/pages/user/doc",
];

router.beforeEach((to, next) => {
	const { user } = useStore();

	if (ignoreToken.includes(to.path) || to.path.startsWith("/pages/demo")) {
		next();
	} else {
		if (user.token) {
			next();
		} else {
			router.login();
		}
	}
});
