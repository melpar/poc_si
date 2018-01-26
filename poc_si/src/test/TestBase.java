package test;

import base.Base;
import bean.Livre;
import bean.Livre2;

public class TestBase {

  public static void main(String[] args) {
    Livre2 livre = new Livre2();
    livre.setTitre("Salammbô");
    livre.setAuteur("Flaubert");
    livre.setAnnee(1862);

    Base base = new Base();
    base.ouvrir();
    base.enregistrer(livre);
    for (Livre o : base.listerLivres()) {
      System.out.println(o.toString());
    }
    base.fermer();
  }

}