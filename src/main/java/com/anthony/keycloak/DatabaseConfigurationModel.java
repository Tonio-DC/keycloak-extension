package com.anthony.keycloak;

public class DatabaseConfigurationModel {
//    private final String url;
//    private final String username;
//    private final String password;
    private final String tableFullName;

//    public DatabaseConfigurationModel(String url, String username, String password, String tableFullName){
//        this.url = url;
//        this.username = username;
//        this.password = password;
//        this.tableFullName = tableFullName;
//    }

    public DatabaseConfigurationModel(String tableFullName){
        this.tableFullName = tableFullName;
    }

    public String getTableFullName() {
        return tableFullName;
    }
}
