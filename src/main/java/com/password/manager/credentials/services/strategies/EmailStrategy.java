package com.password.manager.credentials.services.strategies;

import com.password.manager.credentials.base.Entity;
import com.password.manager.credentials.contracts.ICredential;
import com.password.manager.credentials.entities.Email;
import com.password.manager.credentials.repositories.EmailRepository;

import java.util.ArrayList;

public class EmailStrategy implements ICredential {
    EmailRepository emailRepository;

    public EmailStrategy() {
        this.emailRepository = new EmailRepository();
    }

    @Override
    public boolean addCredential(Entity credential) {
        return this.emailRepository.addCredential(credential);
    }

    @Override
    public boolean editCredential(int id, Entity credential) {
        return  this.emailRepository.editCredential(id, credential);
    }

    @Override
    public boolean removeCredential(int id) {
        return this.emailRepository.removeCredential(id);
    }

    @Override
    public Entity getCredential(int id) {
        return this.emailRepository.getCredential(id);
    }


    public boolean checkPasswordStrength(String password) {
        return false;
    }

    @Override
    public ArrayList<Entity> getAllCredentials() {
        return this.emailRepository.getAllCredentials();
    }

}
