package com.isetsf.nejma.domain;

import java.util.Date;

public class UserMobile {
	private String cin;
	private String nom;
	private String prenom;
	private String tel;
	private String adresse;
	private String mail;
	private String type;
	private Date datens;
    private String login;
	private String password;
	private int inscrire;
	private int valide;
	private int point;
	public String getCin() {
		return cin;
	}
	public void setCin(String cin) {
		this.cin = cin;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getDatens() {
		return datens;
	}
	public void setDatens(Date datens) {
		this.datens = datens;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getInscrire() {
		return inscrire;
	}
	public void setInscrire(int inscrire) {
		this.inscrire = inscrire;
	}
	public int getValide() {
		return valide;
	}
	public void setValide(int valide) {
		this.valide = valide;
	}
	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
	}
	
	public UserMobile(Utilisateur u) {
		super();
		this.cin = u.getCin();
		this.nom = u.getNom();
		this.prenom = u.getPrenom();
		this.tel = u.getTel();
		this.adresse = u.getAdresse();
		this.mail = u.getMail();
		this.type = u.getType();
		this.datens = u.getDatens();
		this.login = u.getLogin();
		this.password = u.getPassword();
		this.inscrire = u.getInscrire();
		this.valide = u.getValide();
		this.point = u.getPoint();
	}
	public UserMobile() {

	}
	
	

}
