package com.password.manager.credentials.seeds;

import com.password.manager.utils.DbConnector;
import com.password.manager.utils.Helpers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

public class CredentialSeeder {

    public static void seedCredentialTypes() {
        // Seed initial credentials data here
        try {
            Connection conn =  DbConnector.getConnection();
             Statement stm = conn.createStatement();
            if(isCredentialsTableEmpty()){
                String[] seedData = {
                        "INSERT INTO credential (type) VALUES ('note')",
                        "INSERT INTO credential (type) VALUES ('password')",
                        "INSERT INTO credential (type) VALUES ('credit_card')"};
                for (String sql : seedData) {
                    stm.addBatch(sql);
                }
             int[] result  = stm.executeBatch();
                Helpers.Logger(
                        "Seeded " + Arrays.toString(result) + " credentials into the database.",
                        "info"
                );
            }
        }catch (Exception e){
            Helpers.Logger(
                    "Failed to seed credentials: " + e.getMessage(),
                    "error"
            );
        }

    }
    // check if credentials table is empty
    private static boolean isCredentialsTableEmpty() throws SQLException {
      Connection conn =  DbConnector.getConnection();
      ResultSet res =  conn.createStatement().executeQuery("SELECT * FROM credential");
        return !res.next();
    }
}
