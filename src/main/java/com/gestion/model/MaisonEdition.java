package com.gestion.model;

public class MaisonEdition {
    private int id;
    private String nomMaison;

    public MaisonEdition() {}

    public MaisonEdition(int id, String nomMaison) {
        this.id = id;
        this.nomMaison = nomMaison;
    }

    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNomMaison() { return nomMaison; }
    public void setNomMaison(String nomMaison) { this.nomMaison = nomMaison; }
}