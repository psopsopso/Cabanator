package Cabanator.domain.cabanon.plancher;

import Cabanator.domain.cabanon.BaseDimensions;
import Cabanator.domain.cabanon.CabanonController;
import java.io.Serializable;
import java.util.ArrayList;
import Cabanator.domain.cabanon.Entremises;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.Rectangle2D;

public class Plancher extends BaseDimensions implements Serializable {

    private ArrayList<Plancher> blocsPlancher = new ArrayList<>();
    private ArrayList<Entremises> entremises = new ArrayList<Entremises>();
    private double distEntreSolive;
    private int nbSolive;
    private int widthSolive = 2;
    private Entremises selectedEnt;
    private CabanonController con;

    public Plancher(double x, double y, double longueurPlancher, double largeurPlancher, double maxDimensions, double distEntreSolive) {
        super(x, y, longueurPlancher, largeurPlancher, maxDimensions);
        this.distEntreSolive = distEntreSolive;
    }

    public double getDistEntreSolive() {
        return this.distEntreSolive;
    }

    public double getwidthSolive() {
        return this.widthSolive;
    }

    public void setdistEntreSolive(double dist) {
        this.distEntreSolive = dist;
    }

    public int getNbSolive() {

        nbSolive = (int) (Math.floor(this.largeur / this.distEntreSolive));

        return nbSolive;
    }

    public ArrayList<Entremises> getEntremise() {
        return this.entremises;
    }

    // +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-
    public void addEntremise(Point point, double longEntremise) {
        this.con = CabanonController.getInstance();
        int[][] plancherP = getCoordinates();
        Polygon polyPlancher = new Polygon(plancherP[0], plancherP[1], 4);
        Rectangle2D plancherBounds = polyPlancher.getBounds();
        Entremises entremise = new Entremises(point, longEntremise, (int) con.getCabanon().getPlancher().getY());
        if (plancherBounds.contains(point)) {
            con.updateCabanon();
            this.entremises.add(entremise);
        }
    }

    public void deleteEntremise(Point p) {
        for (Entremises ent : this.entremises) {
            int[][] test = ent.getEntremiseCoordinates();
            Polygon polyAcc = new Polygon(test[0], test[1], 4);
            Rectangle2D entBounds = polyAcc.getBounds();
            if (entBounds.contains(p)) {
                con.updateCabanon();
                this.entremises.remove(ent);
                return;
            }
        }
    }

    public Entremises getSelectedEntremise() {
        return this.selectedEnt;
    }

    public void selectEntremise(Point p) {
        for (Entremises ent : entremises) {
            int[][] test = ent.getEntremiseCoordinates();
            Polygon polyAcc = new Polygon(test[0], test[1], 4);
            Rectangle2D entBounds = polyAcc.getBounds();
            if (entBounds.contains(p)) {
                if (selectedEnt == null) {
                    ent.setStatutSelection();
                    selectedEnt = ent;
                    return;
                } else if (selectedEnt != ent) {
                    selectedEnt.setStatutSelection();
                    ent.setStatutSelection();
                    selectedEnt = ent;
                    return;
                } else {
                    selectedEnt.setStatutSelection();
                    selectedEnt = null;
                    return;
                }
            }
        }
    }

    public void updateSelectedItemsPosition(Point delta) {
        for (Entremises ent : entremises) {
            if (ent.getStatutSelection()) {
                ent.translate(delta);
            }
        }
    }

    public ArrayList<Plancher> getPlanchersBlocs() {
        return this.blocsPlancher;
    }

