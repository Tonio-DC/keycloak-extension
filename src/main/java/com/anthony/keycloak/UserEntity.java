package com.anthony.keycloak;

public class UserEntity {
    private final Integer id;
    private final String username;
    private String password;

    public UserEntity(String username){
        this(null, username);
    }

    public UserEntity(Integer id, String username){
        this.id = id;
        this.username = username;
    }

    public UserEntity(Integer id, String username, String password){
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public Integer getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }
}
