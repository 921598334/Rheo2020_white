//model中放不用网络传输的类
package com.Rheo.Rheo2020.model;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String passwd;
    private String token;
    private Long gmt_create;
    private Long gmt_modified;
    private String filePath;

    private String tel;
    private String email;
    private String location;
    private String true_name;
    private String organization;
    private String rearch;

}
