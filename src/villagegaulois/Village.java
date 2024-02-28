package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum, int tailleMarche) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		marche = new Marche(tailleMarche);
	}
	
	
	private static class Marche {
		private Etal[] etals;
		
		private Marche(int nbEtals){
			etals = new Etal[nbEtals];
			for(int i=0; i<nbEtals; i++) {
				etals[i] = new Etal();
			}
		}
		
		
		private void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit){
			etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}
		
		
		private int trouverEtalLibre(){
			int i = 0;
			while(i<etals.length && etals[i].isEtalOccupe()) {
				i++;
			}
			if(i==etals.length) {
				return(-1);
			} else {
				return(i);
			}
		}
		
		
		private Etal[] trouverEtals(String produit) {
			int dim = 0;
			Etal[] res;
			for(int i = 0; i<etals.length; i++) {
				if(etals[i].isEtalOccupe() && etals[i].contientProduit(produit)) {
					dim++;
				}
			}
			res = new Etal[dim];
			for(int i = 0; i<etals.length; i++) {
				if(etals[i].isEtalOccupe() && etals[i].contientProduit(produit)) {
					res[res.length-dim] = etals[i];
					dim--;
				}
			}
			return(res);
		}
		
		
		private Etal trouverVendeur(Gaulois gaulois) {
			for(int i = 0; i<etals.length; i++) {
				if(etals[i].isEtalOccupe() && etals[i].getVendeur()==gaulois) {
					return(etals[i]);
				}
			}
			return(null);
		}
		
		
		private String afficherMarcher() {
			StringBuilder chaine = new StringBuilder();
			int occ = 0;
			for(int i = 0; i<etals.length; i++) {
				if(etals[i].isEtalOccupe()) {
					occ++;
					chaine.append(etals[i].afficherEtal());
				}
			}
			return chaine.append("Il reste " + (etals.length-occ) + " étals non utilisés dans le marché.\n").toString();
		}
	
	}
	
	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() {
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	
	
	public String installerVendeur(Gaulois vendeur, String produit, int nbProduit) {
		StringBuilder chaine = new StringBuilder();
		chaine.append(vendeur.getNom() + " cherche un endroit pour vendre " + nbProduit + " " + produit + ".\n");
		int e = marche.trouverEtalLibre();
		marche.utiliserEtal(e, vendeur, produit, nbProduit);
		chaine.append("Le vendeur " + vendeur.getNom() + " vend des " + produit + " à l'étale №" + e + ".");
		return chaine.append("\n").toString();
	}
	
	public String rechercherVendeursProduit(String produit) {
		StringBuilder chaine = new StringBuilder();
		chaine.append("Les vendeurs qui proposent des fleurs sont :");
		Etal etals[] = marche.trouverEtals(produit);
		for(int i=0; i<etals.length; i++) {
			chaine.append("\n- " + etals[i].getVendeur().getNom());
		}
		return chaine.append ("\n").toString();
	}
	
	public Etal rechercherEtal(Gaulois vendeur) {
		return(marche.trouverVendeur(vendeur));
	}
	
	public String partirVendeur(Gaulois vendeur) {
		StringBuilder chaine = new StringBuilder();
		Etal e = marche.trouverVendeur(vendeur);
		chaine.append(e.libererEtal());
		return chaine.toString();
	}
	
	public String afficherMarche() {
		return("Le marché du village " + nom + "\n" + marche.afficherMarcher());
	}
	
	
}	


