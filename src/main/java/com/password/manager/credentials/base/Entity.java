package com.password.manager.credentials.base;

public class Entity {


    protected int id;
    protected  String url;
    protected  String email;
    protected  String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
