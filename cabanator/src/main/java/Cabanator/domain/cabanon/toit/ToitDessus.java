package Cabanator.domain.cabanon.toit;


import Cabanator.domain.cabanon.BaseDimensions;
import Cabanator.domain.cabanon.CabanonController;
import Cabanator.domain.cabanon.mur.Montants;
import Cabanator.domain.util;
import java.io.Serializable;
import java.util.ArrayList;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.Rectangle2D;

public class ToitDessus extends BaseDimensions implements Serializable {
    private double distEntreEntremise;
    private double distEntreFermes;
    private double distanceExtremite;
    private final util util = new util();
    private int nbFermes;
    private int nbEntremise;
    private double taillePorteAFaux;
    private int widthSolive = 2;
    private CabanonController con;
    private ArrayList<Montants> ferme = new ArrayList<>();



    public ToitDessus(double x, double y, double longueur, double largeur, double maxDimensions, double distEntreFerme, double distEntreEntremise, double distanceExtremite, double porteAFaux) {
        super(x, y, longueur, largeur, maxDimensions);
        this.distEntreFermes = distEntreFerme;
        this.distEntreEntremise = distEntreEntremise;
        this.distanceExtremite = distanceExtremite;
        this.taillePorteAFaux = porteAFaux;
    }

    public double getDistEntreFermes() {
        return this.distEntreFermes;
    }

    public void setdistEntreFermes(double dist) {
        this.distEntreFermes = dist;
    }
    
    public void setPorteAFaux(double taille)
    {
        this.taillePorteAFaux = taille;
    }
    
    public double getDistEntreEntremise() {
        return this.distEntreEntremise;
    }
    
    public void setDistanceEntremiseToit(double dist) {
        this.distEntreEntremise = dist;
    }
    
    public double getDistanceExtremite() {
        return this.distanceExtremite;
    }

    public void setDistanceExtremite(double dist) {
        this.distanceExtremite = dist;
    }
    
    public int getNbFermes() {

        nbFermes = (int) (Math.floor((this.longueur-4) / this.distEntreFermes))+2;

        return nbFermes;
     }
    
    public int getNbEntremise() {

        nbEntremise = (int) (Math.floor(this.largeur / this.distEntreFermes));

        return nbEntremise;
     }
    
    public ArrayList<Montants> getFermesMur() {
        return this.ferme;
    }

    public void generateFermes(){
        int debut = util.toPixel(distanceExtremite+2);
        int fin = debut + util.toPixel(longueur+distanceExtremite+4);
        int PorteAFaux = util.toPixel(taillePorteAFaux);
        
        // DESSINER LA FERME EXTREMITÉ DEBUT
        Montants fermeExtremeDebutHaut = new Montants(0, 0,  util.toPixel((largeur+ taillePorteAFaux*2)/2),util.toPixel(2), maxDimensions);
        Montants fermeExtremeDebutBas = new Montants(0, util.toPixel((largeur+ taillePorteAFaux*2)/2),  util.toPixel((largeur+ taillePorteAFaux*2)/2),util.toPixel(2), maxDimensions);
        ferme.add(fermeExtremeDebutHaut);
        ferme.add(fermeExtremeDebutBas);
        
        // DESSINER LA PREMIERE FERME
        Montants fermeDebutHaut = new Montants(debut, 0,  util.toPixel((largeur+ taillePorteAFaux*2)/2),util.toPixel(2), maxDimensions);
        Montants fermeDebutBas = new Montants(debut, util.toPixel((largeur+ taillePorteAFaux*2)/2),  util.toPixel((largeur+ taillePorteAFaux*2)/2),util.toPixel(2), maxDimensions);
        ferme.add(fermeDebutHaut);
        ferme.add(fermeDebutBas);
        
        // DESSINER ENTREMISE DEBUT
        for (double i = PorteAFaux; i < util.toPixel((largeur+ taillePorteAFaux*2)-2)-(PorteAFaux); i += distEntreEntremise)
        {
            Montants newEntremise = new Montants(util.toPixel(2), i,  util.toPixel(2),util.toPixel(distanceExtremite), maxDimensions);
            ferme.add(newEntremise);
        }
        Montants newEntremise = new Montants(util.toPixel(2), util.toPixel((largeur+ taillePorteAFaux*2))-(PorteAFaux),  util.toPixel(2),util.toPixel(distanceExtremite), maxDimensions);
        ferme.add(newEntremise);

        // DESSINER LES FERMES ENTRE PREMIERE ET DERNIERE
        if (distEntreFermes != 0) {  
            for(double i= debut + util.toPixel(distEntreFermes) ; i < debut + util.toPixel(longueur); i+=util.toPixel(distEntreFermes))
            {
                Montants newFerme = new Montants(i, 0,  util.toPixel((largeur+ taillePorteAFaux*2)/2),util.toPixel(2), maxDimensions);
                ferme.add(newFerme);
                Montants newFermeBas = new Montants(i, util.toPixel((largeur+ taillePorteAFaux*2)/2),  util.toPixel((largeur+ taillePorteAFaux*2)/2),util.toPixel(2), maxDimensions);
                ferme.add(newFermeBas);
            }
        }
        // DESSINER LA DERNIERE FERME
        Montants fermeFinHaut = new Montants(debut+(util.toPixel(longueur+2)), 0,  util.toPixel((largeur+ taillePorteAFaux*2)/2),util.toPixel(2), maxDimensions);
        Montants fermeFinBas = new Montants(debut+(util.toPixel(longueur+2)), util.toPixel((largeur+ taillePorteAFaux*2)/2),  util.toPixel((largeur+ taillePorteAFaux*2)/2),util.toPixel(2), maxDimensions);
        ferme.add(fermeFinHaut);
        ferme.add(fermeFinBas);
        
        // DESSINER ENTREMISE FIN
        for (double i = PorteAFaux; i < util.toPixel((largeur+ taillePorteAFaux*2)-2)-(PorteAFaux); i += distEntreEntremise)
        {
            Montants newEntremiseFin = new Montants(util.toPixel(2+longueur+distanceExtremite+4), i,  util.toPixel(2),util.toPixel(distanceExtremite), maxDimensions);
            ferme.add(newEntremiseFin);
        }
        Montants newEntremiseFin = new Montants(util.toPixel(2+longueur+distanceExtremite+4), util.toPixel((largeur+ taillePorteAFaux*2))-(PorteAFaux),  util.toPixel(2),util.toPixel(distanceExtremite), maxDimensions);
        ferme.add(newEntremiseFin);
        
        // DESSINER LA FERME EXTREMITÉ FIN
        Montants fermeExtremeFinHaut = new Montants(fin, 0,  util.toPixel((largeur+ taillePorteAFaux*2)/2),util.toPixel(2), maxDimensions);
        Montants fermeExtremeFinBas = new Montants(fin, util.toPixel((largeur+ taillePorteAFaux*2)/2),  util.toPixel((largeur+ taillePorteAFaux*2)/2),util.toPixel(2), maxDimensions);
        ferme.add(fermeExtremeFinHaut);
        ferme.add(fermeExtremeFinBas);
    }
    public void generateEntremiseToit(){
        int nbEntremise = (int) getNbEntremise();
    }
    
    public void destroyFermes() {
        this.ferme.clear();
        generateFermes();
    }
}