package com.Rheo.Rheo2020.Service;

import com.Rheo.Rheo2020.model.User;
import com.Rheo.Rheo2020.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;



    public User findById(Integer id){
        Optional<User> userTmp = userRepository.findById(id);
        return  userTmp.get();
    }


    public User findByTelAndPasswd(String tel,String passwd){
        return  userRepository.findByTelAndPasswd(tel,passwd);
    }

    public User findByEmailAndPasswd(String email,String passwd){
        return  userRepository.findByEmailAndPasswd(email,passwd);
    }


    public User findByTel(String tel){

        return  userRepository.findByTel(tel);

    }

    public User findByEmail(String email){

        return  userRepository.findByEmail(email);

    }


    //在数据库中检查这个用户是否存在，不存在就创建，否则就更新(主要更新token)
    public User createOrUpdate(User user){

        userRepository.save(user);
        return user;

    }




    public User findByToken(String token){
        User userTmp = userRepository.findByToken(token);

        return userTmp;

    }

}
