package com.Rheo.Rheo2020.Controller;




import com.Rheo.Rheo2020.model.UploadImageResModel;
import com.Rheo.Rheo2020.provider.FileTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


//上传图片后需要返回的结果
@Controller
public class ImageUploadController {

    @Autowired
    FileTool fileTool;

    private static final Logger logger = LoggerFactory.getLogger(ImageUploadController.class);

    @PostMapping("/uploadImage")
    @ResponseBody
    public UploadImageResModel uploadImage(@RequestParam("upload") MultipartFile multipartFile) {
        UploadImageResModel res = new UploadImageResModel();
        res.setUploaded(0);


        String path = fileTool.uploadFile(multipartFile,"image");



        if (path == null){
            return res;
        }else {
            res.setUploaded(1);

            String[] tmp = path.split("/");

            res.setFileName(tmp[tmp.length-1]);
            res.setUrl(path);
            return res;
        }


    }

}