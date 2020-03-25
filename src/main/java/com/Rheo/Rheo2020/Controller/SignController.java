package com.Rheo.Rheo2020.Controller;



import com.Rheo.Rheo2020.Service.UserService;
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


@Controller
public class SignController {


    @Autowired
    UserService userService;


    @GetMapping("/sign")
    public String sign(HttpServletRequest request){


        //这个regUser是在注册时用于信息保留的，如果输入错误那么可以回复
        User sessionUser = new User();
        request.getSession().setAttribute("regUser",sessionUser);


        return "sign";
    }


    @PostMapping("/sign")
    public String sign(HttpServletRequest request,
                       Model model,
                       HttpServletResponse response,
                       @RequestParam(name="name",defaultValue="") String name,
                       @RequestParam(name="true_name",defaultValue="") String true_name,
                       @RequestParam(name="tel",defaultValue="") String tel,
                       @RequestParam(name="userpwd",defaultValue="") String userpwd,
                       @RequestParam(name="userpwd_re",defaultValue="") String userpwd_re,
                       @RequestParam(name="email",defaultValue="") String email,
                       @RequestParam(name="location",defaultValue="") String location,
                       @RequestParam(name="org",defaultValue="") String org,
                       @RequestParam(name="rearch",defaultValue="") String rearch)
    {





        User sessionUser = (User) request.getSession().getAttribute("regUser");
        sessionUser.setName(name);
        sessionUser.setTrue_name(true_name);
        sessionUser.setPasswd(userpwd);
        sessionUser.setTel(tel);
        sessionUser.setEmail(email);
        sessionUser.setLocation(location);
        sessionUser.setOrganization(org);
        sessionUser.setRearch(rearch);


        if(true_name.equals("")){


            model.addAttribute("error","真实姓名不能为空");
            return "sign";

        }




        if(name.equals("")){

            model.addAttribute("error","登陆用的用户名不能为空");
            return "sign";

        }



        if(userpwd.equals("")){

            model.addAttribute("error","密码不能为空");
            return "sign";
        }

        if(userpwd_re.equals("")){
            sessionUser.setPasswd("");
            model.addAttribute("error","请输入确认密码");
            return "sign";
        }



        if(!userpwd_re.equals(userpwd)){
            sessionUser.setPasswd("");
            model.addAttribute("error","2次输入的密码不一致，请确认后重新输入");
            return "sign";
        }




        if(tel.equals("")){

            model.addAttribute("error","手机号不能为空");
            return "sign";
        }



        if(email.equals("")){

            model.addAttribute("error","邮箱不能为空");
            return "sign";
        }




        if(location.equals("")){

            model.addAttribute("error","居住地不能为空");
            return "sign";
        }



        if(org.equals("")){

            model.addAttribute("error","所属的组织机构不能为空");
            return "sign";
        }



        if(rearch.equals("")){

            model.addAttribute("error","研究方向不能为空");
            return "sign";
        }








        if(userService.findByName(name)!=null){

            model.addAttribute("error","该用户名已经被注册过了，如果出现无法解决的问题，请联系技术支持");
            return "sign";
        }




        User user = new User();
        user.setName(name);
        user.setTel(tel);
        user.setEmail(email);
        user.setTrue_name(true_name);
        user.setOrganization(org);
        user.setPasswd(userpwd);
        user.setLocation(location);
        Long createTime = System.currentTimeMillis();
        user.setGmt_create(createTime);
        user.setGmt_modified(createTime);
        user.setRearch(rearch);




        //随机生成一个token
        String token = UUID.randomUUID().toString();

        //token创建时间
        Long tokenTime = System.currentTimeMillis();

        user.setToken(token);
        user.setGmt_modified(tokenTime);


        userService.createOrUpdate(user);
        System.out.println("创建用户成功");
        model.addAttribute("info","创建用户成功");


        //把用户信息存储到cookie中
        response.addCookie(new Cookie("token",token));


        //把user放入session中，以便能在index中显示,因为拦截器不会拦截login页面，所以需要在这里添加session
        request.getSession().setAttribute("user",user);

        return  "redirect:/userManager";



    }



}