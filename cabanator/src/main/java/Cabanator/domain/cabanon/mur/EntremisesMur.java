/**
 *
 * @author dan_j
 */
package Cabanator.domain.cabanon.mur;

import Cabanator.domain.util;
import Cabanator.domain.cabanon.BaseDimensions;
import Cabanator.domain.cabanon.CabanonController;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.ArrayList;

public class EntremisesMur extends BaseDimensions implements Serializable {

    // l'ajout d'une fenetre et d'une porte se fait de la même manière
    // Entremise à gauche et a droite des portes et fenetre
    // Ajout de linteau en haut de porte et fenêtre (face de côté) (2x6, 2x8, 2x10)
    private final util util = new util();
    private boolean statutSelection;
    private boolean isValid = true;

    public EntremisesMur(double x, double y, double longueur, double largeur, double maxDimensions) {
        super(x, y, longueur, largeur, maxDimensions);
    }

//    public double getX() {
//        return this.x;
//    }
//
//    public double GetY() {
//        return this.y;
//    }
    public int[][] getEntCoordinates() {
        int longueur = util.toPixel(this.longueur);
        int largeur = util.toPixel(this.largeur);

        Point bottomLeftPoint = new Point((int) this.getX(), (int) (this.getY() + largeur));
        Point topLeftPoint = new Point((int) this.getX(), (int) this.getY());
        Point bottomRightPoint = new Point((int) (this.getX() + longueur), (int) (this.getY() + largeur));
        Point topRightPoint = new Point((int) (this.getX() + longueur), (int) this.getY());
        int[] x = {bottomLeftPoint.x, topLeftPoint.x, topRightPoint.x, bottomRightPoint.x};
        int[] y = {bottomLeftPoint.y, topLeftPoint.y, topRightPoint.y, bottomRightPoint.y};

        return new int[][]{x, y};
    }

    public int[][] getEntCoordinatesForAddition(Point p) {
        int longueurEnt = util.toPixel(this.longueur);
        int largeurEnt = util.toPixel(this.largeur);

        Point bottomLeftPoint = new Point((int) p.x, (int) (p.y + largeurEnt));
        Point topLeftPoint = new Point((int) p.x, (int) p.y);
        Point bottomRightPoint = new Point((int) (p.x + longueurEnt), (int) (p.y + largeurEnt));
        Point topRightPoint = new Point((int) (p.x + longueurEnt), (int) p.y);
        int[] x = {bottomLeftPoint.x, topLeftPoint.x, topRightPoint.x, bottomRightPoint.x};
        int[] y = {bottomLeftPoint.y, topLeftPoint.y, topRightPoint.y, bottomRightPoint.y};

        return new int[][]{x, y};
    }

    public boolean getIsValid() {
        return this.isValid;
    }

    public void setStatutSelection() {
        this.statutSelection = !this.statutSelection;
    }

    public boolean getStatutSelection() {
        return statutSelection;
    }

    public void translate(Point delta) {
//        this.x = (int) (this.x + delta.x);
        this.y = (int) (this.y + delta.y);
        checkIfValid(this);

    }

    public void checkIfValid(EntremisesMur entremise) {
        this.isValid = true;
        boolean validator = false;
        CabanonController con = CabanonController.getInstance();
        Rectangle entremiseRect = new Rectangle((int) entremise.getX(), (int) entremise.getY(), (int) entremise.getLongueur(), (int) entremise.getLargeur());
        int[][] murP = con.getCabanon().getSelectedMur(con.getOrientation()).getMurCoordinates();
        Polygon polyMur = new Polygon(murP[0], murP[1], 4);
        Rectangle2D mur = polyMur.getBounds();

        for (Mur bloc : con.getSelectedMur(con.getOrientation()).getMursBlocs()) {
            if (entremise.getY() > bloc.getY() + con.toPixel(2) && entremise.getY() < bloc.getY() + con.toPixel(bloc.getLargeur() - 4)) {
                validator = true;
            }

        }
        this.isValid = validator;

        ArrayList<EntremisesMur> entremisesMurList = con.getCabanon().getSelectedMur(con.getOrientation()).getEntremisesMur();

        for (EntremisesMur otherEntremiseMur : entremisesMurList) {
            if (otherEntremiseMur != entremise) {
                Rectangle otherEntremiseRect = new Rectangle((int) otherEntremiseMur.getX(), (int) otherEntremiseMur.getY(), (int) otherEntremiseMur.getLongueur(), (int) otherEntremiseMur.getLargeur());
                if (entremiseRect.intersects(otherEntremiseRect)) {
                    this.isValid = false;
                }
            }
        }

    }
};
