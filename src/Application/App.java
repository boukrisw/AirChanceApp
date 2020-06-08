package Application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class App extends Application{
    
    static final String CONN_URL = "...";

    static final String USER = "...";
    static final String PASSWD = "...";

    public static Connection conn; 


  @Override
  public void start(Stage stage) throws Exception {
      try{
          Parent root;
          root = FXMLLoader.load(getClass().getResource("/Vue/Home.fxml"));
          Scene scene = new Scene(root);

          stage.setTitle("Projet BD");
          stage.setScene(scene);
          stage.centerOnScreen();
          stage.show();

      }catch(IOException e){
          e.printStackTrace();
      }
      
/////////////////*Connection!!!

          try {
            // Etablissement de la connection
            System.out.print("Connecting to the database... ");
            conn = DriverManager.getConnection(CONN_URL, USER,PASSWD);
            System.out.println("connected");
            
            // Enregistrement du driver Oracle
            System.out.print("Loading Oracle driver... ");
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            System.out.println("loaded");
            
            conn.setAutoCommit(true);
          } catch (SQLException e) {
            System.err.println("failed");
            System.out.println("Affichage de la pile d'erreur");
            e.printStackTrace(System.err);
            System.out.println("Affichage du message d'erreur");
            System.out.println(e.getMessage());
            System.out.println("Affichage du code d'erreur");
            System.out.println(e.getErrorCode());
          }
  }

  public static void main(String[] args) {
      App.launch(App.class,args);
  }
}
