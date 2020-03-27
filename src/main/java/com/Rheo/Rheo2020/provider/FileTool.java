package com.Rheo.Rheo2020.provider;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;


//图片或者文件上传的工具类
@Component
public class FileTool {


    //检查文件名称时候合法（ / \ " : | * ? < > _）,false表示非法,true表示合法
    public boolean stringCheck(String fileName){

        //没有匹配到返回true
        boolean isRight = fileName.matches("[^\\s\\\\/:\\*\\?\\\"<>\\|](\\x20|[^\\s\\\\/:\\*\\?\\\"<>\\|])*[^\\s\\\\/:\\*\\?\\\"<>\\|\\.]$");

        if(fileName.contains("_")==false && isRight==true){
            isRight = true;
        }
        else {
            isRight = false;
        }
        return isRight;
    }



    //如果返回null表明上传失败，成功会返回路径，该功能出来用来上传文件外还可以用于图片的上传，区分方法通过userid来区分，如果userid是image，那么就表明是图片上传，需要设置其他路径
    public String uploadFile(MultipartFile multipartFile,String userid ){
        if (multipartFile == null || multipartFile.isEmpty())
            return null;

        //生成新的文件名及存储位置
        String fileName = multipartFile.getOriginalFilename();


        //文件通过时间_用户id_题目来命名
        String newFileName = System.currentTimeMillis()+"_"+userid+"_"+fileName;

        String fullPath = System.getProperty("user.dir")+"/src/main/resources/static/upload/".concat(newFileName);

        System.out.println("上传路径："+fullPath);

        try {
            File target = new File(fullPath);
            if (!target.getParentFile().exists()) { //判断文件父目录是否存在
                target.getParentFile().mkdirs();
            }

            multipartFile.transferTo(target);


            String imgUrl = "/upload/".concat(newFileName);


            return imgUrl;

        } catch (IOException ex) {
            return null;
        }
    }



    public String deleteFile(String path){

        String resultInfo = null;

        int lastIndexOf = path.lastIndexOf("/");

        String fileName = path.substring(lastIndexOf + 1, path.length());

        String fullPath = System.getProperty("user.dir")+"/src/main/resources/static/upload/".concat(fileName);

        File file = new File(fullPath);
        if (file.exists()) {//文件是否存在
            if (file.delete()) {//存在就删了，返回1
                resultInfo =  "1";
                System.out.println("老文件已经删除");
            } else {
                resultInfo =  "0";
                System.out.println("不知道为什么老文件没有删除");
            }
        } else {
            resultInfo = "文件不存在！";
            System.out.println("文件不存在，可以直接上传");
        }
        return resultInfo;

    }
}
