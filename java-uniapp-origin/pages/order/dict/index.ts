export const OrderStatus = [
	{
		label: "待付款",
		desc: "等待用户付款",
		value: 0,
		color: "info",
		icon: "shop-icon-order-paid",
	},
	{
		label: "待发货",
		desc: "等待商家发货",
		value: 1,
		icon: "shop-icon-order-not-shipped",
	},
	{
		label: "待收货",
		desc: "等待用户收货",
		value: 2,
		icon: "shop-icon-order-received",
	},
	{
		label: "待评价",
		desc: "等待用户评价",
		value: 3,
		icon: "shop-icon-order-comment",
	},
	{
		label: "已完成",
		desc: "完成",
		value: 4,
		color: "success",
		icon: "shop-icon-order-success",
	},
	{
		label: "退款中",
		desc: "后台退款中",
		value: 5,
		color: "error",
		icon: "shop-icon-order-refund",
	},
	{
		label: "已退款",
		desc: "订单已退款",
		value: 6,
		color: "warning",
		icon: "shop-icon-order-refund",
	},
	{
		label: "已关闭",
		desc: "订单已关闭",
		value: 7,
		color: "info",
		icon: "shop-icon-order-close",
	},
];

export const PayType = [
	{
		label: "未支付",
		value: 0,
	},
	{
		label: "微信",
		value: 1,
		key: "wxpay",
		icon: "/static/icon/wxpay.png",
	},
	{
		label: "支付宝",
		value: 2,
		key: "alipay",
		icon: "/static/icon/alipay.png",
	},
];

export const RefundStatus = [
	{
		label: "申请中",
		value: 0,
		color: "warning",
	},
	{
		label: "已退款",
		value: 1,
		color: "success",
	},
	{
		label: "拒绝退款",
		value: 2,
		color: "error",
	},
];
