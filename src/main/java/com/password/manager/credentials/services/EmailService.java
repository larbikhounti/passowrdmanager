package com.password.manager.credentials.services;

import com.password.manager.credentials.base.Entity;
import com.password.manager.credentials.contracts.ICredential;

public class EmailService implements ICredential {
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
    public boolean getCredential(int id) {
        return false;
    }

    @Override
    public Entity[] getAllCredentials() {
        return new Entity[0];
    }
}
