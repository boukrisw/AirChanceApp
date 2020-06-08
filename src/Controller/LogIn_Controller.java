package Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


import static Application.App.conn;
import Modele.AfficherRequetes;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * FXML Controller class
 *
 * @author walid
 */
public class LogIn_Controller implements Initializable {

    @FXML
    private AnchorPane rootPane;
    @FXML
    private Button Register_Disable;
    @FXML
    private Button Login_Disable;
    @FXML
    private AnchorPane PaneLogIn;
    @FXML
    private TextField LogIn_prenom;
    @FXML
    private Button Login_btn;
    @FXML
    private TextField LogIn_nom;
    @FXML
    private TextField LogIn_numPassport;
    @FXML
    private TextField Register_prenom;
    @FXML
    private Button Register_btn;
    @FXML
    private TextField Register_CodePostale;
    @FXML
    private TextField Register_Rue;
    @FXML
    private TextField Register_NumAdresse;
    @FXML
    private TextField Register_ville;
    @FXML
    private TextField Register_NumPassport;
    @FXML
    private TextField Register_nom;
    @FXML
    private AnchorPane PaneSignUp;
    @FXML
    private Button quitter_btn;
    @FXML
    private Button return_btn;
    @FXML
    private TextField Register_pays;
    
    public static int numClient; 

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        AfficherRequetes.afficher_LesClients();
    }

    @FXML
    private void Register_Disable_click(ActionEvent event) {
        PaneSignUp.setVisible(true);
        PaneLogIn.setVisible(false);
    }

    @FXML
    private void Login_Disable_click(ActionEvent event) {
        PaneLogIn.setVisible(true);
        PaneSignUp.setVisible(false);
    }

    @FXML
    private void Login_click(ActionEvent event) throws IOException {
        
        String prenom = LogIn_prenom.getText();
        String nom = LogIn_nom.getText();
        String numPassport = LogIn_numPassport.getText();
        
        Statement requete;
        try {
            requete = conn.createStatement();

            ResultSet resultat = requete.executeQuery( "SELECT * FROM LesClients_Projet where nomClient='"+nom+"' and prenomClient='"+prenom+"' and numPassport='"+numPassport+"'");
            if(resultat.next()) {
                numClient = resultat.getInt("NUMCLIENT");
            }
            requete.close();
        }catch (SQLException ex) {
            Logger.getLogger(LogIn_Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        Parent  root = FXMLLoader.load(getClass().getResource("/Vue/Espace_client.fxml"));
        Stage stage = (Stage) Login_btn.getScene().getWindow();
        stage.setScene(new Scene(root));
    }
    
    @FXML
    private void Register_click(ActionEvent event) throws IOException {
        int num=1;
        Statement requete;
        try {
            requete = conn.createStatement();

            ResultSet resultat = requete.executeQuery( "SELECT * FROM LesClients_Projet");
            while(resultat.next()) {
                    num++;
            }
            requete.close();
        }catch (SQLException ex) {
            Logger.getLogger(LogIn_Controller.class.getName()).log(Level.SEVERE, null, ex);
        }


        String prenom = Register_prenom.getText();
        String nom = Register_nom.getText();
        String numPassport = Register_NumPassport.getText();
        int reducion = 0;
        int numRue =  Integer.parseInt(Register_NumAdresse.getText());
        String rue = Register_Rue.getText();
        int codeP =  Integer.parseInt(Register_CodePostale.getText());
        String ville = Register_ville.getText();
        String pays = Register_pays.getText();


        try {
            requete = conn.createStatement();
            requete.executeUpdate("insert into LesClients_Projet values ("+num+",'"+nom+"','"+prenom+"','"+numPassport+"',"+reducion+","+numRue+",'"+rue+"',"+codeP+",'"+ville+"','"+pays+"')");
            requete.close();
        } catch (SQLException ex) {
            Logger.getLogger(LogIn_Controller.class.getName()).log(Level.SEVERE, null, ex);
        }

        
        
        try {
            requete = conn.createStatement();

            ResultSet resultat = requete.executeQuery( "SELECT * FROM LesClients_Projet where nomClient='"+nom+"' and prenomClient='"+prenom+"' and numPassport='"+numPassport+"'");
            if(resultat.next()) {
                numClient = resultat.getInt("NUMCLIENT");
            }
            requete.close();
        }catch (SQLException ex) {
            Logger.getLogger(LogIn_Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        AfficherRequetes.afficher_LesClients();
        Parent  root = FXMLLoader.load(getClass().getResource("/Vue/Espace_client.fxml"));
        Stage stage = (Stage) Register_btn.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void quitter_click(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void return_click(ActionEvent event) throws IOException {
        Parent  root = FXMLLoader.load(getClass().getResource("/Vue/Home.fxml"));
        Stage stage = (Stage) return_btn.getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}