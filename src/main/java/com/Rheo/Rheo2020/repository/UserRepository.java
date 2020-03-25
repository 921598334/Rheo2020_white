package com.Rheo.Rheo2020.repository;


import com.Rheo.Rheo2020.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {


    //根据用户名手机用户
    User findByTel(String tel);


    User findByName(String name);

    User findByToken(String token);


}
