package Controller;

import Modele.*;
import static Application.App.conn;
import java.io.IOException;
import java.net.URL;
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


import static Controller.GestionVol_Controller.NumeroVolGestion;
import static java.lang.Integer.parseInt;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * FXML Controller class
 *
 * @author walid
 */
public class Detail_Vol_Controller implements Initializable {

    @FXML
    private AnchorPane rootPane;
    @FXML
    private Button Modifier_Vol;
    @FXML
    private Button Fin_Vol;
    @FXML
    private Button Supp_Vol;
    @FXML
    private Button GPresonnel_Vol;
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
    private Button quitter_btn;
    @FXML
    private Button return_btn;

    public static String NumeroAvionDetail;
    
    public static String date_Detail_VOL;
    
    
    public static String aeroportOrigine_Detail_VOL;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Statement requete;
        try {
            requete = conn.createStatement();
            ResultSet resultat = requete.executeQuery( "select * from LesVols_Projet where NUMVOL='"+NumeroVolGestion+"'");
            if(resultat.next()) {
                numAvion.setText(resultat.getString("numAvion"));
                NumeroAvionDetail = numAvion.getText();
                aeroportOrigine_Detail_VOL=resultat.getString("aeroportOrigine");
                aeroOrigine.setText(aeroportOrigine_Detail_VOL);
                aeroDestination.setText(resultat.getString("aeroportDestination"));
                String d = "";
                for(int i=0;i<resultat.getString("dateDepart").length()-2;i++){
                    d+=resultat.getString("dateDepart").charAt(i);
                    date_Detail_VOL = d;
                }
                dateDepart.setText(d);
                durree.setText(resultat.getString("durree"));
                distance.setText(resultat.getString("distance"));
            }
            requete.close();            
        } catch (SQLException ex) {
            Logger.getLogger(LogIn_Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void Modifier_Vol_click(ActionEvent event) {
        if(!dateDepart.getText().equals(date_Detail_VOL)){
            try {
                Statement req = conn.createStatement();
                req.executeUpdate("UPDATE LesVols_Projet SET dateDepart=TO_DATE('"+dateDepart.getText()+"','YYYY-MM-DD HH24:MI:SS') WHERE numVol='"+NumeroVolGestion+"'");
            } catch (SQLException ex) {
                Logger.getLogger(LogIn_Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
            AfficherRequetes.afficher_LesVols();
        }
        if(!numAvion.getText().equals(NumeroAvionDetail)){
            try {
                Statement requete = conn.createStatement();
                ResultSet resultat = requete.executeQuery( "SELECT numModel FROM LesAvions_Projet WHERE numAvion="+NumeroAvionDetail);
                if(resultat.next()) {
                    String s1 = resultat.getString("numModel");
                    ResultSet resultat2 = requete.executeQuery( "SELECT numModel FROM LesAvions_Projet WHERE numAvion="+numAvion.getText());
                    if(resultat2.next()){
                        String s2 = resultat2.getString("numModel");
                        if(s1.equals(s2)){
                            requete.executeUpdate("UPDATE LesReservation_Projet SET numAvion="+numAvion.getText()+" WHERE numVol='"+NumeroVolGestion+"'");
                            requete.executeUpdate("UPDATE LesVols_Projet SET numAvion="+numAvion.getText()+" WHERE numVol='"+NumeroVolGestion+"'");
                        }
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(LogIn_Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
            AfficherRequetes.afficher_LesReservation();
            AfficherRequetes.afficher_LesVols();
        }
    }

    @FXML
    private void Fin_Vol_click(ActionEvent event) throws SQLException, IOException {
        System.out.println("Avant Terminaison");
        AfficherRequetes.afficher_LesPilotes_LesHotesses_heure();
        Statement requete;
        try {
            requete = conn.createStatement();
            ResultSet resultat = requete.executeQuery( "SELECT P.numPersonnel, P.nbHeureTotal FROM LesPilotes_Projet P JOIN LesPilotesVol_Projet PV On P.numPersonnel=PV.numPersonnel WHERE numVol='"+NumeroVolGestion+"'");
            while(resultat.next()) {
                int i=resultat.getInt("nbHeureTotal")+parseInt(durree.getText());
                Statement requete1 = conn.createStatement();
                requete1.executeUpdate("UPDATE LesPilotes_Projet SET nbHeureTotal="+i+" WHERE numPersonnel="+resultat.getString("numPersonnel")+"");
            }
            requete.close();            
            Statement requete2 = conn.createStatement();
            ResultSet resultat2 = requete2.executeQuery( "SELECT H.numPersonnel, H.nbHeureTotal FROM LesHotesses_Projet H JOIN LesHotessesVol_Projet HV On H.numPersonnel=HV.numPersonnel WHERE numVol='"+NumeroVolGestion+"'");
            while(resultat2.next()) {
                int i=resultat2.getInt("nbHeureTotal")+parseInt(durree.getText());
                Statement requete1 = conn.createStatement();
                requete1.executeUpdate("UPDATE LesHotesses_Projet SET nbHeureTotal="+i+" WHERE numPersonnel="+resultat2.getString("numPersonnel")+"");
            }
            requete2.close();
            Statement requete3 = conn.createStatement();
            requete3.executeUpdate("DELETE FROM LesVols_Projet WHERE numVol='"+NumeroVolGestion+"'"); 
            requete3.close();
            System.out.println("Aprés Terminaison");
            AfficherRequetes.afficher_LesPilotes_LesHotesses_heure();
            System.out.println("Vol Terminer");
            Parent  root = FXMLLoader.load(getClass().getResource("/Vue/Gestion_vol.fxml"));
            Stage stage = (Stage) return_btn.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (SQLException ex) {
            Logger.getLogger(LogIn_Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void Supp_Vol_click(ActionEvent event) throws IOException {
        Statement requete;
        try {
            System.out.println("Reservation Avant Suppresseion");
            AfficherRequetes.afficher_LesReservation();
            requete = conn.createStatement();
            
            //Recherche vol similaire
            ResultSet resVol = requete.executeQuery("SELECT numVol,numAvion FROM LesVols_Projet WHERE aeroportOrigine='"+aeroOrigine.getText()+"' and aeroportDestination='"+aeroDestination.getText()+"' and dateDepart>TO_DATE('"+dateDepart.getText()+"','YYYY-MM-DD HH24:MI:SS') ORDER BY dateDepart ASC");
            while(resVol.next()) {
                String numVol=resVol.getString("numVol");
                String numAvion=resVol.getString("numAvion");
                //Recherche des Reservation classe Affaire A modifié!
                Statement requeteE;
                requeteE = conn.createStatement();
                ResultSet resRsvE = requeteE.executeQuery( "SELECT R.numReservation,R.numPlace FROM LesReservation_Projet R join LesPlaceAvions_Projet PA on (R.numPlace=PA.numPlace and R.numAvion=PA.numAvion) WHERE R.numVol='"+NumeroVolGestion+"' and PA.typeClasse='affaire'");
                while(resRsvE.next()){
                    System.out.println(" I'm IN");
                    //Recherche places libre dans le vols pour classe affaire!
                    Statement requeteE1;
                    requeteE1 = conn.createStatement();
                    ResultSet resPlace = requeteE1.executeQuery( "SELECT numPlace FROM LesPlaceAvions_Projet where numAvion="+numAvion+" and typeClasse='affaire' MINUS SELECT numPlace FROM LesReservation_Projet where numAvion="+numAvion+" AND numVol='"+numVol+"'");
                    if(resPlace.next()){
                        Statement requeteE11;
                        requeteE11 = conn.createStatement();
                        requeteE11.executeUpdate("UPDATE LesReservation_Projet SET numPlace="+resPlace.getString("numPlace")+" , numAvion="+numAvion+" , numVol='"+numVol+"' WHERE numReservation="+resRsvE.getString("numReservation")+" AND numPlace="+resRsvE.getString("numPlace"));
                        requeteE11.close();
                    }
                    requeteE1.close();
                }
                
                //Recherche des Reservation classe premiere A modifié!
                requeteE = conn.createStatement();
                resRsvE = requeteE.executeQuery( "SELECT R.numReservation,R.numPlace FROM LesReservation_Projet R join LesPlaceAvions_Projet PA on (R.numPlace=PA.numPlace and R.numAvion=PA.numAvion) WHERE R.numVol='"+NumeroVolGestion+"' and PA.typeClasse='premiere'");
                while(resRsvE.next()){
                    System.out.println(" I'm IN");
                    //Recherche places libre dans le vols pour classe affaire!
                    Statement requeteE1;
                    requeteE1 = conn.createStatement();
                    ResultSet resPlace = requeteE1.executeQuery( "SELECT numPlace FROM LesPlaceAvions_Projet where numAvion="+numAvion+" and typeClasse='premiere' MINUS SELECT numPlace FROM LesReservation_Projet where numAvion="+numAvion+" AND numVol='"+numVol+"'");
                    if(resPlace.next()){
                        Statement requeteE11;
                        requeteE11 = conn.createStatement();
                        requeteE11.executeUpdate("UPDATE LesReservation_Projet SET numPlace="+resPlace.getString("numPlace")+" , numAvion="+numAvion+" , numVol='"+numVol+"' WHERE numReservation="+resRsvE.getString("numReservation")+" AND numPlace="+resRsvE.getString("numPlace"));
                        requeteE11.close();
                    }
                    requeteE1.close();
                }
                
                //Recherche des Reservation classe eco A modifié!
                requeteE = conn.createStatement();
                resRsvE = requeteE.executeQuery( "SELECT R.numReservation,R.numPlace FROM LesReservation_Projet R join LesPlaceAvions_Projet PA on (R.numPlace=PA.numPlace and R.numAvion=PA.numAvion) WHERE R.numVol='"+NumeroVolGestion+"' and PA.typeClasse='eco'");
                while(resRsvE.next()){
                    System.out.println(" I'm IN");
                    //Recherche places libre dans le vols pour classe affaire!
                    Statement requeteE1;
                    requeteE1 = conn.createStatement();
                    ResultSet resPlace = requeteE1.executeQuery( "SELECT numPlace FROM LesPlaceAvions_Projet where numAvion="+numAvion+" and typeClasse='eco' MINUS SELECT numPlace FROM LesReservation_Projet where numAvion="+numAvion+" AND numVol='"+numVol+"'");
                    if(resPlace.next()){
                        Statement requeteE11;
                        requeteE11 = conn.createStatement();
                        requeteE11.executeUpdate("UPDATE LesReservation_Projet SET numPlace="+resPlace.getString("numPlace")+" , numAvion="+numAvion+" , numVol='"+numVol+"' WHERE numReservation="+resRsvE.getString("numReservation")+" AND numPlace="+resRsvE.getString("numPlace"));
                        requeteE11.close();
                    }
                    requeteE1.close();
                }
                requeteE.close();
            }
            
            //Supprime le vol
            requete.executeUpdate("DELETE FROM LesVols_Projet WHERE numVol='"+NumeroVolGestion+"'"); 
            requete.close();
            
            System.out.println("Reservation APRES Suppresseion");
            AfficherRequetes.afficher_LesReservation();
            System.out.println("Vol Supprimée");
            
            Parent  root = FXMLLoader.load(getClass().getResource("/Vue/Gestion_vol.fxml"));
            Stage stage = (Stage) return_btn.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (SQLException ex) {
            Logger.getLogger(LogIn_Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void GPresonnel_Vol_click(ActionEvent event) throws IOException {
        Parent  root = FXMLLoader.load(getClass().getResource("/Vue/Gestion_Personnel.fxml"));
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
