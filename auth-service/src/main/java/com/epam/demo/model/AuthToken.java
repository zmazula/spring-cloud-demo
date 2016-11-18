package com.epam.demo.model;

import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

/**
 * Created by zmazula on 09/11/16.
 */
@Table(value = "users")
public class AuthToken{

    @PrimaryKey
    private String username;

    private String password;
    private boolean isValid;

    public AuthToken(String username, String password, boolean isValid) {
        this.username = username;
        this.password = password;
        this.isValid = isValid;
    }

    public AuthToken() {}

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setValid(boolean valid) {
        this.isValid = valid;
    }


}

