package Modele;
import javafx.beans.property.SimpleStringProperty;

public class Vol {
        public SimpleStringProperty Numtext;
        public SimpleStringProperty Fromtext;
        public SimpleStringProperty Totext;
        public SimpleStringProperty date_Departtext;
        public SimpleStringProperty nb_placetext;
        public SimpleStringProperty durre_Maxtext;
        public SimpleStringProperty Distancetext;
;

        public Vol(String Ntext,String Fromtext,String Totext,String date_Departtext,String durre_Maxtext,String Distancetext,String nb_placetext){
            this.Numtext=new SimpleStringProperty(Ntext);
            this.Fromtext=new SimpleStringProperty(Fromtext);
            this.Totext=new SimpleStringProperty(Totext);
            this.date_Departtext=new SimpleStringProperty(date_Departtext);
            this.nb_placetext=new SimpleStringProperty(nb_placetext);
            this.durre_Maxtext=new SimpleStringProperty(durre_Maxtext);
            this.Distancetext=new SimpleStringProperty(Distancetext);
        }


    public String getNumtext() {
        return Numtext.get();
    }

        
        public String getFromtext() {
            return Fromtext.get();
        }

        public String getTotext() {
            return Totext.get();
        }

        public String getDate_Departtext() {
            return date_Departtext.get();
        }

        public String getNb_placetext() {
            return nb_placetext.get();
        }

        public String getDurre_Maxtext() {
            return durre_Maxtext.get();
        }

        public String getDistancetext() {
            return Distancetext.get();
        }



}
