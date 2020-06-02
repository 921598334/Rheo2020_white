package com.Rheo.Rheo2020.Controller;


import com.Rheo.Rheo2020.Service.ConferenceTopicServer;
import com.Rheo.Rheo2020.Service.PageServer;
import com.Rheo.Rheo2020.model.ConferenceTopic;
import com.Rheo.Rheo2020.model.Page;
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

    @Autowired
    PageServer pageServer;

    @Autowired
    ConferenceTopicServer conferenceTopicServer;

    @GetMapping("/")
    public String index(Model model, HttpServletResponse response, HttpServletRequest request)
    {

        //得到最热门的几篇文章
        List<Page> pages = pageServer.showNewPage(5);


        //得到会议主题
        List<ConferenceTopic> conferenceTopics = conferenceTopicServer.findAll();


        model.addAttribute("topics",conferenceTopics);
        model.addAttribute("pages",pages);


        return "index";
    }

}
