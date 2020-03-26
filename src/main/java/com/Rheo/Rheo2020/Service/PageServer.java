package com.Rheo.Rheo2020.Service;


import com.Rheo.Rheo2020.model.Page;
import com.Rheo.Rheo2020.repository.PageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PageServer {


    @Autowired
    PageRepository pageRepository;

    public List<Page> showAllPage(){
        return pageRepository.findAll();
    }

}
