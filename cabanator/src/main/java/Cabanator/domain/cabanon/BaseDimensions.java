package Cabanator.domain.cabanon;

import java.awt.Point;
import java.awt.Toolkit;
import java.io.Serializable;

/**
 * Base class for everything with a dimension
 */
public abstract class BaseDimensions implements Serializable {
    protected double longueur;
    protected double largeur;
    protected double x;
    protected double y;
    protected double maxDimensions;

    /**
     * Constructeur avec longueur et largeur
     */
    public BaseDimensions(double xEnPouces, double yEnPouces, double longueur, double largeur, double maxDimensions) {
        this.x = xEnPouces;
        this.y = yEnPouces;
        this.longueur = longueur;
        this.largeur = largeur;
        this.maxDimensions = maxDimensions;
    }

    public double getLargeur() {
        return this.largeur;
    }
    public double getLongueur() {
        return this.longueur;
    }
    public double getX() {
        return this.x;
    }
    public double getY() {
        return this.y;
    }
    public double getMaxDimensions(){
        return this.maxDimensions;
    }
    public void setX(double newX) {
        this.x = newX;
    }
    public void setY(double newY) {
        this.y = newY;
    }
    public void setLargeur(double largeur) {
        this.largeur = largeur;
    }
    public void setLongueur(double longueur) {
        this.longueur = longueur;
    }
    public void setMaxDimensions(double maxDimensions){
        this.maxDimensions = maxDimensions;
    }
    public int[][] getCoordinates() {
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

    public int toPixel(double pouces) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        double dpi = toolkit.getScreenResolution();
        return (int) Math.round((pouces * dpi) / 48);
    }

    public double toInch(double pixel) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        double dpi = toolkit.getScreenResolution();
        return (double) Math.round((pixel / dpi) * 48);
    }
}