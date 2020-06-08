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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import static Controller.Espace_client_Controller.listCommandes;
import static Controller.Espace_client_Controller.Fromtext;
import static Controller.Espace_client_Controller.Totext;
import static Controller.Espace_client_Controller.date_Departtext;
import static Controller.Espace_client_Controller.price;
import static Controller.LogIn_Controller.numClient;


/**
 * FXML Controller class
 *
 * @author walid
 */
public class Recherche_Controller implements Initializable {

    @FXML
    private AnchorPane rootPane;
    @FXML
    private TextField From;
    @FXML
    private TextField To;
    @FXML
    private TextField date_Depart;
    @FXML
    private Button Plan_Vol;
    @FXML
    private Button return_btn;
    @FXML
    private Button quitter_btn;
    @FXML
    private TableColumn<Vol, String> TableFrom;
    @FXML
    private TableColumn<Vol, String> TableTo;
    @FXML
    private TableColumn<Vol, String> TableDateDep;
    @FXML
    private TableColumn<Vol, String> TableDurre;
    @FXML
    private TableView<Vol> tableView;
    @FXML
    private TableColumn<Vol, String> TableNumVol;
    @FXML
    private Label prix;
    @FXML
    private Label promo;
    @FXML
    private CheckBox Activer;
    @FXML
    private Button payerbtn;

    int promoH;
    
    public static String NumeroVolRecherche;
    ObservableList<Vol> list = FXCollections.observableArrayList();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        prix.setText("Prix: "+price);
        From.setText(Fromtext);
        To.setText(Totext);
        date_Depart.setText(date_Departtext);

