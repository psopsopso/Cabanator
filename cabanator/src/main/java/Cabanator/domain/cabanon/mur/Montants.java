package Cabanator.domain.cabanon.mur;

import java.awt.Point;
import Cabanator.domain.cabanon.BaseDimensions;
import Cabanator.domain.util;
import java.io.Serializable;
import java.util.ArrayList;
/**
 *
 * @author dan_j
 */
public class Montants extends BaseDimensions implements Serializable{

    private final util util = new util();

    public Montants(double x, double y,  double longueur, double largeur, double maxDimensions) {
        super(x,y, longueur, largeur, maxDimensions);
    }

    public int[][] getMontantCoordinates() {
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
    

}
