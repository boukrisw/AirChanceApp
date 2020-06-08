package Controller;

import static Application.App.conn;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author walid
 */
public class Home_Controller implements Initializable {

    @FXML
    private AnchorPane rootPane;
    @FXML
    private Button Reservation_btn;
    @FXML
    private Button Plan_Vol;
    @FXML
    private Button Quitter_btn1;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    


    @FXML
    private void Quitter_click(ActionEvent event) throws SQLException {
        conn.close();
        System.exit(0);
    }

    @FXML
    private void Reservation_click(ActionEvent event) throws IOException {
        Parent  root = FXMLLoader.load(getClass().getResource("/Vue/Log_In.fxml"));
        Stage stage = (Stage) Reservation_btn.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void Plan_Vol_click(ActionEvent event) throws IOException {
        Parent  root = FXMLLoader.load(getClass().getResource("/Vue/Gestion_vol.fxml"));
        Stage stage = (Stage) Reservation_btn.getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}
