package Cabanator.domain.cabanon.toit;

import Cabanator.domain.cabanon.BaseDimensions;
import Cabanator.domain.cabanon.CabanonController;
import java.awt.Point;
import java.awt.Polygon;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import Cabanator.domain.util;


public class Toit implements Serializable{
    
    private double longueurToit;
    private double taillePorteAFaux;
    private double angleToit;
    private double angleHautToit;
    private double distanceFerme;
    private final util util = new util();
    private int epaisseur = util.toPixel(4);
    private int centreToitPointX;
    private Polygon coteGaucheToit = new Polygon();
    private Polygon coteDroitToit = new Polygon();
    private Polygon baseToit = new Polygon();
    private Polygon fermeMilieuToit = new Polygon();
    private Polygon poutresGaucheToit = new Polygon();
    private Polygon poutresDroiteToit = new Polygon();
    private double hypotenuseCoteTotalVueDevant;
    private double hypotenuseSansPorteAFaux;
    private double longueurBase;
    private int pointeurEpaisseur;
    private int compteurPoutresDuCote = 0;
    private ArrayList<Polygon> stackPoutressCoteGaucheToit = new ArrayList<Polygon>();
    private ArrayList<Polygon> stackPoutressCoteDroitToit = new ArrayList<Polygon>();
    private int transfertY1;
    private int transfertY2;
    private double maxDimensions;
    

    
    public Toit(double longueur, double angle, double taillePorteAFaux, double distanceFerme, double maxDimensions) {
        this.longueurBase = longueur;
        this.angleToit = angle;
        this.maxDimensions = maxDimensions;
        this.taillePorteAFaux = taillePorteAFaux;
        this.distanceFerme = distanceFerme;
        //this.angleHautToit = 180 - (2*angleToit);
        //this.centreToitPointX = (int)(longueurBase/2+taillePorteAFaux);
        //this.hypotenuseCoteTotalVueDevant = (longueurBase/2)*Math.cos(Math.toRadians(angleToit))+ taillePorteAFaux;
        //this.hypotenuseSansPorteAFaux = (longueurBase/2)*Math.cos(Math.toRadians(angleToit));
        //this.pointeurEpaisseur = (int)(epaisseur/Math.sin(Math.toRadians(angleHautToit/2)));
    }
    
    
    public void setAngleToit(double angle){
        this.angleToit = angle;
    }
    
    public double getAngleToit(){
        return angleToit;
    }
    
    public double getDistanceFerme()
    {
        return distanceFerme;
    }
    
    public double getLongueurBase()
    {
        return longueurBase;
    }
    
    public void setLongueurBase(double longueur)
    {
        longueurBase = longueur;
    }
    
    public void setAngleHautToit(double angle){
        this.angleHautToit = angle;
    }
    
    public void setTaillePorteAFaux(double taille){
        this.taillePorteAFaux = taille;
    }
    
    public double getTaillePorteAFaux(){
        return taillePorteAFaux;
    }
    
    public void setDistanceFerme(double distance){
        this.distanceFerme = distance;
    }
    
    public void setCentreToitPointX(double longueurBase){
        //this.centreToitPointX = (int)(longueurBase/2+taillePorteAFaux);        
    }
    
    public void setPointeurEpaisseur (double angleHautToit){
        //this.pointeurEpaisseur = ;
    }
    
    public void setHypotenuseCoteTotalVueDevant (double longueurBase, double angleToit){
        //this.hypotenuseCoteTotalVueDevant = ;
    }
    
    public void setHypotenuseSansPorteAFaux (double longueurBase, double angleToit){
        //this.hypotenuseSansPorteAFaux = ;
    }
         
    private int getPointeurEpaisseur(){
        return (int)(epaisseur/Math.sin(Math.toRadians(90 - angleToit)));
    }
    
    public double getHauteurPoutre()
    {
        return util.toInch(getLongueurHypotenuse())*Math.sin(Math.toRadians(angleToit));
    }
    
    
    public double getLongueurHypotenuse(){
        return util.toPixel((longueurBase/2+ taillePorteAFaux)/Math.cos(Math.toRadians(angleToit)));
    }
    
    public double getHypotenuseSansPorteAFaux(){
        return util.toPixel(((longueurBase)/2)/Math.cos(Math.toRadians(angleToit)));
    }
    
    public int getCentreToitPointX(){
        return util.toPixel(longueurBase/2+taillePorteAFaux);
    }
    
