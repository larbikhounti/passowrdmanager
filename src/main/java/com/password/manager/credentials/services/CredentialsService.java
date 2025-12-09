package com.password.manager.credentials.services;

import com.password.manager.credentials.base.Entity;
import com.password.manager.credentials.repositories.CredetialsRepository;

import java.sql.SQLException;
import java.util.ArrayList;

public class CredentialsService {
    CredetialsRepository credetialsRepository;
    public CredentialsService() {
        try {
            this.credetialsRepository = new CredetialsRepository();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Entity> getCredentials() throws SQLException {
        return this.credetialsRepository.getAllCredentials();
    }
}
