declare namespace Eps {
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

	interface PluginInfoEntity {
		/**
		 * 名称
		 */
		name?: string;

		/**
		 * 简介
		 */
		description?: string;

		/**
		 * 实例对象
		 */
		key?: string;

		/**
		 * Hook
		 */
		hook?: string;

		/**
		 * 描述
		 */
		readme?: string;

		/**
		 * 版本
		 */
		version?: string;

		/**
		 * Logo(base64)
		 */
		logo?: string;

		/**
		 * 作者
		 */
		author?: string;

		/**
		 * 状态 0-禁用 1-启用
		 */
		status?: number;

		/**
		 * 插件的plugin.json
		 */
		pluginJson?: string;

		/**
		 * 配置
		 */
		config?: string;

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

	interface DictTypeEntity {
		/**
		 * 名称
		 */
		name?: string;

		/**
		 * 标识
		 */
		key?: string;

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

	interface BaseSysMenuEntity {
		/**
		 * 父菜单ID
		 */
		parentId?: number;

		/**
		 * 菜单名称
		 */
		name?: string;

		/**
		 * 权限
		 */
		perms?: string;

		/**
		 * 类型 0：目录 1：菜单 2：按钮
		 */
		type?: number;

		/**
		 * 图标
		 */
		icon?: string;

		/**
		 * 排序
		 */
		orderNum?: number;

		/**
		 * 菜单地址
		 */
		router?: string;

		/**
		 * 视图地址
		 */
		viewPath?: string;

		/**
		 * 路由缓存
		 */
		keepAlive?: boolean;

		/**
		 * 是否显示
		 */
		isShow?: boolean;

		/**
		 * 任意键值
		 */
		[key: string]: any;
	}

	interface BaseSysDepartmentEntity {
		/**
		 * 部门名称
		 */
		name?: string;

		/**
		 * 上级部门ID
		 */
		parentId?: number;

		/**
		 * 排序
		 */
		orderNum?: number;

		/**
		 * 任意键值
		 */
		[key: string]: any;
	}

	interface BaseSysRoleEntity {
		/**
		 * 用户ID
		 */
		userId?: number;

		/**
		 * 名称
		 */
		name?: string;

		/**
		 * 角色标签
		 */
		label?: string;

		/**
		 * 备注
		 */
		remark?: string;

		/**
		 * 数据权限是否关联上下级
		 */
		relevance?: number;

		/**
		 * 菜单权限
		 */
		menuIdList?: string;

		/**
		 * 部门权限
		 */
		departmentIdList?: string;

		/**
		 * 任意键值
		 */
		[key: string]: any;
	}

	interface BaseSysParamEntity {
		/**
		 * 键
		 */
		keyName?: string;

		/**
		 * 名称
		 */
		name?: string;

		/**
		 * 数据
		 */
		data?: string;

		/**
		 * 数据类型 0:字符串 1:数组 2:键值对
		 */
		dataType?: number;

		/**
		 * 备注
		 */
		remark?: string;

		/**
		 * 任意键值
		 */
		[key: string]: any;
	}

	interface BaseSysLogEntity {
		/**
		 * 用户ID
		 */
		userId?: number;

		/**
		 * 行为
		 */
		action?: string;

		/**
		 * IP
		 */
		ip?: string;

		/**
		 * 参数
		 */
		params?: string;

		/**
		 * 任意键值
		 */
		[key: string]: any;
	}

	interface BaseSysUserEntity {
		/**
		 * 部门ID
		 */
		departmentId?: number;

		/**
		 * 姓名
		 */
		name?: string;

		/**
		 * 用户名
		 */
		username?: string;

		/**
		 * 密码
		 */
		password?: string;

		/**
		 * 密码版本
		 */
		passwordV?: number;

		/**
		 * 昵称
		 */
		nickName?: string;

		/**
		 * 头像
		 */
		headImg?: string;

		/**
		 * 手机号
		 */
		phone?: string;

		/**
		 * 邮箱
		 */
		email?: string;

		/**
		 * 备注
		 */
		remark?: string;

		/**
		 * 状态 0:禁用 1：启用
		 */
		status?: number;

		/**
		 * socketId
		 */
		socketId?: string;

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

	interface OrderGoodsEntity {
		/**
		 * 订单ID
		 */
		orderId?: number;

		/**
		 * 商品ID
		 */
		goodsId?: number;

		/**
		 * 价格
		 */
		price?: string;

		/**
		 * 优惠金额
		 */
		discountPrice?: string;

		/**
		 * 数量
		 */
		count?: number;

		/**
		 * 其他信息
		 */
		remark?: string;

		/**
		 * 商品信息
		 */
		goodsInfo?: string;

		/**
		 * 规格
		 */
		spec?: string;

		/**
		 * 是否评价 0-否 1-是
		 */
		isComment?: number;

		/**
		 * 任意键值
		 */
		[key: string]: any;
	}

	interface TaskInfoEntity {
		/**
		 * 名称
		 */
		name?: string;

		/**
		 * 任务ID
		 */
		jobId?: string;

		/**
		 * 最大执行次数 不传为无限次
		 */
		repeatCount?: number;

		/**
		 * 每间隔多少毫秒执行一次 如果cron设置了 这项设置就无效
		 */
		every?: number;

		/**
		 * 状态 0:停止 1：运行
		 */
		status?: number;

		/**
		 * 服务实例名称
		 */
		service?: string;

		/**
		 * 状态 0:cron 1：时间间隔
		 */
		taskType?: number;

