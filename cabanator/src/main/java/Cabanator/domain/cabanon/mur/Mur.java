package Cabanator.domain.cabanon.mur;

import Cabanator.domain.cabanon.BaseDimensions;
import Cabanator.domain.cabanon.CabanonController;
import java.awt.Point;
import java.awt.Polygon;
import java.io.Serializable;
import java.util.ArrayList;
import java.awt.geom.Rectangle2D;
import Cabanator.domain.util;

public class Mur extends BaseDimensions implements Serializable {

    private ArrayList<Mur> blocMurs = new ArrayList<>();
    private ArrayList<EntremisesMur> entremises = new ArrayList<>();
    private final util util = new util();
    private final String orientation;
    private double distanceMontants;
    private int nbMontants;
    private ArrayList<Ouverture> ouvertures = new ArrayList<>();
    private Ouverture selectedOuv;
    private double distanceMontantsMur;
    private EntremisesMur selectedEnt;
    private ArrayList<Montants> montants = new ArrayList<>();
    private ArrayList<Separators> separators = new ArrayList<>();

    public enum Orientation {
        EST,
        OUEST,
        NORD,
        SUD

    }

    public Mur(double x, double y, double longueur, double hauteur, double maxDimensions,
            double distanceMontantsMur, String orientation, ArrayList<Ouverture> ouvertures) {
        super(x, y, longueur, hauteur, maxDimensions);
        this.orientation = orientation;
        this.distanceMontantsMur = distanceMontantsMur;
        this.ouvertures = ouvertures;
    }

    public ArrayList<Ouverture> getOuvertures() {
        return this.ouvertures;
    }

    public ArrayList<Ouverture> setOuvertures(ArrayList<Ouverture> ouv) {
        return this.ouvertures = ouv;
    }

    public ArrayList<EntremisesMur> getEntremisesMur() {
        return this.entremises;
    }

    public String getOrientation() {
        return this.orientation;
    }

    public double getDistanceMontantMur() {
        return this.distanceMontantsMur;
    }

    /*public int getNbMontants(double longueur, double distance) {
       this.nbMontants = (int) (Math.floor(longueur / distance));

        return this.nbMontants;
    }*/

    public int[][] getMurCoordinates() {
        int longueur = toPixel(this.getLongueur());
        int hauteur = toPixel(this.getLargeur());
        Point bottomLeftPoint = new Point((int) this.getX(), (int) (this.getY() + hauteur));
        Point topLeftPoint = new Point((int) this.getX(), (int) this.getY());
        Point bottomRightPoint = new Point((int) (this.getX() + longueur), (int) (this.getY() + hauteur));
        Point topRightPoint = new Point((int) (this.getX() + longueur), (int) this.getY());
        int[] x = {bottomLeftPoint.x, topLeftPoint.x, topRightPoint.x, bottomRightPoint.x};
        int[] y = {bottomLeftPoint.y, topLeftPoint.y, topRightPoint.y, bottomRightPoint.y};
        return new int[][]{x, y};
    }

    public void setDistanceMontantMur(double distanceMontantsMur) {
        this.distanceMontantsMur = distanceMontantsMur;
    }

    public boolean holdTheDoor() {
        for (Ouverture ouv : this.getOuvertures()) {
            if (ouv.getTypeOuverture().equals("Porte")) {
                return true;
            }
        }
        return false;
    }

    public void addOuverture(Point point, String type, double hauteur) {

        int[][] murP = this.getMurCoordinates();
        Polygon polyMur = new Polygon(murP[0], murP[1], 4);
        Rectangle2D mur = polyMur.getBounds();
        Ouverture ouverture = new Ouverture(point, 25, 25, hauteur, type);
        if (ouvertureIntersecte(ouverture)) {
            return;
        }
        if (type.equals("Porte")) {
            if (!holdTheDoor()) {
                ouverture.setLongueurOuverture(30);
                ouverture.setLargeurOuverture(util.toInch(murP[1][0] - point.y));

            } else {
                return;
            }
        }

        int[][] ouvP = ouverture.getOuvCoordinatesForAddition(point);
        Polygon polyAcc = new Polygon(ouvP[0], ouvP[1], 4);
        Rectangle2D ouv = polyAcc.getBounds();
        if (mur.contains(ouv)) {
            this.ouvertures.add(ouverture);
        }
        destroyMontants();
    }

