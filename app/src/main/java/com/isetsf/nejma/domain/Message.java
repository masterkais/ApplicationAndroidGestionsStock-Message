package com.isetsf.nejma.domain;

import java.util.Date;


public class Message {

	private int id;
	private String objet;
	private String message;
	private String date;
	private String AdrRecepteur;
	private String AdrEmeteur;
	private int valide;
	private String type;
	private String etat;

	private Utilisateur utilisateur;
	public Utilisateur getUtilisateur() {
	return utilisateur;
	}
	public void setUtilisateur(Utilisateur utilisateur) {
	this.utilisateur = utilisateur;
	}

	public Message() {
		super();
	}
	public Message(int id, String objet, String message, String date) {
		super();
		this.id = id;
		this.objet = objet;
		this.message = message;
		this.date = date;
		
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getObjet() {
		return objet;
	}
	public void setObjet(String objet) {
		this.objet = objet;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message2) {
		this.message = message2;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getAdrRecepteur() {
		return AdrRecepteur;
	}
	public void setAdrRecepteur(String adrRecepteur) {
		AdrRecepteur = adrRecepteur;
	}
	public int getValide() {
		return valide;
	}
	public void setValide(int valid) {
		this.valide = valid;
	}
	public String getAdrEmeteur() {
		return AdrEmeteur;
	}
	public void setAdrEmeteur(String adrEmeteur) {
		AdrEmeteur = adrEmeteur;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getEtat() {
		return etat;
	}
	public void setEtat(String etat) {
		this.etat = etat;
	}


}
