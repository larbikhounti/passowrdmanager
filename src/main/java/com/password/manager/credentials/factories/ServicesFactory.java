package com.password.manager.credentials.factories;


import com.password.manager.credentials.contracts.ICredential;
import com.password.manager.credentials.services.CreditCardService;
import com.password.manager.credentials.services.EmailService;
import com.password.manager.credentials.services.NoteService;

public class ServicesFactory {
    public static ICredential EmailService() {
        return new  EmailService();
    }
    public static ICredential CreditCardService() {
        return new  CreditCardService();
    }
    public static ICredential NoteService() {
        return new NoteService();
    }
}