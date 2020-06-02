package com.Rheo.Rheo2020.repository;


import com.Rheo.Rheo2020.model.ConferenceTopic;
import com.Rheo.Rheo2020.model.FileInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ConferenceTopicRepository extends JpaRepository<ConferenceTopic, Integer> {





}
