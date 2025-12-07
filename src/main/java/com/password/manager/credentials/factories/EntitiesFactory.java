package com.password.manager.credentials.factories;

import com.password.manager.credentials.base.Entity;
import com.password.manager.credentials.entities.CreditCard;
import com.password.manager.credentials.entities.Email;
import com.password.manager.credentials.entities.Note;

public class EntitiesFactory {
    public static Note Note() {
        return new Note();
    }

    public static Email Email() {
        return new Email();
    }

    public static CreditCard CreditCard() {
        return new CreditCard();
    }
}
