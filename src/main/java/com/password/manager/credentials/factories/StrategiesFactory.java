package com.password.manager.credentials.factories;


import com.password.manager.credentials.base.Entity;
import com.password.manager.credentials.contracts.ICredential;
import com.password.manager.credentials.entities.CreditCard;
import com.password.manager.credentials.entities.Email;
import com.password.manager.credentials.entities.Note;
import com.password.manager.credentials.services.strategies.CreditCardStrategy;
import com.password.manager.credentials.services.strategies.EmailStrategy;
import com.password.manager.credentials.services.strategies.NoteStrategy;

public class StrategiesFactory {
    public static ICredential EmailStrategy() {
        return new EmailStrategy();
    }
    public static ICredential CreditCardStrategy() {
        return new CreditCardStrategy();
    }
    public static ICredential NoteStrategy() {
        return new NoteStrategy();
    }

    public static ICredential getStrategy(Entity credential) {
        if (credential instanceof Email) {
            return EmailStrategy();
        } else if (credential instanceof CreditCard) {
            return CreditCardStrategy();
        } else if (credential instanceof Note) {
            return NoteStrategy();
        }
        throw new IllegalArgumentException("Unknown credential type");
    }
}