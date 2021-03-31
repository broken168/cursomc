package com.brabos.bahia.cursoSpring.services;

import com.brabos.bahia.cursoSpring.security.UserSS;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserService {

    //return current user
    public static UserSS authenticated(){
        try {
            return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }catch (Exception e){
            return null;
        }

    }
}
