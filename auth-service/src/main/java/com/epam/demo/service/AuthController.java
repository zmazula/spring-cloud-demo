package com.epam.demo.service;

import com.epam.demo.model.AuthToken;
import com.epam.demo.repository.CassandraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zmazula on 09/11/16.
 */
@RestController
public class AuthController {

    @Autowired
    private CassandraRepository cassandraRepository;

    @RequestMapping("/authenticate")
    public boolean authenticateUser(@RequestParam( value="username") String username, @RequestParam( value="password") String password) {
        if (username == null || "".equals(username) ){
            return false;
        }
        if (cassandraRepository.exists(username)) {
            AuthToken authToken = cassandraRepository.findOne(username);
            return authToken.getPassword().equals(password) && authToken.isValid();
        }
        return false;
    }

}
