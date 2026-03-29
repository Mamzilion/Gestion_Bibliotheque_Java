package com.gestion.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.gestion.model.*;
import com.gestion.util.DBConnection;

public class DocumentDAO {

    // 1. Lister tous les documents (Dashboard Admin / Accueil par défaut)
    public List<Document> listerDocuments() {
        List<Document> liste = new ArrayList<>();
        String sql = "SELECT d.*, a.nom AS nomAut, m.NomMaison, t.Designation " +
                     "FROM Document d " +
                     "LEFT JOIN auteur a ON d.idAuteur = a.id " +
                     "LEFT JOIN maisonEdition m ON d.idMaison = m.id " +
                     "LEFT JOIN TypeDocument t ON d.idType = t.Id";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                liste.add(mapperDocument(rs));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return liste;
    }

    // 2. Filtrer les documents par domaine
    public List<Document> listerParDomaine(String domaine) {
        List<Document> liste = new ArrayList<>();
        String sql = "SELECT d.*, a.nom AS nomAut, m.NomMaison, t.Designation " +
                     "FROM Document d " +
                     "LEFT JOIN auteur a ON d.idAuteur = a.id " +
                     "LEFT JOIN maisonEdition m ON d.idMaison = m.id " +
                     "LEFT JOIN TypeDocument t ON d.idType = t.Id " +
                     "WHERE d.domaine = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, domaine);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    liste.add(mapperDocument(rs));
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return liste;
    }

    // 3. Trouver par ISBN
    public Document trouverParIsbn(String isbn) {
        Document doc = null;
        String sql = "SELECT d.*, a.nom AS nomAut, m.NomMaison, t.Designation FROM Document d " +
                     "LEFT JOIN auteur a ON d.idAuteur = a.id " +
                     "LEFT JOIN maisonEdition m ON d.idMaison = m.id " +
                     "LEFT JOIN TypeDocument t ON d.idType = t.Id " +
                     "WHERE d.isbn = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, isbn);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    doc = mapperDocument(rs);
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return doc;
    }

    // 4. Ajouter un document (Modifié pour inclure url_document)
    public void ajouterDocument(Document doc) throws Exception {
        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false); 
            try {
                int idAuteur = recupererOuCreerAuteur(conn, doc.getAuteur().getNom());
                int idMaison = recupererOuCreerMaison(conn, doc.getMaisonEdition().getNomMaison());
                
                String sqlDoc = "INSERT INTO Document (isbn, libellet, description, domaine, idAuteur, idMaison, idType, vignette, url_document) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                try (PreparedStatement ps = conn.prepareStatement(sqlDoc)) {
                    ps.setString(1, doc.getIsbn());
                    ps.setString(2, doc.getTitre());
                    ps.setString(3, doc.getDescription());
                    ps.setString(4, doc.getDomaine());
                    ps.setInt(5, idAuteur);
                    ps.setInt(6, idMaison);
                    ps.setInt(7, doc.getTypeDocument().getId()); 
                    ps.setString(8, doc.getVignette());
                    ps.setString(9, doc.getUrlDocument()); // <-- Nouveau champ
                    ps.executeUpdate();
                }
                conn.commit(); 
            } catch (SQLException e) {
                conn.rollback(); 
                throw e;
            }
        } catch (SQLException e) {
            throw new Exception("Erreur lors de l'enregistrement : " + e.getMessage());
        }
    }

    // 5. Modifier un document (Modifié pour inclure url_document)
    public void modifierDocument(Document doc) throws Exception {
        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);
            try {
                int idAuteur = recupererOuCreerAuteur(conn, doc.getAuteur().getNom());
                int idMaison = recupererOuCreerMaison(conn, doc.getMaisonEdition().getNomMaison());
                
                String sql = "UPDATE Document SET libellet=?, description=?, domaine=?, idAuteur=?, idMaison=?, vignette=?, url_document=? WHERE isbn=?";
                
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setString(1, doc.getTitre());
                    ps.setString(2, doc.getDescription());
                    ps.setString(3, doc.getDomaine());
                    ps.setInt(4, idAuteur);
                    ps.setInt(5, idMaison);
                    ps.setString(6, doc.getVignette());
                    ps.setString(7, doc.getUrlDocument()); // <-- Nouveau champ
                    ps.setString(8, doc.getIsbn());
                    
                    ps.executeUpdate();
                }
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new Exception("Erreur lors de la modification : " + e.getMessage());
        }
    }

    // 6. Supprimer un document
    public void supprimerDocument(String isbn) throws Exception {
        String sql = "DELETE FROM Document WHERE isbn = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, isbn);
            int rowsDeleted = ps.executeUpdate();
            if (rowsDeleted == 0) {
                throw new Exception("Aucun document trouvé avec l'ISBN : " + isbn);
            }
        } catch (SQLException e) {
            throw new Exception("Erreur SQL lors de la suppression : " + e.getMessage());
        }
    }

    // --- MÉTHODES PRIVÉES DE GESTION AUTOMATIQUE ---
    private int recupererOuCreerAuteur(Connection conn, String nom) throws SQLException {
        String sqlSelect = "SELECT id FROM auteur WHERE nom = ?";
        try (PreparedStatement ps = conn.prepareStatement(sqlSelect)) {
            ps.setString(1, nom);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt("id");
        }
        String sqlInsert = "INSERT INTO auteur (nom) VALUES (?)";
        try (PreparedStatement ps = conn.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, nom);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);
        }
        return 1;
    }

    private int recupererOuCreerMaison(Connection conn, String nom) throws SQLException {
        String sqlSelect = "SELECT id FROM maisonEdition WHERE NomMaison = ?";
        try (PreparedStatement ps = conn.prepareStatement(sqlSelect)) {
            ps.setString(1, nom);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt("id");
        }
        String sqlInsert = "INSERT INTO maisonEdition (NomMaison) VALUES (?)";
        try (PreparedStatement ps = conn.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, nom);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);
        }
        return 1;
    }

    // MÉTHODE DE MAPPING PRIVÉE (Modifiée pour récupérer url_document)
    private Document mapperDocument(ResultSet rs) throws SQLException {
        Document doc = new Document();
        doc.setIsbn(rs.getString("isbn"));
        doc.setTitre(rs.getString("libellet"));
        doc.setDescription(rs.getString("description"));
        doc.setDomaine(rs.getString("domaine"));
        doc.setVignette(rs.getString("vignette"));
        doc.setUrlDocument(rs.getString("url_document")); // <-- Nouveau : Récupération du chemin PDF
        
        Auteur aut = new Auteur(); 
        aut.setNom(rs.getString("nomAut"));
        doc.setAuteur(aut);
        
        MaisonEdition me = new MaisonEdition(); 
        me.setNomMaison(rs.getString("NomMaison"));
        doc.setMaisonEdition(me);
        
        TypeDocument td = new TypeDocument();
        try {
            td.setDesignation(rs.getString("Designation"));
        } catch (SQLException e) {
            td.setDesignation("Non spécifié");
        }
        doc.setTypeDocument(td);
        return doc;
    }
}