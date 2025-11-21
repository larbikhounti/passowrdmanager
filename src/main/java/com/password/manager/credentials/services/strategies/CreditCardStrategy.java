package com.password.manager.credentials.services.strategies;

import com.password.manager.credentials.base.Entity;
import com.password.manager.credentials.contracts.ICredential;
import com.password.manager.credentials.entities.CreditCard;

import java.util.ArrayList;

public class CreditCardStrategy implements ICredential {

    public CreditCardStrategy() {
    }
    @Override
    public boolean addCredential(Entity credential) {
        boolean result =  Entity.credentials.add(credential);
        for (Entity c : Entity.credentials) {
            if (c == null) continue;
            if (!(c instanceof CreditCard creditCard)) {
                continue;
            }
            System.out.printf("id is %d creditCard is %s creditCardHolderName is %s creditCardExpiry is %s creditCardCVV is %s \n",
                    c.getId(), creditCard.getId(), creditCard.getCreditCardHolderName(), creditCard.getCreditCardExpiry(), creditCard.getCreditCardCVV());
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
