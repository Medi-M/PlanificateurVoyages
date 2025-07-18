/*package server;

import java.nio.file.*;
import java.sql.*;

public final class Db {
    private static final String DB_FILE = "travel.db";
    private static final String JDBC_URL = "jdbc:sqlite:" + DB_FILE;

    static {
        initIfNeeded();
    }

    private static void initIfNeeded() {
        try (Connection c = DriverManager.getConnection(JDBC_URL)) {
            String schema = new String(
                    Db.class.getResourceAsStream("/schema.sql").readAllBytes()
            );
            try (Statement st = c.createStatement()) {
                st.executeUpdate(schema);
            }
        } catch (Exception e) {
            throw new RuntimeException("DB init failed", e);
        }
    }

    public static Connection get() throws SQLException {
        // Une connexion par thread/handler
        return DriverManager.getConnection(JDBC_URL);
    }
}*/