		/**
		 * 状态 0:系统 1：用户
		 */
		type?: number;

		/**
		 * 任务数据
		 */
		data?: string;

		/**
		 * 备注
		 */
		remark?: string;

		/**
		 * cron
		 */
		cron?: string;

		/**
		 * 下一次执行时间
		 */
		nextRunTime?: Date;

		/**
		 * 开始时间
		 */
		startDate?: Date;

		/**
		 * 结束时间
		 */
		endDate?: Date;

		/**
		 * 任意键值
		 */
		[key: string]: any;
	}

	interface SpaceTypeEntity {
		/**
		 * 类别名称
		 */
		name?: string;

		/**
		 * 父分类ID
		 */
		parentId?: number;

		/**
		 * 任意键值
		 */
		[key: string]: any;
	}

	interface SpaceInfoEntity {
		/**
		 * 地址
		 */
		url?: string;

		/**
		 * 类型
		 */
		type?: string;

		/**
		 * 分类ID
		 */
		classifyId?: number;

		/**
		 * 文件id
		 */
		fileId?: string;

		/**
		 * 文件名
		 */
		name?: string;

		/**
		 * 文件大小
		 */
		size?: number;

		/**
		 * 文档版本
		 */
		version?: number;

		/**
		 * 文件位置
		 */
		filePath?: string;

		/**
		 * 任意键值
		 */
		[key: string]: any;
	}

	interface UserInfoEntity {
		/**
		 * 登录唯一ID
		 */
		unionid?: string;

		/**
		 * 头像
		 */
		avatarUrl?: string;

		/**
		 * 昵称
		 */
		nickName?: string;

		/**
		 * 手机号
		 */
		phone?: string;

		/**
		 * 性别 0-未知 1-男 2-女
		 */
		gender?: number;

		/**
		 * 状态 0-禁用 1-正常 2-已注销
		 */
		status?: number;

		/**
		 * 登录方式 0-小程序 1-公众号 2-H5
		 */
		loginType?: string;

		/**
		 * 密码
		 */
		password?: string;

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

	interface RecycleDataEntity {
		/**
		 * 表信息
		 */
		entityInfo?: string;

		/**
		 * 操作人
		 */
		userId?: number;

		/**
		 * 被删除的数据
		 */
		data?: string;

		/**
		 * 请求的接口
		 */
		url?: string;

		/**
		 * 请求参数
		 */
		params?: string;

		/**
		 * 删除数据条数
		 */
		count?: number;

		/**
		 * 任意键值
		 */
		[key: string]: any;
	}

	type json = any;

	interface CsSession {
		/**
		 * 分页
		 */
		page(data?: any): Promise<{
			pagination: { size: number; page: number; total: number; [key: string]: any };
			list: CsSessionEntity[];
			[key: string]: any;
		}>;

		/**
		 * 新增
		 */
		add(data?: any): Promise<any>;

		/**
		 * 查询
		 */
		list(data?: any): Promise<CsSessionEntity[]>;

		/**
		 * 信息
		 */
		info(data: { id: integer }): Promise<CsSessionEntity>;

		/**
		 * 删除
		 */
		delete(data?: any): Promise<any>;

		/**
		 * 修改
		 */
		update(data?: any): Promise<any>;

		/**
		 * 权限标识
		 */
		permission: {
			page: string;
			add: string;
			list: string;
			info: string;
			delete: string;
			update: string;
		};

		/**
		 * 权限状态
		 */
		_permission: {
			page: boolean;
			add: boolean;
			list: boolean;
			info: boolean;
			delete: boolean;
			update: boolean;
		};

		request: Service["request"];
	}

	interface CsMsg {
		/**
		 * 未读消息数
		 */
		read(data?: any): Promise<any>;

		/**
		 * 未读消息数
		 */
		unreadCount(data?: any): Promise<any>;

		/**
		 * 分页
		 */
		page(data?: any): Promise<{
			pagination: { size: number; page: number; total: number; [key: string]: any };
			list: CsMsgEntity[];
			[key: string]: any;
		}>;

		/**
		 * 权限标识
		 */
		permission: { read: string; unreadCount: string; page: string };

		/**
		 * 权限状态
		 */
		_permission: { read: boolean; unreadCount: boolean; page: boolean };

		request: Service["request"];
	}

	interface PluginInfo {
		/**
		 * 卸载插件
		 */
		delete(data?: any): Promise<any>;

		/**
		 * 分页
		 */
		page(data?: any): Promise<{
			pagination: { size: number; page: number; total: number; [key: string]: any };
			list: PluginInfoEntity[];
			[key: string]: any;
		}>;

		/**
		 * 修改
		 */
		update(data?: any): Promise<any>;

		/**
		 * 查询
		 */
		list(data?: any): Promise<PluginInfoEntity[]>;

		/**
		 * 安装插件
		 */
		install(data: {
			/** 文件  */
			files: array;
			/** 是否强制安装  */
			force: boolean;
		}): Promise<any>;

		/**
		 * 信息
		 */
		info(data: { id: integer }): Promise<PluginInfoEntity>;

		/**
		 * 新增
		 */
		add(data?: any): Promise<any>;

		/**
		 * 权限标识
		 */
		permission: {
			delete: string;
			page: string;
			update: string;
			list: string;
			install: string;
			info: string;
			add: string;
		};

		/**
		 * 权限状态
		 */
		_permission: {
			delete: boolean;
			page: boolean;
			update: boolean;
			list: boolean;
			install: boolean;
			info: boolean;
			add: boolean;
		};