    public void deleteOuverture(Point p) {

        for (Ouverture ouv : ouvertures) {
            int[][] test = ouv.getOuvCoordinates();
            Polygon polyAcc = new Polygon(test[0], test[1], 4);
            Rectangle2D poly2 = polyAcc.getBounds();
            if (poly2.contains(p)) {
                this.ouvertures.remove(ouv);
                return;
            }
        }
        destroyMontants();
    }

    public Ouverture getSelectedOuverture() {
        return this.selectedOuv;
    }

    public void selectOuverture(Point p) {
        for (Ouverture ouv : ouvertures) {
            int[][] test = ouv.getOuvCoordinates();
            Polygon polyAcc = new Polygon(test[0], test[1], 4);
            if (polyAcc.contains(p)) {
                if (selectedOuv == null) {
                    ouv.setStatutSelection();
                    selectedOuv = ouv;
                    return;
                } else if (selectedOuv != ouv) {
                    selectedOuv.setStatutSelection();
                    ouv.setStatutSelection();
                    selectedOuv = ouv;
                    return;
                } else {
                    selectedOuv.setStatutSelection();
                    selectedOuv = null;
                    return;
                }
            }
        }
    }

    public boolean ouvertureIntersecte(Ouverture nouvelleOuv) {
        int x2Start = nouvelleOuv.getPoint().x - util.toPixel(9);
        int x2Fin = nouvelleOuv.getPoint().x + util.toPixel(nouvelleOuv.getlongueurOuverture()) + util.toPixel(9);
        int y2Bloc = blocEnY(nouvelleOuv);

        for (Ouverture ouv : ouvertures) {
            int yBloc = blocEnY(ouv);
            if (yBloc == y2Bloc) {
                int x1Start = ouv.getPoint().x - util.toPixel(9);
                int x1Fin = ouv.getPoint().x + util.toPixel(ouv.getlongueurOuverture()) + util.toPixel(9);

                boolean start = (x2Start < x1Fin) && (x2Start > x1Start);
                boolean finish = (x2Fin > x1Start) && (x2Fin < x1Fin);

                if (start || finish) {
                    return true;
                }
            }
        }
        return false;
    }

    public void updateSelectedItemsPosition(Point delta) {
        for (Ouverture ouv : ouvertures) {
            if (ouv.getStatutSelection()) {
                ouv.translate(delta);
            }
        }
    }

    public void updateSelectedItemsPositionEnt(Point delta) {
        for (EntremisesMur ent : entremises) {
            if (ent.getStatutSelection()) {
                ent.translate(delta);
            }
        }
    }

    public void addEntremiseMur(Point point, double hauteur) {
        CabanonController con = CabanonController.getInstance();
        int[][] murP = this.getMurCoordinates();
        Polygon polyMur = new Polygon(murP[0], murP[1], 4);
        Rectangle2D mur = polyMur.getBounds();
        for(Montants montProche : getMontantsMur())
        {
            if(montProche.getX() > point.x)
            {
                point.x = (int)montProche.getX() - util.toPixel(getDistanceMontantMur()) + util.toPixel(2);
                break;
            }
        }
        
        EntremisesMur ent = new EntremisesMur(point.x, point.y, con.toPixel(getDistanceMontantMur()), con.toPixel(2), maxDimensions);
        int[][] entPoints = ent.getEntCoordinatesForAddition(point);
        Polygon polyAcc = new Polygon(entPoints[0], entPoints[1], 4);
        Rectangle2D entP = polyAcc.getBounds();
        if (mur.contains(entP)) {
            this.entremises.add(ent);
        }
    }

    public void deleteEntremise(Point p) {
        for (EntremisesMur ent : entremises) {
            int[][] entP = ent.getEntCoordinates();
            Polygon polyAcc = new Polygon(entP[0], entP[1], 4);
            Rectangle2D poly2 = polyAcc.getBounds();
            if (poly2.contains(p)) {
                this.entremises.remove(ent);
                return;
            }
        }
    }

