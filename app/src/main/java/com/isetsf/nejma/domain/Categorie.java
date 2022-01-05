package com.isetsf.nejma.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.gson.annotations.Expose;


public class Categorie implements Serializable {

private int id;
private String libelle;
private String nom;
private List <Produit> produits;



public List<Produit> getProduits() {
return produits;
}
public void setProduits(List<Produit> produits) {
this.produits = produits;
}

public String getLibelle() {
	return libelle;
}
public void setLibelle(String libelle) {
	this.libelle = libelle;
}

public int getId() {
return id;
}
public void setId(int id) {
this.id = id;
}
public String getNom() {
return nom;
}
public void setNom(String nom) {
this.nom = nom;
}
public Categorie(int id, String libelle, String nom, List<Produit> produits) {
	super();
	this.id = id;
	this.libelle = libelle;
	this.nom = nom;
	this.produits = produits;
}
public Categorie() {
	super();
}


}