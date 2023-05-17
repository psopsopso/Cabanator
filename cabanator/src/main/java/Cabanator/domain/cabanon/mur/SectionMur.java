package Cabanator.domain.cabanon.mur;

import java.awt.Point;
import java.io.Serializable;
import java.util.List;

public class SectionMur implements Serializable{
    // Max 16' x 16'
    // Entremise entre montant
  
    // Fenêtre et porte ne peut pas être collé sur le côté de la section de mur
    
    //int quantiteEntremiseMur;
    private double longueurSectionMur;
    private double largeurSectionMur;
    private Point point;

    public void sectionMur(Point point, double longueurSection, double largeurSection)
    {
        this.point = point;
        this.longueurSectionMur = longueurSection;
        this.largeurSectionMur = largeurSection;
    }
 
    public Point getPointMur()
    {
        return this.point;
    }
    
    public double getlongueurSectionMur()
    {
        return this.longueurSectionMur;
    }
    
    public double getlargeurSectionMur()
    {
        return this.largeurSectionMur;
    }
    
    public void setLongueurSectionMur(double longueurSecMur)
    {
        this.longueurSectionMur = longueurSecMur;
    }
    
    public void setLargeurSectionMur(double largeurSecMur)
    {
        this.largeurSectionMur = largeurSecMur;
    }
    
    
    
    public void determinerSectionMur(Point point){
        // Updater le void
    }
    
}