		request: Service["request"];
	}

	interface DictInfo {
		/**
		 * 分页
		 */
		page(data?: any): Promise<{
			pagination: { size: number; page: number; total: number; [key: string]: any };
			list: DictInfoEntity[];
			[key: string]: any;
		}>;

		/**
		 * 查询
		 */
		list(data?: any): Promise<DictInfoEntity[]>;

		/**
		 * 删除
		 */
		delete(data?: any): Promise<any>;

		/**
		 * 信息
		 */
		info(data: { id: integer }): Promise<DictInfoEntity>;

		/**
		 * 修改
		 */
		update(data?: any): Promise<any>;

		/**
		 * 获得字典数据
		 */
		data(data?: any): Promise<any>;

		/**
		 * 新增
		 */
		add(data?: any): Promise<any>;

		/**
		 * 权限标识
		 */
		permission: {
			page: string;
			list: string;
			delete: string;
			info: string;
			update: string;
			data: string;
			add: string;
		};

		/**
		 * 权限状态
		 */
		_permission: {
			page: boolean;
			list: boolean;
			delete: boolean;
			info: boolean;
			update: boolean;
			data: boolean;
			add: boolean;
		};

		request: Service["request"];
	}

	interface DictType {
		/**
		 * 分页
		 */
		page(data?: any): Promise<{
			pagination: { size: number; page: number; total: number; [key: string]: any };
			list: DictTypeEntity[];
			[key: string]: any;
		}>;

		/**
		 * 查询
		 */
		list(data?: any): Promise<DictTypeEntity[]>;

		/**
		 * 新增
		 */
		add(data?: any): Promise<any>;

		/**
		 * 信息
		 */
		info(data: { id: integer }): Promise<DictTypeEntity>;

		/**
		 * 删除
		 */
		delete(data?: any): Promise<any>;

		/**
		 * 修改
		 */
		update(data?: any): Promise<any>;

		/**
		 * 权限标识
		 */
		permission: {
			page: string;
			list: string;
			add: string;
			info: string;
			delete: string;
			update: string;
		};

		/**
		 * 权限状态
		 */
		_permission: {
			page: boolean;
			list: boolean;
			add: boolean;
			info: boolean;
			delete: boolean;
			update: boolean;
		};

		request: Service["request"];
	}

	interface GoodsSpec {
		/**
		 * 分页
		 */
		page(data?: any): Promise<{
			pagination: { size: number; page: number; total: number; [key: string]: any };
			list: GoodsSpecEntity[];
			[key: string]: any;
		}>;

		/**
		 * 查询
		 */
		list(data?: any): Promise<GoodsSpecEntity[]>;

		/**
		 * 新增
		 */
		add(data?: any): Promise<any>;

		/**
		 * 信息
		 */
		info(data: { id: integer }): Promise<GoodsSpecEntity>;

		/**
		 * 删除
		 */
		delete(data?: any): Promise<any>;

		/**
		 * 修改
		 */
		update(data?: any): Promise<any>;

		/**
		 * 权限标识
		 */
		permission: {
			page: string;
			list: string;
			add: string;
			info: string;
			delete: string;
			update: string;
		};

		/**
		 * 权限状态
		 */
		_permission: {
			page: boolean;
			list: boolean;
			add: boolean;
			info: boolean;
			delete: boolean;
			update: boolean;
		};

		request: Service["request"];
	}

	interface GoodsInfo {
		/**
		 * 删除
		 */
		delete(data?: any): Promise<any>;

		/**
		 * 修改
		 */
		update(data?: any): Promise<any>;

		/**
		 * 新增
		 */
		add(data?: any): Promise<any>;

		/**
		 * 信息
		 */
		info(data: { id: integer }): Promise<GoodsInfoEntity>;

		/**
		 * 分页
		 */
		page(data?: any): Promise<{
			pagination: { size: number; page: number; total: number; [key: string]: any };
			list: GoodsInfoEntity[];
			[key: string]: any;
		}>;

		/**
		 * 查询
		 */
		list(data?: any): Promise<GoodsInfoEntity[]>;

		/**
		 * 权限标识
		 */
		permission: {
			delete: string;
			update: string;
			add: string;
			info: string;
			page: string;
			list: string;
		};

		/**
		 * 权限状态
		 */
		_permission: {
			delete: boolean;
			update: boolean;
			add: boolean;
			info: boolean;
			page: boolean;
			list: boolean;
		};

		request: Service["request"];
	}

	interface GoodsType {
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
			list: GoodsTypeEntity[];
			[key: string]: any;
		}>;

		/**
		 * 查询
		 */
		list(data?: any): Promise<GoodsTypeEntity[]>;

		/**
		 * 信息
		 */
		info(data: { id: integer }): Promise<GoodsTypeEntity>;

		/**
		 * 新增
		 */
		add(data?: any): Promise<any>;

		/**
		 * 权限标识
		 */
		permission: {
			delete: string;
			update: string;
			page: string;
			list: string;
			info: string;
			add: string;
		};

		/**
		 * 权限状态
		 */
		_permission: {
			delete: boolean;
			update: boolean;
			page: boolean;
			list: boolean;
			info: boolean;
			add: boolean;
		};

		request: Service["request"];
	}

	interface GoodsComment {
		/**
		 * 修改
		 */
		update(data?: any): Promise<any>;

		/**
		 * 信息
		 */
		info(data: { id: integer }): Promise<GoodsCommentEntity>;

		/**
		 * 新增
		 */
		add(data?: any): Promise<any>;

