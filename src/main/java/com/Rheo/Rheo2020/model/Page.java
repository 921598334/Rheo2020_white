package com.Rheo.Rheo2020.model;


import lombok.Data;

import javax.persistence.*;


@Data
@Entity
public class Page {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public Integer id;
    public String title;

    @Column(columnDefinition ="TEXT")
    public String content;
    //创建时间
    public Long time;

}