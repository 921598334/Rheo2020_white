package com.Rheo.Rheo2020.Controller;


import com.Rheo.Rheo2020.Service.FileInfoServer;
import com.Rheo.Rheo2020.Service.UserService;
import com.Rheo.Rheo2020.eunm.TradeStatus;
import com.Rheo.Rheo2020.model.FileInfo;
import com.Rheo.Rheo2020.model.Orders;
import com.Rheo.Rheo2020.model.User;
import com.Rheo.Rheo2020.provider.FileTool;
import com.Rheo.Rheo2020.provider.FileType;
import com.sun.xml.bind.v2.runtime.unmarshaller.XsiNilLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

//这个是主界面
@Controller
public class UserManager {

    @Autowired
    UserService userService;
    @Autowired
    FileInfoServer fileInfoServer;


    @Autowired
    FileTool fileTool;



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


        FileInfo fileInfo = user.getFileInfo();

        if(fileInfo== null){
            model.addAttribute("fileName","目前您还没有上传过任何文件");
            return "userManager";
        }

        //得到用户上传的文件的名称
        String filePath = fileInfo.getPath();

        if(filePath== null){
            model.addAttribute("fileName","目前您还没有上传过任何文件");
        }
        else {


            model.addAttribute("fileName",fileInfo.getTitle());

            Double size = Double.parseDouble(fileInfo.getSize())/1000000d;

            String sizeString = String.format("%.2f", size);

            model.addAttribute("size",sizeString+"Mb");
            model.addAttribute("time",fileInfo.getGmt_modified());
            model.addAttribute("filePath",filePath);
        }




        //判断用户是否有缴费
        boolean isPayed = false;
        Orders orders = user.getOrders();
        if(orders !=null && orders.getTradeStatus()!=null && orders.getTradeStatus()==TradeStatus.PAYED.getType()) isPayed = true;
        model.addAttribute("isPayed",isPayed);

