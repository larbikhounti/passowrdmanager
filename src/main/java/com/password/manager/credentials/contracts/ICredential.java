package com.password.manager.credentials.contracts;

import com.password.manager.credentials.base.Entity;

import java.util.ArrayList;

public interface ICredential {
    public boolean addCredential(Entity credential);

    public boolean editCredential(int id, Entity credential);

    public boolean removeCredential(int id);

    public Entity getCredential(int id);

    public ArrayList<Entity> getAllCredentials();
}
