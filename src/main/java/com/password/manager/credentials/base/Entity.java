package com.password.manager.credentials.base;

import com.password.manager.credentials.enums.CredentialEnum;
import com.password.manager.credentials.services.RenderService;

import java.util.ArrayList;

public abstract class Entity {

    public static final String NOTE = "note";
    public static final String EMAIL = "password";
    public static final String CREDIT_CARD = "credit_card";
    private int id;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }


    public abstract void render(RenderService renderer);
    public abstract void renderMany(RenderService renderer);
}