        return "userManager";
    }



    //用户更新自己的信息
    @PostMapping("/userManager")
    public String userManagerPost(
            HttpServletRequest request,
            Model model,
            @RequestParam(name="id",defaultValue="") String id,
            @RequestParam(name="true_name",defaultValue="") String true_name,
            @RequestParam(name="tel",defaultValue="") String tel,
            @RequestParam(name="userpwd",defaultValue="") String userpwd,
            @RequestParam(name="userpwd_re",defaultValue="") String userpwd_re,
            @RequestParam(name="email",defaultValue="") String email,
            @RequestParam(name="location",defaultValue="") String location,
            @RequestParam(name="org",defaultValue="") String org,
            @RequestParam(name="school",defaultValue="") String school,
            @RequestParam(name="degree",defaultValue="") String degree,
            @RequestParam(name="type",defaultValue="") String userType,
            @RequestParam(name="rearch",defaultValue="") String rearch,

            @RequestParam(name="file") MultipartFile file

    ) throws IOException {



        //注册的时候用于信息保留的
        User sessionUser = (User) request.getSession().getAttribute("user");

        sessionUser.setTrue_name(true_name);
        sessionUser.setPasswd(userpwd);
        sessionUser.setTel(tel);
        sessionUser.setEmail(email);
        sessionUser.setLocation(location);
        sessionUser.setOrganization(org);
        sessionUser.setSchool(school);
        sessionUser.setDegree(degree);
        //会员类型这里暂时不修改

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


        if(school.equals("")){
            model.addAttribute("error","毕业（所在）院校不能为空");
            return "sign";
        }

        if(degree.equals("")){
            model.addAttribute("error","最高学历不能为空");
            return "sign";
        }



        if(rearch.equals("")){

            model.addAttribute("error","研究方向不能为空");
            return "userManager";
        }



        //在数据库中得到老的user，主要需要老user的id和token,这2信息是不在这被更新的
        User oldUser = userService.findById(Integer.valueOf(id));


        User user = new User();
        user.setId(oldUser.getId());
        user.setToken(oldUser.getToken());
        //暂时无法修改，用默认的
        user.setUser_type(oldUser.getUser_type());
        user.setFileInfo(oldUser.getFileInfo());


        user.setTel(tel);
        user.setEmail(email);
        user.setTrue_name(true_name);
        user.setOrganization(org);
        user.setSchool(school);
        user.setDegree(degree);
        user.setLocation(location);
        user.setPasswd(userpwd);
        Long createTime = System.currentTimeMillis();
        user.setGmt_create(createTime);
        user.setGmt_modified(createTime);
        user.setRearch(rearch);



        //本次上传的文件信息
        FileInfo fileInfo = null;
        //用户之前上传的文件
        FileInfo oldFileInfo = oldUser.getFileInfo();

        //判断用户本次是否有选择文件进行上传
        String selectFileName = file.getOriginalFilename();
        if(!selectFileName.equals("")){

            String[] backTmp = selectFileName.split("\\.");
            if(backTmp.length==1){
                model.addAttribute("error","上传的文件类型不正确（只接受pdf与doc类型的文件）,您当前的文件没有后缀");
                return "userManager";
            }
            String back = backTmp[backTmp.length-1];

            //首先通过后缀名字进行简单判断
            if(!back.equals("doc") &&
                    !back.equals("pdf") &&
                    !back.equals("wps") &&
                    !back.equals("docx") &&
                    !back.equals("dot")&&
                    !back.equals("docm") &&
                    !back.equals("dotm")){

                model.addAttribute("error","上传的文件类型不正确（只接受pdf与doc类型的文件）,您当前的文件类型为："+back);
                return "userManager";
            }



            //在进一步判断文件类型是否为word或者pdf
            InputStream in = file.getInputStream();
            String fileType = FileType.getFileType(in);

            if(!fileType.equals("doc") &&
                    !fileType.equals("pdf") &&
                    !fileType.equals("wps") &&
                    !fileType.equals("docx") &&
                    !fileType.equals("dot")&&
                    !fileType.equals("docm") &&
                    !fileType.equals("dotm")){

                model.addAttribute("error","上传的文件类型不正确（只接受pdf与doc类型的文件）,您当前的文件类型为："+fileType);
                return "userManager";
            }



            //文件的大小是byte，除1000000后为mb
            Long size = file.getSize();
            if(size>10000000){
                model.addAttribute("error","文件大小超过10Mb，请用压缩文档中的图片后再上传，如遇到问题请咨询技术支持");
                return "userManager";
            }





            //文件名称合法到时候
            if(fileTool.stringCheck(selectFileName)){

                //文件上传之之前先删除之前的文件
                if(oldFileInfo != null && oldFileInfo.getPath()!=null ){
                    fileTool.deleteFile( oldFileInfo.getPath());

                    //再进行文件上传
                    String profilePath = fileTool.uploadFile(file,id);
                    fileInfo = new FileInfo();
                    fileInfo.setPath(profilePath);
                    fileInfo.setGmt_modified(System.currentTimeMillis());
                    fileInfo.setSize(size.toString());
                    fileInfo.setTitle(selectFileName);
                    //因为不是第一次上传，所以要把老的文件id设置为到新的文件中
                    fileInfo.setId(oldFileInfo.getId());

                }else {

                    //否则是第一次上传文件
                    String profilePath = fileTool.uploadFile(file,id);
                    fileInfo = new FileInfo();
                    fileInfo.setPath(profilePath);
                    fileInfo.setGmt_modified(System.currentTimeMillis());
                    fileInfo.setSize(size.toString());
                    fileInfo.setTitle(selectFileName);
                    //这里就不需要再设置文件的id了，直接用新id就可以了

                }


                //设置新的文件路径
                user.setFileInfo(fileInfo);

            }
            else {
                //文件名称非法到时候
                model.addAttribute("error","文件名称中包含诸如： _ \\ \" : | * ? < > 等非法支付，请修改文件名称后再次上传");
                return "userManager";
            }


        }else {
            //如果用户没有上传新的文件，那么就用之前用户上传的文件
            fileInfo = oldFileInfo;
        }


        if(fileInfo!=null){
            //更新数据库
            fileInfoServer.createOrUpdate(fileInfo);
        }


        userService.createOrUpdate(user);


        System.out.println("用户更新成功");

        model.addAttribute("info","信息已经更新");

        request.getSession().setAttribute("user",user);

        //然后进入用户管理界面
        return  "redirect:/userManager";


    }




}
