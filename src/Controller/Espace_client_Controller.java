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
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import static Application.App.conn;
import static Controller.LogIn_Controller.numClient;
import Modele.Gestion_Reservation;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Label;



/**
 * FXML Controller class
 *
 * @author walid
 */
public class Espace_client_Controller implements Initializable {

    @FXML
    private AnchorPane rootPane;
    @FXML
    private Button quitter_btn;
    @FXML
    private Button return_btn;
    @FXML
    private ListView<String> ListReservation;
    @FXML
    private Button Plan_Vol;
    @FXML
    private TextField From;
    @FXML
    private TextField To;
    @FXML
    private TextField date_Depart;
    
    
    private ObservableList<String> listReservation;
    
    public static ObservableList<Commande> listCommandes;
    public static String Fromtext;
    public static String Totext;
    public static String date_Departtext;
    public static double price;
    @FXML
    private Label Info_Client;
    @FXML
    private Label adresse_Client;
    @FXML
    private Button SuppRes;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        price=0;
        listCommandes = FXCollections.observableArrayList();
        AfficherRequetes.afficher_LesVols();
        String Info="";
        String InfoAdr="";
        Statement requete;
        try {
            requete = conn.createStatement();
            ResultSet resultat = requete.executeQuery( "SELECT * FROM LesClients_Projet where NUMCLIENT="+numClient);

            if(resultat.next()) {
                    Info+= resultat.getString("NOMCLIENT")+" "+resultat.getString("prenomClient");
                    InfoAdr+="numClient: "+resultat.getInt("NUMCLIENT")+" n° passeport :"+resultat.getString("numPassport");
                    InfoAdr+="\n"+resultat.getInt("numero")+" "+resultat.getString("rue")+"\n"+resultat.getInt("codePostale")+" "+resultat.getString("ville")+","+resultat.getString("pays");
            }
            requete.close();            
        } catch (SQLException ex) {
            Logger.getLogger(LogIn_Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        Info_Client.setText(Info);
        adresse_Client.setText(InfoAdr);
        
        listReservation = FXCollections.observableArrayList();
        listReservation.removeAll(listReservation);
        //Ajout des reservation!
        try {
            requete = conn.createStatement();
            ResultSet resultat = requete.executeQuery( "SELECT distinct(numReservation) FROM LesReservation_Projet where NUMCLIENT="+numClient);

            while(resultat.next()) {
                listReservation.add(resultat.getString("numReservation"));
            }
            requete.close();
        } catch (SQLException ex) {
            Logger.getLogger(LogIn_Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        ListReservation.getItems().addAll(listReservation);
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

    @FXML
    private void Plan_Vol_click(ActionEvent event) throws IOException {
        Fromtext=From.getText();
        Totext=To.getText();
        date_Departtext=date_Depart.getText();
        if(date_Departtext.length()==10){
           date_Departtext+=" 00:00:00"; 
        }
        Parent  root = FXMLLoader.load(getClass().getResource("/Vue/Recherche.fxml"));
        Stage stage = (Stage) Plan_Vol.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void Reservation_Click(MouseEvent event) throws SQLException {
        Gestion_Reservation.consultationCommandeClient(ListReservation.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void SuppRes_Cilck(ActionEvent event) throws SQLException{
        if(ListReservation.getSelectionModel().getSelectedItem()!=null){
            Gestion_Reservation.supprimerReservation(ListReservation.getSelectionModel().getSelectedItem());
        }
        
        ListReservation.getItems().removeAll(listReservation);
        //Ajout des reservation!
        try {
            Statement requete = conn.createStatement();
            ResultSet resultat = requete.executeQuery( "SELECT distinct(numReservation) FROM LesReservation_Projet where NUMCLIENT="+numClient);
            listReservation.clear();
            while(resultat.next()) {
                listReservation.add(resultat.getString("numReservation"));
            }
            requete.close();
        } catch (SQLException ex) {
            Logger.getLogger(LogIn_Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        ListReservation.getItems().addAll(listReservation);
    }
}
