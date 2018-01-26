package test;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import bean.Livre;

public class Main {
  public static void main(String[] args) {
    ResourceBundle resource = ResourceBundle.getBundle("resources/resources_fr");
    String url = resource.getString("url");
    String user = resource.getString("user");
    String password = resource.getString("password");
    // String url = "jdbc:mysql://obiwan2.univ-brest.fr/zfm1-zparlanme";
    // String user = "zparlanme";
    // String password = "y4alfp4y";

    try {
      Connection co = (Connection) DriverManager.getConnection(url, user, password);

      Statement st = (Statement) co.createStatement();

      ResultSet rs = (ResultSet) st.executeQuery("select * from t_livre");

      while (rs.next()) {
        // System.out.print(rs.getInt("idLivre") + ": ");
        // System.out.print(rs.getString("titre") + " ");
        // System.out.print(rs.getString("auteur") + " ");
        // System.out.println(rs.getInt("annee"));

        // V2
        Livre livre = new Livre();
        livre.setTitre(rs.getString("titre"));
        livre.setAuteur(rs.getString("auteur"));
        livre.setAnnee(rs.getInt("annee"));
        System.out.println(livre.toString());
      }
      rs.close();
      st.close();
      co.close();
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
