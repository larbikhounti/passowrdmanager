package com.password.manager.credentials.services.strategies;

import com.password.manager.credentials.base.Entity;
import com.password.manager.credentials.contracts.ICredential;

import java.util.ArrayList;

public class wallet implements ICredential {
    @Override
    public boolean addCredential(Entity credential) {

        return false;
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