		/**
		 * 分页
		 */
		page(data?: any): Promise<{
			pagination: { size: number; page: number; total: number; [key: string]: any };
			list: GoodsCommentEntity[];
			[key: string]: any;
		}>;

		/**
		 * 查询
		 */
		list(data?: any): Promise<GoodsCommentEntity[]>;

		/**
		 * 删除
		 */
		delete(data?: any): Promise<any>;

		/**
		 * 权限标识
		 */
		permission: {
			update: string;
			info: string;
			add: string;
			page: string;
			list: string;
			delete: string;
		};

		/**
		 * 权限状态
		 */
		_permission: {
			update: boolean;
			info: boolean;
			add: boolean;
			page: boolean;
			list: boolean;
			delete: boolean;
		};

		request: Service["request"];
	}

	interface GoodsSearchKeyword {
		/**
		 * 分页
		 */
		page(data?: any): Promise<{
			pagination: { size: number; page: number; total: number; [key: string]: any };
			list: GoodsSearchKeywordEntity[];
			[key: string]: any;
		}>;

		/**
		 * 删除
		 */
		delete(data?: any): Promise<any>;

		/**
		 * 修改
		 */
		update(data?: any): Promise<any>;

		/**
		 * 查询
		 */
		list(data?: any): Promise<GoodsSearchKeywordEntity[]>;

		/**
		 * 信息
		 */
		info(data: { id: integer }): Promise<GoodsSearchKeywordEntity>;

		/**
		 * 新增
		 */
		add(data?: any): Promise<any>;

		/**
		 * 权限标识
		 */
		permission: {
			page: string;
			delete: string;
			update: string;
			list: string;
			info: string;
			add: string;
		};

		/**
		 * 权限状态
		 */
		_permission: {
			page: boolean;
			delete: boolean;
			update: boolean;
			list: boolean;
			info: boolean;
			add: boolean;
		};

		request: Service["request"];
	}

	interface BaseSysMenu {
		/**
		 * 删除
		 */
		delete(data?: any): Promise<any>;

		/**
		 * 信息
		 */
		info(data: { id: integer }): Promise<BaseSysMenuEntity>;

		/**
		 * 修改
		 */
		update(data?: any): Promise<any>;

		/**
		 * 导入
		 */
		import(data?: any): Promise<any>;

		/**
		 * 导出
		 */
		export(data?: any): Promise<any>;

		/**
		 * 创建代码
		 */
		create(data?: any): Promise<any>;

		/**
		 * 新增
		 */
		add(data?: any): Promise<any>;

		/**
		 * 分页
		 */
		page(data?: any): Promise<{
			pagination: { size: number; page: number; total: number; [key: string]: any };
			list: BaseSysMenuEntity[];
			[key: string]: any;
		}>;

		/**
		 * 查询
		 */
		list(data?: any): Promise<BaseSysMenuEntity[]>;

		/**
		 * 权限标识
		 */
		permission: {
			delete: string;
			info: string;
			update: string;
			import: string;
			export: string;
			create: string;
			add: string;
			page: string;
			list: string;
		};

		/**
		 * 权限状态
		 */
		_permission: {
			delete: boolean;
			info: boolean;
			update: boolean;
			import: boolean;
			export: boolean;
			create: boolean;
			add: boolean;
			page: boolean;
			list: boolean;
		};

		request: Service["request"];
	}

	interface BaseSysDepartment {
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
		 * 排序
		 */
		order(data?: any): Promise<any>;

		/**
		 * 查询
		 */
		list(data?: any): Promise<BaseSysDepartmentEntity[]>;

		/**
		 * 权限标识
		 */
		permission: { add: string; delete: string; update: string; order: string; list: string };

		/**
		 * 权限状态
		 */
		_permission: {
			add: boolean;
			delete: boolean;
			update: boolean;
			order: boolean;
			list: boolean;
		};

		request: Service["request"];
	}

	interface BaseSysRole {
		/**
		 * 信息
		 */
		info(data: { id: integer }): Promise<BaseSysRoleEntity>;

		/**
		 * 新增
		 */
		add(data?: any): Promise<any>;

		/**
		 * 分页
		 */
		page(data?: any): Promise<{
			pagination: { size: number; page: number; total: number; [key: string]: any };
			list: BaseSysRoleEntity[];
			[key: string]: any;
		}>;

		/**
		 * 查询
		 */
		list(data?: any): Promise<BaseSysRoleEntity[]>;

		/**
		 * 删除
		 */
		delete(data?: any): Promise<any>;

		/**
		 * 修改
		 */
		update(data?: any): Promise<any>;

		/**
		 * 权限标识
		 */
		permission: {
			info: string;
			add: string;
			page: string;
			list: string;
			delete: string;
			update: string;
		};

		/**
		 * 权限状态
		 */
		_permission: {
			info: boolean;
			add: boolean;
			page: boolean;
			list: boolean;
			delete: boolean;
			update: boolean;
		};

		request: Service["request"];
	}

	interface BaseSysParam {
		/**
		 * 删除
		 */
		delete(data?: any): Promise<any>;

		/**
		 * 根据键返回网页的参数值
		 */
		html(data: { key: string }): Promise<any>;

		/**
		 * 信息
		 */
		info(data: { id: integer }): Promise<BaseSysParamEntity>;

		/**
		 * 修改
		 */
		update(data?: any): Promise<any>;

		/**
		 * 分页
		 */
		page(data?: any): Promise<{
			pagination: { size: number; page: number; total: number; [key: string]: any };
			list: BaseSysParamEntity[];
			[key: string]: any;
		}>;

