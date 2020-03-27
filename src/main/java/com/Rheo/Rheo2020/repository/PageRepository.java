package com.Rheo.Rheo2020.repository;


import com.Rheo.Rheo2020.model.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//用户创建的通知文章有关
@Repository
public interface PageRepository extends JpaRepository<Page, Integer> {


    List<Page> findAllByOrderByTimeDesc();



}