    public void setCoteGaucheToit(int lon){
        coteGaucheToit.reset();
        coteGaucheToit.addPoint (lon/2 ,                                0);
        coteGaucheToit.addPoint (lon/2 ,                                getPointeurEpaisseur());
        int extremeGauche = (int)(lon/2 - getCentreToitPointX());
        coteGaucheToit.addPoint (extremeGauche ,                       (int)(getLongueurHypotenuse()*Math.sin(Math.toRadians(angleToit))));
        coteGaucheToit.addPoint (extremeGauche,                        (int)((getLongueurHypotenuse()*Math.sin(Math.toRadians(angleToit))))- getPointeurEpaisseur());

    }
    
    public void setCoteDroitToit(int lon){
        coteDroitToit.reset();
        coteDroitToit.addPoint (lon/2,                                  0);
        coteDroitToit.addPoint (lon/2,                                  getPointeurEpaisseur());
        int extremeDroite = (int)(lon/2 + getCentreToitPointX());
        coteDroitToit.addPoint (extremeDroite,                         (int)(this.getLongueurHypotenuse()*Math.sin(Math.toRadians(angleToit))));
        coteDroitToit.addPoint (extremeDroite,                         (int)((this.getLongueurHypotenuse()*Math.sin(Math.toRadians(angleToit)))- getPointeurEpaisseur()));
    }
    
    public void setBaseToit(int lon){
        baseToit.reset();
        baseToit.addPoint(lon/2 + (int)(this.getHypotenuseSansPorteAFaux()*Math.cos(Math.toRadians(angleToit)))-2,   (int)(this.getHypotenuseSansPorteAFaux()*Math.sin(Math.toRadians(angleToit))));
        baseToit.addPoint(lon/2 - (int)(this.getHypotenuseSansPorteAFaux()*Math.cos(Math.toRadians(angleToit)))+2,   (int)(this.getHypotenuseSansPorteAFaux()*Math.sin(Math.toRadians(angleToit))));
        baseToit.addPoint(lon/2 - (int)(this.getHypotenuseSansPorteAFaux()*Math.cos(Math.toRadians(angleToit)))+2+ (int)((epaisseur/2)/Math.sin(Math.toRadians(angleToit))*Math.sin(Math.toRadians(90-angleToit))),   (int)(this.getHypotenuseSansPorteAFaux()*Math.sin(Math.toRadians(angleToit)))- (epaisseur/2));
        baseToit.addPoint(lon/2 + (int)(this.getHypotenuseSansPorteAFaux()*Math.cos(Math.toRadians(angleToit)))-2 - (int)((epaisseur/2)/Math.sin(Math.toRadians(angleToit))*Math.sin(Math.toRadians(90-angleToit))),   (int)(this.getHypotenuseSansPorteAFaux()*Math.sin(Math.toRadians(angleToit)))- epaisseur/2);
      
    }
    
    public Polygon getCoteGaucheToit(){
        return coteGaucheToit;
    }
    
    public Polygon getCoteDroitToit(){
        return coteDroitToit;
    }
    
    public Polygon getBaseToit(){
        return baseToit;
    }
    
    //Pour Toit Dessus
    public double getLongueurToit()
    {
        return this.longueurToit;
    }
    public void clearPolygons(){
        baseToit.reset();
        coteDroitToit.reset();
        coteGaucheToit.reset();
    }
    
    //Calcul des fermes
    public void setPoutreMilieu(int lon){
        fermeMilieuToit.reset();
        fermeMilieuToit.addPoint(lon/2 + (getPointeurEpaisseur()/2)/2 , (int)(getPointeurEpaisseur()+( util.toPixel(2) )*Math.tan(Math.toRadians(this.getAngleToit()))));
        fermeMilieuToit.addPoint(lon/2 , getPointeurEpaisseur());
        fermeMilieuToit.addPoint(lon/2 - (getPointeurEpaisseur()/2)/2 , (int)(getPointeurEpaisseur()+( util.toPixel(2) )*Math.tan(Math.toRadians(this.getAngleToit()))));
        fermeMilieuToit.addPoint(lon/2 - (getPointeurEpaisseur()/2)/2 , (int)(this.getHypotenuseSansPorteAFaux()*Math.sin(Math.toRadians(this.getAngleToit())))- util.toPixel(2));
        fermeMilieuToit.addPoint(lon/2 + (getPointeurEpaisseur()/2)/2 , (int)(this.getHypotenuseSansPorteAFaux()*Math.sin(Math.toRadians(this.getAngleToit())))- util.toPixel(2));
    }
    
    public Polygon getPoutreMilieu(){
        return this.fermeMilieuToit;
    }
    
