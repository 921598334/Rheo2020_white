package com.Rheo.Rheo2020.provider;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zhaoliancan
 * @description 配置类
 * @create 2019-08-08 18:50
 */


public class AlipayConfig {



    // 作为身份标识的应用ID
    public static String app_id = "2016101800714294";

    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCX0FRdqtaVx0XY3AKTJxNKVdTriRKGXZDNMHzsFvStal5p/lef/F1ot6Iy73QsPgp9Ja3nFVBGpo5iZlJ+sfT0cD1Ooq9DNJRryzuNtaJ9RnVDQx68J75wBRAcsvsQUx7NTaDmcKLLFS/ZuwjktrikSlmyD3/B9u4hBrJ8PGeiiEssmTNXrYvihZNWaEKfcfQ+mzU15P3HuXTL8I3aFz8reY2jvzuoS8FQiayjbqZPHcTIVOr4U3yhVSLpWg3hYooiNLDiFiauXUJqS/3od6XUwzzlsGn0XJegEMxGlkKAAbxbHFNu+e8Y+1z91qzXmelwTfZG51LLEr3LoMZRen6VAgMBAAECggEBAItRnUrET97uG/RSm25Nqs9KC3cHuN9kGFGlkM7cljha6EAfMGzanSJPe2OBCjzbxqZooLnzflMH4Jz+x/ALAgAczLZQZ8SmCRsPTBzVNkP/F/fdLeSAGK0WiF2+BtdJYHgKKIGxlkQGsn3+vb+ey73BmaTlwlnPcWMPkVaf3H9zXER4YYXWpNOlmhFNsyDUIq/8TxNYunyUMozf7lSNXJNIs+4ullHIkgRqK0IvJqIz2nnWOXQ3E9v196ybsTMi6GQ4R2sODDAXlNPnsAB1+Lvxzeqqn+g6xiqS2lZFPhT2+z2u4UXCnBrwT4gz5gnyK4+LoEU0kMCUzW23hj3uDJUCgYEA//UtQZ15yHHQ3EgKGMEu40RA2zwCk31X5hraVX3P+9u5RiUrU0SVwSReUNoCWeKMf+Dt+/9mfUcapP5rw+XLvL/cdm3hOQSVkAHwJTzuqqXNwE3+iWT3It+ggcvrjnmlOxBTJfO8oKhfU5jkLxTZFR/+i7aboZXfalJ9A2IEzIsCgYEAl9a/wD120EfsH5jBwXzOywANbE93hrBSZjzJvgsBcmlU4/xoKDf+H8Bxy/7opA019alcPFDVXWz5lfF9EhHgMft1F+XnrPwZEbWIFbU8XgHo7SD09AniIuxAPUvTWOVmCNaXPp2NQvDQbrbiKYZcXngG64LuTv4A+cKyZLg3pV8CgYBFTMyEB28Bpk82VQrmyOc5ATHOKeFIrZcu5fZXT0kQl90B8O5aDSe+s2oV2fDpBT2lIfFNWUkWgjnkkj37PCx9XjUCm51vIp6b7upmKqkz6tBaUMOpz3xaJNIAUoXkfWsPIsxgai2YEf49VBfz52EfJxp0GWzpwvpKfyr5z4AdIQKBgGz30EstkSwuwZV3cm3inSHwmrGFYllGBwyaSYpaqaIrV6H6jeF3Nagh20k0Wk8JsBTqg2hB9q4CvCfQ1Bp4u7YDKWiKNTbfATrFVbGGjJlGs5wiT8knoqO/eubCmR2uHAjxwD9OoIdO/bbHBw4YAAhICWdRYqdMAPU9tfs0fbLvAoGAbXBFNWZTYVR8QO+xOw5ZCYnuEW70SQGfvV6ZTn2uJv0ACPoi+1EhNZEMBK+pjfpaztuRcWo4sgus/DONYmNv8X8NQFabDW0Md0Mj9o9TR6lFh6I1pph20F5+0jzAXZzRQ4jc3OubntXI6hhMWnuOWfHLs3O1WAJ6Dm+2Mrh0iX0=";

    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAkLAVYD1Poy6UTBavYqUIo+zzXJECuNydiplBG1OhHPaB8zQO9+sHgk2kQgnlDwMIKVI3GaCzrbm8/a7a9g0twLKweMhmYWV8KIBXlVDKsKs58gVhy6jqvYi5Uo9wr+/x9eSYN7shPvCIgkhkmC8WD4vxK3lWCslsLP+UYn1eDWW5GUYfgxAbC101UD2joUxDCkn284VAi8obrF4/W9vuruAVe48tp+DjgjlYln/nZ1AzgvH0a1X/r50xLtJi0Gg+Mnxs3tAOZvOjUMuFysOaOiOzgfDl3Mq4MQjwgS7V8CJGZm9xxwyFQog97VPJODmC0SUjcWqQWYAuXY1j11LLjQIDAQAB";


    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://www.ssclab.cn:5003/pay_notify";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String return_url = "http://www.ssclab.cn:5003/pay_return";

    // 签名方式
    public static String sign_type = "RSA2";

    // 字符编码格式
    public static String charset = "utf-8";

    // 支付宝网关
    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";
}






