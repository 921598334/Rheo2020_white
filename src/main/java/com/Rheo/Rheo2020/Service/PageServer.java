package com.Rheo.Rheo2020.Service;


import com.Rheo.Rheo2020.model.Page;
import com.Rheo.Rheo2020.repository.PageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PageServer {


    @Autowired
    PageRepository pageRepository;

    //寻找所有文章，并且按照降序排列
    public List<Page> showAllPage(){
        return pageRepository.findAllByOrderByTimeDesc();

    }




    //寻找最新的文章，并且按照降序排列
    public List<Page> showNewPage(Integer count){


        PageRequest pageable = PageRequest.of(0, count, Sort.Direction.DESC, "time");

        return pageRepository.findAll(pageable).toList();
    }



    public void createOrUpdate(Page page){
        pageRepository.save(page);
    }

    public Page showPageById(Integer id){
        Optional<Page> page = pageRepository.findById(id);
        return  page.get();
    }

    public boolean deletePage(Integer id){

        Page page = showPageById(id);

        if(page== null){
            return  false;
        }else {
            pageRepository.deleteById(id);
            return true;
        }

    }

}
