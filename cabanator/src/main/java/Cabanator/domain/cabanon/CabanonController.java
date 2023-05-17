package Cabanator.domain.cabanon;

import Cabanator.domain.cabanon.mur.EntremisesMur;
import Cabanator.domain.cabanon.plancher.Plancher;
import Cabanator.domain.cabanon.mur.Mur;
import Cabanator.domain.cabanon.mur.Ouverture;
import Cabanator.gui.frames.MainWindow;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Toolkit;
import java.awt.geom.Rectangle2D;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import static java.util.Collections.reverse;
import java.util.Stack;

public class CabanonController implements Serializable {

    private Cabanon cabanon;
    private static CabanonController controller;
    private String currentView;
    private double scale = 1;
    private int nb2x6 = 0;
    private Stack<Cabanon> undoStack = new Stack<>();
    private Stack<Cabanon> redoStack = new Stack<>();

    public CabanonController() {

        cabanon = new Cabanon();
    }

    public CabanonController(Cabanon cabanon) {
        this.cabanon = cabanon;
        undoStack = new Stack<>();
        redoStack = new Stack<>();
    }

    public static CabanonController getInstance() {
        if (controller == null) {
            controller = new CabanonController();
        }
        return controller;
    }

    public static CabanonController getnewInstance() {
        if (controller != null) {
            controller = new CabanonController();
        }
        return controller;
    }

    public void updateCabanon() {
        Cabanon cabanonClone = cabanon.deepClone();
        undoStack.push(cabanonClone);
        redoStack.clear();
    }

    public void undo() {
        if (!undoStack.empty()) {
            redoStack.push(cabanon.deepClone());
            cabanon = undoStack.pop();
        }
    }

    public void redo() {
        if (!redoStack.empty()) {
            undoStack.push(cabanon.deepClone());
            cabanon = redoStack.pop();
        }
    }
    
    public void clear()
    {
        undoStack.clear();
        redoStack.clear();
    }

    public Cabanon getCabanon() {
        return cabanon;
    }

    public ArrayList<Mur> getMurs() {
        return this.cabanon.getMurs();
    }

    public void setMurs() {
        //this.murs = murs;
    }

    /*public Plancher getPlanchers() {
        return this.plancher;
    }*/
    public void setPlancher() {
        //this.plancher = plancher;
    }

    public void setCurrentView(String orientation) {
        currentView = orientation;
    }

    public void setCabanonLargeur(double largeur) {
        double longueur = getCabanonLongueur();
        double hauteur = getCabanonHauteur();
        double lar = getCabanonLargeur();
        cabanon.setCabanonLargeur(largeur);

        fixOuverturesPositions(longueur, lar, hauteur, longueur, largeur, hauteur);

    }

    public void setCabanonLongueur(double longueur) {
        double lon = getCabanonLongueur();
        double hauteur = getCabanonHauteur();
        double largeur = getCabanonLargeur();
        cabanon.setCabanonLongueur(longueur);

        fixOuverturesPositions(lon, largeur, hauteur, longueur, largeur, hauteur);

    }

    public void setCabanonHauteur(double hauteur) {
        double lon = getCabanonLongueur();
        double hau = getCabanonHauteur();
        double largeur = getCabanonLargeur();
        cabanon.setCabanonHauteur(hauteur);

        fixOuverturesPositions(lon, largeur, hau, lon, largeur, hauteur);

    }
    
    public void setCabanonTaillePiece(double taille)
    {
        double lon = getCabanonLongueur();
        double hau = getCabanonHauteur();
        double largeur = getCabanonLargeur();
        cabanon.setTaillePiece(taille);
        
        fixOuverturesPositions(lon, largeur, hau, lon, largeur, hau);
    }

    public void setdistSolivePlancher(double dist) {
        cabanon.setDistanceSolive(dist);
    }

    public double getCabanonLongueur() {
        fixMontantsMur();
        return cabanon.getCabanonLongueur();
    }

    public double getCabanonLargeur() {
        fixMontantsMur();
        return cabanon.getCabanonLargeur();
    }

    public double getCabanonHauteur() {
        fixMontantsMur();
        return cabanon.getCabanonHauteur();
    }

    public double getDistanceSolive() {
        return cabanon.getPlancher().getDistEntreSolive();
    }

    public int getNbSolive() {
        return cabanon.getPlancher().getNbSolive();
    }

    public String getOrientation() {
        return cabanon.getOrientation(currentView);
    }

    public double getTaillePorteAFaux() {
        return cabanon.getToit().getTaillePorteAFaux();
    }

    public double getAngleToit() {
        return cabanon.getToit().getAngleToit();
    }
    
    public double getDistanceFerme()
    {
        return cabanon.getToit().getDistanceFerme();
    }
    public double getDistanceExtremite()
    {
        return cabanon.getToitDessus().getDistanceExtremite();
    }
    
    /*public int getNbMontants()
    {
        return cabanon.getNbMontants(currentView);
    }*/

    public double getDistanceMontant() {
        return cabanon.getDistanceMontant(currentView);
    }

    public ArrayList<Ouverture> getOuvertures(Mur mur) {
        return this.cabanon.getOuvertures(mur);
    }

    public Mur getSelectedMur(String orientation) {
        return cabanon.getSelectedMur(orientation);
    }

    public String getCurrentView() {
        return this.currentView.substring(4);
    }

    public void setPlancherPoint(Point point) {
        cabanon.getPlancher().setX(point.x);
        cabanon.getPlancher().setY(point.y);
    }