    public EntremisesMur getSelectedEntremise() {
        return this.selectedEnt;
    }

    public void selectEntremise(Point p) {
        for (EntremisesMur ent : entremises) {
            int[][] entP = ent.getEntCoordinates();
            Polygon polyAcc = new Polygon(entP[0], entP[1], 4);
            Rectangle2D poly2 = polyAcc.getBounds();
            if (poly2.contains(p)) {
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

    public void generateMontants() {
        if (distanceMontantsMur != 0) {
            for (Mur bloc : blocMurs) {
                for (double i = bloc.getX() + util.toPixel(distanceMontantsMur); i < bloc.getX() + util.toPixel(bloc.getLongueur()); i += util.toPixel(distanceMontantsMur)) {
                    Montants newMont = new Montants(i, bloc.getY() + util.toPixel(2), util.toPixel(bloc.getLargeur() - 4), util.toPixel(2), maxDimensions);
                    if (!montantIntersecte(newMont)) {
                        montants.add(newMont);
                    }
                }
            }
        }
    }

    public boolean montantIntersecte(Montants newMont) {
        int x2Start = (int) newMont.getX();
        int x2Fin = (int) newMont.getX() + util.toPixel(newMont.getLongueur());
        int y2Bloc = blocEnYmont(newMont);

        for (Ouverture ouv : ouvertures) {
            int yBloc = blocEnY(ouv);

            if (yBloc == y2Bloc) {
                int x1Start = ouv.getPoint().x - util.toPixel(6);
                int x1Fin = ouv.getPoint().x + util.toPixel(ouv.getlongueurOuverture() + util.toPixel(6));
                boolean start = (x2Start < x1Fin) && (x2Start > x1Start);
                boolean finish = (x2Fin > x1Start) && (x2Fin < x1Fin);

                if (start || finish) {
                    return true;
                }
            }
        }
        return false;
    }

    public ArrayList<Separators> getSeparators() {
        return this.separators;
    }

    public ArrayList<Montants> getMontantsMur() {
        return this.montants;
    }

    public int blocEnY(Ouverture ouv) {
        return (int) Math.floor((ouv.getPoint().y - this.getY()) / util.toPixel(192));
    }

    public int blocEnYmont(Montants mont) {
        return (int) Math.floor((mont.getY() - this.getY()) / util.toPixel(192));
    }

    public void destroyMontants() {
        this.montants.clear();
//        this.separators.clear();
//        generateBlocs();
        generateMontants();
    }

    /*public int getNbBloc() {
        return (int) Math.floor(util.toPixel(this.largeur) / util.toPixel(192));
    }*/
    ///////////////////////////////////
    public ArrayList<Mur> getMursBlocs() {
        return this.blocMurs;
    }

    public ArrayList<Mur> wallBlocks() {
        blocMurs.clear();
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

                        Mur murBloc = new Mur(toPixel(maxDimensions) * i,
                                toPixel(maxDimensions) * j,
                                (maxDimensions),
                                (lastBlocVerti),
                                this.maxDimensions,
                                this.distanceMontantsMur,
                                this.orientation,
                                new ArrayList<Ouverture>());
                        this.blocMurs.add(murBloc);
                        j++;
                    } else if (i == blocHori && remainingVerti == maxDimensions) {
                        Mur murBloc = new Mur(toPixel(maxDimensions) * i,
                                toPixel(maxDimensions) * j,
                                (lastBlocHori),
                                (maxDimensions),
                                this.maxDimensions,
                                this.distanceMontantsMur,
                                this.orientation,
                                new ArrayList<Ouverture>());
                        this.blocMurs.add(murBloc);
                        j++;
                    } else if (i == blocHori && remainingVerti < maxDimensions) {
                        Mur murBloc = new Mur(toPixel(maxDimensions) * i,
                                toPixel(maxDimensions) * j,
                                (lastBlocHori),
                                (lastBlocVerti),
                                this.maxDimensions,
                                this.distanceMontantsMur,
                                this.orientation,
                                new ArrayList<Ouverture>());
                        this.blocMurs.add(murBloc);
                        j++;
                    } else if (i == blocHori && remainingVerti > maxDimensions) {
                        Mur murBloc = new Mur(toPixel(maxDimensions) * i,
                                toPixel(maxDimensions) * j,
                                (lastBlocHori),
                                (maxDimensions),
                                this.maxDimensions,
                                this.distanceMontantsMur,
                                this.orientation,
                                new ArrayList<Ouverture>());
                        this.blocMurs.add(murBloc);
                        j++;
                    } else if (i != blocHori && remainingVerti <= maxDimensions) {
                        Mur murBloc = new Mur(toPixel(maxDimensions) * i,
                                toPixel(maxDimensions) * j,
                                (maxDimensions),
                                (maxDimensions),
                                this.maxDimensions,
                                this.distanceMontantsMur,
                                this.orientation,
                                new ArrayList<Ouverture>());
                        this.blocMurs.add(murBloc);
                        j++;
                    } else {
                        Mur murBloc = new Mur(toPixel(maxDimensions) * i,
                                toPixel(maxDimensions) * j,
                                (maxDimensions),
                                (maxDimensions),
                                this.maxDimensions,
                                this.distanceMontantsMur,
                                this.orientation,
                                new ArrayList<Ouverture>());
                        this.blocMurs.add(murBloc);
                        remainingHori -= maxDimensions;
                        j++;
                    }
                }
                i++;

            }
        } else if ((largeur / maxDimensions) < 1.01 && (longueur / maxDimensions) < 1.01) {
            //1 bloc
            Mur murBloc = new Mur(0,
                    0,
                    (longueur),
                    (largeur),
                    this.maxDimensions,
                    this.distanceMontantsMur,
                    this.orientation,
                    new ArrayList<Ouverture>());
            this.blocMurs.add(murBloc);
        } else if ((longueur / maxDimensions) > 1.01 && (largeur / maxDimensions) < 1.01) {

            while (compteurBlocHori <= blocHori) {
                //HORI SEULEMENT
                if (remainingHori < maxDimensions) {
                    Mur murBloc = new Mur(
                            (compteurBlocHori * toPixel(maxDimensions)),
                            0,
                            (remainingHori),
                            (remainingVerti),
                            this.maxDimensions,
                            this.distanceMontantsMur,
                            this.orientation,
                            new ArrayList<Ouverture>());
                    this.blocMurs.add(murBloc);
                    compteurBlocHori++;
                } else {
                    Mur murBloc = new Mur(
                            (compteurBlocHori * toPixel(maxDimensions)),
                            0,
                            (maxDimensions),
                            (remainingVerti),
                            this.maxDimensions,
                            this.distanceMontantsMur,
                            this.orientation,
                            new ArrayList<Ouverture>());
                    this.blocMurs.add(murBloc);
                    compteurBlocHori++;
                    remainingHori -= maxDimensions;
                }
            }
        } else if ((largeur / maxDimensions) > 1.01 && (longueur / maxDimensions) < 1.01) {
            //verti seulement
            while (compteurBlocVerti <= blocVerti) {
                if (remainingVerti < maxDimensions) {
                    Mur murBloc = new Mur(
                            0,
                            (compteurBlocVerti * toPixel(maxDimensions)),
                            (remainingHori),
                            (remainingVerti),
                            this.maxDimensions,
                            this.distanceMontantsMur,
                            this.orientation,
                            new ArrayList<Ouverture>());
                    this.blocMurs.add(murBloc);
                    compteurBlocVerti++;
                } else {
                    Mur murBloc = new Mur(
                            0,
                            (compteurBlocVerti * toPixel(maxDimensions)),
                            (remainingHori),
                            (maxDimensions),
                            this.maxDimensions,
                            this.distanceMontantsMur,
                            this.orientation,
                            new ArrayList<Ouverture>());
                    this.blocMurs.add(murBloc);
                    compteurBlocVerti++;
                    remainingVerti -= maxDimensions;
                }

            }
        }
        return blocMurs;
    }
}
