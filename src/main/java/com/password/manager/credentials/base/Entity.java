package com.password.manager.credentials.base;

import com.password.manager.credentials.enums.CredentialEnum;
import com.password.manager.credentials.services.RenderService;

import java.util.ArrayList;

public abstract class Entity {
    private int id;
    public static ArrayList<Entity> credentials = new ArrayList<>();

    public Entity(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public abstract void render(RenderService renderer);
    public abstract void renderMany(RenderService renderer);
}