		/**
		 * 新增
		 */
		add(data?: any): Promise<any>;

		/**
		 * 权限标识
		 */
		permission: {
			delete: string;
			html: string;
			info: string;
			update: string;
			page: string;
			add: string;
		};

		/**
		 * 权限状态
		 */
		_permission: {
			delete: boolean;
			html: boolean;
			info: boolean;
			update: boolean;
			page: boolean;
			add: boolean;
		};

		request: Service["request"];
	}

	interface BaseSysLog {
		/**
		 * 设置日志保存时间
		 */
		setKeep(data?: any): Promise<any>;

		/**
		 * 清理日志
		 */
		clear(data?: any): Promise<any>;

		/**
		 * 分页
		 */
		page(data?: any): Promise<{
			pagination: { size: number; page: number; total: number; [key: string]: any };
			list: BaseSysLogEntity[];
			[key: string]: any;
		}>;

		/**
		 * 获得日志报错时间
		 */
		getKeep(data?: any): Promise<any>;

		/**
		 * 权限标识
		 */
		permission: { setKeep: string; clear: string; page: string; getKeep: string };

		/**
		 * 权限状态
		 */
		_permission: { setKeep: boolean; clear: boolean; page: boolean; getKeep: boolean };

		request: Service["request"];
	}

	interface BaseSysUser {
		/**
		 * 分页
		 */
		page(data?: any): Promise<{
			pagination: { size: number; page: number; total: number; [key: string]: any };
			list: BaseSysUserEntity[];
			[key: string]: any;
		}>;

		/**
		 * 新增
		 */
		add(data?: any): Promise<any>;

		/**
		 * 删除
		 */
		delete(data?: any): Promise<any>;

		/**
		 * 移动部门
		 */
		move(data?: any): Promise<any>;

		/**
		 * 信息
		 */
		info(data: { id: integer }): Promise<BaseSysUserEntity>;

		/**
		 * 修改
		 */
		update(data?: any): Promise<any>;

		/**
		 * 权限标识
		 */
		permission: {
			page: string;
			add: string;
			delete: string;
			move: string;
			info: string;
			update: string;
		};

		/**
		 * 权限状态
		 */
		_permission: {
			page: boolean;
			add: boolean;
			delete: boolean;
			move: boolean;
			info: boolean;
			update: boolean;
		};

		request: Service["request"];
	}

	interface BaseOpen {
		/**
		 * 登录
		 */
		login(data?: any): Promise<any>;

		/**
		 * 验证码
		 */
		captcha(data: {
			/** 类型：svg|base64  */
			type?: string;
			/** 宽度  */
			width?: integer;
			/** 高度  */
			height?: integer;
		}): Promise<any>;

		/**
		 * 刷新token
		 */
		refreshToken(data: { refreshToken: string }): Promise<any>;

		/**
		 * 获得网页内容的参数值
		 */
		html(data?: any): Promise<any>;

		/**
		 * 实体信息与路径
		 */
		eps(data?: any): Promise<any>;

		/**
		 * 权限标识
		 */
		permission: {
			login: string;
			captcha: string;
			refreshToken: string;
			html: string;
			eps: string;
		};

		/**
		 * 权限状态
		 */
		_permission: {
			login: boolean;
			captcha: boolean;
			refreshToken: boolean;
			html: boolean;
			eps: boolean;
		};

		request: Service["request"];
	}

	interface BaseComm {
		/**
		 * 修改个人信息
		 */
		personUpdate(data?: any): Promise<any>;

		/**
		 * 文件上传模式
		 */
		uploadMode(data?: any): Promise<any>;

		/**
		 * 编程
		 */
		program(data?: any): Promise<any>;

		/**
		 * 权限与菜单
		 */
		permmenu(data?: any): Promise<any>;

		/**
		 * 实体信息与路径
		 */
		eps(data?: any): Promise<any>;

		/**
		 * 退出
		 */
		logout(data?: any): Promise<any>;

		/**
		 * 个人信息
		 */
		person(data?: any): Promise<any>;

		/**
		 * 文件上传
		 */
		upload(data?: any): Promise<any>;

		/**
		 * 权限标识
		 */
		permission: {
			personUpdate: string;
			uploadMode: string;
			program: string;
			permmenu: string;
			eps: string;
			logout: string;
			person: string;
			upload: string;
		};

		/**
		 * 权限状态
		 */
		_permission: {
			personUpdate: boolean;
			uploadMode: boolean;
			program: boolean;
			permmenu: boolean;
			eps: boolean;
			logout: boolean;
			person: boolean;
			upload: boolean;
		};

		request: Service["request"];
	}

	interface InfoBanner {
		/**
		 * 信息
		 */
		info(data: { id: integer }): Promise<InfoBannerEntity>;

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
			list: InfoBannerEntity[];
			[key: string]: any;
		}>;

		/**
		 * 查询
		 */
		list(data?: any): Promise<InfoBannerEntity[]>;

		/**
		 * 新增
		 */
		add(data?: any): Promise<any>;

		/**
		 * 权限标识
		 */
		permission: {
			info: string;
			delete: string;
			update: string;
			page: string;
			list: string;
			add: string;
		};

		/**
		 * 权限状态
		 */
		_permission: {
			info: boolean;
			delete: boolean;
			update: boolean;
			page: boolean;
			list: boolean;
			add: boolean;
		};

		request: Service["request"];
	}

