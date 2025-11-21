package com.password.manager.credentials.entities;

import com.password.manager.credentials.base.Entity;
import com.password.manager.credentials.services.RenderService;

public class Email extends Entity {
    private String url;
    private String email;
    private String password;

    public Email(int id, String url, String email, String password) {
        super(id);
        this.url = url;
        this.email = email;
        this.password = password;
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

    @Override
    public void render(RenderService renderer) {
        renderer.renderEmailCredential(this);
    }

    @Override
    public void renderMany(RenderService renderer) {
        renderer.renderCredentialMany(this.getId(), this.email, this.password, "[Email]");
    }
}
