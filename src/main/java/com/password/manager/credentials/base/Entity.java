package com.password.manager.credentials.base;

import com.password.manager.credentials.enums.CredentialEnum;

import java.util.ArrayList;

public class Entity {
    private static int nextId = 0;
    private final int id;
    private final String url;
    private final String email;
    private final String password;
    private final String title;
    private final String note;
    private final String creditCardNumber;
    private final String creditCardExpiry;
    private final String creditCardCVV;
    private final String creditCardHolderName;
    private final CredentialEnum credentialType;
    static ArrayList<Entity>  credentials = new ArrayList<>();

    public String getcreditCardNumber() {
        return creditCardNumber;
    }

    public String getcreditCardExpiry() {
        return creditCardExpiry;
    }

    public String getcreditCardCVV() {
        return creditCardCVV;
    }

    public String getcreditCardHolderName() {
        return creditCardHolderName;
    }


    public static ArrayList<Entity> getCredentials() {
        return credentials;
    }

    private Entity(Builder builder) {
        this.id = nextId++;
        this.url = builder.url;
        this.email = builder.email;
        this.password = builder.password;
        this.title = builder.title;
        this.note = builder.note;
        this.creditCardNumber = builder.creditCardNumber;
        this.creditCardExpiry = builder.creditCardExpiry;
        this.creditCardCVV = builder.creditCardCVV;
        this.creditCardHolderName = builder.creditCardHolderName;
        this.credentialType = builder.credentialType;


    }

    public CredentialEnum getCredentialType() {
        return credentialType;
    }

    public String getTitle() {
        return title;
    }

    public String getNote() {
        return note;
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
        private  String title;
        private  String note;
        private String creditCardNumber;
        private String creditCardExpiry;
        private String creditCardCVV;
        private String creditCardHolderName;
        private CredentialEnum credentialType;

        public Builder setNumber(String creditCardNumber) {
            this.creditCardNumber = creditCardNumber;
            return this;
        }
        public Builder setExpiry(String creditCardExpiry) {
            this.creditCardExpiry = creditCardExpiry;
            return this;
        }
        public Builder setCVV(String creditCardCVV) {
            this.creditCardCVV = creditCardCVV;
            return this;
        }
        public Builder setHolderName(String creditCardHolderName) {
            this.creditCardHolderName = creditCardHolderName;
            return this;
        }

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

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setNote(String note) {
            this.note = note;
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