    public ArrayList<Plancher> floorBlocks() {
        blocsPlancher.clear();
        double blocHori = Math.floor(this.longueur / this.maxDimensions);
        double blocVerti = Math.floor(this.largeur / this.maxDimensions);
        double lastBlocHori = longueur % maxDimensions;
        double lastBlocVerti = largeur % maxDimensions;

        double remainingHori = this.longueur;
        double remainingVerti = this.largeur;
        int compteurBlocHori = 0;

        int compteurBlocVerti = 0;

        if ((longueur / maxDimensions) > 1 && (largeur / maxDimensions) > 1) {
            for (int i = 0; i <= longueur / maxDimensions;) {
                remainingHori = (remainingHori - (maxDimensions * i));
                remainingVerti = this.largeur;
                for (int j = 0; j <= largeur / maxDimensions;) {
                    remainingVerti = (largeur - (maxDimensions * j));
                    if (remainingVerti < maxDimensions && i < blocHori) {

                        Plancher plancherBloc = new Plancher(toPixel(maxDimensions) * i,
                                toPixel(maxDimensions) * j,
                                (maxDimensions),
                                (lastBlocVerti),
                                this.maxDimensions,
                                this.distEntreSolive);
                        this.blocsPlancher.add(plancherBloc);
                        j++;
                    } else if (i == blocHori && remainingVerti == maxDimensions) {
                        Plancher plancherBloc = new Plancher(toPixel(maxDimensions) * i,
                                toPixel(maxDimensions) * j,
                                (lastBlocHori),
                                (maxDimensions),
                                this.maxDimensions,
                                this.distEntreSolive);
                        this.blocsPlancher.add(plancherBloc);
                        j++;
                    } else if (i == blocHori && remainingVerti < maxDimensions) {
                        Plancher plancherBloc = new Plancher(toPixel(maxDimensions) * i,
                                toPixel(maxDimensions) * j,
                                (lastBlocHori),
                                (lastBlocVerti),
                                this.maxDimensions,
                                this.distEntreSolive);
                        this.blocsPlancher.add(plancherBloc);
                        j++;
                    } else if (i == blocHori && remainingVerti > maxDimensions) {
                        Plancher plancherBloc = new Plancher(toPixel(maxDimensions) * i,
                                toPixel(maxDimensions) * j,
                                (lastBlocHori),
                                (maxDimensions),
                                this.maxDimensions,
                                this.distEntreSolive);
                        this.blocsPlancher.add(plancherBloc);
                        j++;
                    } else if (i != blocHori && remainingVerti <= maxDimensions) {
                        Plancher plancherBloc = new Plancher(toPixel(maxDimensions) * i,
                                toPixel(maxDimensions) * j,
                                (maxDimensions),
                                (maxDimensions),
                                this.maxDimensions,
                                this.distEntreSolive);
                        this.blocsPlancher.add(plancherBloc);
                        j++;
                    } else {
                        Plancher plancherBloc = new Plancher(toPixel(maxDimensions) * i,
                                toPixel(maxDimensions) * j,
                                (maxDimensions),
                                (maxDimensions),
                                this.maxDimensions,
                                this.distEntreSolive);
                        this.blocsPlancher.add(plancherBloc);
                        remainingHori -= maxDimensions;
                        j++;
                    }
                }
                i++;

            }
        } else if ((largeur / maxDimensions) < 1.01 && (longueur / maxDimensions) < 1.01) {
            //1 bloc
            Plancher plancherBloc = new Plancher(0,
                    0,
                    (longueur),
                    (largeur),
                    this.maxDimensions,
                    this.distEntreSolive);
            this.blocsPlancher.add(plancherBloc);
        } else if ((longueur / maxDimensions) > 1.01 && (largeur / maxDimensions) < 1.01) {

            while (compteurBlocHori <= blocHori) {
                //HORI SEULEMENT
                if (remainingHori < maxDimensions) {
                    Plancher plancherBloc = new Plancher(
                            (compteurBlocHori * toPixel(maxDimensions)),
                            0,
                            (remainingHori),
                            (remainingVerti),
                            this.maxDimensions,
                            this.distEntreSolive);
                    this.blocsPlancher.add(plancherBloc);
                    compteurBlocHori++;
                } else {
                    Plancher plancherBloc = new Plancher(
                            (compteurBlocHori * toPixel(maxDimensions)),
                            0,
                            (maxDimensions),
                            (remainingVerti),
                            this.maxDimensions,
                            this.distEntreSolive);
                    compteurBlocHori++;
                    remainingHori -= maxDimensions;
                    this.blocsPlancher.add(plancherBloc);
                }
            }
        } else if ((largeur / maxDimensions) > 1.01 && (longueur / maxDimensions) < 1.01) {
            //verti seulement
            while (compteurBlocVerti <= blocVerti) {
                if (remainingVerti < maxDimensions) {
                    Plancher plancherBloc = new Plancher(
                            0,
                            (compteurBlocVerti * toPixel(maxDimensions)),
                            (remainingHori),
                            (remainingVerti),
                            this.maxDimensions,
                            this.distEntreSolive);
                    this.blocsPlancher.add(plancherBloc);
                    compteurBlocVerti++;
                } else {
                    Plancher plancherBloc = new Plancher(
                            0,
                            (compteurBlocVerti * toPixel(maxDimensions)),
                            (remainingHori),
                            (maxDimensions),
                            this.maxDimensions,
                            this.distEntreSolive);
                    compteurBlocVerti++;
                    remainingVerti -= maxDimensions;
                    this.blocsPlancher.add(plancherBloc);
                }

            }
        }
        return blocsPlancher;
    }

