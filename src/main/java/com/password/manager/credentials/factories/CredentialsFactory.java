package com.password.manager.credentials.factories;


import com.password.manager.credentials.base.Entity;
import com.password.manager.credentials.contracts.ICredential;
import com.password.manager.credentials.enums.CredentialEnum;
import com.password.manager.credentials.services.CreditCardService;
import com.password.manager.credentials.services.EmailService;
import com.password.manager.credentials.services.NoteService;

public class CredentialsFactory {

    public static ICredential getCredentialService(Entity credential) {
        return switch (credential.getCredentialType()) {
            case CredentialEnum.EMAIL -> new EmailService();
            case CredentialEnum.CREDIT_CARD -> new CreditCardService();
            case CredentialEnum.NOTE -> new NoteService();
            default -> null;
        };
    }
}