    public void setPoutresSurplus(){
        // !!! Remplacer << 16 >> par la donnée du mainWindow !!!
        if (getHypotenuseSansPorteAFaux() > util.toPixel(maxDimensions)){
           compteurPoutresDuCote = (int) Math.floor(getHypotenuseSansPorteAFaux() / util.toPixel(maxDimensions));
        }
        else 
            compteurPoutresDuCote = 0;
    }
    
    public int getCompteurPoutresDuCote (){
        return compteurPoutresDuCote;
    }
    
    
    
        // !!! Remplacer << 16 >> par la donnée du mainWindow !!!

    public void setPoutresGauche(int lon){
        this.stackPoutressCoteGaucheToit.clear();
        setPoutresSurplus();
        int dimMax = util.toPixel(maxDimensions);
        int longueurEntrePoutre = (int)(dimMax*Math.cos(Math.toRadians(angleToit)));

        if (getCompteurPoutresDuCote()>0){

            int temp = 0;
            int test = getCompteurPoutresDuCote();           
            while (temp < test){
                Polygon tempPoly = new Polygon();

                int xGauche = ((lon/2) - util.toPixel(longueurBase/2) + longueurEntrePoutre*(temp+1) + getPointeurEpaisseur()/2);
                int xDroite = ((lon/2) - util.toPixel(longueurBase/2) + longueurEntrePoutre*(temp+1) - getPointeurEpaisseur()/2);
                //Base
                tempPoly.addPoint(xGauche , (int)(getHypotenuseSansPorteAFaux()*Math.sin(Math.toRadians(angleToit)))- util.toPixel(2));
                tempPoly.addPoint(xDroite , (int)(getHypotenuseSansPorteAFaux()*Math.sin(Math.toRadians(angleToit)))- util.toPixel(2));
                //Top
                tempPoly.addPoint(xDroite , (int)((lon/2 - xDroite)*Math.tan(Math.toRadians(getAngleToit())))+ getPointeurEpaisseur()/2*(temp+1));
                tempPoly.addPoint(xGauche , (int)((lon/2 - xGauche)*Math.tan(Math.toRadians(getAngleToit())))+ getPointeurEpaisseur()/2*(temp+1));
                stackPoutressCoteGaucheToit.add(tempPoly);
                temp++;
            } 
        }
    }
    
    public void setPoutresDroite(int lon){
       
        this.stackPoutressCoteDroitToit.clear();
        setPoutresSurplus();
        int dimMax = util.toPixel(maxDimensions);
        int longueurEntrePoutre = (int)(dimMax*Math.cos(Math.toRadians(angleToit)));
        
            int temp = 0;
            
            int test = getCompteurPoutresDuCote();
            if (getCompteurPoutresDuCote() > 0){              
                while (temp < test){
                    Polygon tempPoly = new Polygon();
                    int xGauche = ((lon/2) + util.toPixel(longueurBase/2) - longueurEntrePoutre*(temp+1) + getPointeurEpaisseur()/2);
                    int xDroite = ((lon/2) + util.toPixel(longueurBase/2) - longueurEntrePoutre*(temp+1) - getPointeurEpaisseur()/2);
                    // Base
                    tempPoly.addPoint(xGauche , (int)(getHypotenuseSansPorteAFaux()*Math.sin(Math.toRadians(angleToit)))- util.toPixel(2));
                    tempPoly.addPoint(xDroite , (int)(getHypotenuseSansPorteAFaux()*Math.sin(Math.toRadians(angleToit)))- util.toPixel(2));
                    //Top
                    tempPoly.addPoint(xDroite , (int)((lon/2 - ((lon/2) - util.toPixel(longueurBase/2) + longueurEntrePoutre*(temp+1) + getPointeurEpaisseur()/2))*Math.tan(Math.toRadians(getAngleToit())))+ getPointeurEpaisseur()/2*(temp+1));
                    tempPoly.addPoint(xGauche , (int)((lon/2 - ((lon/2) - util.toPixel(longueurBase/2) + longueurEntrePoutre*(temp+1) - getPointeurEpaisseur()/2))*Math.tan(Math.toRadians(getAngleToit())))+ getPointeurEpaisseur()/2*(temp+1));

                    stackPoutressCoteDroitToit.add(tempPoly);

                    temp++;            
                }
            }
    }
    public ArrayList<Polygon> getPoutresCoteGaucheToit(){
        return stackPoutressCoteGaucheToit;
    }
    
    public ArrayList<Polygon>  getPoutresCoteDroitToit(){
        return stackPoutressCoteDroitToit;
    }
    
    public void destroyPoutres(int lon) {
        this.stackPoutressCoteDroitToit.clear();
        this.stackPoutressCoteGaucheToit.clear();

        setPoutresDroite(lon);
        setPoutresGauche(lon);
    }
}
