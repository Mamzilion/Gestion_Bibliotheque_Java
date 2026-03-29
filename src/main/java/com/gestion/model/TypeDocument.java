package com.gestion.model;

public class TypeDocument {
    private int id;
    private String designation;

    public TypeDocument() {}

    public TypeDocument(int id, String designation) {
        this.id = id;
        this.designation = designation;
    }

    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getDesignation() { return designation; }
    public void setDesignation(String designation) { this.designation = designation; }
}