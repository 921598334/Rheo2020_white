package com.Rheo.Rheo2020.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

//这个是主界面
@Controller
public class IndexController {


    int reFreshTime = 0;



    @GetMapping("/")
    public String index(Model model, HttpServletResponse response, HttpServletRequest request)
    {

        //发送邮件成功时给用户的的反馈
        String info = (String) request.getSession().getAttribute("info");
        if(info!=null && !info.equals("")){
            model.addAttribute("info",info);
            request.getSession().removeAttribute("info");
        }


        return "index";
    }

}
