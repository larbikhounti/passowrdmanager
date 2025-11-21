package com.password.manager.credentials.services;

import com.password.manager.credentials.base.Entity;
import com.password.manager.credentials.contracts.ICredential;
import com.password.manager.credentials.entities.Note;

import java.util.ArrayList;

public class NoteService implements ICredential {

    public NoteService() {
    }
    @Override
    public boolean addCredential(Entity credential) {
        boolean result =  Entity.credentials.add(credential);
        for (Entity c : Entity.credentials) {
            if (c == null) continue;
            if (!( c instanceof Note noteEntity))  continue;
            System.out.printf("id is %d title is %s note %s \n", noteEntity.getId(), noteEntity.getTitle(), noteEntity.getNote());
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
