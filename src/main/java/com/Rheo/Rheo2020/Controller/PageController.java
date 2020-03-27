package com.Rheo.Rheo2020.Controller;

import com.Rheo.Rheo2020.Service.PageServer;
import com.Rheo.Rheo2020.eunm.UserType;
import com.Rheo.Rheo2020.model.Page;
import com.Rheo.Rheo2020.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


//与文章的添加修改显示有关
@Controller
public class PageController {


    @Autowired
    PageServer pageServer;




    @GetMapping("/add")
    public String addPage(Model model, HttpServletResponse response, HttpServletRequest request)
    {
        //判断是否是管理员在登陆
        User user = (User) request.getSession().getAttribute("user");

        if(user.getUser_type()==UserType.ADMIN.getType()){
            return "admin/addPage";
        }else {
            return "redirect:/";
        }
    }


    //添加文章
    @PostMapping("/add")
    public String addPage(Model model,
                          HttpServletResponse response,
                          HttpServletRequest request,
                          @RequestParam(name="title",defaultValue="") String title,
                          @RequestParam(name="content",defaultValue="") String content)
    {

        //想把用户的输入保存
        Page pageTmp = new Page();
        pageTmp.setTitle(title);
        pageTmp.setContent(content);

        model.addAttribute("addPageTmp",pageTmp);

        //判断是否是管理元在登陆
        User user = (User) request.getSession().getAttribute("user");

        if(user.getUser_type()==UserType.ADMIN.getType()){



            if(title.equals("")){
                model.addAttribute("error","标题不能为空");
                return "admin/addPage";
            }

            if(content.equals("")){
                model.addAttribute("error","内容不能为空");
                return "admin/addPage";
            }



            Page page = new Page();
            page.setTitle(title);
            page.setContent(content);
            page.setTime(System.currentTimeMillis());

            pageServer.createOrUpdate(page);

            return "redirect:/admin";

        }else {
            return "redirect:/";
        }
    }




    //修改文章时，首先把文章呈现出来
    @GetMapping("/modification")
    public String modificationPage(@RequestParam(name="id") Integer id,
                           Model model,
                           HttpServletRequest request
    ) {

        //判断是否是管理元在登陆
        User user = (User) request.getSession().getAttribute("user");

        if(user.getUser_type()==UserType.ADMIN.getType()){

            Page page = pageServer.showPageById(id);

            model.addAttribute("pageTmp",page);

            return "admin/modifityPage";
        }else {
            return "redirect:/";
        }



    }


    //修改文章
    @PostMapping("/modification")
    public String modificationPage(Model model,
                          HttpServletResponse response,
                          HttpServletRequest request,
                          @RequestParam(name="id",defaultValue="") String id,
                          @RequestParam(name="time",defaultValue="") String time,
                          @RequestParam(name="title",defaultValue="") String title,
                          @RequestParam(name="content",defaultValue="") String content)
    {

        //想把用户的输入保存
        Page pageTmp = new Page();
        pageTmp.setId(Integer.valueOf(id));
        pageTmp.setTitle(title);
        pageTmp.setContent(content);
        pageTmp.setTime(Long.valueOf(time));

        model.addAttribute("pageTmp",pageTmp);



        //判断是不是管理员在登陆
        User user = (User) request.getSession().getAttribute("user");

        if(user.getUser_type()==UserType.ADMIN.getType()){

            if(title.equals("")){
                model.addAttribute("error","标题不能为空");
                return "admin/modifityPage";

            }

            if(content.equals("")){
                model.addAttribute("error","内容不能为空");
                return "admin/modifityPage";
            }



            Page page = new Page();
            page.setTitle(title);
            page.setContent(content);
            page.setId(Integer.valueOf(id));
            page.setTime(Long.valueOf(time));

            pageServer.createOrUpdate(page);

            return "redirect:/admin";

        }else {
            return "redirect:/";
        }



    }



    //显示某一文章(这个是不能修改的)
    @GetMapping("/page")
    public String showPage(@RequestParam(name="id") Integer id,
                           Model model,
                           HttpServletRequest request
    ) {

        Page page = pageServer.showPageById(id);

        model.addAttribute("page",page);

        return "showPage";
    }







    @GetMapping("/delete")
    public String deletePage(@RequestParam(name="id") Integer id,
                           Model model,
                           HttpServletRequest request
    ) {


        //判断是不是管理员在登陆
        User user = (User) request.getSession().getAttribute("user");

        if(user.getUser_type()==UserType.ADMIN.getType()){
            //成功删除
            if(pageServer.deletePage(id)){
                model.addAttribute("info","删除成功");
            }else {
                model.addAttribute("error","文章不存在");
            }

            return "redirect:/admin";
        }else{
            return "redirect:/";
        }


    }


}
