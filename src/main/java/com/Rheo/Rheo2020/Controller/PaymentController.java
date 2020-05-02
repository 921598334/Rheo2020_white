package com.Rheo.Rheo2020.Controller;

import com.Rheo.Rheo2020.Service.AlipayService;
import com.Rheo.Rheo2020.Service.UserService;
import com.Rheo.Rheo2020.eunm.TradeStatus;
import com.Rheo.Rheo2020.model.Orders;
import com.Rheo.Rheo2020.model.User;

import com.alipay.api.AlipayApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;



/**
 * @author zhaoliancan
 * @description 支付控制类
 * @create 2019-08-08 18:52
 */
@Controller
public class PaymentController {

    @Autowired
    AlipayService alipayService;

    @Autowired
    UserService userService;


    //支付调用
    @RequestMapping("/pay")
    public void payMent(HttpServletResponse response, HttpServletRequest request) throws IOException {


        response.setContentType("text/html;charset=utf-8");

        PrintWriter out = response.getWriter();


        //首先需要判断用户是否完成过支付
        User user = (User) request.getSession().getAttribute("user");

        Orders orders = user.getOrders();

        //表明已经成功支付了
        if(orders!=null && orders.getTradeStatus()== TradeStatus.PAYED.getType()){

            out.print("您已经成功支付过了");

        }else {
            try {
                 alipayService.aliPay(response,request);
            } catch (IOException e) {
                e.printStackTrace();
                out.print("请求支付错误");

            }
        }

    }









    //异步，扣款成功时进行数据库写入
    @RequestMapping(value = "/pay_notify",method = RequestMethod.POST)
    public void notifyUrl(HttpServletResponse response,HttpServletRequest request) throws IOException, AlipayApiException {

        alipayService.notifyUrl(response,request);
    }







    //同步通知,调用接口成功时，进行页面跳转
    @RequestMapping(value = "/pay_return",method = RequestMethod.GET)
    public String returnUrl(HttpServletRequest request) throws UnsupportedEncodingException, AlipayApiException {
        return alipayService.returnUrl(request);
    }





}