import { service, useWx } from "/@/cool";

export function useOrder() {
	const wx = useWx();

	const payTypes = [
		{
			label: "微信支付",
			value: 1,
			key: "wxpay",
			icon: "/static/icon/wxpay.png",
		},
		// {
		// 	label: "支付宝支付",
		// 	value: 2,
		// 	key: "alipay",
		// 	icon: "/static/icon/alipay.png",
		// },
	];

	async function toPay(orderId: number, type = "wxpay") {
		// #ifdef MP-WEIXIN
		return service.order.pay.wxMiniPay({ orderId }).then((res) => {
			return wx.miniPay(res.data);
		});
		// #endif

		// #ifdef H5
		if (wx.isWxBrowser()) {
			return service.order.pay.wxMpPay({ orderId }).then((res) => {
				return wx.mpPay(res.data);
			});
		}
		// #endif

		// #ifdef APP
		return service.order.pay.wxAppPay({ orderId }).then((res) => {
			return wx.appPay(res.data);
		});
		// #endif
	}

	return {
		toPay,
		payTypes,
	};
}
