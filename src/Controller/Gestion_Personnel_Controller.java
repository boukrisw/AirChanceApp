package Controller;

import Modele.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import static Application.App.conn;
import static Controller.Detail_Vol_Controller.NumeroAvionDetail;
import static Controller.GestionVol_Controller.NumeroVolGestion;
import java.text.ParseException;

/**
 * FXML Controller class
 *
 * @author walid
 */
public class Gestion_Personnel_Controller implements Initializable {

    @FXML
    private AnchorPane rootPane;
    @FXML
    private ChoiceBox<String> choicePilotes;
    @FXML
    private ListView<String> listPilotes;
    @FXML
    private ChoiceBox<String> choiceHotesses;
    @FXML
    private ListView<String> listHotesses;
    @FXML
    private Label erreurAjout;
    @FXML
    private Button close;
    
    ObservableList<String> listpilotes = FXCollections.observableArrayList();
    ObservableList<String> listhotesse = FXCollections.observableArrayList();

    
    ObservableList<String> pilotes = FXCollections.observableArrayList();
    ObservableList<String> hotesse = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        AfficherRequetes.afficher_LesPilotes_LesHotesses_Vol(NumeroVolGestion);
        Statement requete;
        try {
            requete = conn.createStatement();
            ResultSet resultat1 = requete.executeQuery("SELECT * FROM LesAvions_Projet where numAvion="+NumeroAvionDetail);
            System.out.println("-----------------------------");
            if(resultat1.next()) {
                System.out.println("n° Model  :  "+resultat1.getString("numModel"));
            }
            System.out.println("-----------------------------");
            
            ResultSet resultat = requete.executeQuery( "SELECT * FROM LesPilotesModel_Projet where numModel='"+resultat1.getString("numModel")+"'");
            while(resultat.next()) {
              pilotes.add(resultat.getString("numPersonnel"));
            }
            requete.close();
        } catch (SQLException ex) {
            Logger.getLogger(LogIn_Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            requete = conn.createStatement();
            ResultSet resultat = requete.executeQuery( "SELECT * FROM LesHotesses_Projet");
            while(resultat.next()) {
              hotesse.add(resultat.getString("numPersonnel"));
            }
            requete.close();
        } catch (SQLException ex) {
            Logger.getLogger(LogIn_Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        choicePilotes.setItems(pilotes);
        choiceHotesses.setItems(hotesse);
        
        
        try {
            requete = conn.createStatement();
            ResultSet resultat = requete.executeQuery( "SELECT * FROM LesPilotesVol_Projet where numVol='"+NumeroVolGestion+"'");
            while(resultat.next()) {
              listpilotes.add(resultat.getString("numPersonnel"));
            }
            requete.close();
        } catch (SQLException ex) {
            Logger.getLogger(LogIn_Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        listPilotes.getItems().addAll(listpilotes);
        
        try {
            requete = conn.createStatement();
            ResultSet resultat = requete.executeQuery( "SELECT * FROM LesHotessesVol_Projet where numVol='"+NumeroVolGestion+"'");
            while(resultat.next()) {
              listhotesse.add(resultat.getString("numPersonnel"));
            }
            requete.close();
        } catch (SQLException ex) {
            Logger.getLogger(LogIn_Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        listHotesses.getItems().addAll(listhotesse);
        AfficherRequetes.afficher_LesPilotes();
        AfficherRequetes.afficher_LesHotesses();
    }    


    @FXML
    private void addPilote_Cilck(ActionEvent event) throws ParseException {
        String numPilote;
        numPilote = choicePilotes.getSelectionModel().getSelectedItem();
        if(numPilote!=null){
            erreurAjout.setText(Gestion_Personnel.Ajouter_Pilote(numPilote));
            try {
                Statement requete = conn.createStatement();
                ResultSet resultat = requete.executeQuery( "SELECT * FROM LesPilotesVol_Projet where numVol='"+NumeroVolGestion+"'");
                listpilotes.removeAll(listpilotes);
                while(resultat.next()) {
                  listpilotes.add(resultat.getString("numPersonnel"));
                }
                requete.close();
            } catch (SQLException ex) {
                Logger.getLogger(LogIn_Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
            listPilotes.getItems().removeAll(listpilotes);
            listPilotes.getItems().addAll(listpilotes);
        }
    }


    @FXML
    private void addHotesse_Cilck(ActionEvent event) {
        //Ajouter_Hotesse
                
        String numHotesse;
        numHotesse = choiceHotesses.getSelectionModel().getSelectedItem();
        if(numHotesse!=null){
            erreurAjout.setText(Gestion_Personnel.Ajouter_Hotesse(numHotesse));
            
            try {
                Statement requete = conn.createStatement();
                ResultSet resultat = requete.executeQuery( "SELECT * FROM LesHotessesVol_Projet where numVol='"+NumeroVolGestion+"'");
                listhotesse.removeAll(listhotesse);
                while(resultat.next()) {
                  listhotesse.add(resultat.getString("numPersonnel"));
                }
                requete.close();
            } catch (SQLException ex) {
                Logger.getLogger(LogIn_Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
            listHotesses.getItems().removeAll(listhotesse);
            listHotesses.getItems().addAll(listhotesse);
        }
    }

    @FXML
    private void supprimePilote_Onlick(ActionEvent event) {
        String n = (String)listPilotes.getSelectionModel().getSelectedItem();
        if(n!=null){
            try {
                Statement requete = conn.createStatement();
                requete.executeQuery("DELETE FROM LesPilotesVol_Projet where numPersonnel="+n+" and numVol='"+NumeroVolGestion+"'");
                listpilotes.remove(n);
                listPilotes.getItems().remove(n);
                //listPilotes.getItems().addAll(listpilotes);
                System.out.println("Pilote numero : "+n+" supprime!");
                AfficherRequetes.afficher_LesPilotes_LesHotesses_Vol(NumeroVolGestion);
                requete.close();
            } catch (SQLException ex) {
                Logger.getLogger(LogIn_Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            erreurAjout.setText("Erreur, Selectionner un pilote avant de clicker!");
        }
    }

    @FXML
    private void supprimeHotesse_Onlick(ActionEvent event) {
        String n=(String)listHotesses.getSelectionModel().getSelectedItem();
        if(n!=null){
            try {
                Statement requete = conn.createStatement();
                requete.executeQuery("DELETE FROM LesHotessesVol_Projet where numPersonnel="+n+" and numVol='"+NumeroVolGestion+"'");
                listhotesse.remove(n);
                listHotesses.getItems().remove(n);
                //listHotesses.getItems().addAll(listhotesse);
                System.out.println("Hotesse numero : "+n+" supprime!");
                AfficherRequetes.afficher_LesPilotes_LesHotesses_Vol(NumeroVolGestion);
                requete.close();
           } catch (SQLException ex) {
                Logger.getLogger(LogIn_Controller.class.getName()).log(Level.SEVERE, null, ex);
           }
        }else{
            erreurAjout.setText("Erreur, Selectionner une hotesse avant de clicker!");
        }
    }
    
    @FXML
    private void close_Cilck(ActionEvent event) throws IOException {
        Parent  root = FXMLLoader.load(getClass().getResource("/Vue/Detail_vol.fxml"));
        Stage stage = (Stage) close.getScene().getWindow();
        stage.setScene(new Scene(root));
    }
    
}
