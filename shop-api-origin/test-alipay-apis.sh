#!/bin/bash

# 支付宝支付接口测试脚本
# 使用前请确保：
# 1. 后端服务已启动
# 2. 已配置正确的支付宝参数
# 3. 已创建测试订单

# 配置
BASE_URL="http://localhost:8001"
ORDER_ID=1  # 替换为实际的订单ID

echo "=== 支付宝支付接口测试 ==="
echo

# 测试支付宝手机网站支付
echo "1. 测试支付宝手机网站支付"
curl -X POST \
  -H "Content-Type: application/json" \
  -d "{\"orderId\": $ORDER_ID}" \
  "$BASE_URL/app/order/pay/alipayWapPay" | jq
echo

# 测试支付宝APP支付
echo "2. 测试支付宝APP支付"
curl -X POST \
  -H "Content-Type: application/json" \
  -d "{\"orderId\": $ORDER_ID}" \
  "$BASE_URL/app/order/pay/alipayAppPay" | jq
echo

# 测试支付宝电脑网站支付
echo "3. 测试支付宝电脑网站支付"
curl -X POST \
  -H "Content-Type: application/json" \
  -d "{\"orderId\": $ORDER_ID}" \
  "$BASE_URL/app/order/pay/alipayPagePay" | jq
echo

# 测试支付宝小程序支付
echo "4. 测试支付宝小程序支付"
curl -X POST \
  -H "Content-Type: application/json" \
  -d "{\"orderId\": $ORDER_ID}" \
  "$BASE_URL/app/order/pay/alipayMiniPay" | jq
echo

echo "=== 测试完成 ==="
echo "注意：测试前请确保已正确配置支付宝参数并创建有效订单"