	interface CountHome {
		/**
		 * 用户概况
		 */
		userSummary(data?: any): Promise<any>;

		/**
		 * 用户图表
		 */
		userChart(data?: any): Promise<any>;

		/**
		 * 订单概况
		 */
		orderSummary(data?: any): Promise<any>;

		/**
		 * 商品分类
		 */
		goodsCategory(data?: any): Promise<any>;

		/**
		 * 订单图表
		 */
		orderChart(data?: any): Promise<any>;

		/**
		 * goodsRank
		 */
		goodsRank(data?: any): Promise<any>;

		/**
		 * 权限标识
		 */
		permission: {
			userSummary: string;
			userChart: string;
			orderSummary: string;
			goodsCategory: string;
			orderChart: string;
			goodsRank: string;
		};

		/**
		 * 权限状态
		 */
		_permission: {
			userSummary: boolean;
			userChart: boolean;
			orderSummary: boolean;
			goodsCategory: boolean;
			orderChart: boolean;
			goodsRank: boolean;
		};

		request: Service["request"];
	}

	interface MarketCouponInfo {
		/**
		 * 信息
		 */
		info(data: { id: integer }): Promise<MarketCouponInfoEntity>;

		/**
		 * 删除
		 */
		delete(data?: any): Promise<any>;

		/**
		 * 新增
		 */
		add(data?: any): Promise<any>;

		/**
		 * 分页
		 */
		page(data?: any): Promise<{
			pagination: { size: number; page: number; total: number; [key: string]: any };
			list: MarketCouponInfoEntity[];
			[key: string]: any;
		}>;

		/**
		 * 查询
		 */
		list(data?: any): Promise<MarketCouponInfoEntity[]>;

		/**
		 * 修改
		 */
		update(data?: any): Promise<any>;

		/**
		 * 权限标识
		 */
		permission: {
			info: string;
			delete: string;
			add: string;
			page: string;
			list: string;
			update: string;
		};

		/**
		 * 权限状态
		 */
		_permission: {
			info: boolean;
			delete: boolean;
			add: boolean;
			page: boolean;
			list: boolean;
			update: boolean;
		};

		request: Service["request"];
	}

	interface MarketCouponUser {
		/**
		 * 新增
		 */
		add(data?: any): Promise<any>;

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
		 * 查询
		 */
		list(data?: any): Promise<MarketCouponUserEntity[]>;

		/**
		 * 删除
		 */
		delete(data?: any): Promise<any>;

		/**
		 * 修改
		 */
		update(data?: any): Promise<any>;

		/**
		 * 权限标识
		 */
		permission: {
			add: string;
			info: string;
			page: string;
			list: string;
			delete: string;
			update: string;
		};

		/**
		 * 权限状态
		 */
		_permission: {
			add: boolean;
			info: boolean;
			page: boolean;
			list: boolean;
			delete: boolean;
			update: boolean;
		};

		request: Service["request"];
	}

	interface AppComplain {
		/**
		 * 信息
		 */
		info(data: { id: integer }): Promise<AppComplainEntity>;

		/**
		 * 删除
		 */
		delete(data?: any): Promise<any>;

		/**
		 * 修改
		 */
		update(data?: any): Promise<any>;

		/**
		 * 新增
		 */
		add(data?: any): Promise<any>;

		/**
		 * 分页
		 */
		page(data?: any): Promise<{
			pagination: { size: number; page: number; total: number; [key: string]: any };
			list: AppComplainEntity[];
			[key: string]: any;
		}>;

		/**
		 * 查询
		 */
		list(data?: any): Promise<AppComplainEntity[]>;

		/**
		 * 权限标识
		 */
		permission: {
			info: string;
			delete: string;
			update: string;
			add: string;
			page: string;
			list: string;
		};

		/**
		 * 权限状态
		 */
		_permission: {
			info: boolean;
			delete: boolean;
			update: boolean;
			add: boolean;
			page: boolean;
			list: boolean;
		};

		request: Service["request"];
	}

	interface AppGoods {
		/**
		 * 新增
		 */
		add(data?: any): Promise<any>;

		/**
		 * 分页
		 */
		page(data?: any): Promise<{
			pagination: { size: number; page: number; total: number; [key: string]: any };
			list: AppGoodsEntity[];
			[key: string]: any;
		}>;

		/**
		 * 查询
		 */
		list(data?: any): Promise<AppGoodsEntity[]>;

		/**
		 * 信息
		 */
		info(data: { id: integer }): Promise<AppGoodsEntity>;

		/**
		 * 删除
		 */
		delete(data?: any): Promise<any>;

		/**
		 * 修改
		 */
		update(data?: any): Promise<any>;

		/**
		 * 权限标识
		 */
		permission: {
			add: string;
			page: string;
			list: string;
			info: string;
			delete: string;
			update: string;
		};

		/**
		 * 权限状态
		 */
		_permission: {
			add: boolean;
			page: boolean;
			list: boolean;
			info: boolean;
			delete: boolean;
			update: boolean;
		};

		request: Service["request"];
	}

	interface AppVersion {
		/**
		 * 查询
		 */
		list(data?: any): Promise<AppVersionEntity[]>;

		/**
		 * 新增
		 */
		add(data?: any): Promise<any>;

		/**
		 * 信息
		 */
		info(data: { id: integer }): Promise<AppVersionEntity>;

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
			list: AppVersionEntity[];
			[key: string]: any;
		}>;

		/**
		 * 权限标识
		 */
		permission: {
			list: string;
			add: string;
			info: string;
			delete: string;
			update: string;
			page: string;
		};

