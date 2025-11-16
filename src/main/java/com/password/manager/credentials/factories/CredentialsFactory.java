package com.password.manager.credentials.factories;


import com.password.manager.credentials.contracts.ICredential;
import com.password.manager.credentials.services.CreditCardService;
import com.password.manager.credentials.services.EmailService;
import com.password.manager.credentials.services.NoteService;

public class CredentialsFactory {

    public static ICredential getCredentialService(String credentialType) {
        return switch (credentialType) {
            case "EMAIL" -> new EmailService();
            case "CREDITCARD" -> new CreditCardService();
            case "NOTE" -> new NoteService();
            default -> null;
        };
    }
}