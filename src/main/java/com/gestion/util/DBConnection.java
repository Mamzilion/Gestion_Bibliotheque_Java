package com.gestion.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    // On ne stocke plus la connexion ici pour éviter qu'elle soit partagée et fermée par erreur
    private static final String URL = "jdbc:mysql://localhost:3306/gestionBibliotheque?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String USER = "root";
    private static final String PASSWORD = "mamzi";

    /**
     * Retourne une NOUVELLE connexion à chaque appel.
     * C'est indispensable pour fonctionner avec le try-with-resources du DAO.
     */
    public static Connection getConnection() throws SQLException {
        try {
            // 1. Charger explicitement le driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // 2. Créer et retourner une nouvelle instance de connexion
            return DriverManager.getConnection(URL, USER, PASSWORD);
            
        } catch (ClassNotFoundException e) {
            throw new SQLException("Erreur : Driver MySQL non trouvé - " + e.getMessage());
        }
    }
}