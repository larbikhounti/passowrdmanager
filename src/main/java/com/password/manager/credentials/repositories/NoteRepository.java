package com.password.manager.credentials.repositories;

import com.password.manager.credentials.base.Entity;
import com.password.manager.credentials.contracts.ICredential;
import com.password.manager.credentials.entities.Note;
import com.password.manager.utils.DbConnector;
import com.password.manager.utils.Helpers;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class NoteRepository implements ICredential {
    Connection connection;
    public NoteRepository() {
        try {
            connection = DbConnector.getConnection();
        } catch (SQLException e) {
            Helpers.showAlert("Database Error", "Unable to connect to the database. " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    @Override
    public boolean addCredential(Entity credential) {
        if (credential instanceof Note) {
            try {
                Statement stmt =  connection.createStatement();

                String sql = String.format("INSERT INTO notes (title, note, credential_id) VALUES ('%s', '%s','%d')",
                        ((Note) credential).getTitle(),
                        ((Note) credential).getNote(),
                        (1)
                );
                stmt.executeQuery(sql);
            } catch (SQLException e) {
                Helpers.showAlert("Database Error", "Unable to add note. " + e.getMessage(), Alert.AlertType.ERROR);
            }
            return false;
        }
        return false;
    }

    @Override
    public boolean editCredential(int id, Entity credential) {
        if (credential instanceof Note) {
            try {
                Statement stmt =  connection.createStatement();
                String sql = String.format("UPDATE notes SET title = '%s', note = '%s' WHERE id = %d",
                        ((Note) credential).getTitle(),
                        ((Note) credential).getNote(),
                        id
                );
                stmt.executeQuery(sql);
            } catch (SQLException e) {
                Helpers.showAlert("Database Error", "Unable to edit note. " + e.getMessage(), Alert.AlertType.ERROR);
            }
            return false;
        }
        return false;
    }

    @Override
    public boolean removeCredential(int id) {
        try {
            Statement stmt =  connection.createStatement();
            String sql = String.format("DELETE FROM notes WHERE id = %d", id);
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
            String sql = String.format("SELECT * FROM notes WHERE id = %d", id);
            var resultSet = stmt.executeQuery(sql);
            if (resultSet.next()) {
                Note note = new Note();
                note.setId(resultSet.getInt("id"));
                note.setTitle(resultSet.getString("title"));
                note.setNote(resultSet.getString("note"));
                return note;
            }
        } catch (SQLException e) {
            Helpers.showAlert("Database Error", "Unable to get note. " + e.getMessage(), Alert.AlertType.ERROR);
        }
        return null;
    }

    @Override
    public ArrayList<Entity> getAllCredentials() {
        ArrayList<Entity> notes = new ArrayList<>();
        try {
            Statement stmt =  connection.createStatement();
            String sql = "SELECT * FROM notes";
            var resultSet = stmt.executeQuery(sql);

            while (resultSet.next()) {
                Note note = new Note();
                note.setId(resultSet.getInt("id"));
                note.setTitle(resultSet.getString("title"));
                note.setNote(resultSet.getString("note"));
                notes.add(note);
            }
        } catch (SQLException e) {
            Helpers.showAlert("Database Error", "Unable to get notes. " + e.getMessage(), Alert.AlertType.ERROR);
        }
        return notes;
    }
}
