package Modele;
import static Application.App.conn;
import static Controller.Detail_Vol_Controller.aeroportOrigine_Detail_VOL;
import static Controller.Detail_Vol_Controller.date_Detail_VOL;
import static Controller.GestionVol_Controller.NumeroVolGestion;

import Controller.LogIn_Controller;
import static java.lang.Integer.parseInt;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;



public class Gestion_Personnel{
    public static String Ajouter_Pilote(String numPersonnel){
        String res="Pilote Ajouter";
        try {
            Statement requete = conn.createStatement();
            ResultSet resultat = requete.executeQuery( "SELECT MAX(dateDepart) as min FROM LesPilotesVol_Projet PV join LesVols_Projet V on (PV.numVol=V.numVol) where PV.numPersonnel="+numPersonnel+" and V.dateDepart<TO_DATE('"+date_Detail_VOL+"', 'YYYY-MM-DD HH24:MI:SS')");
            if(resultat.next()){
                String d = "";
                String min=resultat.getString("min");
                if(min!=null){
                    for(int i=0;i<min.length()-2;i++){
                        d+=min.charAt(i);
                    }
                    resultat = requete.executeQuery( "SELECT V.durree,V.aeroportDestination FROM LesPilotesVol_Projet PV join LesVols_Projet V on (PV.numVol=V.numVol) where PV.numPersonnel="+numPersonnel+" and V.dateDepart=TO_DATE('"+d+"', 'YYYY-MM-DD HH24:MI:SS')");
                    if(resultat.next()){
                        String Destination = resultat.getString("aeroportDestination");
                        if(!Destination.equals(aeroportOrigine_Detail_VOL)){
                            
                            res = "Le dernier vol de "+numPersonnel+" à comme destenation "+Destination+"!\n";
                            res += "Vous ne pouvez pas l'ajouter a ce vol d'origine de "+aeroportOrigine_Detail_VOL+".";
                            return res;
                        }
                        int durre = resultat.getInt("durree");
                        if(!changedateFormat(d,date_Detail_VOL,durre)){
                            res = "Le dernier vol de "+numPersonnel+" à commencer à "+d+" d'une durre de "+durre+"\n";
                            res += "Vous ne pouvez pas l'ajouter a ce vol qui commence à "+date_Detail_VOL+"\n";
                            res += "Le Pilote "+numPersonnel+" doit faire une pause d'au moins "+(durre/2);
                            return res;
                        }
                    }    
                }
            }
            requete.executeUpdate("insert into LesPilotesVol_Projet values ("+numPersonnel+",'"+NumeroVolGestion+"')");
            requete.close();
        } catch (SQLException ex) {
            Logger.getLogger(LogIn_Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }
    
    
    public static String Ajouter_Hotesse(String numPersonnel){
        String res="Hotesse Ajouter";
        try {
            Statement requete = conn.createStatement();
            ResultSet resultat = requete.executeQuery( "SELECT MAX(dateDepart) as min FROM LesHotessesVol_Projet HV join LesVols_Projet V on (HV.numVol=V.numVol) where HV.numPersonnel="+numPersonnel+" and V.dateDepart<TO_DATE('"+date_Detail_VOL+"', 'YYYY-MM-DD HH24:MI:SS')");
            if(resultat.next()){
                String d = "";
                String min=resultat.getString("min");
                if(min!=null){
                    for(int i=0;i<min.length()-2;i++){
                        d+=min.charAt(i);
                    }
                    resultat = requete.executeQuery( "SELECT V.durree,V.aeroportDestination FROM LesHotessesVol_Projet HV join LesVols_Projet V on (HV.numVol=V.numVol) where HV.numPersonnel="+numPersonnel+" and V.dateDepart=TO_DATE('"+d+"', 'YYYY-MM-DD HH24:MI:SS')");
                    if(resultat.next()){
                        String Destination = resultat.getString("aeroportDestination");
                        if(!Destination.equals(aeroportOrigine_Detail_VOL)){
                            res ="Le dernier vol de "+numPersonnel+" à comme destenation "+Destination+"!\n";
                            res +="Vous ne pouvez pas l'ajouter a ce vol d'origine de "+aeroportOrigine_Detail_VOL+".";
                            return res;
                        }
                        int durre = resultat.getInt("durree");
                        if(!changedateFormat(d,date_Detail_VOL,durre)){
                            res ="Le dernier vol de "+numPersonnel+" à commencer à "+d+" d'une durre de "+durre+"\n";
                            res +="Vous ne pouvez pas l'ajouter a ce vol qui commence à "+date_Detail_VOL+"\n";
                            res +="Le Pilote "+numPersonnel+" doit faire une pause d'au moins "+(durre/2);
                            return res;
                        }
                    }    
                }
            }
            requete.executeUpdate("insert into LesHotessesVol_Projet values ("+numPersonnel+",'"+NumeroVolGestion+"')");
            requete.close();
        } catch (SQLException ex) {
            Logger.getLogger(LogIn_Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }
    
    public static boolean changedateFormat(String datedep,String datedep2,int durre){
        String A="";
        for(int i=0;i<4;i++) A+= datedep.charAt(i);
        String M="";
        for(int i=5;i<7;i++) M+= datedep.charAt(i);
        String D="";
        for(int i=8;i<10;i++) D+= datedep.charAt(i);
        String H="";
        for(int i=11;i<13;i++) H+= datedep.charAt(i);
        
        String A2="";
        for(int i=0;i<4;i++) A2+= datedep2.charAt(i);
        String M2="";
        for(int i=5;i<7;i++) M2+= datedep2.charAt(i);
        String D2="";
        for(int i=8;i<10;i++) D2+= datedep2.charAt(i);
        String H2="";
        for(int i=11;i<13;i++) H2+= datedep2.charAt(i);
        
        if(parseInt(A) < parseInt(A2)){
            return true;
        }else if(parseInt(A) == parseInt(A2)){
            if(parseInt(M) < parseInt(M2)){
                return true;
            }else if(parseInt(M) == parseInt(M2)){
                if(parseInt(D) < parseInt(D2)){
                    return true;
                }else if(parseInt(D) == parseInt(D2)){
                    if(parseInt(H) < parseInt(H2)-durre){
                        return true;
                    }
                    return false;
                }
                return false;
            }
            return false;
        }
        return false;
    }
    

}


