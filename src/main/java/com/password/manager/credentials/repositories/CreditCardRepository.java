package com.password.manager.credentials.repositories;

import com.password.manager.credentials.base.Entity;
import com.password.manager.credentials.contracts.ICredential;
import com.password.manager.credentials.entities.CreditCard;
import com.password.manager.credentials.entities.Email;
import com.password.manager.credentials.factories.EntitiesFactory;
import com.password.manager.utils.DbConnector;
import com.password.manager.utils.Helpers;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CreditCardRepository implements ICredential {
    Connection connection;

    public CreditCardRepository() {
        try {
            connection = DbConnector.getConnection();
        } catch (SQLException e) {
            Helpers.showAlert("Database Error", "Unable to connect to the database. " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @Override
    public boolean addCredential(Entity credential) {
        if (credential instanceof CreditCard) {
            try {
                Statement stmt =  connection.createStatement();

                String sql = String.format("INSERT INTO credit_cards (card_number " +
                                ",card_expiry " +
                                ",card_cvv, " +
                                "card_holder_name " +
                                ",credential_id) " +
                                "VALUES ('%s', '%s','%s','%s', '%d')",
                        ((CreditCard) credential).getCreditCardNumber(),
                        ((CreditCard) credential).getCreditCardExpiry(),
                        ((CreditCard) credential).getCreditCardCVV(),
                        ((CreditCard) credential).getCreditCardHolderName(),
                        (3)
                );
                stmt.executeQuery(sql);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return false;
        }
        return false;
    }

    @Override
    public boolean editCredential(int id, Entity credential) {
        if (credential instanceof CreditCard) {
            try {
                Statement stmt =  connection.createStatement();
                String sql = String.format("UPDATE credit_cards SET " +
                                "card_number = '%s', " +
                                "card_expiry = '%s', " +
                                "card_cvv = '%s', " +
                                "card_holder_name = '%s' " +
                                "WHERE id = %d",
                        ((CreditCard) credential).getCreditCardNumber(),
                        ((CreditCard) credential).getCreditCardExpiry(),
                        ((CreditCard) credential).getCreditCardCVV(),
                        ((CreditCard) credential).getCreditCardHolderName(),
                        id
                );
                stmt.executeQuery(sql);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return false;
        }
        return false;
    }

    @Override
    public boolean removeCredential(int id) {
        try {
            Statement stmt =  connection.createStatement();
            String sql = String.format("DELETE FROM credit_cards WHERE id = %d", id);
            stmt.executeQuery(sql);
            return true;
        } catch (SQLException e) {
            Helpers.showAlert("Database Error", "Unable to remove credit Card. " + e.getMessage(), Alert.AlertType.ERROR);
            return false;
        }

    }

    @Override
    public Entity getCredential(int id) {
        try {
            Statement stmt =  connection.createStatement();
            String sql = String.format("SELECT * FROM emails WHERE id = %d", id);
            ResultSet resultSet = stmt.executeQuery(sql);
            if (resultSet.next()) {
                CreditCard creditCard = EntitiesFactory.CreditCard();
                creditCard.setId(resultSet.getInt("id"));
                creditCard.setCreditCardNumber(resultSet.getString("card_number"));
                creditCard.setCreditCardExpiry(resultSet.getString("card_expiry"));
                creditCard.setCreditCardCVV(resultSet.getString("card_cvv"));
                creditCard.setCreditCardHolderName(resultSet.getString("card_holder_name"));
                return creditCard;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public ArrayList<Entity> getAllCredentials() {
        ArrayList<Entity> creditCards = new ArrayList<>();
        try {
            Statement stmt =  connection.createStatement();
            String sql = "SELECT * FROM credit_cards";
            ResultSet resultSet = stmt.executeQuery(sql);
            while (resultSet.next()) {
                CreditCard creditCard = EntitiesFactory.CreditCard();
                creditCard.setId(resultSet.getInt("id"));
                creditCard.setCreditCardNumber(resultSet.getString("card_number"));
                creditCard.setCreditCardExpiry(resultSet.getString("card_expiry"));
                creditCard.setCreditCardCVV(resultSet.getString("card_cvv"));
                creditCard.setCreditCardHolderName(resultSet.getString("card_holder_name"));
                creditCards.add(creditCard);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return creditCards;
    }
}
