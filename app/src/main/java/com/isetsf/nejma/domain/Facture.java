package com.isetsf.nejma.domain;




import java.util.*;




public  class Facture  
{
	    private  int id;
	    private String date_fact;
	    private int quantite;
	    private  double montant;


		private Utilisateur utilisateur;
		
		
		public Facture() {
			super();
			
			
		}
			
		public int getId() {
			return id;
		}


		public void setId(int id) {
			this.id = id;
		}


		public String getDate_fact() {
			return date_fact;
		}


		public void setDate_fact(String date_fact) {
			this.date_fact = date_fact;
		}



	




		public double getMontant() {
			return montant;
		}


		public void setMontant(double montant) {
			this.montant = montant;
		}

		public int getQuantite() {
			return quantite;
		}


		public void setQuantite(int quantite) {
			this.quantite = quantite;
		}


	

		public Utilisateur getUtilisateur() {
			return utilisateur;
		}

		public void setUtilisateur(Utilisateur utilisateur) {
			this.utilisateur = utilisateur;
		}

		
	

		
} 


