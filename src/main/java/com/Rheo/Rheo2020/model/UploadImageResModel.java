package com.Rheo.Rheo2020.model;

import lombok.Data;

@Data
public class UploadImageResModel {
    /**
     * 1成功，0失败
     */
    private Integer uploaded;

    //文件名称
    private String fileName;

    //访问路径
    private String url;


}