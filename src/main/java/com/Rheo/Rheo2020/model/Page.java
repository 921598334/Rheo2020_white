package com.Rheo.Rheo2020.model;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Data
@Entity
public class Page {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public Integer id;
    public String title;
    public String content;
    //创建时间
    public String time;

}