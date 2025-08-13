# 支付宝支付集成说明

本项目已集成支付宝支付功能，支持以下支付方式：
- 支付宝手机网站支付 (H5)
- 支付宝APP支付
- 支付宝电脑网站支付 (PC)
- 支付宝小程序支付

## 配置步骤

### 1. 注册支付宝开放平台账号

1. 访问 [支付宝开放平台](https://open.alipay.com)
2. 注册开发者账号并完成实名认证
3. 创建应用，获取应用ID (app_id)

### 2. 生成RSA密钥

1. 下载 [支付宝密钥生成工具](https://opendocs.alipay.com/common/02kipl)
2. 生成RSA2（推荐）密钥对
3. 将应用公钥上传到支付宝开放平台
4. 获取支付宝公钥

### 3. 配置应用信息

在 `application.yml` 中配置支付宝相关参数：

```yaml
# 支付宝配置
alipay:
  app-id: 2021000122671234  # 替换为您的支付宝应用ID
  merchant-private-key: MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQ...  # 替换为您的商户私钥
  alipay-public-key: MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA...  # 替换为支付宝公钥
  sandbox: true  # 沙箱环境：true，生产环境：false
  notify-url: https://your-domain.com/app/order/pay/alipayNotify  # 支付结果异步通知地址
  return-url: https://your-domain.com/pay/success  # 支付完成后同步跳转地址
```

### 4. 设置系统参数

在系统参数表中设置以下参数：

| 参数名 | 参数值 | 说明 |
|--------|--------|------|
| alipayNotifyUrl | https://your-domain.com/app/order/pay/alipayNotify | 支付宝异步通知地址 |
| alipayReturnUrl | https://your-domain.com/pay/success | 支付宝同步返回地址 |

## API接口

### 支付接口

| 接口路径 | 请求方式 | 说明 |
|----------|----------|------|
| `/app/order/pay/alipayWapPay` | POST | 支付宝手机网站支付 |
| `/app/order/pay/alipayAppPay` | POST | 支付宝APP支付 |
| `/app/order/pay/alipayPagePay` | POST | 支付宝电脑网站支付 |
| `/app/order/pay/alipayMiniPay` | POST | 支付宝小程序支付 |

### 请求参数

```json
{
  "orderId": 123456
}
```

### 回调接口

| 接口路径 | 请求方式 | 说明 |
|----------|----------|------|
| `/app/order/pay/alipayNotify` | POST | 支付宝支付结果异步通知 |

## 测试流程

### 1. 沙箱环境测试

1. 设置 `alipay.sandbox: true`
2. 使用沙箱环境的应用ID和密钥
3. 使用沙箱买家账号进行测试

### 2. 沙箱买家账号

支付宝提供测试买家账号，可在开放平台控制台查看：
- 买家账号：jfjbwb4477@sandbox.com
- 登录密码：111111
- 支付密码：111111

### 3. 测试步骤

1. 创建订单
2. 调用支付接口
3. 使用沙箱买家账号完成支付
4. 验证支付回调和订单状态更新

## 生产环境部署

### 1. 更新配置

```yaml
alipay:
  sandbox: false  # 切换到生产环境
  notify-url: https://your-production-domain.com/app/order/pay/alipayNotify
  return-url: https://your-production-domain.com/pay/success
```

### 2. 应用上线

1. 在支付宝开放平台提交应用审核
2. 通过审核后上线应用
3. 配置生产环境的公钥和密钥

### 3. 域名配置

确保回调地址域名已在支付宝开放平台配置并通过验证。

## 注意事项

1. **密钥安全**：私钥必须妥善保管，不要提交到代码仓库
2. **回调验签**：系统会自动验证支付宝回调的签名，确保数据安全
3. **订单幂等**：支付回调可能重复推送，系统已做幂等处理
4. **金额单位**：支付金额单位为元，系统会自动处理小数点
5. **网络安全**：生产环境请使用HTTPS协议

## 常见问题

### Q: 支付宝回调验签失败？
A: 检查支付宝公钥是否正确，确保使用的是应用对应的支付宝公钥。

### Q: 沙箱环境支付失败？
A: 确认使用的是沙箱环境的应用ID和密钥，以及正确的沙箱买家账号。

### Q: 生产环境无法收到回调？
A: 检查回调地址是否可公网访问，域名是否已在支付宝开放平台配置。

## 技术支持

如有问题，请参考：
- [支付宝开放平台文档](https://opendocs.alipay.com)
- [支付宝SDK文档](https://github.com/alipay/alipay-sdk-java-all)
