package bean;

import annotation.Id;
import annotation.Table;

@Table(name = "t_livre")
public class Livre2 {
  @Id
  private Integer idLivre = null;
  private String auteur = null;
  private String titre = null;
  private Integer annee = null;

  // obligatoire
  public Livre2() {
  }

  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append(this.titre).append(" ");
    builder.append(this.auteur).append(" ");
    builder.append(this.annee);
    return builder.toString();
  }

  public Livre2(String auteur, String titre, Integer annee) {
    this.annee = annee;
    this.auteur = auteur;
    this.titre = titre;
  }

  // accesseurs (getters / setters)
  public String getAuteur() {
    return auteur;
  }

  public void setAuteur(String auteur) {
    this.auteur = auteur;
  }

  public String getTitre() {
    return titre;
  }

  public void setTitre(String titre) {
    this.titre = titre;
  }

  public Integer getAnnee() {
    return annee;
  }

  public void setAnnee(Integer annee) {
    this.annee = annee;
  }
}
