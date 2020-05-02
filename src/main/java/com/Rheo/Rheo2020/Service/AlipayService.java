package com.Rheo.Rheo2020.Service;

import com.Rheo.Rheo2020.eunm.TradeStatus;
import com.Rheo.Rheo2020.eunm.PayType;
import com.Rheo.Rheo2020.model.Orders;
import com.Rheo.Rheo2020.model.User;
import com.Rheo.Rheo2020.provider.AlipayConfig;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class AlipayService {


    @Autowired
    OrdersService ordersService;

    @Autowired
    UserService userService;


    //支付
    public void aliPay(HttpServletResponse response, HttpServletRequest request) throws IOException {



        response.setContentType("text/html;charset=utf-8");

        PrintWriter out = response.getWriter();
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
        //设置请求参数
        AlipayTradePagePayRequest aliPayRequest = new AlipayTradePagePayRequest();
        aliPayRequest.setReturnUrl(AlipayConfig.return_url);
        aliPayRequest.setNotifyUrl(AlipayConfig.notify_url);

        //商户订单号，后台可以写一个工具类生成一个订单号，必填

        //订单创建时间
        Long orderCreateTime = System.currentTimeMillis();

        User user = (User) request.getSession().getAttribute("user");

        int randomNum=(int)(Math.random()*900)+100;

        //订单号，创建时间+3位随机数+用户手机
        String order_number = new String(orderCreateTime.toString()+randomNum+user.getTel());

        //付款金额，从前台获取，必填
        String total_amount = new String("100");
        //订单名称，必填
        String subject = new String("用户："+user.getTrue_name()+"的第15届流变学会议注册费");

        aliPayRequest.setBizContent("{\"out_trade_no\":\"" + order_number + "\","
                + "\"total_amount\":\"" + total_amount + "\","
                + "\"subject\":\"" + subject + "\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        //请求
        String result = null;



        try {

            result = alipayClient.pageExecute(aliPayRequest).getBody();

        } catch (AlipayApiException e) {

            System.out.println("支付失败");
            e.printStackTrace();
        }


        System.out.println("生产订单");

        Orders orders = user.getOrders();


        //表明是第一次创建订单
        if(orders == null){

            orders = new Orders();

            orders.setGmtCreate(orderCreateTime);
            //0表示支付宝，1表示微信
            orders.setPayType(PayType.ALIPAY.getType());

            orders.setOrderNo(order_number);
            orders.setTotalAmount(total_amount);
            orders.setTradeStatus(TradeStatus.NOPAY.getType());


            ordersService.createOrUpdate(orders);

            user.setOrders(orders);

            userService.createOrUpdate(user);

        }else {

            orders.setGmtCreate(orderCreateTime);
            //0表示支付宝，1表示微信
            orders.setPayType(PayType.ALIPAY.getType());

            orders.setOrderNo(order_number);
            orders.setTotalAmount(total_amount);
            orders.setTradeStatus(TradeStatus.NOPAY.getType());

            ordersService.createOrUpdate(orders);

        }


        out.println(result);

    }



    //同步回调
    public String returnUrl(HttpServletRequest request) throws UnsupportedEncodingException, AlipayApiException {
        System.out.println("同步通知");
        request.setCharacterEncoding("utf-8");//乱码解决，这段代码在出现乱码时使用
        //获取支付宝GET过来反馈信息
        Map<String,String> params = new HashMap<String,String>();
        Map<String,String[]> requestParams = request.getParameterMap();
        for(String str :requestParams.keySet()){
            String name = str;
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
        }

        boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type); //调用SDK验证签名

        if(signVerified) {
            System.out.println("验签成功-跳转到成功后页面");

            //商户订单号,之前生成的带用户ID的订单号
//            String order_number = params.get("out_trade_no");
//            Orders orders = ordersService.findByOrderNo(order_number);
//            orders.setTradeStatus(TradeStatus.PAY.getType());
//            ordersService.createOrUpdate(orders);

            //跳转至支付成功后的页面,
            return "redirect:/userManager";
        }else {
            System.out.println("验签失败-跳转到充值页面让用户重新充值");
            return "redirect:/pay";
        }


    }





    //异步回调
    public void notifyUrl(HttpServletResponse response,HttpServletRequest request) throws IOException, AlipayApiException {
        System.out.println("异步通知");
        PrintWriter out = response.getWriter();
        request.setCharacterEncoding("utf-8");//乱码解决，这段代码在出现乱码时使用
        //获取支付宝POST过来反馈信息
        Map<String,String> params = new HashMap<String,String>();
        Map<String,String[]> requestParams = request.getParameterMap();
        for(String str :requestParams.keySet()){
            String name = str;
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
        }

        boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type); //调用SDK验证签名

        if(!signVerified) {
            System.out.println("验签失败");
            out.print("fail");
            return;
        }


        //交易状态
        String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");

        //商户订单号,之前生成的带用户ID的订单号
        String order_number = params.get("out_trade_no");
        String trade_no =  params.get("trade_no");



        if(trade_status.equals("TRADE_FINISHED")){
            /*此处可自由发挥*/
            //判断该笔订单是否在商户网站中已经做过处理
            //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
            //如果有做过处理，不执行商户的业务程序
            //注意：
            //退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
        }else if (trade_status.equals("TRADE_SUCCESS")){
            //判断该笔订单是否在商户网站中已经做过处理
            //如果没有做过处理，根据订单号（out_trade_no在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
            //如果有做过处理，不执行商户的业务程序

            Orders orders = ordersService.findByOrderNo(order_number);
            orders.setTradeStatus(TradeStatus.PAYED.getType());
            orders.setGmtCompleted(System.currentTimeMillis());
            orders.setTradeNo(trade_no);
            orders.setTradeStatus(TradeStatus.PAYED.getType());
            ordersService.createOrUpdate(orders);

        }

        out.print("success");
    }



}
