package com.Rheo.Rheo2020.Controller;


import com.Rheo.Rheo2020.Service.UserService;
import com.Rheo.Rheo2020.eunm.UserType;
import com.Rheo.Rheo2020.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;


//登陆与退出的管理
@Controller
public class LoginController {

    @Autowired
    UserService userService;



    @GetMapping("/login")
    public String login(Model model){

         return  "login";
    }


    @PostMapping("/login")
    public String login( @RequestParam(name="tel_email",defaultValue="") String tel_email,
                         @RequestParam(name="userpwd",defaultValue="") String password,
                         HttpServletRequest request,
                         HttpServletResponse response,
                         Model model){

        if(tel_email.equals("")){

            model.addAttribute("error","请输入注册时的手机或邮箱进行登陆");
            return "login";

        }

        if(password.equals("")){
            System.out.println("输入密码为空");
            model.addAttribute("error","密码不能为空");
            return "login";
        }


        User user = null;


        //尝试通过手机或者邮件去寻找用户
        user = userService.findByTelAndPasswd(tel_email,password);
        if(user==null) user = userService.findByEmailAndPasswd(tel_email,password);



        if(user!=null){

            //随机生成一个token
            String token = UUID.randomUUID().toString();

            //创建时间
            Long time = System.currentTimeMillis();

            user.setToken(token);
            user.setGmt_modified(time);
            //在数据库中检查这个用户是否存在，不存在就创建，否则就更新(主要更新token)
            user = userService.createOrUpdate(user);


            //把用户信息存储到cookie中
            response.addCookie(new Cookie("token",token));


            //把user放入session中，以便能在index中显示,因为拦截器不会拦截login页面，所以需要在这里添加session
            request.getSession().setAttribute("user",user);


            if(user.getUser_type()==UserType.ADMIN.getType()){
                return "redirect:/admin";
            }else {
                return  "redirect:/userManager";
            }




        }else {
            System.out.println("输入用户名或者密码有错误");
            model.addAttribute("error","输入用户名或者密码有错误");



            return "login";



        }


    }



    @GetMapping("/logout")
    public String logout(
            HttpServletRequest request,
            HttpServletResponse response
    ){

        request.getSession().removeAttribute("user");

        //java删除cookie的方法
        Cookie cookie = new Cookie("token",null);
        response.addCookie(cookie);
        cookie.setMaxAge(0);
        //删除session
        request.getSession().removeAttribute("unreadCount");
        request.getSession().removeAttribute("user");


        System.out.println("已经退出登陆");
        return  "redirect:/";
    }


}








