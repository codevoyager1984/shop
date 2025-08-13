declare interface OrderGoods {
	id?: any;
	count: number;
	isComment?: number;
	orderId?: number;
	price?: any;
	spec: Eps.GoodsSpecEntity;
	goodsInfo: Eps.GoodsInfoEntity;
	[key: string]: any;
}

declare interface CouponInfo extends Eps.MarketCouponInfoEntity {
	condition?: {
		fullAmount: number;
	};
}

declare interface OrderInfo extends Eps.OrderInfoEntity {
	address?: Eps.UserAddressEntity;
	discountSource?: {
		type: number;
		objectId: number;
		info: CouponInfo;
	};
	goodsList?: OrderGoods[];
	refund?: {
		orderNum: string;
		amount: number;
		realAmount: number;
		status: number;
		applyTime: Date;
		time: Date;
		reason: string;
		refuseReason: string;
	};
}
