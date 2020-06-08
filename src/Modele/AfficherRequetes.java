package Modele;

import static Application.App.conn;
import Controller.LogIn_Controller;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AfficherRequetes{
    public static void afficher_LesClients(){
        Statement requete;
        try {
            requete = conn.createStatement();
            ResultSet resultat = requete.executeQuery( "SELECT * FROM LesClients_Projet ORDER BY NUMCLIENT ASC");
            System.out.println("|--------------------------------------------------------------------|");
            System.out.println("|-------------Table des clients--------------------------------------|");
            System.out.println("|--------------------------------------------------------------------|");
            System.out.println("|N°   |NOM                 |Prenom              |numPassport         |");
            System.out.println("|--------------------------------------------------------------------|");
            while(resultat.next()) {
                String res="|"+resultat.getString("NUMCLIENT");
                for(int i=0;i<5-resultat.getString("NUMCLIENT").length();i++) res+=" ";
                res+="|"+resultat.getString("NOMCLIENT");
                for(int i=0;i<20-resultat.getString("NOMCLIENT").length();i++) res+=" ";
                res+="|"+resultat.getString("prenomClient");
                for(int i=0;i<20-resultat.getString("prenomClient").length();i++) res+=" ";
                res+="|"+resultat.getString("numPassport");
                for(int i=0;i<20-resultat.getString("numPassport").length();i++) res+=" ";
                res+="|";
                System.out.println(res);
            }
            System.out.println("|--------------------------------------------------------------------|");
            requete.close();
        } catch (SQLException ex) {
            Logger.getLogger(LogIn_Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void afficher_LesVols(){
        Statement requete;
        try {
            requete = conn.createStatement();
            ResultSet resultat = requete.executeQuery( "SELECT * FROM LesVols_Projet ORDER BY numVol ASC");
            System.out.println("|------------------------------------------------------------------------------------------------------------------------------------------|");
            System.out.println("|----------------------------------Table des vols------------------------------------------------------------------------------------------|");
            System.out.println("|------------------------------------------------------------------------------------------------------------------------------------------|");
            System.out.println("|N°   |num Avion      |Aeroport Origine    |Aeroport Destination|Date                 |durree         |distance       |nb Place Disponible |");
            System.out.println("|------------------------------------------------------------------------------------------------------------------------------------------|");
            while(resultat.next()) {
                String res="|"+resultat.getString("numVol");
                for(int i=0;i<5-resultat.getString("numVol").length();i++) res+=" ";
                res+="|"+resultat.getString("numAvion");
                for(int i=0;i<15-resultat.getString("numAvion").length();i++) res+=" ";
                res+="|"+resultat.getString("aeroportOrigine");
                for(int i=0;i<20-resultat.getString("aeroportOrigine").length();i++) res+=" ";
                res+="|"+resultat.getString("aeroportDestination");
                for(int i=0;i<20-resultat.getString("aeroportDestination").length();i++) res+=" ";
                res+="|"+resultat.getString("dateDepart");
                for(int i=0;i<20-resultat.getString("dateDepart").length();i++) res+=" ";
                res+="|"+resultat.getString("durree");
                for(int i=0;i<15-resultat.getString("durree").length();i++) res+=" ";
                res+="|"+resultat.getString("distance");
                for(int i=0;i<15-resultat.getString("distance").length();i++) res+=" ";
                res+="|"+resultat.getString("nbPlaceDispoMin");
                for(int i=0;i<20-resultat.getString("nbPlaceDispoMin").length();i++) res+=" ";
                res+="|";
                System.out.println(res);
            }
            System.out.println("|------------------------------------------------------------------------------------------------------------------------------------------|");
            requete.close();
        } catch (SQLException ex) {
            Logger.getLogger(LogIn_Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void afficher_LesPilotes(){
        Statement requete;
        try {
            requete = conn.createStatement();
            ResultSet resultat = requete.executeQuery( "SELECT * FROM LesPilotes_Projet P join LesPilotesModel_Projet M on P.numPersonnel=M.numPersonnel ORDER BY P.numPersonnel ASC");
            System.out.println("|-----------------------------------------------------|");
            System.out.println("|-------------Table des pilotes et les Models---------|");
            System.out.println("|-----------------------------------------------------|");
            System.out.println("|N°   |nom            |prenom         |numModel       |");
            System.out.println("|-----------------------------------------------------|");
            while(resultat.next()) {
                String res="|"+resultat.getString("numPersonnel");
                for(int i=0;i<5-resultat.getString("numPersonnel").length();i++) res+=" ";
                res+="|"+resultat.getString("nom");
                for(int i=0;i<15-resultat.getString("nom").length();i++) res+=" ";
                res+="|"+resultat.getString("prenom");
                for(int i=0;i<15-resultat.getString("prenom").length();i++) res+=" ";
                res+="|"+resultat.getString("numModel");
                for(int i=0;i<15-resultat.getString("numModel").length();i++) res+=" ";
                res+="|";
                System.out.println(res);
            }

            System.out.println("|-----------------------------------------------------|");
            requete.close();
        } catch (SQLException ex) {
            Logger.getLogger(LogIn_Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void afficher_LesHotesses(){
        Statement requete;
        try {
            requete = conn.createStatement();
            ResultSet resultat = requete.executeQuery( "SELECT * FROM LesHotesses_Projet H join LesHotessesLangues_Projet L on H.numPersonnel=L.numPersonnel ORDER BY H.numPersonnel ASC");
            System.out.println("|-----------------------------------------------------|");
            System.out.println("|---------------Table des hotesses et langues---------|");
            System.out.println("|-----------------------------------------------------|");
            System.out.println("|N°   |nom            |prenom         |langue         |");
            System.out.println("|-----------------------------------------------------|");
            while(resultat.next()) {
                String res="|"+resultat.getString("numPersonnel");
                for(int i=0;i<5-resultat.getString("numPersonnel").length();i++) res+=" ";
                res+="|"+resultat.getString("nom");
                for(int i=0;i<15-resultat.getString("nom").length();i++) res+=" ";
                res+="|"+resultat.getString("prenom");
                for(int i=0;i<15-resultat.getString("prenom").length();i++) res+=" ";
                res+="|"+resultat.getString("langue");
                for(int i=0;i<15-resultat.getString("langue").length();i++) res+=" ";
                res+="|";
                System.out.println(res);
            }
            System.out.println("|-----------------------------------------------------|");
            requete.close();
          } catch (SQLException ex) {
            Logger.getLogger(LogIn_Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void afficher_LesPilotes_LesHotesses_Vol(String numVol){
        System.out.println("Vol numero : "+numVol);
        Statement requete;
        try {
            requete = conn.createStatement();
            ResultSet resultat = requete.executeQuery( "SELECT * FROM LesPilotesVol_Projet PV join LesPilotes_Projet P on (PV.numPersonnel=P.numPersonnel) where PV.numVol='"+numVol+"'");
            System.out.println("|-----------------------------------------------|");
            System.out.println("|-------------Table des pilotes-----------------|");
            System.out.println("|-----------------------------------------------|");
            System.out.println("|N°   |NOM                 |Prenom              |");
            System.out.println("|-----------------------------------------------|");
            
            boolean vide=true;
            while(resultat.next()) {
                String res="|"+resultat.getString("numPersonnel");
                for(int i=0;i<5-resultat.getString("numPersonnel").length();i++) res+=" ";
                res+="|"+resultat.getString("nom");
                for(int i=0;i<20-resultat.getString("nom").length();i++) res+=" ";
                res+="|"+resultat.getString("prenom");
                for(int i=0;i<20-resultat.getString("prenom").length();i++) res+=" ";
                res+="|";
                System.out.println(res);
                vide=false;
            }
            if(vide){
              System.out.println("|-----------Aucuns Pilote pour ce vol-----------|");
            }
            System.out.println("|-----------------------------------------------|");


            resultat = requete.executeQuery( "SELECT * FROM LesHotessesVol_Projet HV join LesHotesses_Projet H on HV.numPersonnel=H.numPersonnel where HV.numVol='"+numVol+"'");
            System.out.println("|-----------------------------------------------|");
            System.out.println("|-------------Table des hotesses----------------|");
            System.out.println("|-----------------------------------------------|");
            System.out.println("|N°   |NOM                 |Prenom              |");
            System.out.println("|-----------------------------------------------|");
            vide=true;
            while(resultat.next()) {
                String res="|"+resultat.getString("numPersonnel");
                for(int i=0;i<5-resultat.getString("numPersonnel").length();i++) res+=" ";
                res+="|"+resultat.getString("nom");
                for(int i=0;i<20-resultat.getString("nom").length();i++) res+=" ";
                res+="|"+resultat.getString("prenom");
                for(int i=0;i<20-resultat.getString("prenom").length();i++) res+=" ";
                res+="|";
                System.out.println(res);
                vide=false;
            }
            if(vide){
              System.out.println("|----------Aucuns Hotesse pour ce vol-----------|");
            }
            System.out.println("|-----------------------------------------------|");
            requete.close();
        } catch (SQLException ex) {
            Logger.getLogger(LogIn_Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void afficher_LesPilotes_LesHotesses_heure(){
        Statement requete;
        try {
            requete = conn.createStatement();
            ResultSet resultat = requete.executeQuery( "SELECT * FROM LesPilotes_Projet ORDER BY numPersonnel ASC");
            System.out.println("|---------------------------------------------------------------|");
            System.out.println("|-------------Table des pilotes---------------------------------|");
            System.out.println("|---------------------------------------------------------------|");
            System.out.println("|N°   |NOM                 |Prenom              |nbHeureTotale  |");
            System.out.println("|---------------------------------------------------------------|");
            
            boolean vide=true;
            while(resultat.next()) {
                String res="|"+resultat.getString("numPersonnel");
                for(int i=0;i<5-resultat.getString("numPersonnel").length();i++) res+=" ";
                res+="|"+resultat.getString("nom");
                for(int i=0;i<20-resultat.getString("nom").length();i++) res+=" ";
                res+="|"+resultat.getString("prenom");
                for(int i=0;i<20-resultat.getString("prenom").length();i++) res+=" ";
                res+="|"+resultat.getString("nbHeureTotal");
                for(int i=0;i<15-resultat.getString("nbHeureTotal").length();i++) res+=" ";
                res+="|";
                System.out.println(res);
                vide=false;
            }
            if(vide){
              System.out.println("|-----------Aucuns Pilote pour ce vol---------------------------|");
            }
            System.out.println("|---------------------------------------------------------------|");


            resultat = requete.executeQuery( "SELECT * FROM LesHotesses_Projet ORDER BY numPersonnel ASC");
            System.out.println("|---------------------------------------------------------------|");
            System.out.println("|-------------Table des hotesses--------------------------------|");
            System.out.println("|---------------------------------------------------------------|");
            System.out.println("|N°   |NOM                 |Prenom              |nbHeureTotale  |");
            System.out.println("|---------------------------------------------------------------|");
            vide=true;
            while(resultat.next()) {
                String res="|"+resultat.getString("numPersonnel");
                for(int i=0;i<5-resultat.getString("numPersonnel").length();i++) res+=" ";
                res+="|"+resultat.getString("nom");
                for(int i=0;i<20-resultat.getString("nom").length();i++) res+=" ";
                res+="|"+resultat.getString("prenom");
                for(int i=0;i<20-resultat.getString("prenom").length();i++) res+=" ";
                res+="|"+resultat.getString("nbHeureTotal");
                for(int i=0;i<15-resultat.getString("nbHeureTotal").length();i++) res+=" ";
                res+="|";
                System.out.println(res);
                vide=false;
            }
            if(vide){
              System.out.println("|----------Aucuns Hotesse pour ce vol---------------------------|");
            }
            System.out.println("|---------------------------------------------------------------|");
            requete.close();
        } catch (SQLException ex) {
            Logger.getLogger(LogIn_Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void afficher_LesReservation(){
        Statement requete;
        try {
            requete = conn.createStatement();
            ResultSet resultat = requete.executeQuery("select * from LesReservation_Projet ORDER BY NUMRESERVATION ASC");
            System.out.println("|--------------------------------------------------|");
            System.out.println("|-------------Table des reservation----------------|");
            System.out.println("|--------------------------------------------------|");
            System.out.println("|N°   |NUMCLIENT |NUMVOL         |NUMPLACE|NUMAVION|");
            System.out.println("|--------------------------------------------------|");
            while(resultat.next()) {
                String res="|"+resultat.getString("NUMRESERVATION");
                for(int i=0;i<5-resultat.getString("NUMRESERVATION").length();i++) res+=" ";
                res+="|"+resultat.getString("NUMCLIENT");
                for(int i=0;i<10-resultat.getString("NUMCLIENT").length();i++) res+=" ";
                res+="|"+resultat.getString("numVol");
                for(int i=0;i<15-resultat.getString("numVol").length();i++) res+=" ";
                res+="|"+resultat.getString("NUMPLACE");
                for(int i=0;i<8-resultat.getString("NUMPLACE").length();i++) res+=" ";
                res+="|"+resultat.getString("NUMAVION");
                for(int i=0;i<8-resultat.getString("NUMAVION").length();i++) res+=" ";
                res+="|";
                System.out.println(res);
            }
            System.out.println("|--------------------------------------------------|");
            requete.close();
        } catch (SQLException ex) {
            Logger.getLogger(LogIn_Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
