package Modele;

import static Application.App.conn;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import static Controller.LogIn_Controller.numClient;


/**
 *
 * @author walid
 */
public class Gestion_Reservation {

    //Consultation des commandes d'un client
    public static void  consultationCommandeClient(String numReservation)throws SQLException {
            Statement requete = conn.createStatement();
            ResultSet resultat = requete.executeQuery( "SELECT * FROM LesReservation_Projet  natural join LesVols_Projet WHERE numClient="+numClient+" AND numReservation="+numReservation);
            
            System.out.println("|-------------------------------------------------------------------------------------------------------------------------------|");
            System.out.println("|-------------Details Reservation-----------------------------------------------------------------------------------------------|");
            System.out.println("|-------------------------------------------------------------------------------------------------------------------------------|");
            System.out.println("|n°Client |n°Reservation |dateReservation|n°Vol   |n° Avion|n° Place|prix Total|Aero Origine   |Aero Destination|DATE           |");
            System.out.println("|-------------------------------------------------------------------------------------------------------------------------------|");
            while(resultat.next()) {
                String res="|"+resultat.getString("numClient");
                for(int i=0;i<9-resultat.getString("numClient").length();i++) res+=" ";
                res+="|"+resultat.getString("numReservation");
                for(int i=0;i<14-resultat.getString("numReservation").length();i++) res+=" ";
                res+="|";
                for(int i=0;i<10;i++)res+=resultat.getString("dateReservation").charAt(i);
                for(int i=0;i<5;i++) res+=" ";
                res+="|"+resultat.getString("numVol");
                for(int i=0;i<8-resultat.getString("numVol").length();i++) res+=" ";
                res+="|"+resultat.getString("numAvion");
                for(int i=0;i<8-resultat.getString("numAvion").length();i++) res+=" ";
                res+="|"+resultat.getString("numPlace");
                for(int i=0;i<8-resultat.getString("numPlace").length();i++) res+=" ";
                res+="|"+resultat.getString("prix");
                for(int i=0;i<10-resultat.getString("prix").length();i++) res+=" ";
                res+="|"+resultat.getString("aeroportOrigine");
                for(int i=0;i<15-resultat.getString("aeroportOrigine").length();i++) res+=" ";
                res+="|"+resultat.getString("aeroportDestination");
                for(int i=0;i<16-resultat.getString("aeroportDestination").length();i++) res+=" ";
                res+="|";
                for(int i=0;i<13;i++)res+=resultat.getString("dateDepart").charAt(i);
                res+="h |";
                System.out.println(res);
            }
            System.out.println("|-------------------------------------------------------------------------------------------------------------------------------|");
    }     
    
    public static void  supprimerReservation(String numReservation)throws SQLException {
            Statement requete = conn.createStatement();
            ResultSet resultat =requete.executeQuery( "DELETE FROM LesReservation_Projet  WHERE numReservation="+numReservation);
    }
}
