package com.password.manager.credentials.contracts;

import com.password.manager.credentials.base.Entity;

import java.util.ArrayList;

public interface ICredential {
    boolean addCredential(Entity credential);

    boolean editCredential(int id, Entity credential);

    boolean removeCredential(int id);

    Entity getCredential(int id);

    ArrayList<Entity> getAllCredentials();
}
