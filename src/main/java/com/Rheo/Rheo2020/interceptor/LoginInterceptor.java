//package com.denghanbo.resume.interceptor;
//
//import com.alibaba.fastjson.JSON;
//import com.example.community.advice.CustomizeException;
//import com.example.community.dto.ResultDTO;
//import com.example.community.mapper.UserMapper;
//import com.example.community.model.User;
//import com.example.community.service.NotificationService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.io.PrintWriter;
//
////不加Service的话，Autowired无法识别
//@Service
//public class LoginInterceptor implements HandlerInterceptor {
//
//
//
//    //post请求时会进行了时候有登陆的判断
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//
//
//        boolean isJson = false;
//        //对应comment和question链接发过来的请求是post请求，需要返回post
//        if(request.getRequestURI().contains("comment") || request.getRequestURI().contains("question")){
//            //当前发送过来的是json
//            isJson = true;
//        }
//
//
//
//        //通过cookie得到在数据库中得到用户
//        Cookie[] cookies = request.getCookies();
//
//        if(cookies==null || cookies.length==0)
//        {
//
//            System.out.println("token不存在，被拦截,返回登陆界面");
//
//            if(isJson){
//                //如果收到的是json，也需要返回json
//                returnJson(response,JSON.toJSONString(new ResultDTO(2003,"用户没有登陆，是否需要跳转到登陆界面")));
//            }else {
//                //如果收到的是页面，返回页面即可
//                response.sendRedirect( "/login");
//            }
//
//
//            return false;
//        }
//
//        for (Cookie cookie : cookies) {
//            if(cookie.getName().equals("token")){
//                //在数据库中看看有没有token
//
//                User user = userMapper.findByToken(cookie.getValue()) ;
//
//                if(user == null){
//                    System.out.println("没有通过token找到用户，被拦截");
//
//
//
//                    if(isJson){
//                        //如果收到的是json，也需要返回json
//                        returnJson(response,JSON.toJSONString(new ResultDTO(2003,"用户没有登陆，是否需要跳转到登陆界面")));
//                    }else {
//                        //如果收到的是页面，返回页面即可
//
//
//                        throw new CustomizeException("用户没有登陆");
//                        //response.sendRedirect( "/login");
//                    }
//                    return false;
//                }
//
//                //把用户信息存储到seesion中，然后在前段判断这个session中有没有这个用户，如果没有就显示登陆
//                request.getSession().setAttribute("user",user);
//
//                //寻找到该用户没有读读消息
//                Integer count = notificationService.unReadCount(user.getId());
//                request.getSession().setAttribute("unreadCount",count);
//
//                System.out.println("在数据库中找到token");
//
//
//                return  true;
//            }
//
//        }
//
//        System.out.println("被拦截");
//
//        if(isJson){
//            //如果收到的是json，也需要返回json
//
//            returnJson(response,JSON.toJSONString(new ResultDTO(2003,"用户没有登陆，是否需要跳转到登陆界面")));
//        }else {
//            //如果收到的是页面，返回页面即可
//            response.sendRedirect( "/login");
//        }
//
//
//        return false;
//
//        //return  true;
//    }
//
//
//
//
//    /*返回客户端数据*/
//    private void returnJson(HttpServletResponse response, String json) throws Exception{
//        PrintWriter writer = null;
//        response.setCharacterEncoding("UTF-8");
//        response.setContentType("text/html; charset=utf-8");
//        try {
//            writer = response.getWriter();
//            writer.print(json);
//
//        } catch (IOException e) {
//        } finally {
//            if (writer != null)
//                writer.close();
//        }
//    }
//
//
//
//    @Override
//    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//
//    }
//
//    @Override
//    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//
//    }
//}
