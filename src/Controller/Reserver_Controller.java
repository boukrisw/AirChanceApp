/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static Application.App.conn;
import static Controller.Espace_client_Controller.price;
import static Controller.Recherche_Controller.NumeroVolRecherche;
import static Controller.Espace_client_Controller.listCommandes;
/**
 * FXML Controller class
 *
 * @author walid
 */
public class Reserver_Controller implements Initializable {

    @FXML
    private AnchorPane rootPane;
    @FXML
    private ListView<String> A;
    @FXML
    private ListView<String> B;
    @FXML
    private ListView<String> C;
    @FXML
    private ListView<String> D;
    @FXML
    private ListView<String> E;
    @FXML
    private ListView<String> F;
    @FXML
    private Button confirmerbtn;
    @FXML
    private ListView<String> listPlace;
    
    int numAvion;

    ObservableList<String> listPlaceSeleted = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        for(Commande c:listCommandes){
            if(c.Vol.equals(NumeroVolRecherche)){
                listPlaceSeleted.add(c.Place);
                listPlace.getItems().add(c.Place);
            }
        }
        
        Statement requete;
        int nbPlaces=0;
        try {
            requete = conn.createStatement();
            ResultSet resultat = requete.executeQuery("select numModel from LesVols_Projet V join LesAvions_Projet A on (V.numAvion=A.numAvion) where numVol='"+NumeroVolRecherche+"'");
            if(resultat.next()){
                resultat = requete.executeQuery("select nbPlace from LesModelAvions_Projet where numModel='"+resultat.getString("numModel")+"'");
                if(resultat.next())nbPlaces =resultat.getInt("nbPlace");
            }
            requete.close();
        } catch (SQLException ex) {
            Logger.getLogger(LogIn_Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        ObservableList<String> Alist = FXCollections.observableArrayList();
        ObservableList<String> Blist = FXCollections.observableArrayList();
        ObservableList<String> Clist = FXCollections.observableArrayList();
        ObservableList<String> Dlist = FXCollections.observableArrayList();
        ObservableList<String> Elist = FXCollections.observableArrayList();
        ObservableList<String> Flist = FXCollections.observableArrayList();
                        
        ObservableList<Integer> l = FXCollections.observableArrayList();
        try {
                requete = conn.createStatement();
                ResultSet resultat = requete.executeQuery("select numPlace from LesReservation_Projet where numVol='"+NumeroVolRecherche+"'");
                while(resultat.next()){
                    l.add(resultat.getInt("numPlace"));
                }
                requete.close();
        } catch (SQLException ex) {
            Logger.getLogger(LogIn_Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        for(int i=1;i<=nbPlaces;i++){
            if(l.contains(i)){
                    if(i%6==1) Alist.add("#");
                    if(i%6==2) Blist.add("#");
                    if(i%6==3) Clist.add("#");
                    if(i%6==4) Dlist.add("#");
                    if(i%6==5) Elist.add("#");
                    if(i%6==0) Flist.add("#");
                }else{
                    if(i%6==1) Alist.add(i+"");
                    if(i%6==2) Blist.add(i+"");
                    if(i%6==3) Clist.add(i+"");
                    if(i%6==4) Dlist.add(i+"");
                    if(i%6==5) Elist.add(i+"");
                    if(i%6==0) Flist.add(i+"");    
                }    
                
        }
        A.getItems().addAll(Alist);
        B.getItems().addAll(Blist);
        C.getItems().addAll(Clist);
        D.getItems().addAll(Dlist);
        E.getItems().addAll(Elist);
        F.getItems().addAll(Flist);
    }    

    @FXML
    private void A_click(MouseEvent event) {
        String s = A.getSelectionModel().getSelectedItem();
        if(s!="#" && !listPlaceSeleted.contains(s)){
            listPlace.getItems().add(s);
            listPlaceSeleted.add(s);
            try {
                Statement requete = conn.createStatement();
                ResultSet resultat = requete.executeQuery("select numAvion from LesVols_Projet where numVol='"+NumeroVolRecherche+"'");
                if(resultat.next()){
                    numAvion=resultat.getInt("numAvion");
                }
                requete.close();
                requete = conn.createStatement();
                resultat = requete.executeQuery("select prix from LesPlaceAvions_Projet where numPlace="+s+" AND numAvion="+numAvion);
                
                listCommandes.add(new Commande(NumeroVolRecherche,s,""+numAvion));
                if(resultat.next()){
                    price+=resultat.getInt("prix");
                }
                requete.close();
            } catch (SQLException ex) {
                Logger.getLogger(LogIn_Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void B_click(MouseEvent event) {
    String s = B.getSelectionModel().getSelectedItem();
        if(s!="#" && !listPlaceSeleted.contains(s)){
            listPlace.getItems().add(s);
            listPlaceSeleted.add(s);
            try {
                Statement requete = conn.createStatement();
                ResultSet resultat = requete.executeQuery("select numAvion from LesVols_Projet where numVol='"+NumeroVolRecherche+"'");
                if(resultat.next()){
                    numAvion=resultat.getInt("numAvion");
                }
                requete.close();
                requete = conn.createStatement();
                resultat = requete.executeQuery("select prix from LesPlaceAvions_Projet where numPlace="+s+" AND numAvion="+numAvion);
                listCommandes.add(new Commande(NumeroVolRecherche,s,""+numAvion));
                if(resultat.next()){
                    price+=resultat.getInt("prix");
                }
                requete.close();
            } catch (SQLException ex) {
                Logger.getLogger(LogIn_Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void C_click(MouseEvent event) {
        String s = C.getSelectionModel().getSelectedItem();
        if(s!="#" && !listPlaceSeleted.contains(s)){
            listPlace.getItems().add(s);
            listPlaceSeleted.add(s);
            try {
                Statement requete = conn.createStatement();
                ResultSet resultat = requete.executeQuery("select numAvion from LesVols_Projet where numVol='"+NumeroVolRecherche+"'");
                if(resultat.next()){
                    numAvion=resultat.getInt("numAvion");
                }
                requete.close();
                requete = conn.createStatement();
                resultat = requete.executeQuery("select prix from LesPlaceAvions_Projet where numPlace="+s+" AND numAvion="+numAvion);
                listCommandes.add(new Commande(NumeroVolRecherche,s,""+numAvion));
                if(resultat.next()){
                    price+=resultat.getInt("prix");
                }
                requete.close();
            } catch (SQLException ex) {
                Logger.getLogger(LogIn_Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void D_click(MouseEvent event) {
        String s = D.getSelectionModel().getSelectedItem();
        if(s!="#" && !listPlaceSeleted.contains(s)){
            listPlace.getItems().add(s);
            listPlaceSeleted.add(s);
            try {
                Statement requete = conn.createStatement();
                ResultSet resultat = requete.executeQuery("select numAvion from LesVols_Projet where numVol='"+NumeroVolRecherche+"'");
                if(resultat.next()){
                    numAvion=resultat.getInt("numAvion");
                }
                requete.close();
                requete = conn.createStatement();
                resultat = requete.executeQuery("select prix from LesPlaceAvions_Projet where numPlace="+s+" AND numAvion="+numAvion);
                listCommandes.add(new Commande(NumeroVolRecherche,s,""+numAvion));
                if(resultat.next()){
                    price+=resultat.getInt("prix");
                }
                requete.close();
            } catch (SQLException ex) {
                Logger.getLogger(LogIn_Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void E_click(MouseEvent event) {
        String s = E.getSelectionModel().getSelectedItem();
        if(s!="#" && !listPlaceSeleted.contains(s)){
            listPlace.getItems().add(s);
            listPlaceSeleted.add(s);
            try {
                Statement requete = conn.createStatement();
                ResultSet resultat = requete.executeQuery("select numAvion from LesVols_Projet where numVol='"+NumeroVolRecherche+"'");
                if(resultat.next()){
                    numAvion=resultat.getInt("numAvion");
                }
                requete.close();
                requete = conn.createStatement();
                resultat = requete.executeQuery("select prix from LesPlaceAvions_Projet where numPlace="+s+" AND numAvion="+numAvion);
                listCommandes.add(new Commande(NumeroVolRecherche,s,""+numAvion));
                if(resultat.next()){
                    price+=resultat.getInt("prix");
                }
                requete.close();
            } catch (SQLException ex) {
                Logger.getLogger(LogIn_Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void F_click(MouseEvent event) {
        String s = F.getSelectionModel().getSelectedItem();
        if(s!="#" && !listPlaceSeleted.contains(s)){
            listPlace.getItems().add(s);
            listPlaceSeleted.add(s);
            try {
                Statement requete = conn.createStatement();
                ResultSet resultat = requete.executeQuery("select numAvion from LesVols_Projet where numVol='"+NumeroVolRecherche+"'");
                if(resultat.next()){
                    numAvion=resultat.getInt("numAvion");
                }
                requete.close();
                requete = conn.createStatement();
                resultat = requete.executeQuery("select prix from LesPlaceAvions_Projet where numPlace="+s+" AND numAvion="+numAvion);
                listCommandes.add(new Commande(NumeroVolRecherche,s,""+numAvion));
                if(resultat.next()){
                    price+=resultat.getInt("prix");
                }
                requete.close();
            } catch (SQLException ex) {
                Logger.getLogger(LogIn_Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void Confirmer_Onlick(ActionEvent event) throws IOException {
        Parent  root = FXMLLoader.load(getClass().getResource("/Vue/Recherche.fxml"));
        Stage stage = (Stage) confirmerbtn.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void Supp_Cilck(ActionEvent event) {
    }
}
