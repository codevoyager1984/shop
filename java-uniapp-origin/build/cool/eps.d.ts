declare namespace Eps {
	interface OrderInfoEntity {
		/**
		 * 用户ID
		 */
		userId?: number;
		/**
		 * 标题
		 */
		title?: string;
		/**
		 * 支付方式 0-待支付 1-微信 2-支付宝
		 */
		payType?: number;
		/**
		 * 支付时间
		 */
		payTime?: Date;
		/**
		 * 订单号
		 */
		orderNum?: string;
		/**
		 * 状态 0-待付款 1-待发货 2-待收货 3-待评价 4-交易完成 5-退款中 6-已退款 7-已关闭
		 */
		status?: number;
		/**
		 * 价格
		 */
		price?: string;
		/**
		 * 优惠金额
		 */
		discountPrice?: string;
		/**
		 * 优惠来源
		 */
		discountSource?: string;
		/**
		 * 地址
		 */
		address?: string;
		/**
		 * 物流信息
		 */
		logistics?: string;
		/**
		 * 退款
		 */
		refund?: string;
		/**
		 * 退款状态
		 */
		refundStatus?: number;
		/**
		 * 退款申请时间
		 */
		refundApplyTime?: Date;
		/**
		 * 备注
		 */
		remark?: string;
		/**
		 * 关闭备注
		 */
		closeRemark?: string;
		/**
		 * 已开票: 0-未开票 1-已开票
		 */
		invoice?: number;
		/**
		 * 微信类型 0-小程序 1-公众号 2-App
		 */
		wxType?: number;
		/**
		 * 任意键值
		 */
		[key: string]: any;
	}

	interface AppComplainEntity {
		/**
		 * 用户ID
		 */
		userId?: number;
		/**
		 * 类型
		 */
		type?: number;
		/**
		 * 联系方式
		 */
		contact?: string;
		/**
		 * 内容
		 */
		content?: string;
		/**
		 * 图片
		 */
		images?: string;
		/**
		 * 状态 0-未处理 1-已处理
		 */
		status?: number;
		/**
		 * 处理人ID
		 */
		handlerId?: number;
		/**
		 * 备注
		 */
		remark?: string;
		/**
		 * 任意键值
		 */
		[key: string]: any;
	}

	interface AppGoodsEntity {
		/**
		 * 标题
		 */
		title?: string;
		/**
		 * 价格
		 */
		price?: string;
		/**
		 * 原价
		 */
		originalPrice?: string;
		/**
		 * 描述
		 */
		description?: string;
		/**
		 * 状态 0-禁用 1-启用
		 */
		status?: number;
		/**
		 * 排序
		 */
		sort?: number;
		/**
		 * 类型 0-天 1-月 2-年 3-永久
		 */
		type?: number;
		/**
		 * 时长
		 */
		duration?: number;
		/**
		 * 标签
		 */
		tag?: string;
		/**
		 * 标签颜色
		 */
		tagColor?: string;
		/**
		 * 任意键值
		 */
		[key: string]: any;
	}

	interface AppFeedbackEntity {
		/**
		 * 用户ID
		 */
		userId?: number;
		/**
		 * 联系方式
		 */
		contact?: string;
		/**
		 * 类型
		 */
		type?: number;
		/**
		 * 内容
		 */
		content?: string;
		/**
		 * 图片
		 */
		images?: string;
		/**
		 * 状态 0-未处理 1-已处理
		 */
		status?: number;
		/**
		 * 处理人ID
		 */
		handlerId?: number;
		/**
		 * 备注
		 */
		remark?: string;
		/**
		 * 任意键值
		 */
		[key: string]: any;
	}

	interface AppVersionEntity {
		/**
		 * 名称
		 */
		name?: string;
		/**
		 * 版本号
		 */
		version?: string;
		/**
		 * 类型
		 */
		type?: number;
		/**
		 * 下载地址
		 */
		url?: string;
		/**
		 * 强制更新 0-否 1-是
		 */
		forceUpdate?: number;
		/**
		 * 状态 0-禁用 1-启用
		 */
		status?: number;
		/**
		 * 热更新 0-否 1-是
		 */
		hotUpdate?: number;
		/**
		 * 描述
		 */
		description?: string;
		/**
		 * 任意键值
		 */
		[key: string]: any;
	}

	interface CsMsgEntity {
		/**
		 * 用户ID
		 */
		userId?: number;
		/**
		 * 会话ID
		 */
		sessionId?: number;
		/**
		 * 消息内容
		 */
		content?: string;
		/**
		 * 类型 0-反馈 1-回复
		 */
		type?: number;
		/**
		 * 状态 0-未读 1-已读
		 */
		status?: number;
		/**
		 * 任意键值
		 */
		[key: string]: any;
	}

	interface CsSessionEntity {
		/**
		 * 用户ID
		 */
		userId?: number;
		/**
		 * 最后一条消息
		 */
		lastMsg?: string;
		/**
		 * 客服未读消息数
		 */
		adminUnreadCount?: number;
		/**
		 * 任意键值
		 */
		[key: string]: any;
	}

	interface GoodsSearchKeywordEntity {
		/**
		 * 名称
		 */
		name?: string;
		/**
		 * 排序
		 */
		sortNum?: number;
		/**
		 * 任意键值
		 */
		[key: string]: any;
	}

	interface GoodsSpecEntity {
		/**
		 * 商品ID
		 */
		goodsId?: number;
		/**
		 * 名称
		 */
		name?: string;
		/**
		 * 价格
		 */
		price?: string;
		/**
		 * 库存
		 */
		stock?: number;
		/**
		 * 排序
		 */
		sortNum?: number;
		/**
		 * 图片
		 */
		images?: string;
		/**
		 * 任意键值
		 */
		[key: string]: any;
	}

	interface GoodsInfoEntity {
		/**
		 * 类型ID
		 */
		typeId?: number;
		/**
		 * 标题
		 */
		title?: string;
		/**
		 * 副标题
		 */
		subTitle?: string;
		/**
		 * 主图
		 */
		mainPic?: string;
		/**
		 * 图片
		 */
		pics?: string;
		/**
		 * 价格
		 */
		price?: string;
		/**
		 * 已售
		 */
		sold?: number;
		/**
		 * 详情
		 */
		content?: string;
		/**
		 * 状态 0-下架 1-上架
		 */
		status?: number;
		/**
		 * 排序
		 */
		sortNum?: number;
		/**
		 * 任意键值
		 */
		[key: string]: any;
	}

	interface GoodsTypeEntity {
		/**
		 * 名称
		 */
		name?: string;
		/**
		 * 父ID
		 */
		parentId?: number;
		/**
		 * 排序
		 */
		sortNum?: number;
		/**
		 * 图片
		 */
		pic?: string;
		/**
		 * 状态 0-禁用 1-启用
		 */
		status?: number;
		/**
		 * 任意键值
		 */
		[key: string]: any;
	}

	interface GoodsCommentEntity {
		/**
		 * 用户ID
		 */
		userId?: number;
		/**
		 * 商品ID
		 */
		goodsId?: number;
		/**
		 * 订单ID
		 */
		orderId?: number;
		/**
		 * 内容
		 */
		content?: string;
		/**
		 * 星数
		 */
		starCount?: number;
		/**
		 * 图片
		 */
		pics?: string;
		/**
		 * 任意键值
		 */
		[key: string]: any;
	}

	interface UserAddressEntity {
		/**
		 * 用户ID
		 */
		userId?: number;
		/**
		 * 联系人
		 */
		contact?: string;
		/**
		 * 手机号
		 */
		phone?: string;
		/**
		 * 省
		 */
		province?: string;
		/**
		 * 市
		 */
		city?: string;
		/**
		 * 区
		 */
		district?: string;
		/**
		 * 地址
		 */
		address?: string;
		/**
		 * 是否默认
		 */
		isDefault?: boolean;
		/**
		 * 任意键值
		 */
		[key: string]: any;
	}

	interface MarketCouponUserEntity {
		/**
		 * 用户ID
		 */
		userId?: number;
		/**
		 * 优惠券ID
		 */
		couponId?: number;
		/**
		 * 状态 0-未使用 1-已使用
		 */
		status?: number;
		/**
		 * 使用时间
		 */
		useTime?: Date;
		/**
		 * 任意键值
		 */
		[key: string]: any;
	}

	interface MarketCouponInfoEntity {
		/**
		 * 标题
		 */
		title?: string;
		/**
		 * 描述
		 */
		description?: string;
		/**
		 * 类型 0-满减
		 */
		type?: number;
		/**
		 * 金额
		 */
		amount?: string;
		/**
		 * 数量
		 */
		num?: number;
		/**
		 * 已领取
		 */
		receivedNum?: number;
		/**
		 * 开始时间
		 */
		startTime?: Date;
		/**
		 * 结束时间
		 */
		endTime?: Date;
		/**
		 * 状态 0-禁用 1-启用
		 */
		status?: number;
		/**
		 * 条件
		 */
		condition?: string;
		/**
		 * 任意键值
		 */
		[key: string]: any;
	}

	interface DictInfoEntity {
		/**
		 * 类型ID
		 */
		typeId?: number;
		/**
		 * 父ID
		 */
		parentId?: number;
		/**
		 * 名称
		 */
		name?: string;
		/**
		 * 值
		 */
		value?: string;
		/**
		 * 排序
		 */
		orderNum?: number;
		/**
		 * 备注
		 */
		remark?: string;
		/**
		 * 任意键值
		 */
		[key: string]: any;
	}

	interface InfoBannerEntity {
		/**
		 * 描述
		 */
		description?: string;
		/**
		 * 跳转路径
		 */
		path?: string;
		/**
		 * 图片
		 */
		pic?: string;
		/**
		 * 排序
		 */
		sortNum?: number;
		/**
		 * 状态 1:启用 2:禁用
		 */
		status?: number;
		/**
		 * 任意键值
		 */
		[key: string]: any;
	}

	interface TestEntity {
		/**
		 * 任意键值
		 */
		[key: string]: any;
	}
	interface OrderPay {
		/**
		 * 微信小程序支付
		 */
		wxMiniPay(data?: any): Promise<any>;
		/**
		 * 支付回调通知处理
		 */
		wxNotify(data?: any): Promise<any>;
		/**
		 * 微信APP支付
		 */
		wxAppPay(data?: any): Promise<any>;
		/**
		 * 微信小程序支付
		 */
		wxMpPay(data?: any): Promise<any>;
		/**
		 * logoff
		 */
		logoff(data?: any): Promise<any>;
		/**
		 * bindPhone
		 */
		bindPhone(data?: any): Promise<any>;
		/**
		 * phone
		 */
		phone(data?: any): Promise<any>;
		/**
		 * wxApp
		 */
		wxApp(data?: any): Promise<any>;
		/**
		 * uniPhone
		 */
		uniPhone(data?: any): Promise<any>;
		/**
		 * mini
		 */
		mini(data?: any): Promise<any>;
		/**
		 * 权限标识
		 */
		permission: {
			wxMiniPay: string;
			wxNotify: string;
			wxAppPay: string;
			wxMpPay: string;
			logoff: string;
			bindPhone: string;
			phone: string;
			wxApp: string;
			uniPhone: string;
			mini: string;
		};
		/**
		 * 权限状态
		 */
		_permission: {
			wxMiniPay: boolean;
			wxNotify: boolean;
			wxAppPay: boolean;
			wxMpPay: boolean;
			logoff: boolean;
			bindPhone: boolean;
			phone: boolean;
			wxApp: boolean;
			uniPhone: boolean;
			mini: boolean;
		};
		/**
		 * 请求
		 */
		request: Service["request"];
	}

	interface OrderInfo {
		/**
		 * 分页
		 */
		page(data?: any): Promise<{
			pagination: { size: number; page: number; total: number; [key: string]: any };
			list: OrderInfoEntity[];
			[key: string]: any;
		}>;
		/**
		 * 物流信息
		 */
		logistics(data?: any): Promise<any>;
		/**
		 * 信息
		 */
		info(data: { id: integer }): Promise<OrderInfoEntity>;
		/**
		 * 创建订单
		 */
		create(data?: any): Promise<any>;
		/**
		 * 确认收货
		 */
		confirm(data?: any): Promise<any>;
		/**
		 * 退款
		 */
		refund(data?: any): Promise<any>;
		/**
		 * 取消订单
		 */
		cancel(data?: any): Promise<any>;
		/**
		 * 用户订单统计
		 */
		userCount(data?: any): Promise<any>;
		/**
		 * 修改
		 */
		update(data?: any): Promise<any>;
		/**
		 * miniPhone
		 */
		miniPhone(data?: any): Promise<any>;
		/**
		 * 权限标识
		 */
		permission: {
			page: string;
			logistics: string;
			info: string;
			create: string;
			confirm: string;
			refund: string;
			cancel: string;
			userCount: string;
			update: string;
			miniPhone: string;
		};
		/**
		 * 权限状态
		 */
		_permission: {
			page: boolean;
			logistics: boolean;
			info: boolean;
			create: boolean;
			confirm: boolean;
			refund: boolean;
			cancel: boolean;
			userCount: boolean;
			update: boolean;
			miniPhone: boolean;
		};
		/**
		 * 请求
		 */
		request: Service["request"];
	}

	interface AppComplain {
		/**
		 * 分页
		 */
		page(data?: any): Promise<{
			pagination: { size: number; page: number; total: number; [key: string]: any };
			list: AppComplainEntity[];
			[key: string]: any;
		}>;
		/**
		 * 提交投诉举报
		 */
		submit(data?: any): Promise<any>;
		/**
		 * 信息
		 */
		info(data: { id: integer }): Promise<AppComplainEntity>;
		/**
		 * info
		 */
		info(data?: any): Promise<AppComplainEntity>;
		/**
		 * list
		 */
		list(data?: any): Promise<AppComplainEntity[]>;
		/**
		 * delete
		 */
		delete(data?: any): Promise<any>;
		/**
		 * update
		 */
		update(data?: any): Promise<any>;
		/**
		 * smsCode
		 */
		smsCode(data?: any): Promise<any>;
		/**
		 * captcha
		 */
		captcha(data?: any): Promise<any>;
		/**
		 * phone
		 */
		phone(data?: any): Promise<any>;
		/**
		 * 权限标识
		 */
		permission: {
			page: string;
			submit: string;
			info: string;
			info: string;
			list: string;
			delete: string;
			update: string;
			smsCode: string;
			captcha: string;
			phone: string;
		};
		/**
		 * 权限状态
		 */
		_permission: {
			page: boolean;
			submit: boolean;
			info: boolean;
			info: boolean;
			list: boolean;
			delete: boolean;
			update: boolean;
			smsCode: boolean;
			captcha: boolean;
			phone: boolean;
		};
		/**
		 * 请求
		 */
		request: Service["request"];
	}

	interface AppGoods {
		/**
		 * 查询
		 */
		list(data?: any): Promise<AppGoodsEntity[]>;
		/**
		 * add
		 */
		add(data?: any): Promise<any>;
		/**
		 * delete
		 */
		delete(data?: any): Promise<any>;
		/**
		 * page
		 */
		page(data?: any): Promise<{
			pagination: { size: number; page: number; total: number; [key: string]: any };
			list: AppGoodsEntity[];
			[key: string]: any;
		}>;
		/**
		 * info
		 */
		info(data?: any): Promise<AppGoodsEntity>;
		/**
		 * list
		 */
		list(data?: any): Promise<AppGoodsEntity[]>;
		/**
		 * create
		 */
		create(data?: any): Promise<any>;
		/**
		 * info
		 */
		info(data?: any): Promise<AppGoodsEntity>;
		/**
		 * update
		 */
		update(data?: any): Promise<any>;
		/**
		 * 权限标识
		 */
		permission: {
			list: string;
			add: string;
			delete: string;
			page: string;
			info: string;
			list: string;
			create: string;
			info: string;
			update: string;
		};
		/**
		 * 权限状态
		 */
		_permission: {
			list: boolean;
			add: boolean;
			delete: boolean;
			page: boolean;
			info: boolean;
			list: boolean;
			create: boolean;
			info: boolean;
			update: boolean;
		};
		/**
		 * 请求
		 */
		request: Service["request"];
	}

	interface AppFeedback {
		/**
		 * 分页
		 */
		page(data?: any): Promise<{
			pagination: { size: number; page: number; total: number; [key: string]: any };
			list: AppFeedbackEntity[];
			[key: string]: any;
		}>;
		/**
		 * 提交意见反馈
		 */
		submit(data?: any): Promise<any>;
		/**
		 * 信息
		 */
		info(data: { id: integer }): Promise<AppFeedbackEntity>;
		/**
		 * update
		 */
		update(data?: any): Promise<any>;
		/**
		 * add
		 */
		add(data?: any): Promise<any>;
		/**
		 * page
		 */
		page(data?: any): Promise<{
			pagination: { size: number; page: number; total: number; [key: string]: any };
			list: AppFeedbackEntity[];
			[key: string]: any;
		}>;
		/**
		 * list
		 */
		list(data?: any): Promise<AppFeedbackEntity[]>;
		/**
		 * confirm
		 */
		confirm(data?: any): Promise<any>;
		/**
		 * create
		 */
		create(data?: any): Promise<any>;
		/**
		 * mp
		 */
		mp(data?: any): Promise<any>;
		/**
		 * 权限标识
		 */
		permission: {
			page: string;
			submit: string;
			info: string;
			update: string;
			add: string;
			page: string;
			list: string;
			confirm: string;
			create: string;
			mp: string;
		};
		/**
		 * 权限状态
		 */
		_permission: {
			page: boolean;
			submit: boolean;
			info: boolean;
			update: boolean;
			add: boolean;
			page: boolean;
			list: boolean;
			confirm: boolean;
			create: boolean;
			mp: boolean;
		};
		/**
		 * 请求
		 */
		request: Service["request"];
	}

	interface AppVersion {
		/**
		 * 检查版本
		 */
		check(data?: any): Promise<any>;
		/**
		 * submit
		 */
		submit(data?: any): Promise<any>;
		/**
		 * logoff
		 */
		logoff(data?: any): Promise<any>;
		/**
		 * bindPhone
		 */
		bindPhone(data?: any): Promise<any>;
		/**
		 * miniPhone
		 */
		miniPhone(data?: any): Promise<any>;
		/**
		 * updatePerson
		 */
		updatePerson(data?: any): Promise<any>;
		/**
		 * info
		 */
		info(data?: any): Promise<AppVersionEntity>;
		/**
		 * refreshToken
		 */
		refreshToken(data?: any): Promise<any>;
		/**
		 * miniPhone
		 */
		miniPhone(data?: any): Promise<any>;
		/**
		 * wxApp
		 */
		wxApp(data?: any): Promise<any>;
		/**
		 * 权限标识
		 */
		permission: {
			check: string;
			submit: string;
			logoff: string;
			bindPhone: string;
			miniPhone: string;
			updatePerson: string;
			info: string;
			refreshToken: string;
			miniPhone: string;
			wxApp: string;
		};
		/**
		 * 权限状态
		 */
		_permission: {
			check: boolean;
			submit: boolean;
			logoff: boolean;
			bindPhone: boolean;
			miniPhone: boolean;
			updatePerson: boolean;
			info: boolean;
			refreshToken: boolean;
			miniPhone: boolean;
			wxApp: boolean;
		};
		/**
		 * 请求
		 */
		request: Service["request"];
	}

	interface CsMsg {
		/**
		 * 未读消息数
		 */
		read(data?: any): Promise<any>;
		/**
		 * 分页
		 */
		page(data?: any): Promise<{
			pagination: { size: number; page: number; total: number; [key: string]: any };
			list: CsMsgEntity[];
			[key: string]: any;
		}>;
		/**
		 * 未读消息数
		 */
		unreadCount(data?: any): Promise<any>;
		/**
		 * wxApp
		 */
		wxApp(data?: any): Promise<any>;
		/**
		 * mini
		 */
		mini(data?: any): Promise<any>;
		/**
		 * phone
		 */
		phone(data?: any): Promise<any>;
		/**
		 * mp
		 */
		mp(data?: any): Promise<any>;
		/**
		 * smsCode
		 */
		smsCode(data?: any): Promise<any>;
		/**
		 * refreshToken
		 */
		refreshToken(data?: any): Promise<any>;
		/**
		 * uniPhone
		 */
		uniPhone(data?: any): Promise<any>;
		/**
		 * 权限标识
		 */
		permission: {
			read: string;
			page: string;
			unreadCount: string;
			wxApp: string;
			mini: string;
			phone: string;
			mp: string;
			smsCode: string;
			refreshToken: string;
			uniPhone: string;
		};
		/**
		 * 权限状态
		 */
		_permission: {
			read: boolean;
			page: boolean;
			unreadCount: boolean;
			wxApp: boolean;
			mini: boolean;
			phone: boolean;
			mp: boolean;
			smsCode: boolean;
			refreshToken: boolean;
			uniPhone: boolean;
		};
		/**
		 * 请求
		 */
		request: Service["request"];
	}

	interface CsSession {
		/**
		 * 创建会话
		 */
		create(data?: any): Promise<any>;
		/**
		 * 会话详情
		 */
		detail(data?: any): Promise<any>;
		/**
		 * wxMpPay
		 */
		wxMpPay(data?: any): Promise<any>;
		/**
		 * wxMiniPay
		 */
		wxMiniPay(data?: any): Promise<any>;
		/**
		 * confirm
		 */
		confirm(data?: any): Promise<any>;
		/**
		 * cancel
		 */
		cancel(data?: any): Promise<any>;
		/**
		 * userCount
		 */
		userCount(data?: any): Promise<any>;
		/**
		 * info
		 */
		info(data?: any): Promise<CsSessionEntity>;
		/**
		 * update
		 */
		update(data?: any): Promise<any>;
		/**
		 * smsCode
		 */
		smsCode(data?: any): Promise<any>;
		/**
		 * 权限标识
		 */
		permission: {
			create: string;
			detail: string;
			wxMpPay: string;
			wxMiniPay: string;
			confirm: string;
			cancel: string;
			userCount: string;
			info: string;
			update: string;
			smsCode: string;
		};
		/**
		 * 权限状态
		 */
		_permission: {
			create: boolean;
			detail: boolean;
			wxMpPay: boolean;
			wxMiniPay: boolean;
			confirm: boolean;
			cancel: boolean;
			userCount: boolean;
			info: boolean;
			update: boolean;
			smsCode: boolean;
		};
		/**
		 * 请求
		 */
		request: Service["request"];
	}

	interface GoodsSearchKeyword {
		/**
		 * 新增
		 */
		add(data?: any): Promise<any>;
		/**
		 * 删除
		 */
		delete(data?: any): Promise<any>;
		/**
		 * 修改
		 */
		update(data?: any): Promise<any>;
		/**
		 * 分页
		 */
		page(data?: any): Promise<{
			pagination: { size: number; page: number; total: number; [key: string]: any };
			list: GoodsSearchKeywordEntity[];
			[key: string]: any;
		}>;
		/**
		 * 查询
		 */
		list(data?: any): Promise<GoodsSearchKeywordEntity[]>;
		/**
		 * 信息
		 */
		info(data: { id: integer }): Promise<GoodsSearchKeywordEntity>;
		/**
		 * update
		 */
		update(data?: any): Promise<any>;
		/**
		 * info
		 */
		info(data?: any): Promise<GoodsSearchKeywordEntity>;
		/**
		 * confirm
		 */
		confirm(data?: any): Promise<any>;
		/**
		 * smsCode
		 */
		smsCode(data?: any): Promise<any>;
		/**
		 * 权限标识
		 */
		permission: {
			add: string;
			delete: string;
			update: string;
			page: string;
			list: string;
			info: string;
			update: string;
			info: string;
			confirm: string;
			smsCode: string;
		};
		/**
		 * 权限状态
		 */
		_permission: {
			add: boolean;
			delete: boolean;
			update: boolean;
			page: boolean;
			list: boolean;
			info: boolean;
			update: boolean;
			info: boolean;
			confirm: boolean;
			smsCode: boolean;
		};
		/**
		 * 请求
		 */
		request: Service["request"];
	}

	interface GoodsSpec {
		/**
		 * 查询
		 */
		list(data?: any): Promise<GoodsSpecEntity[]>;
		/**
		 * page
		 */
		page(data?: any): Promise<{
			pagination: { size: number; page: number; total: number; [key: string]: any };
			list: GoodsSpecEntity[];
			[key: string]: any;
		}>;
		/**
		 * receive
		 */
		receive(data?: any): Promise<any>;
		/**
		 * list
		 */
		list(data?: any): Promise<GoodsSpecEntity[]>;
		/**
		 * page
		 */
		page(data?: any): Promise<{
			pagination: { size: number; page: number; total: number; [key: string]: any };
			list: GoodsSpecEntity[];
			[key: string]: any;
		}>;
		/**
		 * add
		 */
		add(data?: any): Promise<any>;
		/**
		 * page
		 */
		page(data?: any): Promise<{
			pagination: { size: number; page: number; total: number; [key: string]: any };
			list: GoodsSpecEntity[];
			[key: string]: any;
		}>;
		/**
		 * refund
		 */
		refund(data?: any): Promise<any>;
		/**
		 * info
		 */
		info(data?: any): Promise<GoodsSpecEntity>;
		/**
		 * uniPhone
		 */
		uniPhone(data?: any): Promise<any>;
		/**
		 * 权限标识
		 */
		permission: {
			list: string;
			page: string;
			receive: string;
			list: string;
			page: string;
			add: string;
			page: string;
			refund: string;
			info: string;
			uniPhone: string;
		};
		/**
		 * 权限状态
		 */
		_permission: {
			list: boolean;
			page: boolean;
			receive: boolean;
			list: boolean;
			page: boolean;
			add: boolean;
			page: boolean;
			refund: boolean;
			info: boolean;
			uniPhone: boolean;
		};
		/**
		 * 请求
		 */
		request: Service["request"];
	}

	interface GoodsInfo {
		/**
		 * 分页
		 */
		page(data?: any): Promise<{
			pagination: { size: number; page: number; total: number; [key: string]: any };
			list: GoodsInfoEntity[];
			[key: string]: any;
		}>;
		/**
		 * 信息
		 */
		info(data: { id: integer }): Promise<GoodsInfoEntity>;
		/**
		 * info
		 */
		info(data?: any): Promise<GoodsInfoEntity>;
		/**
		 * cancel
		 */
		cancel(data?: any): Promise<any>;
		/**
		 * update
		 */
		update(data?: any): Promise<any>;
		/**
		 * refund
		 */
		refund(data?: any): Promise<any>;
		/**
		 * confirm
		 */
		confirm(data?: any): Promise<any>;
		/**
		 * create
		 */
		create(data?: any): Promise<any>;
		/**
		 * logistics
		 */
		logistics(data?: any): Promise<any>;
		/**
		 * mini
		 */
		mini(data?: any): Promise<any>;
		/**
		 * 权限标识
		 */
		permission: {
			page: string;
			info: string;
			info: string;
			cancel: string;
			update: string;
			refund: string;
			confirm: string;
			create: string;
			logistics: string;
			mini: string;
		};
		/**
		 * 权限状态
		 */
		_permission: {
			page: boolean;
			info: boolean;
			info: boolean;
			cancel: boolean;
			update: boolean;
			refund: boolean;
			confirm: boolean;
			create: boolean;
			logistics: boolean;
			mini: boolean;
		};
		/**
		 * 请求
		 */
		request: Service["request"];
	}

	interface GoodsType {
		/**
		 * 查询
		 */
		list(data?: any): Promise<GoodsTypeEntity[]>;
		/**
		 * wxNotify
		 */
		wxNotify(data?: any): Promise<any>;
		/**
		 * wxAppPay
		 */
		wxAppPay(data?: any): Promise<any>;
		/**
		 * wxMpPay
		 */
		wxMpPay(data?: any): Promise<any>;
		/**
		 * update
		 */
		update(data?: any): Promise<any>;
		/**
		 * info
		 */
		info(data?: any): Promise<GoodsTypeEntity>;
		/**
		 * refund
		 */
		refund(data?: any): Promise<any>;
		/**
		 * confirm
		 */
		confirm(data?: any): Promise<any>;
		/**
		 * page
		 */
		page(data?: any): Promise<{
			pagination: { size: number; page: number; total: number; [key: string]: any };
			list: GoodsTypeEntity[];
			[key: string]: any;
		}>;
		/**
		 * miniPhone
		 */
		miniPhone(data?: any): Promise<any>;
		/**
		 * 权限标识
		 */
		permission: {
			list: string;
			wxNotify: string;
			wxAppPay: string;
			wxMpPay: string;
			update: string;
			info: string;
			refund: string;
			confirm: string;
			page: string;
			miniPhone: string;
		};
		/**
		 * 权限状态
		 */
		_permission: {
			list: boolean;
			wxNotify: boolean;
			wxAppPay: boolean;
			wxMpPay: boolean;
			update: boolean;
			info: boolean;
			refund: boolean;
			confirm: boolean;
			page: boolean;
			miniPhone: boolean;
		};
		/**
		 * 请求
		 */
		request: Service["request"];
	}

	interface GoodsComment {
		/**
		 * 提交评论
		 */
		submit(data?: any): Promise<any>;
		/**
		 * 分页
		 */
		page(data?: any): Promise<{
			pagination: { size: number; page: number; total: number; [key: string]: any };
			list: GoodsCommentEntity[];
			[key: string]: any;
		}>;
		/**
		 * param
		 */
		param(data?: any): Promise<any>;
		/**
		 * upload
		 */
		upload(data?: any): Promise<any>;
		/**
		 * page
		 */
		page(data?: any): Promise<{
			pagination: { size: number; page: number; total: number; [key: string]: any };
			list: GoodsCommentEntity[];
			[key: string]: any;
		}>;
		/**
		 * update
		 */
		update(data?: any): Promise<any>;
		/**
		 * list
		 */
		list(data?: any): Promise<GoodsCommentEntity[]>;
		/**
		 * refund
		 */
		refund(data?: any): Promise<any>;
		/**
		 * create
		 */
		create(data?: any): Promise<any>;
		/**
		 * mini
		 */
		mini(data?: any): Promise<any>;
		/**
		 * 权限标识
		 */
		permission: {
			submit: string;
			page: string;
			param: string;
			upload: string;
			page: string;
			update: string;
			list: string;
			refund: string;
			create: string;
			mini: string;
		};
		/**
		 * 权限状态
		 */
		_permission: {
			submit: boolean;
			page: boolean;
			param: boolean;
			upload: boolean;
			page: boolean;
			update: boolean;
			list: boolean;
			refund: boolean;
			create: boolean;
			mini: boolean;
		};
		/**
		 * 请求
		 */
		request: Service["request"];
	}

	interface BaseComm {
		/**
		 * 实体信息与路径
		 */
		eps(data?: any): Promise<any>;
		/**
		 * 文件上传模式
		 */
		uploadMode(data?: any): Promise<any>;
		/**
		 * 文件上传
		 */
		upload(data?: any): Promise<any>;
		/**
		 * 参数配置
		 */
		param(data?: any): Promise<any>;
		/**
		 * update
		 */
		update(data?: any): Promise<any>;
		/**
		 * list
		 */
		list(data?: any): Promise<any[]>;
		/**
		 * wxApp
		 */
		wxApp(data?: any): Promise<any>;
		/**
		 * mp
		 */
		mp(data?: any): Promise<any>;
		/**
		 * captcha
		 */
		captcha(data?: any): Promise<any>;
		/**
		 * phone
		 */
		phone(data?: any): Promise<any>;
		/**
		 * 权限标识
		 */
		permission: {
			eps: string;
			uploadMode: string;
			upload: string;
			param: string;
			update: string;
			list: string;
			wxApp: string;
			mp: string;
			captcha: string;
			phone: string;
		};
		/**
		 * 权限状态
		 */
		_permission: {
			eps: boolean;
			uploadMode: boolean;
			upload: boolean;
			param: boolean;
			update: boolean;
			list: boolean;
			wxApp: boolean;
			mp: boolean;
			captcha: boolean;
			phone: boolean;
		};
		/**
		 * 请求
		 */
		request: Service["request"];
	}

	interface UserLogin {
		/**
		 * 手机号登录
		 */
		phone(data?: any): Promise<any>;
		/**
		 * 公众号登录
		 */
		mp(data?: any): Promise<any>;
		/**
		 * 刷新token
		 */
		refreshToken(data?: any): Promise<any>;
		/**
		 * 一键手机号登录
		 */
		uniPhone(data?: any): Promise<any>;
		/**
		 * 小程序登录
		 */
		mini(data?: any): Promise<any>;
		/**
		 * 图片验证码
		 */
		captcha(data: { param: string }): Promise<any>;
		/**
		 * 绑定小程序手机号
		 */
		miniPhone(data?: any): Promise<any>;
		/**
		 * 验证码
		 */
		smsCode(data?: any): Promise<any>;
		/**
		 * 密码登录
		 */
		password(data?: any): Promise<any>;
		/**
		 * 微信APP授权登录
		 */
		wxApp(data?: any): Promise<any>;
		/**
		 * 权限标识
		 */
		permission: {
			phone: string;
			mp: string;
			refreshToken: string;
			uniPhone: string;
			mini: string;
			captcha: string;
			miniPhone: string;
			smsCode: string;
			password: string;
			wxApp: string;
		};
		/**
		 * 权限状态
		 */
		_permission: {
			phone: boolean;
			mp: boolean;
			refreshToken: boolean;
			uniPhone: boolean;
			mini: boolean;
			captcha: boolean;
			miniPhone: boolean;
			smsCode: boolean;
			password: boolean;
			wxApp: boolean;
		};
		/**
		 * 请求
		 */
		request: Service["request"];
	}

	interface UserAddress {
		/**
		 * 分页
		 */
		page(data?: any): Promise<{
			pagination: { size: number; page: number; total: number; [key: string]: any };
			list: UserAddressEntity[];
			[key: string]: any;
		}>;
		/**
		 * 查询
		 */
		list(data?: any): Promise<UserAddressEntity[]>;
		/**
		 * 默认地址
		 */
		default(data?: any): Promise<any>;
		/**
		 * 删除
		 */
		delete(data?: any): Promise<any>;
		/**
		 * 信息
		 */
		info(data: { id: integer }): Promise<UserAddressEntity>;
		/**
		 * 新增
		 */
		add(data?: any): Promise<any>;
		/**
		 * 修改
		 */
		update(data?: any): Promise<any>;
		/**
		 * info
		 */
		info(data?: any): Promise<UserAddressEntity>;
		/**
		 * userCount
		 */
		userCount(data?: any): Promise<any>;
		/**
		 * miniPhone
		 */
		miniPhone(data?: any): Promise<any>;
		/**
		 * 权限标识
		 */
		permission: {
			page: string;
			list: string;
			default: string;
			delete: string;
			info: string;
			add: string;
			update: string;
			info: string;
			userCount: string;
			miniPhone: string;
		};
		/**
		 * 权限状态
		 */
		_permission: {
			page: boolean;
			list: boolean;
			default: boolean;
			delete: boolean;
			info: boolean;
			add: boolean;
			update: boolean;
			info: boolean;
			userCount: boolean;
			miniPhone: boolean;
		};
		/**
		 * 请求
		 */
		request: Service["request"];
	}

	interface UserInfo {
		/**
		 * 更新用户信息
		 */
		updatePerson(data?: any): Promise<any>;
		/**
		 * 用户个人信息
		 */
		person(data?: any): Promise<any>;
		/**
		 * 更新用户密码
		 */
		updatePassword(data?: any): Promise<any>;
		/**
		 * 注销
		 */
		logoff(data?: any): Promise<any>;
		/**
		 * 绑定手机号
		 */
		bindPhone(data?: any): Promise<any>;
		/**
		 * 绑定小程序手机号
		 */
		miniPhone(data?: any): Promise<any>;
		/**
		 * wxApp
		 */
		wxApp(data?: any): Promise<any>;
		/**
		 * miniPhone
		 */
		miniPhone(data?: any): Promise<any>;
		/**
		 * smsCode
		 */
		smsCode(data?: any): Promise<any>;
		/**
		 * phone
		 */
		phone(data?: any): Promise<any>;
		/**
		 * 权限标识
		 */
		permission: {
			updatePerson: string;
			person: string;
			updatePassword: string;
			logoff: string;
			bindPhone: string;
			miniPhone: string;
			wxApp: string;
			miniPhone: string;
			smsCode: string;
			phone: string;
		};
		/**
		 * 权限状态
		 */
		_permission: {
			updatePerson: boolean;
			person: boolean;
			updatePassword: boolean;
			logoff: boolean;
			bindPhone: boolean;
			miniPhone: boolean;
			wxApp: boolean;
			miniPhone: boolean;
			smsCode: boolean;
			phone: boolean;
		};
		/**
		 * 请求
		 */
		request: Service["request"];
	}

	interface MarketCouponUser {
		/**
		 * 信息
		 */
		info(data: { id: integer }): Promise<MarketCouponUserEntity>;
		/**
		 * 分页
		 */
		page(data?: any): Promise<{
			pagination: { size: number; page: number; total: number; [key: string]: any };
			list: MarketCouponUserEntity[];
			[key: string]: any;
		}>;
		/**
		 * '领取优惠券'
		 */
		receive(data?: any): Promise<any>;
		/**
		 * 查询
		 */
		list(data?: any): Promise<MarketCouponUserEntity[]>;
		/**
		 * add
		 */
		add(data?: any): Promise<any>;
		/**
		 * info
		 */
		info(data?: any): Promise<MarketCouponUserEntity>;
		/**
		 * update
		 */
		update(data?: any): Promise<any>;
		/**
		 * userCount
		 */
		userCount(data?: any): Promise<any>;
		/**
		 * logistics
		 */
		logistics(data?: any): Promise<any>;
		/**
		 * mp
		 */
		mp(data?: any): Promise<any>;
		/**
		 * 权限标识
		 */
		permission: {
			info: string;
			page: string;
			receive: string;
			list: string;
			add: string;
			info: string;
			update: string;
			userCount: string;
			logistics: string;
			mp: string;
		};
		/**
		 * 权限状态
		 */
		_permission: {
			info: boolean;
			page: boolean;
			receive: boolean;
			list: boolean;
			add: boolean;
			info: boolean;
			update: boolean;
			userCount: boolean;
			logistics: boolean;
			mp: boolean;
		};
		/**
		 * 请求
		 */
		request: Service["request"];
	}

	interface MarketCouponInfo {
		/**
		 * 分页
		 */
		page(data?: any): Promise<{
			pagination: { size: number; page: number; total: number; [key: string]: any };
			list: MarketCouponInfoEntity[];
			[key: string]: any;
		}>;
		/**
		 * page
		 */
		page(data?: any): Promise<{
			pagination: { size: number; page: number; total: number; [key: string]: any };
			list: MarketCouponInfoEntity[];
			[key: string]: any;
		}>;
		/**
		 * unreadCount
		 */
		unreadCount(data?: any): Promise<any>;
		/**
		 * eps
		 */
		eps(data?: any): Promise<any>;
		/**
		 * logistics
		 */
		logistics(data?: any): Promise<any>;
		/**
		 * userCount
		 */
		userCount(data?: any): Promise<any>;
		/**
		 * page
		 */
		page(data?: any): Promise<{
			pagination: { size: number; page: number; total: number; [key: string]: any };
			list: MarketCouponInfoEntity[];
			[key: string]: any;
		}>;
		/**
		 * update
		 */
		update(data?: any): Promise<any>;
		/**
		 * create
		 */
		create(data?: any): Promise<any>;
		/**
		 * 权限标识
		 */
		permission: {
			page: string;
			page: string;
			unreadCount: string;
			eps: string;
			logistics: string;
			userCount: string;
			page: string;
			update: string;
			create: string;
		};
		/**
		 * 权限状态
		 */
		_permission: {
			page: boolean;
			page: boolean;
			unreadCount: boolean;
			eps: boolean;
			logistics: boolean;
			userCount: boolean;
			page: boolean;
			update: boolean;
			create: boolean;
		};
		/**
		 * 请求
		 */
		request: Service["request"];
	}

	interface DictInfo {
		/**
		 * 获得字典数据
		 */
		data(data?: any): Promise<any>;
		/**
		 * list
		 */
		list(data?: any): Promise<DictInfoEntity[]>;
		/**
		 * info
		 */
		info(data?: any): Promise<DictInfoEntity>;
		/**
		 * page
		 */
		page(data?: any): Promise<{
			pagination: { size: number; page: number; total: number; [key: string]: any };
			list: DictInfoEntity[];
			[key: string]: any;
		}>;
		/**
		 * update
		 */
		update(data?: any): Promise<any>;
		/**
		 * list
		 */
		list(data?: any): Promise<DictInfoEntity[]>;
		/**
		 * 权限标识
		 */
		permission: {
			data: string;
			list: string;
			info: string;
			page: string;
			update: string;
			list: string;
		};
		/**
		 * 权限状态
		 */
		_permission: {
			data: boolean;
			list: boolean;
			info: boolean;
			page: boolean;
			update: boolean;
			list: boolean;
		};
		/**
		 * 请求
		 */
		request: Service["request"];
	}

	interface InfoBanner {
		/**
		 * 查询
		 */
		list(data?: any): Promise<InfoBannerEntity[]>;
		/**
		 * page
		 */
		page(data?: any): Promise<{
			pagination: { size: number; page: number; total: number; [key: string]: any };
			list: InfoBannerEntity[];
			[key: string]: any;
		}>;
		/**
		 * receive
		 */
		receive(data?: any): Promise<any>;
		/**
		 * list
		 */
		list(data?: any): Promise<InfoBannerEntity[]>;
		/**
		 * person
		 */
		person(data?: any): Promise<any>;
		/**
		 * logoff
		 */
		logoff(data?: any): Promise<any>;
		/**
		 * 权限标识
		 */
		permission: {
			list: string;
			page: string;
			receive: string;
			list: string;
			person: string;
			logoff: string;
		};
		/**
		 * 权限状态
		 */
		_permission: {
			list: boolean;
			page: boolean;
			receive: boolean;
			list: boolean;
			person: boolean;
			logoff: boolean;
		};
		/**
		 * 请求
		 */
		request: Service["request"];
	}

	interface Test {
		/**
		 * page
		 */
		page(data?: any): Promise<{
			pagination: { size: number; page: number; total: number; [key: string]: any };
			list: TestEntity[];
			[key: string]: any;
		}>;
		/**
		 * list
		 */
		list(data?: any): Promise<TestEntity[]>;
		/**
		 * info
		 */
		info(data?: any): Promise<TestEntity>;
		/**
		 * delete
		 */
		delete(data?: any): Promise<any>;
		/**
		 * update
		 */
		update(data?: any): Promise<any>;
		/**
		 * add
		 */
		add(data?: any): Promise<any>;
		/**
		 * 权限标识
		 */
		permission: {
			page: string;
			list: string;
			info: string;
			delete: string;
			update: string;
			add: string;
		};
		/**
		 * 权限状态
		 */
		_permission: {
			page: boolean;
			list: boolean;
			info: boolean;
			delete: boolean;
			update: boolean;
			add: boolean;
		};
		/**
		 * 请求
		 */
		request: Service["request"];
	}

	type json = any;

	type Service = {
		request(options?: {
			url: string;
			method?: "POST" | "GET" | "PUT" | "DELETE" | "PATCH" | "HEAD" | "OPTIONS";
			data?: any;
			params?: any;
			headers?: {
				[key: string]: any;
			};
			timeout?: number;
			proxy?: boolean;
			[key: string]: any;
		}): Promise<any>;
		order: { pay: OrderPay; info: OrderInfo };
		app: { complain: AppComplain; goods: AppGoods; feedback: AppFeedback; version: AppVersion };
		cs: { msg: CsMsg; session: CsSession };
		goods: {
			search: { keyword: GoodsSearchKeyword };
			spec: GoodsSpec;
			info: GoodsInfo;
			type: GoodsType;
			comment: GoodsComment;
		};
		base: { comm: BaseComm };
		user: { login: UserLogin; address: UserAddress; info: UserInfo };
		market: { coupon: { user: MarketCouponUser; info: MarketCouponInfo } };
		dict: { info: DictInfo };
		info: { banner: InfoBanner };
		test: Test;
	};
}
