package com.Rheo.Rheo2020.repository;


import com.Rheo.Rheo2020.model.FileInfo;
import com.Rheo.Rheo2020.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface UserRepository extends JpaRepository<User, Integer> {




    User findByTel(String tel);
    User findByEmail(String email);

    //这2个用来检查用户是否存在
    User findByTelAndPasswd(String tel,String passwd);
    User findByEmailAndPasswd(String email,String passwd);




    User findByToken(String token);






}
