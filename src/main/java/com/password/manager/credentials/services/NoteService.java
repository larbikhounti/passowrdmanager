package com.password.manager.credentials.services;

import com.password.manager.credentials.base.Entity;
import com.password.manager.credentials.contracts.ICredential;

import java.util.ArrayList;

public class NoteService implements ICredential {
    private final ArrayList<Entity> _credentials;

    public NoteService() {
        _credentials = Entity.getCredentials();
    }
    @Override
    public boolean addCredential(Entity credential) {
        boolean result =  _credentials.add(credential);
        for (Entity c : _credentials) {
            System.out.printf("id is %d title is %s note %s \n", c.getId(), c.getTitle(), c.getNote());
        }
        return result;
    }

    @Override
    public boolean editCredential(int id, Entity credential) {
        return false;
    }

    @Override
    public boolean removeCredential(int id) {
        return false;
    }

    @Override
    public Entity getCredential(int id) {
        return null;
    }

    @Override
    public ArrayList<Entity> getAllCredentials() {
        return null;
    }
}
