package com.password.manager.credentials.base;

import com.password.manager.credentials.enums.CredentialEnum;

import java.util.ArrayList;

public class Entity {
    private static int nextId = 0;
    private  int id;
    private  String url;
    private  String email;
    private  String password;
    private CredentialEnum credentialType;
    static ArrayList<Entity>  credentials = new ArrayList<Entity>();


    public static ArrayList<Entity> getCredentials() {
        return credentials;
    }




    private Entity(Builder builder) {
        this.id = nextId++;
        this.url = builder.url;
        this.email = builder.email;
        this.password = builder.password;
        this.credentialType = builder.credentialType;
    }

    public CredentialEnum getCredentialType() {
        return credentialType;
    }

    public int getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public static class Builder {
        private String url;
        private String email;
        private String password;
        private CredentialEnum credentialType;

        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder setCredentialType(CredentialEnum credentialType) {
            this.credentialType = credentialType;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Entity build() {
            return new Entity(this);
        }
    }
}