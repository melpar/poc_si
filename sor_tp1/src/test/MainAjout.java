package test;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import bean.Livre;

public class MainAjout {
  public static void main(String[] args) {
    // ResourceBundle resource = ResourceBundle.getBundle("resources");
    // String url = resource.getString("url");
    // String user = resource.getString("url");
    // String password = resource.getString("url");
    String url = "jdbc:mysql://obiwan2.univ-brest.fr/zfm1-zparlanme";
    String user = "zparlanme";
    String password = "y4alfp4y";

    Livre livre = new Livre();
    livre.setTitre("Salammbô");
    livre.setAuteur("Flaubert");
    livre.setAnnee(1862);

    try {
      Connection co = (Connection) DriverManager.getConnection(url, user, password);

      Statement st = (Statement) co.createStatement();

      // st.executeUpdate(
      // "insert into t_livre (titre, auteur, annee) values ('Salammbô', 'Flaubert',
      // 1862)");

      // V2
      st.executeUpdate("insert into t_livre (titre, auteur, annee) values ('" + livre.getTitre()
          + "', '" + livre.getAuteur() + "', " + livre.getAnnee() + ")");

      st.close();
      co.close();
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
