package com.password.manager.credentials.services.strategies;

import com.password.manager.credentials.base.Entity;
import com.password.manager.credentials.contracts.ICredential;
import com.password.manager.credentials.entities.CreditCard;
import com.password.manager.credentials.repositories.CreditCardRepository;

import java.util.ArrayList;

public class CreditCardStrategy implements ICredential {
    CreditCardRepository creditCardRepository;

    public CreditCardStrategy() {
        this.creditCardRepository = new CreditCardRepository();
    }
    @Override
    public boolean addCredential(Entity credential) {
        return this.creditCardRepository.addCredential(credential);
    }

    @Override
    public boolean editCredential(int id, Entity credential) {
        return this.creditCardRepository.editCredential(id, credential);
    }

    @Override
    public boolean removeCredential(int id) {
        return this.creditCardRepository.removeCredential(id);
    }

    @Override
    public Entity getCredential(int id) {
        return this.creditCardRepository.getCredential(id);
    }

    @Override
    public ArrayList<Entity> getAllCredentials() {
        return this.creditCardRepository.getAllCredentials();
    }
}