		/**
		 * 权限状态
		 */
		_permission: {
			list: boolean;
			add: boolean;
			info: boolean;
			delete: boolean;
			update: boolean;
			page: boolean;
		};

		request: Service["request"];
	}

	interface AppFeedback {
		/**
		 * 删除
		 */
		delete(data?: any): Promise<any>;

		/**
		 * 修改
		 */
		update(data?: any): Promise<any>;

		/**
		 * 新增
		 */
		add(data?: any): Promise<any>;

		/**
		 * 分页
		 */
		page(data?: any): Promise<{
			pagination: { size: number; page: number; total: number; [key: string]: any };
			list: AppFeedbackEntity[];
			[key: string]: any;
		}>;

		/**
		 * 查询
		 */
		list(data?: any): Promise<AppFeedbackEntity[]>;

		/**
		 * 信息
		 */
		info(data: { id: integer }): Promise<AppFeedbackEntity>;

		/**
		 * 权限标识
		 */
		permission: {
			delete: string;
			update: string;
			add: string;
			page: string;
			list: string;
			info: string;
		};

		/**
		 * 权限状态
		 */
		_permission: {
			delete: boolean;
			update: boolean;
			add: boolean;
			page: boolean;
			list: boolean;
			info: boolean;
		};

		request: Service["request"];
	}

	interface OrderInfo {
		/**
		 * 发货
		 */
		deliver(data?: any): Promise<any>;

		/**
		 * 信息
		 */
		info(data: { id: integer }): Promise<OrderInfoEntity>;

		/**
		 * 分页
		 */
		page(data?: any): Promise<{
			pagination: { size: number; page: number; total: number; [key: string]: any };
			list: OrderInfoEntity[];
			[key: string]: any;
		}>;

		/**
		 * 修改
		 */
		update(data?: any): Promise<any>;

		/**
		 * 退款处理
		 */
		refundHandle(data?: any): Promise<any>;

		/**
		 * 物流信息
		 */
		logistics(data?: any): Promise<any>;

		/**
		 * 权限标识
		 */
		permission: {
			deliver: string;
			info: string;
			page: string;
			update: string;
			refundHandle: string;
			logistics: string;
		};

		/**
		 * 权限状态
		 */
		_permission: {
			deliver: boolean;
			info: boolean;
			page: boolean;
			update: boolean;
			refundHandle: boolean;
			logistics: boolean;
		};

		request: Service["request"];
	}

	interface OrderGoods {
		/**
		 * 新增
		 */
		add(data?: any): Promise<any>;

		/**
		 * 分页
		 */
		page(data?: any): Promise<{
			pagination: { size: number; page: number; total: number; [key: string]: any };
			list: OrderGoodsEntity[];
			[key: string]: any;
		}>;

		/**
		 * 查询
		 */
		list(data?: any): Promise<OrderGoodsEntity[]>;

		/**
		 * 删除
		 */
		delete(data?: any): Promise<any>;

		/**
		 * 信息
		 */
		info(data: { id: integer }): Promise<OrderGoodsEntity>;

		/**
		 * 修改
		 */
		update(data?: any): Promise<any>;

		/**
		 * 权限标识
		 */
		permission: {
			add: string;
			page: string;
			list: string;
			delete: string;
			info: string;
			update: string;
		};

		/**
		 * 权限状态
		 */
		_permission: {
			add: boolean;
			page: boolean;
			list: boolean;
			delete: boolean;
			info: boolean;
			update: boolean;
		};

		request: Service["request"];
	}

	interface TaskInfo {
		/**
		 * 分页
		 */
		page(data?: any): Promise<{
			pagination: { size: number; page: number; total: number; [key: string]: any };
			list: TaskInfoEntity[];
			[key: string]: any;
		}>;

		/**
		 * 新增
		 */
		add(data?: any): Promise<any>;

		/**
		 * 开始任务
		 */
		start(data?: any): Promise<any>;

		/**
		 * 执行一次
		 */
		once(data?: any): Promise<any>;

		/**
		 * 删除
		 */
		delete(data?: any): Promise<any>;

		/**
		 * 修改
		 */
		update(data?: any): Promise<any>;

		/**
		 * 任务日志
		 */
		log(data?: any): Promise<any>;

		/**
		 * 信息
		 */
		info(data: { id: integer }): Promise<TaskInfoEntity>;

		/**
		 * 停止任务
		 */
		stop(data?: any): Promise<any>;

		/**
		 * 权限标识
		 */
		permission: {
			page: string;
			add: string;
			start: string;
			once: string;
			delete: string;
			update: string;
			log: string;
			info: string;
			stop: string;
		};

		/**
		 * 权限状态
		 */
		_permission: {
			page: boolean;
			add: boolean;
			start: boolean;
			once: boolean;
			delete: boolean;
			update: boolean;
			log: boolean;
			info: boolean;
			stop: boolean;
		};

		request: Service["request"];
	}

	interface SpaceType {
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
			list: SpaceTypeEntity[];
			[key: string]: any;
		}>;

		/**
		 * 新增
		 */
		add(data?: any): Promise<any>;

		/**
		 * 查询
		 */
		list(data?: any): Promise<SpaceTypeEntity[]>;

		/**
		 * 信息
		 */
		info(data: { id: integer }): Promise<SpaceTypeEntity>;

		/**
		 * 权限标识
		 */
		permission: {
			delete: string;
			update: string;
			page: string;
			add: string;
			list: string;
			info: string;
		};

