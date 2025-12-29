package com.password.manager.credentials.repositories;

import com.password.manager.credentials.base.Entity;
import com.password.manager.credentials.contracts.ICredential;
import com.password.manager.credentials.entities.Email;
import com.password.manager.utils.DbConnector;
import com.password.manager.utils.Helpers;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class EmailRepository implements ICredential {
    Connection connection;

    public EmailRepository() {
        try {
            connection = DbConnector.getConnection();
        } catch (SQLException e) {
            Helpers.showAlert("Database Error", "Unable to connect to the database. " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @Override
    public boolean addCredential(Entity credential) {
        if (credential instanceof Email) {
            try {
                Statement stmt =  connection.createStatement();

                String sql = String.format("INSERT INTO emails (url ,email ,password , credential_id) VALUES ('%s', '%s','%s','%d')",
                        ((Email) credential).getUrl(),
                        ((Email) credential).getEmail(),
                        ((Email) credential).getPassword(),
                        (2)
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
        if (credential instanceof Email) {
            try {
                Statement stmt =  connection.createStatement();
                String sql = String.format("UPDATE emails SET url = '%s', email = '%s', password = '%s' WHERE id = %d",
                        ((Email) credential).getUrl(),
                        ((Email) credential).getEmail(),
                        ((Email) credential).getPassword(),
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
            String sql = String.format("DELETE FROM emails WHERE id = %d", id);
            stmt.executeQuery(sql);
            return true;
        } catch (SQLException e) {
            Helpers.showAlert("Database Error", "Unable to remove email. " + e.getMessage(), Alert.AlertType.ERROR);
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
                Email email = new Email();
                email.setId(resultSet.getInt("id"));
                email.setUrl(resultSet.getString("url"));
                email.setEmail(resultSet.getString("email"));
                email.setPassword(resultSet.getString("password"));
                return email;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public ArrayList<Entity> getAllCredentials() {
        ArrayList<Entity> emails = new ArrayList<>();
        try {
            Statement stmt =  connection.createStatement();
            String sql = "SELECT * FROM emails";
            ResultSet resultSet = stmt.executeQuery(sql);
            while (resultSet.next()) {
                Email email = new Email();
                email.setId(resultSet.getInt("id"));
                email.setUrl(resultSet.getString("url"));
                email.setEmail(resultSet.getString("email"));
                email.setPassword(resultSet.getString("password"));
                emails.add(email);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return emails;
    }
}
