package com.gestion.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.gestion.model.MaisonEdition;
import com.gestion.util.DBConnection;

public class MaisonEditionDAO {

    /**
     * MÉTHODE INDISPENSABLE : Utilisée par AdminServlet pour remplir le formulaire
     * Nommée listerTout() pour corriger l'erreur de compilation (java.lang.Error).
     */
    public List<MaisonEdition> listerTout() {
        List<MaisonEdition> liste = new ArrayList<>();
        String sql = "SELECT * FROM maisonEdition ORDER BY NomMaison ASC";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                MaisonEdition me = new MaisonEdition();
                me.setId(rs.getInt("id"));
                me.setNomMaison(rs.getString("NomMaison"));
                liste.add(me);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return liste;
    }

    /**
     * AJOUTER UNE MAISON : Pour étendre ton catalogue d'éditeurs
     */
    public void ajouterMaison(MaisonEdition me) {
        String sql = "INSERT INTO maisonEdition (NomMaison) VALUES (?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, me.getNomMaison());
            ps.executeUpdate();
            System.out.println("Maison d'édition '" + me.getNomMaison() + "' enregistrée !");
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Alias pour garder la compatibilité avec tes anciens appels
    public List<MaisonEdition> listerMaisons() {
        return listerTout();
    }
}