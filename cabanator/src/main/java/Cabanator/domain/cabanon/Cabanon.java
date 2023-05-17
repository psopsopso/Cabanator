package Cabanator.domain.cabanon;

import Cabanator.domain.cabanon.mur.EntremisesMur;
import Cabanator.domain.cabanon.plancher.Plancher;
import Cabanator.domain.cabanon.mur.Mur;
import Cabanator.domain.cabanon.mur.Ouverture;
import Cabanator.domain.cabanon.toit.Toit;
import Cabanator.domain.cabanon.toit.ToitDessus;
import Cabanator.domain.util;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
//import Cabanator.domain.cabanon.toit.Toit;
import java.util.List;

public class Cabanon implements Cloneable, Serializable{

    private double longueur = 192;
    private double largeur = 192;
    private double hauteur = 192;
    private double distanceSolive = 48;
    private double distanceMontant = 24;
    private double maxDimensions = 192;
    private double taillePorteAFaux = 24;

    private double angleToit = 45;

    //toit dessus
    private double distanceFerme = 24;
    private double distanceExtremite = 24;
    private double distanceEntremise = 24;

    private ArrayList<Mur> murList;
    private Plancher plancher;
    private Mur mur;
    private Toit toit;
    private ToitDessus toitDessus;
    private final util util = new util();

    public Cabanon() {
        plancher = new Plancher(0.0, 0.0, longueur, largeur, maxDimensions, distanceSolive);
        toit = new Toit(longueur, angleToit, taillePorteAFaux, distanceFerme, maxDimensions);
        toitDessus = new ToitDessus(0,0,longueur, largeur,maxDimensions, distanceFerme, distanceEntremise, distanceExtremite, taillePorteAFaux );
        murList = new ArrayList<Mur>();

        murList.add(new Mur(0.0, 0.0, largeur, hauteur, maxDimensions, distanceMontant, "Est", new ArrayList<Ouverture>()));
        murList.add(new Mur(0.0, 0.0, largeur, hauteur, maxDimensions, distanceMontant, "Ouest", new ArrayList<Ouverture>()));
        murList.add(new Mur(0.0, 0.0, longueur, hauteur, maxDimensions, distanceMontant, "Sud", new ArrayList<Ouverture>()));
        murList.add(new Mur(0.0, 0.0, longueur, hauteur, maxDimensions, distanceMontant, "Nord", new ArrayList<Ouverture>()));
    }

    public Plancher getPlancher() {
        return plancher;
    }

    public ArrayList<Mur> getMurs() {
        return murList;
    }
    
    public Toit getToit()
    {
        return toit;
    }
    
    public ToitDessus getToitDessus()
    {
        return toitDessus;
    }

    public void setDistanceSolive(double distanceSolive) {
        this.distanceSolive = distanceSolive;
    }

    public void setCabanonHauteur(double hauteur) {
        this.hauteur = hauteur;
        for(Mur mur: getMurs()){
            mur.wallBlocks();
        mur.destroyMontants();}
    }

    public void setCabanonLongueur(double longueur) {
        this.longueur = longueur;
        getPlancher().floorBlocks();
        for(Mur mur: getMurs()){
        mur.wallBlocks();
        mur.destroyMontants();}
        getToit().setLongueurBase(longueur);
        getToitDessus().destroyFermes();
    }

    public void setCabanonLargeur(double largeur) {
        this.largeur = largeur;
        getPlancher().floorBlocks();
        for(Mur mur: getMurs()){
        mur.wallBlocks();
        mur.destroyMontants();}
        getToitDessus().destroyFermes();
    }
    
    public void setTaillePorteAFaux(double taille)
    {
        this.taillePorteAFaux = taille;
    }
    
    public void setTaillePiece(double taille)
    {
        this.maxDimensions = taille;
        getPlancher().setMaxDimensions(taille);
        getPlancher().floorBlocks();
        for(Mur mur: getMurs())
        {
            mur.setMaxDimensions(taille);
            mur.wallBlocks();
        }
    }

    public double getCabanonHauteur() {
        return hauteur;
    }

    public double getCabanonLargeur() {
        return largeur;
    }

    public double getCabanonLongueur() {
        return longueur;
    }

    /*public double getDistanceSolive() {
        return distanceSolive;
    }*/

    /*public int getNbSolive() {
        return plancher.getNbSolive(largeur, distanceSolive);
    }*/

    public String getOrientation(String orientation) {
        return getSelectedMur(orientation).getOrientation();
    }

    public double getDistanceMontant(String orientation) {
        return getSelectedMur(orientation).getDistanceMontantMur();
    }

    /*public int getNbMontants(String orientation) {
        return getSelectedMur(orientation).getNbMontants(longueur, distanceMontant);
    }*/

    public ArrayList<Ouverture> getOuvertures(Mur mur) {
        return this.mur.getOuvertures();
    }
    
    public ArrayList<EntremisesMur> getEntremiseMur(Mur mur)
    {
        return this.mur.getEntremisesMur();
    }