		/**
		 * 权限状态
		 */
		_permission: {
			delete: boolean;
			update: boolean;
			page: boolean;
			add: boolean;
			list: boolean;
			info: boolean;
		};

		request: Service["request"];
	}

	interface SpaceInfo {
		/**
		 * 分页
		 */
		page(data?: any): Promise<{
			pagination: { size: number; page: number; total: number; [key: string]: any };
			list: SpaceInfoEntity[];
			[key: string]: any;
		}>;

		/**
		 * 查询
		 */
		list(data?: any): Promise<SpaceInfoEntity[]>;

		/**
		 * 信息
		 */
		info(data: { id: integer }): Promise<SpaceInfoEntity>;

		/**
		 * 删除
		 */
		delete(data?: any): Promise<any>;

		/**
		 * 修改
		 */
		update(data?: any): Promise<any>;

		/**
		 * 新增
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

		request: Service["request"];
	}

	interface UserInfo {
		/**
		 * 删除
		 */
		delete(data?: any): Promise<any>;

		/**
		 * 修改
		 */
		update(data?: any): Promise<any>;

		/**
		 * 新增
		 */
		add(data?: any): Promise<any>;

		/**
		 * 信息
		 */
		info(data: { id: integer }): Promise<UserInfoEntity>;

		/**
		 * 分页
		 */
		page(data?: any): Promise<{
			pagination: { size: number; page: number; total: number; [key: string]: any };
			list: UserInfoEntity[];
			[key: string]: any;
		}>;

		/**
		 * 查询
		 */
		list(data?: any): Promise<UserInfoEntity[]>;

		/**
		 * 权限标识
		 */
		permission: {
			delete: string;
			update: string;
			add: string;
			info: string;
			page: string;
			list: string;
		};

		/**
		 * 权限状态
		 */
		_permission: {
			delete: boolean;
			update: boolean;
			add: boolean;
			info: boolean;
			page: boolean;
			list: boolean;
		};

		request: Service["request"];
	}

	interface UserAddress {
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
			list: UserAddressEntity[];
			[key: string]: any;
		}>;

		/**
		 * 查询
		 */
		list(data?: any): Promise<UserAddressEntity[]>;

		/**
		 * 新增
		 */
		add(data?: any): Promise<any>;

		/**
		 * 信息
		 */
		info(data: { id: integer }): Promise<UserAddressEntity>;

		/**
		 * 权限标识
		 */
		permission: {
			delete: string;
			update: string;
			page: string;
			list: string;
			add: string;
			info: string;
		};

		/**
		 * 权限状态
		 */
		_permission: {
			delete: boolean;
			update: boolean;
			page: boolean;
			list: boolean;
			add: boolean;
			info: boolean;
		};

		request: Service["request"];
	}

	interface RecycleData {
		/**
		 * 分页
		 */
		page(data?: any): Promise<{
			pagination: { size: number; page: number; total: number; [key: string]: any };
			list: RecycleDataEntity[];
			[key: string]: any;
		}>;

		/**
		 * 新增
		 */
		add(data?: any): Promise<any>;

		/**
		 * 查询
		 */
		list(data?: any): Promise<RecycleDataEntity[]>;

		/**
		 * 信息
		 */
		info(data: { id: integer }): Promise<RecycleDataEntity>;

		/**
		 * 删除
		 */
		delete(data?: any): Promise<any>;

		/**
		 * 修改
		 */
		update(data?: any): Promise<any>;

		/**
		 * 恢复数据
		 */
		restore(data?: any): Promise<any>;

		/**
		 * 权限标识
		 */
		permission: {
			page: string;
			add: string;
			list: string;
			info: string;
			delete: string;
			update: string;
			restore: string;
		};

		/**
		 * 权限状态
		 */
		_permission: {
			page: boolean;
			add: boolean;
			list: boolean;
			info: boolean;
			delete: boolean;
			update: boolean;
			restore: boolean;
		};

		request: Service["request"];
	}

	type Service = {
		/**
		 * 基础请求
		 */
		request(options?: {
			url: string;
			method?: "POST" | "GET" | "PUT" | "DELETE" | "PATCH" | "HEAD" | "OPTIONS";
			data?: any;
			params?: any;
			headers?: {
				authorization?: string;
				[key: string]: any;
			};
			timeout?: number;
			proxy?: boolean;
			[key: string]: any;
		}): Promise<any>;

		cs: { session: CsSession; msg: CsMsg };
		plugin: { info: PluginInfo };
		dict: { info: DictInfo; type: DictType };
		goods: {
			spec: GoodsSpec;
			info: GoodsInfo;
			type: GoodsType;
			comment: GoodsComment;
			searchKeyword: GoodsSearchKeyword;
		};
		base: {
			sys: {
				menu: BaseSysMenu;
				department: BaseSysDepartment;
				role: BaseSysRole;
				param: BaseSysParam;
				log: BaseSysLog;
				user: BaseSysUser;
			};
			open: BaseOpen;
			comm: BaseComm;
		};
		info: { banner: InfoBanner };
		count: { home: CountHome };
		market: { coupon: { info: MarketCouponInfo; user: MarketCouponUser } };
		app: { complain: AppComplain; goods: AppGoods; version: AppVersion; feedback: AppFeedback };
		order: { info: OrderInfo; goods: OrderGoods };
		task: { info: TaskInfo };
		space: { type: SpaceType; info: SpaceInfo };
		user: { info: UserInfo; address: UserAddress };
		recycle: { data: RecycleData };
	};
}
