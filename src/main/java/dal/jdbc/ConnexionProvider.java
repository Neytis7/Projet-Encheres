package dal.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ConnexionProvider {

  private static DataSource datasource;

  static {
    try {
      Context context = new InitialContext();
      datasource = (DataSource) context.lookup("java:comp/env/jdbc/pool_cnx_encheres");
    } catch (NamingException e) {
      e.printStackTrace();
      throw new RuntimeException("Impossible de charger la ressource demandée !");
    }
  }

  public static Connection getConnection() throws SQLException {
    // piocher une connexion dans le pool de connexions d�fini par le context.xml
    return datasource.getConnection();
  }
}
