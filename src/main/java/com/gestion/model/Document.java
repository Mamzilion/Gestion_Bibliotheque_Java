package com.gestion.model;

import java.io.Serializable;

/**
 * Modèle Document représentant un livre ou un ouvrage numérique.
 * Implémente Serializable pour permettre le stockage en session.
 */
public class Document implements Serializable {
    private static final long serialVersionUID = 1L;

    private String isbn;
    private String titre;
    private String description;
    private String domaine;
    private String vignette;    // Nom de l'image (ex: 97801.jpg)
    private String urlDocument; // Chemin local du fichier PDF (ex: C:/docs/java.pdf)
    
    // Objets liés (Relations du diagramme de classes)
    private Auteur auteur;
    private MaisonEdition maisonEdition;
    private TypeDocument typeDocument;

    // Constructeur par défaut (Indispensable pour le DAO)
    public Document() {}

    // Constructeur complet
    public Document(String isbn, String titre, String description, String domaine, String vignette,
            String urlDocument, Auteur auteur, MaisonEdition maisonEdition, TypeDocument typeDocument) {
        this.isbn = isbn;
        this.titre = titre;
        this.description = description;
        this.domaine = domaine;
        this.vignette = vignette;
        this.urlDocument = urlDocument;
        this.auteur = auteur;
        this.maisonEdition = maisonEdition;
        this.typeDocument = typeDocument;
    }

    // --- GETTERS & SETTERS ---

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getDomaine() { return domaine; }
    public void setDomaine(String domaine) { this.domaine = domaine; }

    public String getVignette() { return vignette; }
    public void setVignette(String vignette) { this.vignette = vignette; }

    public String getUrlDocument() { return urlDocument; }
    public void setUrlDocument(String urlDocument) { this.urlDocument = urlDocument; }

    public Auteur getAuteur() { return auteur; }
    public void setAuteur(Auteur auteur) { this.auteur = auteur; }

    public MaisonEdition getMaisonEdition() { return maisonEdition; }
    public void setMaisonEdition(MaisonEdition maisonEdition) { this.maisonEdition = maisonEdition; }

    public TypeDocument getTypeDocument() { return typeDocument; }
    public void setTypeDocument(TypeDocument typeDocument) { this.typeDocument = typeDocument; }
}