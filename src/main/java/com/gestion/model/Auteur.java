package com.gestion.model;

public class Auteur {
	private int id;
	private String nom;
	private String prenom;
	private String nationalite;
	
	
	public Auteur () {}
	
	public Auteur(int id, String nom, String prenom, String nationalite) {
			this.id = id;
			this.nom = nom;
			this.prenom = prenom;
			this.nationalite = nationalite;
	}
	public int getId() {
		return id;
	}
	public void setId(int nouveauId) {
		this.id = nouveauId;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nouveauNom) {
		this.nom = nouveauNom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String nouveauPrenom) {
		this.prenom = nouveauPrenom;
	}
	public String getNationalite() {
		return nationalite;
	}
	public void  setNationalite(String nouveauNationalite) {
		this.nationalite = nouveauNationalite;
	}
}
