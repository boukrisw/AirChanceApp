package Controller;


import Modele.*;
import static Application.App.conn;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author walid
 */
public class GestionVol_Controller implements Initializable {

    @FXML
    private AnchorPane rootPane;
    @FXML
    private Button Ajout_Vol;
    @FXML
    private Button quitter_btn;
    @FXML
    private Button return_btn;
    @FXML
    private TableView<Vol> tableView;
    @FXML
    private TableColumn<Vol, String> TableNumVol;
    @FXML
    private TableColumn<Vol, String> TableFrom;
    @FXML
    private TableColumn<Vol, String> TableTo;
    @FXML
    private TableColumn<Vol, String> TableDateDep;
    @FXML
    private TableColumn<Vol, String> TableDurre;
    
    public static String NumeroVolGestion;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Statement requete;
        try {
            requete = conn.createStatement();
            ResultSet resultat = requete.executeQuery( "SELECT * FROM LesVols_Projet");

            ObservableList<Vol> list = FXCollections.observableArrayList();
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
    private void Ajout_Vol_click(ActionEvent event) throws IOException {
        Parent  root = FXMLLoader.load(getClass().getResource("/Vue/Ajout_vol.fxml"));
        Stage stage = (Stage) return_btn.getScene().getWindow();
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

    @FXML
    private void list_Click(MouseEvent event) throws Exception {
        if(tableView.getSelectionModel().getSelectedItem()!=null){
            NumeroVolGestion = tableView.getSelectionModel().getSelectedItem().getNumtext();  
            Parent  root = FXMLLoader.load(getClass().getResource("/Vue/Detail_vol.fxml"));
            Stage stage = (Stage) return_btn.getScene().getWindow();
            stage.setScene(new Scene(root));    
        }
    }
}
