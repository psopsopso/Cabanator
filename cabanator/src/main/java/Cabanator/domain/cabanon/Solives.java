package Cabanator.domain.cabanon;

import java.util.ArrayList;
import Cabanator.domain.cabanon.plancher.Plancher;
import Cabanator.domain.cabanon.mur.Mur;
import java.io.Serializable;
// import Cabanator.domain.cabanon.toit.Toit;

public class Solives extends ArrayList<Entremises> implements Serializable{

    
    private double longueur;
    private float hauteur = 4;
    private float largeur = 2;      
    private String className = Thread.currentThread().getClass().getName();
    private Plancher plancher;
    private Mur mur;
    // private Toit toit;
    
    
    public Solives(){
         
      if (className == "Plancher"){                                  
        this.longueur = plancher.getLongueur();
      }
      if (className == "Mur"){
        this.longueur = mur.getLongueur();
      }
      if (className == "Toit"){
        // this.longueur = toit.getLongueur();
      }
      
    }
    
}
