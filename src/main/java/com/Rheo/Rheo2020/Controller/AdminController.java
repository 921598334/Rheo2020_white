package com.Rheo.Rheo2020.Controller;


import com.Rheo.Rheo2020.Service.ConferenceTopicServer;
import com.Rheo.Rheo2020.Service.PageServer;
import com.Rheo.Rheo2020.Service.UserService;
import com.Rheo.Rheo2020.eunm.UserType;
import com.Rheo.Rheo2020.model.ConferenceTopic;
import com.Rheo.Rheo2020.model.Page;
import com.Rheo.Rheo2020.model.User;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

//这个是主界面
@Controller
public class AdminController {

    @Autowired
    PageServer pageServer;

    @Autowired
    UserService userService;

    @Autowired
    ConferenceTopicServer conferenceTopicServer;

    //显示发布的通知还有所有人上传的论文（这里应该做成分页，不然用户很多的时候会很卡）
    @GetMapping("/admin")
    public String admin(Model model, HttpServletResponse response, HttpServletRequest request)
    {

        //记录当前用户的选择，之后在前端进行显示,最开始显示全部，所以是0
        model.addAttribute("currentTopic",0);

        User user = (User) request.getSession().getAttribute("user");

        //如果是管理员，就可以进入管理员界面
        if(user.getUser_type()== UserType.ADMIN.getType()){
            List<Page> pages = pageServer.showAllPage();
            model.addAttribute("pages",pages);

            //得到后要滤除管理员
            List<User> usersTmp = userService.findAllUser();
            List<User> users = usersTmp.stream().filter(s->s.getUser_type()!=UserType.ADMIN.getType()).collect(Collectors.toList());
            model.addAttribute("users",users);





            //得到所有的会议主题
            List<ConferenceTopic> conferenceTopics = conferenceTopicServer.findAll();
            model.addAttribute("topics",conferenceTopics);


            return "admin/admin";
        }
        //否则无法进入
        else {
            return "redirect:/";
        }



    }




    //论文筛选
    @PostMapping("/filter")
    public String filter(
            HttpServletRequest request,
            Model model,
            @RequestParam(name="topic",defaultValue="") String topic
    ){





        User user = (User) request.getSession().getAttribute("user");

        //如果是管理员，就可以进入管理员界面
        if(user.getUser_type()== UserType.ADMIN.getType()){
            List<Page> pages = pageServer.showAllPage();
            model.addAttribute("pages",pages);

            //得到后要滤除管理员
            List<User> usersTmp = userService.findAllUser();
            List<User> users = usersTmp.stream().filter(s->s.getUser_type()!=UserType.ADMIN.getType()).collect(Collectors.toList());


            List<User> filterUser = null;

            //表示显示全部
            if(topic!=null && topic.equals("")){
                filterUser = users;
                model.addAttribute("currentTopic",0);
            }
            else {
                //根据会议主题得到相关的文章
                filterUser = users.stream().filter(s->s.getFileInfo()!=null && s.getFileInfo().getConferenceTopic()!=null && s.getFileInfo().getConferenceTopic().getId()==Integer.parseInt(topic)).collect(Collectors.toList());

                //记录当前用户的选择，之后在前端进行显示
                model.addAttribute("currentTopic",Integer.parseInt(topic));
            }


            model.addAttribute("users",filterUser);



            //得到所有的会议主题
            List<ConferenceTopic> conferenceTopics = conferenceTopicServer.findAll();
            model.addAttribute("topics",conferenceTopics);


            return "admin/admin";
        }
        //否则无法进入
        else {
            return "redirect:/";
        }


    }

}
