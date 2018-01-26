package base;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import annotation.Id;
import annotation.Table;
import bean.Livre;

public class Base {

  private String config = "resources/config";
  private Connection co;

  public boolean ouvrir() {
    ResourceBundle resource = ResourceBundle.getBundle(config);
    String url = resource.getString("url");
    String user = resource.getString("user");
    String password = resource.getString("password");

    try {
      co = (Connection) DriverManager.getConnection(url, user, password);

    } catch (SQLException e) {
      System.out.println("Erreur à l'ouverture");
      return false;
    }
    return true;

  }

  public void fermer() {
    try {
      co.close();
    } catch (SQLException e) {
      System.out.println("Erreur à la fermeture");
    }
  }

  public List<Livre> listerLivres() {
    List<Livre> ret = new ArrayList<Livre>();
    ResultSet rs;
    Statement st;
    try {
      st = (Statement) co.createStatement();
      try {
        rs = (ResultSet) st.executeQuery("select * from t_livre");
        while (rs.next()) {
          Livre livre = new Livre();
          livre.setTitre(rs.getString("titre"));
          livre.setAuteur(rs.getString("auteur"));
          livre.setAnnee(rs.getInt("annee"));
          ret.add(livre);
        }
        rs.close();
      } catch (SQLException e) {
        System.out.println("Erreur dans listerLivre");
      } finally {
        st.close();
      }
    } catch (SQLException e1) {
      System.out.println("Erreur connexion dans listerLivre");
    }

    return ret;
  }

  // public List<Object> lister(Class c, String query) {
  // List<Object> ret = new ArrayList<Object>();
  // ResultSet rs;
  // Statement st;
  // try {
  // st = (Statement) co.createStatement();
  // try {
  // rs = (ResultSet) st.executeQuery(query);
  // while (rs.next()) {
  //
  // Livre livre = new Livre();
  // livre.setTitre(rs.getString("titre"));
  // livre.setAuteur(rs.getString("auteur"));
  // livre.setAnnee(rs.getInt("annee"));
  // ret.add(livre);
  // }
  // } catch (SQLException e) {
  // System.out.println("Erreur dans lister");
  // } finally {
  // st.close();
  // }
  // } catch (SQLException e1) {
  // System.out.println("Erreur connexion dans lister");
  // }
  // return ret;
  // }

  public int enregistrerLivre(Livre livre) {
    int res = 0;
    try {
      String sql = "insert into t_livre (titre, auteur, annee) values (?, ?, ?)";
      PreparedStatement preparedStatement = co.prepareStatement(sql);
      preparedStatement.setString(1, livre.getTitre());
      preparedStatement.setString(2, livre.getAuteur());
      preparedStatement.setInt(3, livre.getAnnee());
      // exécution de la requête
      res = preparedStatement.executeUpdate();
      preparedStatement.close();
    } catch (SQLException e) {
      System.out.println("Erreur dans enregistrerLivre");
      return res;
    }

    return res;
  }

  public int enregistrer(Object o) {
    int res = 0;
    try {
      Class c = o.getClass();

      // Annotation
      Table annotation = (Table) c.getAnnotation(Table.class);
      System.out.println(annotation.name());

      String sql = "insert into " + annotation.name() + " (";

      Field[] f = c.getDeclaredFields();
      for (int i = 0; i < f.length; i++) {
        Field fi = c.getField(c.getFields()[i].getName());
        Id id = fi.getAnnotation(Id.class);
        if (i > 0)
          sql += ",";
        sql += f[i].getName();
      }
      sql += ") VALUES (";
      for (int i = 0; i < f.length; i++) {
        if (i > 0)
          sql += ",";
        sql += "?";
      }
      sql += ");";

      PreparedStatement preparedStatement = co.prepareStatement(sql);

      for (int i = 0; i < f.length; i++) {
        String nom = f[i].getName();
        String nomGetter = "get" + nom.substring(0, 1).toUpperCase()
            + nom.substring(1).toLowerCase();

        Method m = c.getMethod(nomGetter);
        System.out.println(m.invoke(o));
        preparedStatement.setObject(i + 1, m.invoke(o));
      }

      // exécution de la requête
      res = preparedStatement.executeUpdate();
      preparedStatement.close();
    } catch (Exception e) {
      System.err.println("Erreur Base.enregistrer " + e.getMessage());
    }
    return res;
  }
}