    public void fixOuvertureHeight() {
        ArrayList<Mur> murs = getMurs();
        for (Mur mur : murs) {
            int[][] murP = mur.getMurCoordinates();
            ArrayList<Ouverture> ouvs = mur.getOuvertures();
            for (Ouverture ouv : ouvs) {
                if (ouv.getTypeOuverture().equals("Porte")) {
                    Point point = new Point((int) (ouv.getPoint().x), (int) (murP[1][0] - controller.toPixel(ouv.getlargeurOuverture())));
                    ouv.setPoint(point);
                }
                if (ouv.getTypeOuverture().equals("Fenetre")) {
                    Point point = new Point((int) (ouv.getPoint().x), (int) (murP[1][0] - controller.toPixel(ouv.getlargeurOuverture())));
                    ouv.setPoint(point);
                }
            }
        }
    }

    public void fixOuverturesPositions(double oldLen, double oldWid, double oldH, double newL, double newW, double newH) {
        ArrayList<Mur> murs = getMurs();
        for (Mur mur : murs) {
            int[][] murP = mur.getMurCoordinates();
            Polygon polyMur = new Polygon(murP[0], murP[1], 4);
            Rectangle2D murBounds = polyMur.getBounds();
            if (mur.getOrientation() == "Nord" || mur.getOrientation() == "Sud") {
                ArrayList<Ouverture> ouvs = mur.getOuvertures();
                ArrayList<Ouverture> modifiedOuv = mur.getOuvertures();

                for (Ouverture ouv : ouvs) {
                    Point point = new Point((int) (ouv.getPoint().x + (newL - oldLen)), (int) (ouv.getPoint().y + (newH - oldH)));
                    int[][] ouvP = ouv.getOuvCoordinatesForAddition(point);
                    Polygon polyOuv = new Polygon(ouvP[0], ouvP[1], 4);
                    Rectangle2D ouvBounds = polyOuv.getBounds();

//                 if(murBounds.intersects(ouvBounds)){
//                     modifiedOuv.remove(ouv);
//                 }
//                 else{
                    modifiedOuv.remove(ouv);
                    Ouverture o = ouv;
                    o.setPoint(point);
                    modifiedOuv.add(o);
//                         }
                }
                mur.setOuvertures(modifiedOuv);
            } else {

                ArrayList<Ouverture> ouvs = mur.getOuvertures();
                ArrayList<Ouverture> modifiedOuv = mur.getOuvertures();
                for (Ouverture ouv : ouvs) {
                    Point point = new Point((int) (ouv.getPoint().x + (newW - oldWid)), (int) (ouv.getPoint().y + (newH - oldH)));
                    int[][] ouvP = ouv.getOuvCoordinatesForAddition(point);
                    Polygon polyOuv = new Polygon(ouvP[0], ouvP[1], 4);
                    Rectangle2D ouvBounds = polyOuv.getBounds();

//                 if(ouvBounds.intersects(murBounds)){
//                     modifiedOuv.remove(ouv);
//                 }
//                 else{
                    modifiedOuv.remove(ouv);
                    Ouverture o = ouv;
                    o.setPoint(point);
                    modifiedOuv.add(o);
//                         }
                    mur.setOuvertures(modifiedOuv);
                }
            }
        }
    }

    public void fixMontantsMur() {
        ArrayList<Mur> murs = getMurs();
        for (Mur mur : murs) {
            mur.destroyMontants();
        }
    }

    public void fixDistanceMontants() {
        Mur mur = getSelectedMur(currentView);
        ArrayList<EntremisesMur> ents = mur.getEntremisesMur();
        for (EntremisesMur ent : ents) {
            ent.setLongueur(getSelectedMur(currentView).getDistanceMontantMur());
        }
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

    public void setZoomScale(double scaling) {
        scale = scaling;
    }

    public double getZoomScale() {
        return this.scale;
    }

    public void sauvegarderCabanon(String nom) {

        FileOutputStream fos;
        ObjectOutputStream out;

        try {

            nom += ".ser";
            fos = new FileOutputStream(nom, false);
            out = new ObjectOutputStream(fos);

            out.writeObject(cabanon);
            out.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void loadCabanon(String fileName) {
        try {
            FileInputStream fileIn = new FileInputStream(fileName);
            ObjectInputStream ois = new ObjectInputStream(fileIn);

            Cabanon cabanonLoad = (Cabanon) ois.readObject();

            cabanon = cabanonLoad;
            ois.close();
            fileIn.close();
        } catch (IOException | ClassNotFoundException ex) {
        }
    }
    
    public void CsvExporter() {
        String filename = "pieces.csv";
        try {
            FileWriter fw = new FileWriter(filename);
            fw.write("Type matériaux, Longueur(po), Angle de coupe (gauche), Angle de coupe (droit), Qté\n");
            fw.write("2x4,"+cabanon.getMaxDimension()+ ",90, 90,"+cabanon.getNb2x4()+"\n");
            fw.write("2x4 Ferme,"+cabanon.getMaxDimension()+ ","+(90-cabanon.getToit().getAngleToit())+","+ (180-(90-cabanon.getToit().getAngleToit())) +","+cabanon.getNb2x4Ferme()+"\n");
            fw.write("2x4 Soutien,"+cabanon.getMaxDimension()+ ","+(cabanon.getToit().getAngleToit())+","+ cabanon.getToit().getAngleToit() +","+cabanon.getNb2x4Soutien()+"\n");
            fw.write("2x6,"+cabanon.getMaxDimension()+ ",90, 90,"+cabanon.getNb2x6()+"\n");
            fw.write("2x8,"+cabanon.getMaxDimension()+ ",90, 90,"+cabanon.getNb2x8()+"\n");
            fw.write("2x10,"+cabanon.getMaxDimension()+ ",90, 90,"+cabanon.getNb2x10()+"\n");
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
