//model中放不用网络传输的类
package com.Rheo.Rheo2020.model;


import com.Rheo.Rheo2020.eunm.UserType;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;


    private String passwd;
    private String token;
    private Long gmt_create;
    private Long gmt_modified;

    @OneToOne
    private FileInfo fileInfo;

    private String tel;
    private String email;
    private String location;
    private String true_name;
    private String organization;
    private String school;
    private String degree;
    private String rearch;

    private boolean is_pay;
    private Integer user_type;

}
