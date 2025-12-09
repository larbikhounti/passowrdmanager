package com.password.manager.credentials.entities;

import com.password.manager.credentials.base.Entity;
import com.password.manager.credentials.services.RenderService;

public class CreditCard  extends Entity {
    private  String creditCardNumber;
    private  String creditCardExpiry;
    private  String creditCardCVV;
    private  String creditCardHolderName;

    public CreditCard( ) {
        super();
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public String getCreditCardExpiry() {
        return creditCardExpiry;
    }

    public void setCreditCardExpiry(String creditCardExpiry) {
        this.creditCardExpiry = creditCardExpiry;
    }

    public String getCreditCardCVV() {
        return creditCardCVV;
    }

    public void setCreditCardCVV(String creditCardCVV) {
        this.creditCardCVV = creditCardCVV;
    }

    public String getCreditCardHolderName() {
        return creditCardHolderName;
    }

    public void setCreditCardHolderName(String creditCardHolderName) {
        this.creditCardHolderName = creditCardHolderName;
    }

    @Override
    public void render(RenderService renderer) {
        renderer.renderCreditCardCredential(this);
    }

    @Override
    public void renderMany(RenderService renderer) {
        renderer.renderCredentialMany(this.getId(), this.getCreditCardHolderName(), this.creditCardNumber,  "[card]", this);
    }
}
