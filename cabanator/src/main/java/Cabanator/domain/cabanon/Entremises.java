package Cabanator.domain.cabanon;

import Cabanator.domain.cabanon.mur.Linteau;
import java.util.ArrayList;
import Cabanator.domain.cabanon.plancher.Plancher;
import Cabanator.domain.util;
import Cabanator.domain.cabanon.mur.Mur;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import Cabanator.domain.cabanon.BaseDimensions;
import java.awt.Rectangle;

// import Cabanator.domain.cabanon.toit.Toit;
public class Entremises implements Serializable {

    private final util util = new util();
    private Point point;
    private boolean statutSelection;
    private double longueurEntremise = 2;
    private double largeurEntremise;
    private boolean isValid = true;

    public Entremises(Point point, double largeur, int y) {

        this.point = new Point(point.x, y);
        this.largeurEntremise = largeur;
    }

    public void translate(Point delta) {
        this.point.x = (int) (this.point.x + delta.x);
        this.point.y = (int) (this.point.y + delta.y);
        this.checkIfValid(this);
    }

    public void checkIfValid(Entremises entremise) {
        point = new Point((int) entremise.getPoint().getX(), (int) entremise.getPoint().getY());
        Rectangle entremiseRect = new Rectangle((int) point.getX(), (int) point.getY(), (int) entremise.getlongueurEntremise(), (int) entremise.getlargeurEntremise());
        CabanonController con = CabanonController.getInstance();
        int[][] plancherP = con.getCabanon().getPlancher().getCoordinates();
        Polygon polyPlancher = new Polygon(plancherP[0], plancherP[1], 4);
        Rectangle2D plancherBounds = polyPlancher.getBounds();
        this.isValid = true;
        if (!plancherBounds.contains(point)) {
            this.isValid = false;
        }
        ArrayList<Entremises> entremiseList = con.getCabanon().getPlancher().getEntremise();
        // Check if intersect with other entremises
        for (Entremises otherEntremise : entremiseList) {
            if (otherEntremise != entremise) {
                Rectangle otherEntremiseRect = new Rectangle((int) otherEntremise.getPoint().getX(), (int) otherEntremise.getPoint().getY(), (int) otherEntremise.getlongueurEntremise(), (int) otherEntremise.getlargeurEntremise());
                if (entremiseRect.intersects(otherEntremiseRect)) {
                    this.isValid = false;
                }
            }
        }
        // Check if intersect with blocs edges

        ArrayList<Plancher> blocs = con.getCabanon().getPlancher().getPlanchersBlocs();
        for (Plancher bloc : blocs) {
            Rectangle finBloc = new Rectangle((int)bloc.getX()+util.toPixel(bloc.longueur-bloc.getwidthSolive()-1), 0, util.toPixel(bloc.getwidthSolive()+1), util.toPixel(bloc.getLargeur()));
            Rectangle debutBloc = new Rectangle((int)bloc.getX(), 0, util.toPixel(bloc.getwidthSolive()+1), util.toPixel(bloc.getLargeur()));
            //Rectangle debutBloc = new Rectangle((int)bloc.getX()+util.toPixel(bloc.getwidthSolive()), 0, util.toPixel(bloc.getwidthSolive()), util.toPixel(bloc.getLargeur())) ;
            if (entremiseRect.intersects(debutBloc)) {
                this.isValid = false;
            }
            else if (entremiseRect.intersects(finBloc))
            {
                this.isValid = false;
            }

        }
    }

    public double getlongueurEntremise() {
        return this.longueurEntremise;
    }

    public double getlargeurEntremise() {
        return this.largeurEntremise;
    }

    public boolean getStatutSelection() {
        return statutSelection;
    }

    public Point getPoint() {
        return this.point;
    }

    public boolean getIsValid() {
        return this.isValid;
    }

    public void setlongueurEntremise(double longueur) {
        this.longueurEntremise = longueur;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public void setStatutSelection() {
        this.statutSelection = !this.statutSelection;
    }

    public int[][] getEntremiseCoordinates() {
        int longueur = util.toPixel(this.getlongueurEntremise());
        int largeur = util.toPixel(this.getlargeurEntremise());

        Point bottomLeftPoint = new Point((int) this.getPoint().x, (int) (this.getPoint().y + largeur));
        Point topLeftPoint = new Point((int) this.getPoint().x, (int) this.getPoint().y);
        Point bottomRightPoint = new Point((int) (this.getPoint().x + longueur), (int) (this.getPoint().y + largeur));
        Point topRightPoint = new Point((int) (this.getPoint().x + longueur), (int) this.getPoint().y);
        int[] x = {bottomLeftPoint.x, topLeftPoint.x, topRightPoint.x, bottomRightPoint.x};
        int[] y = {bottomLeftPoint.y, topLeftPoint.y, topRightPoint.y, bottomRightPoint.y};

        return new int[][]{x, y};
    }

    public int[][] getEntremiseCoordinatesForAddition(Point p) {
        int longueur = util.toPixel(this.getlongueurEntremise());
        int largeur = util.toPixel(this.getlargeurEntremise());

        Point bottomLeftPoint = new Point((int) p.x, (int) (p.y + largeur));
        Point topLeftPoint = new Point((int) p.x, (int) p.y);
        Point bottomRightPoint = new Point((int) (p.x + longueur), (int) (p.y + largeur));
        Point topRightPoint = new Point((int) (p.x + longueur), (int) p.y);
        int[] x = {bottomLeftPoint.x, topLeftPoint.x, topRightPoint.x, bottomRightPoint.x};
        int[] y = {bottomLeftPoint.y, topLeftPoint.y, topRightPoint.y, bottomRightPoint.y};

        return new int[][]{x, y};
    }

}