    public Mur getSelectedMur(String orientation) {
        String searchValue = orientation;
        if (orientation.length() > 5) {
            searchValue = orientation.substring(4);
        }
        Mur selectedMur = null;
        for (Mur mur : murList) {
            if (mur.getOrientation().toUpperCase().equals(searchValue.toUpperCase())) {
                selectedMur = mur;
                break;
            }
        }
        return selectedMur;
    }
    
    public double getWidthSolive()
    {
        return getPlancher().getwidthSolive();
    }
    
    public double getMaxDimension()
    {
        return maxDimensions;
    }
    
    
    /*public void determinerSection(Point point) {
        // method code here
    }*/
 /*public float calculerPrix(List<Piece> piecesList) {
        float prix = 0.0f;
        // code to calculate the price based on the pieces in the list
        return prix;
    }*/
    // getter and setter methods for the instance variables
    
    public Cabanon deepClone() {
        try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(this);

                ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
                ObjectInputStream ois = new ObjectInputStream(bais);
                return (Cabanon) ois.readObject();
        } catch (IOException e) {
                return null;
        } catch (ClassNotFoundException e) {
                return null;
        }
    }
    
    public int getNb2x4()
    {
        double nb2x4 = 0;
        
        for(Mur listemur : getMurs())
        {
            for(Mur blocsmur : listemur.getMursBlocs())
            {
                nb2x4 += (blocsmur.getLongueur()/maxDimensions)*2;
                nb2x4 += ((blocsmur.getLargeur()-4)/maxDimensions)*2;
            }
            for(Ouverture ouv : listemur.getOuvertures())
            {
                nb2x4 += (ouv.gethauteurOuverture()/maxDimensions)*6;
                if(ouv.getDistanceMontantOuv() != 0 || !(ouv.getDistanceMontantOuv()>ouv.getlongueurOuverture()) )
                    nb2x4 += (ouv.getlongueurOuverture()/ouv.getDistanceMontantOuv())*(ouv.gethauteurOuverture()/maxDimensions);
            }
            for(EntremisesMur ent : listemur.getEntremisesMur())
            {
                nb2x4 += ent.getLongueur()/maxDimensions;
            }
            
            int nbMontant = listemur.getMontantsMur().size();
            nb2x4 += nbMontant;
        }
        
        nb2x4 += toit.getHauteurPoutre()/maxDimensions * toitDessus.getNbFermes();
        
        int nb2x4Final = (int)Math.ceil(nb2x4);

        return nb2x4Final;
    }
    
    public int getNb2x4Ferme()
    {
        double nb2x4Ferme = 0;
        
        nb2x4Ferme += util.toInch(toit.getLongueurHypotenuse()*2)/maxDimensions*toitDessus.getNbFermes();

        int nb2x4FermeFinal = (int)Math.ceil(nb2x4Ferme);
        
        return nb2x4FermeFinal;
    }
    
    public int getNb2x4Soutien()
    {
        double nb2x4Soutien = 0;
        
        nb2x4Soutien += toit.getLongueurBase()/maxDimensions*toitDessus.getNbFermes();

        int nb2x4SoutienFinal = (int)Math.ceil(nb2x4Soutien);
        
        return nb2x4SoutienFinal;
    }
    
    public int getNb2x6()
    {
        double nb2x6 = 0;
        for(Plancher blocs : getPlancher().getPlanchersBlocs())
        {
            nb2x6 += (blocs.getLongueur()/maxDimensions)*2;
            nb2x6 += ((blocs.getLargeur()-4)/maxDimensions)*2;
            int nbSolive = (int)Math.floor((blocs.getLargeur()-4) / getPlancher().getDistEntreSolive());
            nb2x6 += nbSolive * (blocs.getLongueur()/maxDimensions);
        }

        nb2x6 += getPlancher().getEntremise().size() * (largeur / maxDimensions);

        int nb2x6Final = (int)Math.ceil(nb2x6);
        
        return nb2x6Final;
    }

    public int getNb2x8()
    {
        double nb2x8 = 0;
        
        for(Mur listemur : getMurs())
        {
            for(Ouverture ouverture : listemur.getOuvertures())
            {
                if(ouverture.getHauteurLinteau() == 8)
                {
                    nb2x8 += ouverture.getlongueurOuverture()/maxDimensions;
                }
            }
        }
        
        int nb2x8Final = (int)Math.ceil(nb2x8);
        
        return nb2x8Final;
    }
        
    public int getNb2x10()
    {
        double nb2x10 = 0;
        
        for(Mur listemur : getMurs())
        {
            for(Ouverture ouverture : listemur.getOuvertures())
            {
                if(ouverture.getHauteurLinteau() == 10)
                {
                    nb2x10 += ouverture.getlongueurOuverture()/maxDimensions;
                }
            }
        }
        
        int nb2x10Final = (int)Math.ceil(nb2x10);
        
        return nb2x10Final;
    }
    
}
