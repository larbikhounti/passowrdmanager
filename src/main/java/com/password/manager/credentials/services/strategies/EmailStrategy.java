package com.password.manager.credentials.services.strategies;

import com.password.manager.credentials.base.Entity;
import com.password.manager.credentials.contracts.ICredential;
import com.password.manager.credentials.entities.Email;

import java.util.ArrayList;

public class EmailStrategy implements ICredential {


    public EmailStrategy() {
    }

    @Override
    public boolean addCredential(Entity credential) {
        boolean result = Entity.credentials.add(credential);
        for (Entity c : Entity.credentials) {
            if (c == null) continue;
            if (!(c instanceof Email emailEntity)) continue;
            System.out.printf("id is %d email is %s password is %s url is %s \n", c.getId(), emailEntity.getEmail(), emailEntity.getPassword(), emailEntity.getUrl());
        }
        return result;
    }

    @Override
    public boolean editCredential(int id, Entity credential) {
        if (credential != null) {
            for (int i = 0; i < Entity.credentials.size(); i++) {
                Email emailEntity = (Email) Entity.credentials.get(i);
                if (emailEntity.getId() == id) {
                    Entity.credentials.set(i, credential);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean removeCredential(int id) {
        Entity emailCredentialId = this.getCredential(id);
        if (emailCredentialId != null) {
            Entity.credentials.remove(emailCredentialId);
            return true;
        }

        return false;
    }

    @Override
    public Entity getCredential(int id) {
        for (Entity c : Entity.credentials) {
            Email emailEntity = (Email) c;
            if (emailEntity.getId() == id) {
                System.out.printf("Found credential: id is %d email is %s password is %s url is %s \n", c.getId(), emailEntity.getEmail(), emailEntity.getPassword(), emailEntity.getUrl());
                return emailEntity;
            }
        }
        return null;
    }


    public boolean checkPasswordStrength(String password) {
        return false;
    }

    @Override
    public ArrayList<Entity> getAllCredentials() {
        return Entity.credentials;
    }

}
