package com.gestion.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.gestion.model.TypeDocument;
import com.gestion.util.DBConnection;

public class TypeDocumentDAO {

    /**
     * MÉTHODE INDISPENSABLE : Utilisée par AdminServlet pour le formulaire d'ajout.
     * Nommée listerTout() pour correspondre à l'appel dans la Servlet.
     */
    public List<TypeDocument> listerTout() {
        List<TypeDocument> liste = new ArrayList<>();
        // On trie par désignation pour que la liste soit propre dans le formulaire
        String sql = "SELECT * FROM TypeDocument ORDER BY Designation ASC";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                TypeDocument td = new TypeDocument();
                // Attention : vérifie bien la casse "Id" ou "id" dans ta base MySQL
                td.setId(rs.getInt("Id")); 
                td.setDesignation(rs.getString("Designation"));
                liste.add(td);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return liste;
    }

    /**
     * AJOUTER UN TYPE : Pour créer de nouvelles catégories (ex: Thèse, Revue)
     */
    public void ajouterType(TypeDocument td) {
        String sql = "INSERT INTO TypeDocument (Designation) VALUES (?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, td.getDesignation());
            ps.executeUpdate();
            System.out.println("Type '" + td.getDesignation() + "' ajouté !");
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Alias pour la compatibilité avec tes autres classes
    public List<TypeDocument> listerTypes() {
        return listerTout();
    }
}