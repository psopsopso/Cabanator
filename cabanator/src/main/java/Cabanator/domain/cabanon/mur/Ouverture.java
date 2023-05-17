package Cabanator.domain.cabanon.mur;

import Cabanator.domain.cabanon.CabanonController;
import java.awt.Point;
import Cabanator.domain.util;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.ArrayList;

public class Ouverture implements Serializable {

    // l'ajout d'une fenetre et d'une porte se fait de la même manière
    // Entremise à gauche et a droite des portes et fenetre
    // Ajout de linteau en haut de porte et fenêtre (face de côté) (2x6, 2x8, 2x10)
    private Point point;
    private final util util = new util();
    private boolean statutSelection;
    private double longueurOuverture;
    private double largeurOuverture;
    private double hauteurOuverture;
    private final String typeOuverture;
    private Linteau linteau;
    private double hauteurLinteau = 6;
    private double distanceMontantOuv = 12;
    private boolean isValid = true;

    public Ouverture(Point point, double longueurOuverture, double largeurOuverture, double hauteur, String typeOuverture) {
        this.point = point;
        this.longueurOuverture = longueurOuverture;
        this.largeurOuverture = largeurOuverture;
        this.hauteurOuverture = hauteur;
        this.typeOuverture = typeOuverture;
    }

    public String getTypeOuverture() {
        return this.typeOuverture;
    }

    public Linteau getLinteau() {
        return this.linteau;
    }

    public Point getPoint() {
        return this.point;
    }

    public double getHauteurLinteau() {
        return hauteurLinteau;
    }

    public boolean getIsValid() {
        return this.isValid;
    }

    public void setHauteurOuverture(double hauteur) {
        this.hauteurOuverture = hauteur;
    }

    public void setLargeurOuverture(double largeur) {
        this.largeurOuverture = largeur;
    }

    public void setLongueurOuverture(double longueur) {
        this.longueurOuverture = longueur;
    }

    public void setHauteurLinteau(double hauteur) {
        this.hauteurLinteau = hauteur;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public void setDistanceMontantOuv(double distanceMontant) {
        this.distanceMontantOuv = distanceMontant;
    }

    public double getDistanceMontantOuv() {
        return this.distanceMontantOuv;
    }

    void translate(Point delta) {
        this.point.x = (int) (this.point.x + delta.x);
        this.point.y = (int) (this.point.y + delta.y);
        this.checkIfValid(this);
    }

    public int[][] getOuvCoordinates() {
        int longueur = util.toPixel(this.getlongueurOuverture());
        int largeur = util.toPixel(this.getlargeurOuverture());

        Point bottomLeftPoint = new Point((int) this.getPoint().x, (int) (this.getPoint().y + largeur));
        Point topLeftPoint = new Point((int) this.getPoint().x, (int) this.getPoint().y);
        Point bottomRightPoint = new Point((int) (this.getPoint().x + longueur), (int) (this.getPoint().y + largeur));
        Point topRightPoint = new Point((int) (this.getPoint().x + longueur), (int) this.getPoint().y);
        int[] x = {bottomLeftPoint.x, topLeftPoint.x, topRightPoint.x, bottomRightPoint.x};
        int[] y = {bottomLeftPoint.y, topLeftPoint.y, topRightPoint.y, bottomRightPoint.y};

        return new int[][]{x, y};
    }

    public int[][] getOuvCoordinatesForAddition(Point p) {
        int longueur = util.toPixel(this.getlongueurOuverture());
        int largeur = util.toPixel(this.getlargeurOuverture());

        Point bottomLeftPoint = new Point((int) p.x, (int) (p.y + largeur));
        Point topLeftPoint = new Point((int) p.x, (int) p.y - util.toPixel(10));
        Point bottomRightPoint = new Point((int) (p.x + longueur), (int) (p.y + largeur));
        Point topRightPoint = new Point((int) (p.x + longueur), (int) p.y - util.toPixel(10));
        int[] x = {bottomLeftPoint.x, topLeftPoint.x, topRightPoint.x, bottomRightPoint.x};
        int[] y = {bottomLeftPoint.y, topLeftPoint.y, topRightPoint.y, bottomRightPoint.y};

        return new int[][]{x, y};
    }

    public void checkIfValid(Ouverture ouv) {
        this.isValid = true;
        point = new Point((int) ouv.getPoint().getX(), (int) ouv.getPoint().getY());
        CabanonController con = CabanonController.getInstance();
        Rectangle ouvRect = new Rectangle((int) point.getX() - con.toPixel(10), (int) point.getY(), (int) con.toPixel(ouv.getlongueurOuverture() + con.toPixel(13)), (int) con.toPixel(ouv.getlargeurOuverture()));

        int[][] murP = con.getCabanon().getSelectedMur(con.getOrientation()).getMurCoordinates();
        Polygon polyMur = new Polygon(murP[0], murP[1], 4);
        Rectangle2D mur = polyMur.getBounds();

        if (!mur.contains(ouvRect)) {
            this.isValid = false;
        }

        // Intersection avec d'autres ouvertures
        if (con.getCabanon().getSelectedMur(con.getOrientation()).ouvertureIntersecte(this)) {
            this.isValid = false;
        }
        // Intersection avec le edge des blocs de mur
        ArrayList<Mur> blocs = con.getCabanon().getSelectedMur(con.getOrientation()).getMursBlocs();
        for (Mur bloc : blocs) {
            if (ouvRect.contains(bloc.getX(), ouvRect.y) || ouvRect.contains(ouvRect.x, bloc.getY())) {
                this.isValid = false;
            }
        }
    }

    ;

public double getlargeurOuverture() {
        return largeurOuverture;
    }

    public double gethauteurOuverture() {
        return hauteurOuverture;
    }

    public double getlongueurOuverture() {
        return longueurOuverture;
    }

    public void setStatutSelection() {
        this.statutSelection = !this.statutSelection;
    }

    public boolean getStatutSelection() {
        return statutSelection;
    }

}
