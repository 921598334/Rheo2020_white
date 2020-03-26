package com.Rheo.Rheo2020.model;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class FileInfo {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    private String title;
    private String size;
    private String path;

    private Long gmt_modified;


}
