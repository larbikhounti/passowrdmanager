package com.password.manager.credentials.services;

import com.password.manager.credentials.base.Entity;
import com.password.manager.credentials.contracts.ICredential;
import com.password.manager.utils.Helpers;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class EmailService implements ICredential {
    private static final ArrayList<Entity> _credentials = new ArrayList<Entity>();
    private static int _id = 0;


    @Override
    public boolean addCredential(Entity credential) {
        credential.setId(++_id);
        _credentials.add(credential);
        for (Entity c : _credentials) {
            System.out.printf("id is %d email is %s password is %s url is %s \n", c.getId(), c.getEmail(), c.getPassword(), c.getUrl());
        }
        return false;
    }

    @Override
    public boolean editCredential(int id, Entity credential) {
     Entity entity = this.getCredential(id);
        if (entity != null) {
            entity.setEmail(credential.getEmail());
            entity.setPassword(credential.getPassword());
            entity.setUrl(credential.getUrl());
            _credentials.set(id, entity);
        }

        return false;
    }

    @Override
    public boolean removeCredential(int id) {
        Entity entity = this.getCredential(id);
        if (entity != null) {
            _credentials.remove(entity);
            return true;
        }

        return false;
    }
    @Override
    public Entity getCredential(int id) {
        for (Entity c : _credentials) {
            if (c.getId() == id) {
                System.out.printf("Found credential: id is %d email is %s password is %s url is %s \n", c.getId(), c.getEmail(), c.getPassword(), c.getUrl());
                return c;
            }
        }
        return null;
    }

    public boolean checkPasswordStrength(String password) {
        return false;
    }

    @Override
    public ArrayList<Entity> getAllCredentials() {
        return _credentials;
    }

}
