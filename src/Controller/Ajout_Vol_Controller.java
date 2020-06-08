package Controller;

import static Application.App.conn;
import Modele.AfficherRequetes;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author walid
 */
public class Ajout_Vol_Controller implements Initializable {

    @FXML
    private AnchorPane rootPane;
    @FXML
    private Button Ajout_Vol;
    @FXML
    private Button quitter_btn;
    @FXML
    private Button return_btn;
    @FXML
    private TextField aeroOrigine;
    @FXML
    private TextField numAvion;
    @FXML
    private TextField aeroDestination;
    @FXML
    private TextField dateDepart;
    @FXML
    private TextField durree;
    @FXML
    private TextField distance;
    @FXML
    private Label erreurAjout;
    @FXML
    private TextField numVol;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        erreurAjout.setText("Veuillez entrez des bonnes \n valeur!");
        AfficherRequetes.afficher_LesVols();
    }

    @FXML
    private void Ajout_Vol_click(ActionEvent event) throws IOException {
        //Test si le numVol n'existe pas!
        try {
            Statement requete = conn.createStatement();
            ResultSet resultat = requete.executeQuery( "SELECT * FROM LesVols_Projet where numVol='"+numVol.getText()+"'");

            if(resultat.next()) {
                erreurAjout.setText("Changer le num de vol!\n Il existe déjà un vol avec ce n°");
                numVol.setText("");
                requete.close();
                return ;
            }
            requete.close();
        } catch (SQLException ex) {
            Logger.getLogger(LogIn_Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Tout est bon Instertion de Vol! 
        int nbPlaceDispoMin=0;
        int nbPlacesEcoMin=0;
        int nbPlacesPremiereMin=0;
        int nbPlacesAffaire=0;
        try {
            Statement requete = conn.createStatement();
            ResultSet resultat = requete.executeQuery( "SELECT * FROM LesAvions_Projet where numAvion='"+numAvion.getText()+"'");
            if(resultat.next()) {
                nbPlacesEcoMin =resultat.getInt("nbPlacesEco");
                nbPlacesPremiereMin = resultat.getInt("nbPlacesPremiere");
                nbPlacesAffaire = resultat.getInt("nbPlacesAffaire");
                nbPlaceDispoMin = nbPlacesEcoMin+nbPlacesPremiereMin+nbPlacesAffaire;
            }
            requete.close();
        } catch (SQLException ex) {
            Logger.getLogger(LogIn_Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            Statement requete = conn.createStatement();
            requete.executeUpdate("insert into LesVols_Projet values ('"+numVol.getText()+"',"+numAvion.getText()+",'"+aeroOrigine.getText()+"','"+aeroDestination.getText()+"',TO_DATE('"+dateDepart.getText()+"', 'YYYY-MM-DD HH24:MI:SS'),"+durree.getText()+",'"+distance.getText()+"',"+nbPlaceDispoMin+","+nbPlacesEcoMin+","+nbPlacesPremiereMin+","+nbPlacesAffaire+")");
            requete.close();
        } catch (SQLException ex) {
            Logger.getLogger(LogIn_Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Tout est bien passe retour a l'accueil!
        Parent  root = FXMLLoader.load(getClass().getResource("/Vue/Gestion_vol.fxml"));
        Stage stage = (Stage) return_btn.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void quitter_click(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void return_click(ActionEvent event) throws IOException {
        Parent  root = FXMLLoader.load(getClass().getResource("/Vue/Gestion_vol.fxml"));
        Stage stage = (Stage) return_btn.getScene().getWindow();
        stage.setScene(new Scene(root));
    }


}
        