package com.gestion.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.gestion.model.Auteur;
import com.gestion.util.DBConnection;

public class AuteurDAO {

    /**
     * MÉTHODE INDISPENSABLE : Utilisée par AdminServlet pour remplir le formulaire
     * Nommée listerTout() pour corriger l'erreur de compilation.
     */
    public List<Auteur> listerTout() {
        List<Auteur> liste = new ArrayList<>();
        String sql = "SELECT * FROM auteur ORDER BY nom ASC";
        
        // Utilisation du try-with-resources pour fermer automatiquement la connexion
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Auteur a = new Auteur();
                a.setId(rs.getInt("id"));
                a.setNom(rs.getString("nom"));
                a.setPrenom(rs.getString("prenom"));
                a.setNationalite(rs.getString("nationalite"));
                liste.add(a); 
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return liste;
    }

    /**
     * AJOUTER UN AUTEUR : Pour étendre ta base de données
     */
    public void ajouterAuteur(Auteur auteur) {
        String sql = "INSERT INTO auteur (nom, prenom, nationalite) VALUES (?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, auteur.getNom());
            ps.setString(2, auteur.getPrenom());
            ps.setString(3, auteur.getNationalite());
            
            ps.executeUpdate();
            System.out.println("Auteur " + auteur.getNom() + " ajouté avec succès !");
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Si tu as besoin de listerAuteurs() ailleurs, tu peux la garder comme alias
    public List<Auteur> listerAuteurs() {
        return listerTout();
    }
}