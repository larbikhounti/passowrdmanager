package com.password.manager.credentials.contracts;

import com.password.manager.credentials.base.Entity;

public interface ICredential {
    public boolean addCredential(Entity credential);

    public boolean editCredential(int id, Entity credential);

    public boolean removeCredential(int id);

    public boolean getCredential(int id);

    public Entity[] getAllCredentials();
}
