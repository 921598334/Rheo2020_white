package com.Rheo.Rheo2020.Controller;

import com.Rheo.Rheo2020.Service.PageServer;
import com.Rheo.Rheo2020.model.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


@Controller
public class PageController {


    @Autowired
    PageServer pageServer;

    //访问管理界面时首先显示所有的通知
    @GetMapping("/pageAll")
    public String showPageAll(Model model, HttpServletResponse response, HttpServletRequest request)
    {

        List<Page> pages = pageServer.showAllPage();

        model.addAttribute("pages",pages);

        return "admin";
    }


    //添加文章
    @GetMapping("/add")
    public String addPage(Model model, HttpServletResponse response, HttpServletRequest request)
    {


        return "page";
    }


}
