package com.Rheo.Rheo2020.interceptor;

import com.Rheo.Rheo2020.Service.UserService;
import com.Rheo.Rheo2020.model.User;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

//不加Service的话，Autowired无法识别
@Service
public class LoginInterceptor implements HandlerInterceptor {


    @Autowired
    UserService userService;


    //post请求时会进行了时候有登陆的判断
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {




        //通过cookie得到在数据库中得到用户
        Cookie[] cookies = request.getCookies();

        if(cookies==null || cookies.length==0)
        {

            System.out.println("token不存在，被拦截,返回登陆界面");


            response.sendRedirect( "/login");


            return false;
        }


        for (Cookie cookie : cookies) {
            if(cookie.getName().equals("token")){

                //在数据库中看看有没有token
                User user = userService.findByToken(cookie.getValue()) ;

                if(user == null){
                    System.out.println("没有通过token找到用户，被拦截");


                    response.sendRedirect( "/login");
                    return false;
                }

                //把用户信息存储到seesion中，然后在前段判断这个session中有没有这个用户，如果没有就显示登陆
                request.getSession().setAttribute("user",user);


                System.out.println("在数据库中找到token");


                return  true;
            }

        }

        System.out.println("被拦截");


        response.sendRedirect( "/login");


        return false;


    }




    /*返回客户端数据*/
    private void returnJson(HttpServletResponse response, String json) throws Exception{
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
        try {
            writer = response.getWriter();
            writer.print(json);

        } catch (IOException e) {
        } finally {
            if (writer != null)
                writer.close();
        }
    }



    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
