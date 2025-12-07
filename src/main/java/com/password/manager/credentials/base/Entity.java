package com.password.manager.credentials.base;

import com.password.manager.credentials.enums.CredentialEnum;
import com.password.manager.credentials.services.RenderService;

import java.util.ArrayList;

public abstract class Entity {

    public static ArrayList<Entity> credentials = new ArrayList<>();
    public static final int NOTE = 1;
    public static final int EMAIL = 2;
    public static final int CREDIT_CARD = 3;


    public abstract void render(RenderService renderer);
    public abstract void renderMany(RenderService renderer);
}