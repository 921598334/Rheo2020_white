package com.Rheo.Rheo2020.Controller;


import com.Rheo.Rheo2020.Service.UserService;
import com.Rheo.Rheo2020.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//这个是主界面
@Controller
public class UserManager {

    @Autowired
    UserService userService;


    //用户自己的管理页面
    @GetMapping("/userManager")
    public String userManager(
                         Model model,
                         HttpServletRequest request
    ){

        //在进入该页面时，拦截器会首先进行判断，如果有用户了，用户信息会被放在session中
        User user = (User)request.getSession().getAttribute("user");

        //如果用户没有登陆，就返回
        if(user == null){
            model.addAttribute("error","用户名没有登陆");
            return "login";
        }




        return "userManager";
    }



    //用户更新自己的信息
    @PostMapping("/userManager")
    public String userManagerPost(
            HttpServletRequest request,
            Model model,
            @RequestParam(name="name",defaultValue="") String name,
            @RequestParam(name="true_name",defaultValue="") String true_name,
            @RequestParam(name="tel",defaultValue="") String tel,
            @RequestParam(name="userpwd",defaultValue="") String userpwd,
            @RequestParam(name="userpwd_re",defaultValue="") String userpwd_re,
            @RequestParam(name="email",defaultValue="") String email,
            @RequestParam(name="location",defaultValue="") String location,
            @RequestParam(name="org",defaultValue="") String org,
            @RequestParam(name="rearch",defaultValue="") String rearch
    ){




        //注册的时候用于信息保留的
        User sessionUser = (User) request.getSession().getAttribute("user");
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
            return "userManager";
        }










        if(userpwd.equals("")){


            model.addAttribute("error","密码不能为空");
            return "userManager";
        }


        if(userpwd_re.equals("")){
            sessionUser.setPasswd("");
            model.addAttribute("error","请输入确认密码");
            return "userManager";
        }


        if(!userpwd_re.equals(userpwd)){
            sessionUser.setPasswd("");
            model.addAttribute("error","2次输入的密码不一致，请确认后重新输入");
            return "userManager";
        }

        sessionUser.setPasswd(userpwd);


        if(tel.equals("")){

            model.addAttribute("error","手机号不能为空");
            return "userManager";
        }



        if(email.equals("")){

            model.addAttribute("error","邮箱不能为空");
            return "userManager";
        }



        if(location.equals("")){

            model.addAttribute("error","居住地不能为空");
            return "userManager";
        }


        if(org.equals("")){

            model.addAttribute("error","所属的组织机构不能为空");
            return "userManager";
        }


        if(rearch.equals("")){

            model.addAttribute("error","研究方向不能为空");
            return "userManager";
        }




        //在数据库中得到老的user，主要需要老user的id和token,这2歌曲信息是不在这被更新的
        User oldUser = userService.findByName(name);



        User user = new User();
        user.setId(oldUser.getId());
        user.setToken(oldUser.getToken());

        user.setName(name);
        user.setTel(tel);
        user.setEmail(email);
        user.setTrue_name(true_name);
        user.setOrganization(org);
        user.setLocation(location);
        user.setPasswd(userpwd);
        Long createTime = System.currentTimeMillis();
        user.setGmt_create(createTime);
        user.setGmt_modified(createTime);
        user.setRearch(rearch);








        userService.createOrUpdate(user);


        System.out.println("用户更新成功");
        model.addAttribute("info","用户信息已经更新");


        request.getSession().setAttribute("user",user);

        //然后进入用户管理界面
        return  "redirect:/userManager";


    }




}