    /*public void fixBlocPlancher()
    {
        for(Plancher blocs : blocsPlancher)
        {
            blocs.setMaxDimensions(maxDimensions);
        }
    }*/
}

//        while(compteurBlocHori <= blocHori && remainingHori != 0)
//        {
//            int compteurBlocVerti = 0;
//            double fullHori = this.longueur;
//            double fullVerti = this.largeur;
//                if(blocVerti != 0 && lastBlocVerti != 0){
//                    //condition 6: MaxDimension x et y
//                  for(int j = 0; j<blocVerti ; j++){
//
//                    Plancher plancherBloc = new Plancher((((longueur - lastBlocHori) / blocHori) * compteurBlocHori),
//                            (((largeur - lastBlocVerti) / blocVerti) * j),
//                            (maxDimensions),
//                            (maxDimensions),
//                            this.maxDimensions,
//                            this.distEntreSolive );
//
//                    this.blocsPlancher.add(plancherBloc);
//                    fullVerti -= maxDimensions;
//                    remainingVerti -= maxDimensions;
//                    compteurBlocVerti++;
//                   }
//                }else if(remainingHori < maxDimensions && remainingVerti < maxDimensions ){
//                    //condition 3: dernier bloc ou plus petit
//                    Plancher plancherBloc = new Plancher(0,
//                            0,
//                            (remainingHori),
//                            (remainingVerti),
//                            this.maxDimensions,
//                            this.distEntreSolive );
//                    this.blocsPlancher.add(plancherBloc);
//                }else if(remainingHori > maxDimensions && remainingVerti < maxDimensions){
//                    //condition 4: SEULEMENT HORIZONTALE
//                    Plancher plancherBloc = new Plancher((((longueur - lastBlocHori) / blocHori) * compteurBlocHori),
//                            0,
//                            (maxDimensions),
//                            (remainingVerti),
//                            this.maxDimensions,
//                            this.distEntreSolive );
//
//                    this.blocsPlancher.add(plancherBloc);
//                    remainingHori -= maxDimensions;
//                }else if(compteurBlocHori == blocHori && (remainingHori<maxDimensions)){
//                    //dernier bloc X
//                     Plancher plancherBloc = new Plancher((compteurBlocHori * maxDimensions),
//                            0,
//                            (remainingHori),
//                            (remainingVerti),
//                            this.maxDimensions,
//                            this.distEntreSolive );
//
//                    this.blocsPlancher.add(plancherBloc);
////                }
//            remainingHori -= maxDimensions;
//            compteurBlocHori++;