        Statement requete;
        try {
            requete = conn.createStatement();
            
            String req="SELECT * FROM LesVols_Projet";
            if(!Fromtext.equals("")){
                req+=" where aeroportOrigine = '"+Fromtext+"'";
                if(!Totext.equals("")){
                    req+=" and aeroportDestination = '"+Totext+"'";
                    if(!date_Departtext.equals("")){
                        req+=" and dateDepart >= TO_DATE('"+date_Departtext+"', 'YYYY-MM-DD HH24:MI:SS')";
                    }
                }else{
                    if(!date_Departtext.equals("")){
                        req+=" and dateDepart >= TO_DATE('"+date_Departtext+"', 'YYYY-MM-DD HH24:MI:SS')";
                    }
                }
            }else{
                if(!Totext.equals("")){
                    req+=" where aeroportDestination = '"+Totext+"'";
                    if(!date_Departtext.equals("")){
                        req+=" and dateDepart >= TO_DATE('"+date_Departtext+"', 'YYYY-MM-DD HH24:MI:SS')";
                    }
                }else{
                    if(!date_Departtext.equals("")){
                        req+=" where dateDepart >= TO_DATE('"+date_Departtext+"', 'YYYY-MM-DD HH24:MI:SS')";
                    }
                }
            }
            ResultSet resultat = requete.executeQuery(req+" ORDER BY dateDepart ASC");

            list.removeAll(list);
            while(resultat.next()) {
                    Vol v = new Vol(resultat.getString("NUMVOL"),resultat.getString("AEROPORTORIGINE"),resultat.getString("AEROPORTDESTINATION"),resultat.getString("dateDepart"),resultat.getString("durree"),resultat.getString("distance"),resultat.getString("nbPlaceDispoMin"));
                    list.addAll(v);
            }
            
            
            TableNumVol.setCellValueFactory(new PropertyValueFactory<>("Numtext"));
            TableFrom.setCellValueFactory(new PropertyValueFactory<>("Fromtext"));
            TableTo.setCellValueFactory(new PropertyValueFactory<>("Totext"));
            TableDateDep.setCellValueFactory(new PropertyValueFactory<>("date_Departtext"));
            TableDurre.setCellValueFactory(new PropertyValueFactory<>("durre_Maxtext"));
            tableView.getItems().addAll(list);
            resultat = requete.executeQuery("SELECT * FROM LesClients_Projet where NUMCLIENT="+numClient);
            if(resultat.next()){
                promoH=resultat.getInt("reduction");
                promo.setText("Promo :"+promoH+" h\n50h->5% reduc");
            }
            requete.close();
        } catch (SQLException ex) {
            Logger.getLogger(LogIn_Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void Plan_Vol_click(ActionEvent event) {
        Statement requete;
        try {
            requete = conn.createStatement();
            String d=date_Depart.getText();
            if(d.length()==10){
                d+=" 00:00:00"; 
            }
            
            Fromtext=From.getText();
            Totext=To.getText();
            date_Departtext=d;
            String req="SELECT * FROM LesVols_Projet";
            if(!Fromtext.equals("")){
                req+=" where aeroportOrigine = '"+Fromtext+"'";
                if(!Totext.equals("")){
                    req+=" and aeroportDestination = '"+Totext+"'";
                    if(!date_Departtext.equals("")){
                        req+=" and dateDepart >= TO_DATE('"+date_Departtext+"', 'YYYY-MM-DD HH24:MI:SS')";
                    }
                }else{
                    if(!date_Departtext.equals("")){
                        req+=" and dateDepart >= TO_DATE('"+d+"', 'YYYY-MM-DD HH24:MI:SS')";
                    }
                }
            }else{
                if(!Totext.equals("")){
                    req+=" where aeroportDestination = '"+Totext+"'";
                    if(!date_Departtext.equals("")){
                        req+=" and dateDepart >= TO_DATE('"+d+"', 'YYYY-MM-DD HH24:MI:SS')";
                    }
                }else{
                    if(!date_Departtext.equals("")){
                        req+=" where dateDepart >= TO_DATE('"+d+"', 'YYYY-MM-DD HH24:MI:SS')";
                    }
                }
            }
            
            ResultSet resultat = requete.executeQuery(req+" ORDER BY dateDepart ASC");

            tableView.getItems().removeAll(list);
            list.removeAll(list);
            while(resultat.next()) {
                    Vol v = new Vol(resultat.getString("NUMVOL"),resultat.getString("AEROPORTORIGINE"),resultat.getString("AEROPORTDESTINATION"),resultat.getString("dateDepart"),resultat.getString("durree"),resultat.getString("distance"),resultat.getString("nbPlaceDispoMin"));
                    list.addAll(v);
            }
            
            TableNumVol.setCellValueFactory(new PropertyValueFactory<>("Numtext"));
            TableFrom.setCellValueFactory(new PropertyValueFactory<>("Fromtext"));
            TableTo.setCellValueFactory(new PropertyValueFactory<>("Totext"));
            TableDateDep.setCellValueFactory(new PropertyValueFactory<>("date_Departtext"));
            TableDurre.setCellValueFactory(new PropertyValueFactory<>("durre_Maxtext"));
            tableView.getItems().addAll(list);
            requete.close();
        } catch (SQLException ex) {
            Logger.getLogger(LogIn_Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void return_click(ActionEvent event) throws IOException {
        Parent  root = FXMLLoader.load(getClass().getResource("/Vue/Espace_client.fxml"));
        Stage stage = (Stage) return_btn.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void quitter_click(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void list_Click(MouseEvent event) throws IOException {
        if(tableView.getSelectionModel().getSelectedItem()!=null){
            NumeroVolRecherche = tableView.getSelectionModel().getSelectedItem().getNumtext();  
        
            Parent  root = FXMLLoader.load(getClass().getResource("/Vue/Reserver.fxml"));
            Stage stage = (Stage) return_btn.getScene().getWindow();
            stage.setScene(new Scene(root));
        }
    }

    @FXML
    private void Payer_Onlick(ActionEvent event) throws IOException {
        System.out.println("Reservation avant");
        AfficherRequetes.afficher_LesReservation();
        try {
                int nextRes=(int)(Math.random()*100);
                Statement requete = conn.createStatement();
                ResultSet resultat = requete.executeQuery("select * from LesReservation_Projet where numReservation="+nextRes);
                while(resultat.next()){
                    nextRes=(int)(Math.random()*1000);
                    resultat = requete.executeQuery("select * from LesReservation_Projet where numReservation="+nextRes);
                }
                
                LocalDateTime currentTime = LocalDateTime.now();
                String audd;
                audd = currentTime.toLocalDate().getDayOfMonth()+"-"+currentTime.toLocalDate().getMonthValue()+"-"+currentTime.toLocalDate().getYear();
                requete = conn.createStatement();
                int rdAjout=0;
                for(Commande c:listCommandes){
                    requete.executeUpdate("insert into LesReservation_Projet values ("+nextRes+","+price+",TO_DATE('"+audd+"', 'DD-MM-YYYY'),"+numClient+",'"+c.Vol+"',"+c.Place+","+c.Avion+")");
                    resultat = requete.executeQuery("SELECT durree FROM LesVols_Projet where NUMVOL='"+c.Vol+"'");
                    if(resultat.next()){
                        rdAjout+=resultat.getInt("durree");
                    }
                }
                rdAjout+=promoH;
                requete.executeUpdate("UPDATE LesClients_Projet SET reduction="+rdAjout+" WHERE numClient="+numClient);
           
                
                requete.close();
                System.out.println("Reservation apres");
                AfficherRequetes.afficher_LesReservation();
        } catch (SQLException ex) {
            Logger.getLogger(LogIn_Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        Parent  root = FXMLLoader.load(getClass().getResource("/Vue/Espace_client.fxml"));
        Stage stage = (Stage) payerbtn.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void ActiverAction(ActionEvent event) {
        if(Activer.isSelected() && promoH>=50){
            double red= promoH/10;
            price=price-(price*red/100);
            prix.setText("Prix: "+price);
            promoH=0;
            promo.setText("Promo :"+promoH+" h\n50h->5% reduc");
            try {
                Statement requete = conn.createStatement();
                requete.executeUpdate("UPDATE LesClients_Projet SET reduction=0 WHERE numClient="+numClient);
            } catch (SQLException ex) {
                Logger.getLogger(LogIn_Controller.class.getName()).log(Level.SEVERE, null, ex);
            }  
        } 
    }
}
