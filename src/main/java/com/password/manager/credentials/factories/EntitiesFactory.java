package com.password.manager.credentials.factories;

import com.password.manager.credentials.base.Entity;
import com.password.manager.credentials.entities.CreditCard;
import com.password.manager.credentials.entities.Email;
import com.password.manager.credentials.entities.Note;

public class EntitiesFactory {
    private static int idCounter = 0;
    public static Note Note(String title, String note) {
        idCounter += 1;
        return new Note(idCounter, title, note);
    }

    public static Email Email(String url, String email, String password) {
        idCounter += 1;
        return new Email(idCounter, url, email, password);
    }

    public static CreditCard CreditCard(String HolderName, String cardNumber, String expirationDate, String cvv) {
        idCounter += 1;
        return new CreditCard(idCounter, HolderName, cardNumber, expirationDate, cvv);
    }
}
