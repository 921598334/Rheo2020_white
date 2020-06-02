package com.Rheo.Rheo2020.Service;


import com.Rheo.Rheo2020.model.ConferenceTopic;
import com.Rheo.Rheo2020.model.FileInfo;
import com.Rheo.Rheo2020.repository.ConferenceTopicRepository;
import com.Rheo.Rheo2020.repository.FileInfoRepository;
import org.aspectj.weaver.loadtime.definition.Definition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConferenceTopicServer {


    @Autowired
    ConferenceTopicRepository conferenceTopicRepository;


    public ConferenceTopic create(ConferenceTopic conferenceTopic){

        conferenceTopicRepository.save(conferenceTopic);
        return conferenceTopic;
    }


    public List<ConferenceTopic> findAll(){

        return conferenceTopicRepository.findAll();
    }

    public ConferenceTopic findById(Integer id){
        return conferenceTopicRepository.findById(id).get();
    }

}
