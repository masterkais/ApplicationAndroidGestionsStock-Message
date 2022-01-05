package com.isetsf.nejma.domain;

import java.util.Date;

public class CommandeMobile {
	private int id;
	private Date date_commande;
	private Date date_livraison;
	private double mtUnitial;
	private double mtFinal;
	private int transport;
	private int point;
	public CommandeMobile() {
		super();
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getDate_commande() {
		return date_commande;
	}
	public void setDate_commande(Date date_commande) {
		this.date_commande = date_commande;
	}
	public Date getDate_livraison() {
		return date_livraison;
	}
	public void setDate_livraison(Date date_livraison) {
		this.date_livraison = date_livraison;
	}
	public double getMtUnitial() {
		return mtUnitial;
	}
	public void setMtUnitial(double mtUnitial) {
		this.mtUnitial = mtUnitial;
	}
	public double getMtFinal() {
		return mtFinal;
	}
	public void setMtFinal(double mtFinal) {
		this.mtFinal = mtFinal;
	}
	public int getTransport() {
		return transport;
	}
	public void setTransport(int transport) {
		this.transport = transport;
	}
	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
	}

}
