package com.password.manager.credentials.repositories;

import com.password.manager.credentials.base.Entity;
import com.password.manager.credentials.entities.CreditCard;
import com.password.manager.credentials.entities.Email;
import com.password.manager.credentials.entities.Note;
import com.password.manager.credentials.factories.EntitiesFactory;
import com.password.manager.utils.DbConnector;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static com.password.manager.credentials.base.Entity.*;

public class CredetialsRepository {
    Connection connection;
    public CredetialsRepository() throws SQLException {
        this.connection = DbConnector.getConnection();
    }

    public ArrayList<Entity> getAllCredentials() throws  SQLException {
        ArrayList<Entity> credentials = new ArrayList<>();
        try {
            Statement stm = this.connection.createStatement();
            String sql = """
                            SELECT
                              n.id AS note_id,
                              n.title,
                              n.note,
                              n.credential_id AS note_credential_id,
                              e.id AS email_id,
                              e.email,
                              e.url,
                              e.password,
                              e.credential_id AS email_credential_id,
                              c.id AS credit_id,
                              c.card_holder_name,
                              c.card_number,
                              c.card_cvv,
                              c.card_expiry,
                              c.credential_id AS credit_credential_id,
                              cred.id AS credential_id,
                              cred.type AS credential_type
                            FROM credential cred
                            LEFT JOIN notes n ON n.credential_id = cred.id
                            LEFT JOIN emails e ON e.credential_id = cred.id
                            LEFT JOIN credit_cards c ON c.credential_id = cred.id
                            
                            """;
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                switch (rs.getInt("credential_id")) {
                    case NOTE -> {
                        // create Note entity
                        Note note = EntitiesFactory.Note();
                        note.setId(rs.getInt("note_id"));
                        note.setTitle(rs.getString("title"));
                        note.setNote(rs.getString("note"));
                        credentials.add(note);
                    }
                    case EMAIL -> {
                        Email email = EntitiesFactory.Email();
                        email.setId(rs.getInt("email_id"));
                        email.setEmail(rs.getString("email"));
                        email.setUrl(rs.getString("url"));
                        email.setPassword(rs.getString("password"));
                        credentials.add(email);
                    }
                    case CREDIT_CARD -> {
                        CreditCard creditCard = EntitiesFactory.CreditCard();
                        creditCard.setId(rs.getInt("credit_id"));
                        creditCard.setCreditCardHolderName(rs.getString("card_holder_name"));
                        creditCard.setCreditCardNumber(rs.getString("card_number"));
                        creditCard.setCreditCardExpiry(rs.getString("card_expiry"));
                        creditCard.setCreditCardCVV(rs.getString("card_cvv"));
                        credentials.add(creditCard);
                    }
                    default -> {
                    }
                }

            }
            // loop and print all credentials with the id
            for (Entity credential : credentials) {
                System.out.println("Credential ID: " + credential.getId());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return credentials;
    }
}
