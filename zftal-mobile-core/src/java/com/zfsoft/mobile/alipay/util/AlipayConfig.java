package com.zfsoft.mobile.alipay.util;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *版本：3.3
 *日期：2012-08-10
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
	
 *提示：如何获取安全校验码和合作身份者ID
 *1.用您的签约支付宝账号登录支付宝网站(www.alipay.com)
 *2.点击“商家服务”(https://b.alipay.com/order/myOrder.htm)
 *3.点击“查询合作者身份(PID)”、“查询安全校验码(Key)”

 *安全校验码查看时，输入支付密码后，页面呈灰色的现象，怎么办？
 *解决方法：
 *1、检查浏览器配置，不让浏览器做弹框屏蔽设置
 *2、更换浏览器或电脑，重新登录查询。
 */

public class AlipayConfig {
	
	//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
	// 合作身份者ID，以2088开头由16位纯数字组成的字符串
	public static String partner = "2088521244214177";
	// 商户的私钥
	public static String private_key = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJ8k7ZWc0xVaMEvFCn9HqkmQK08Lw+eSPd83WLGcHzuyniBvB3mHvMr1sb+V7pXXFHac7TAj7CXG+oZIzSvCUEPIRsZ8mb497qxkIWvs+BVX6FsBMxa4HTOppfLBzKTgddoFeTXiPLH9M1lXXOrwfZVm8fUthTN+MdiVv/JO2BFpAgMBAAECgYBttguD5MUwJyi8U25VKJageB8jMz0u24aD56fhaXuusAiK24APrB/e21padYYxDkExNCXf1iPqNy5mfLHUxPSHb7E96NmxENnapsGDTopZv15iJdyJl9uLJPWT3vUPijuXNjcT0oN8y86IRZPl2R3j0czO87U2h6sTHXZ+lHKP8QJBAMqLXMm1oPnkbzKkPeFVy8vcu7n+BQapxjS6rcXIJIbca3TFwZb2MosAQfusog5dgaFNY9lEgfg9v0lr72kTEBsCQQDJJUtQ4+wRjHlu4yM2GTslJWIx4xfg4c+u+34qOdzwqnHunIF/qGoCVFcXWl2GeE5uOIUWTwFGqf3TRA4Ty6TLAkEAsi9OTumpl43eQYf6JF2tL0ieIi8VztnW1BaSv5emgroxEobTG3K13lE4uycRXl/mpbgGqmI5kOEJgpchBKhPlwJAX96Kmy9G4MZXSmav7aTw7691sdlPFDwMRJZRd8bEa2CRlOy7PCEFBb81E0haszC8EQSXSh61uPxkokEZhI3XKQJADGyfeJQIcev44jpkoQuTJRR69dfNTBlDFl+7zkZpZryXWmhKfYr4kMX9blh8QfDVcKeAU9o29eNfKawtr/PQ6g==";
	
	// 支付宝的公钥，无需修改该值
	public static String ali_public_key  = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";

	//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
	

	// 调试用，创建TXT日志文件夹路径
	public static String log_path = "D:\\cxf";

	// 字符编码格式 目前支持 gbk 或 utf-8
	public static String input_charset = "utf-8";
	
	// 签名方式 不需修改
	public static String sign_type = "RSA";

}
