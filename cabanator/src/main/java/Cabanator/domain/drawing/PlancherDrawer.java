package Cabanator.domain.drawing;

import Cabanator.domain.cabanon.CabanonController;
import Cabanator.domain.cabanon.Entremises;
import Cabanator.domain.cabanon.plancher.Plancher;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.util.ArrayList;

public class PlancherDrawer {

    private final CabanonController controller;
    private ArrayList<Entremises> entremise;

    public PlancherDrawer(Graphics g, double longueur, double largeur, double distanceSolive) {
        controller = CabanonController.getInstance();
        controller.setCabanonLongueur(longueur);
        controller.setCabanonLargeur(largeur);
        controller.setdistSolivePlancher(distanceSolive);
    }
    
    public void drawPlancher(Graphics g, int longueur, int largeur) {
        int widthSolive = controller.toPixel(controller.getCabanon().getPlancher().getwidthSolive());
        for (Plancher bloc : controller.getCabanon().getPlancher().getPlanchersBlocs()) {
            //Draw des solives de contour
            g.drawRect((int)bloc.getX(), (int)bloc.getY(), controller.toPixel((int)bloc.getLongueur()), widthSolive);
            g.drawRect((int)bloc.getX(), (int)bloc.getY()+widthSolive, widthSolive, controller.toPixel((int)bloc.getLargeur())-(widthSolive*2));
            g.drawRect((int)bloc.getX()+ controller.toPixel((int)bloc.getLongueur())-widthSolive, (int)bloc.getY()+widthSolive, widthSolive, controller.toPixel((int)bloc.getLargeur())-(widthSolive*2));
            g.drawRect((int)bloc.getX(), (int)bloc.getY()+controller.toPixel((int)bloc.getLargeur())-widthSolive, controller.toPixel((int)bloc.getLongueur()), widthSolive);
            
            //Draw des solives à l'intérieur du plancher
            for (int i = (int) bloc.getY()+controller.toPixel(controller.getDistanceSolive()+1); i < ((int) bloc.getY() + controller.toPixel(bloc.getLargeur()) - 2); i+=controller.toPixel(controller.getDistanceSolive()))
            {
                g.drawRect((int)bloc.getX()+widthSolive, i, controller.toPixel((int)bloc.getLongueur())-widthSolive*2, widthSolive);
            }
        }

    }

    ;
//        int compteurSolive = 1;
//
//        int compteurBlocLon = controller.getCabanon().getPlancher().getNbBlocLon();
//        int compteurBlocLar = controller.getCabanon().getPlancher().getNbBlocLar();
//        int compteurAddBlocLon = 0;
//        int compteurAddBlocLar = 0;
//
//        double longueurPlancher = controller.getCabanon().getPlancher().getLongueurPlancher();
//        double largeurPlancher = controller.getCabanon().getPlancher().getLargeurPlancher();
//
//        int lonPlancher = controller.toPixel(longueurPlancher);
//        int larPlancher = controller.toPixel(largeurPlancher);
//
//        int heightSolive = controller.toPixel(2);
//        int lonPlancherMax = controller.toPixel(192);
//        int larPlancherMax = controller.toPixel(192);
//
//        int width = (int)(longueur - lonPlancher);
//        int height = (int)(largeur - larPlancher);
//        width = width/2;
//        height = height/2;
//        Point pointDepart = new Point(width, height);
//        controller.setPlancherPoint(pointDepart);
//
//        int NbSolive = controller.getNbSolive();
//        NbSolive += compteurBlocLar;
//        int distSolive = controller.toPixel(controller.getDistanceSolive());
//
//            if (NbSolive == 0 || distSolive > controller.getCabanonLongueur())
//            {
//                g.drawRect( width,
//                            height ,
//                            lonPlancher,
//                            heightSolive);
//                g.drawRect( width,
//                            height+heightSolive,
//                            heightSolive,
//                            larPlancher-heightSolive);
//                g.drawRect( width + lonPlancher - heightSolive,
//                            height+heightSolive ,
//                            heightSolive,
//                            larPlancher-heightSolive);
//                g.drawRect( width,
//                            height + larPlancher,
//                            lonPlancher,
//                            heightSolive);
//            }
//            else
//            {
//                //Dessin de la ligne du haut et de gauche
//                g.drawRect( width,
//                            height ,
//                            lonPlancher,
//                            heightSolive);
//                g.drawRect( width,
//                            height+heightSolive,
//                            heightSolive,
//                            larPlancher-heightSolive);
//
//                while(compteurAddBlocLar < compteurBlocLar)
//                {
//                    int distanceParcourue = distSolive + heightSolive;
//                    int compteurTemp = 1;
//                    int heightTemp = height;
//                    int NbSoliveTemp = NbSolive;
//                    int heightSoliveTemp = 0;
//                    while(compteurSolive < NbSoliveTemp) // Vérifier le calcul de la grosseur d'une solive dans le calcul du nombre de solive.
//                    {
//                        if(distanceParcourue >= larPlancherMax)
//                        {
//                            distanceParcourue = distSolive;
//                            heightTemp += larPlancherMax - heightSolive;
//                            compteurTemp = 1;
//                            NbSoliveTemp --;
//                            heightSoliveTemp = heightSolive;
//                        }
//                        else
//                        {
//                            g.drawRect( width + heightSolive,
//                                        heightTemp + (compteurTemp*distSolive) + heightSoliveTemp,
//                                        lonPlancher - 2*heightSolive,
//                                        heightSolive);
//                            distanceParcourue += distSolive;
//                            compteurTemp++;
//                        }
//                        compteurSolive++;
//
//                    }
//
//                    if(compteurAddBlocLar >= 1)
//                    {
//                        g.setColor(Color.black);
//                        g.drawRect( width+heightSolive,
//                                    height + (compteurAddBlocLar*larPlancherMax),
//                                    lonPlancher-heightSolive*2,
//                                    heightSolive);
//                        g.drawRect( width+heightSolive,
//                                    height + heightSolive+ (compteurAddBlocLar*larPlancherMax),
//                                    lonPlancher - heightSolive*2,
//                                    heightSolive);
//                    }
//                    compteurAddBlocLar++;
//                }
//                while(compteurAddBlocLon < compteurBlocLon)
//                {
//                    if(compteurAddBlocLon >= 1)
//                    {
//                        g.setColor(Color.black);
//                        g.drawRect(width + (compteurAddBlocLon*lonPlancherMax),
//                                   height +heightSolive ,
//                                   heightSolive,
//                                   larPlancher - heightSolive);
//                        g.drawRect( width + heightSolive + (compteurAddBlocLon*lonPlancherMax),
//                                    height +heightSolive,
//                                    heightSolive,
//                                    larPlancher - heightSolive);
//                    }
//                    compteurAddBlocLon++;
//                }
//                // Dessin de la ligne de droite et celle du bas
//                g.drawRect( width + lonPlancher - heightSolive,
//                            height+heightSolive ,
//                            heightSolive,
//                            larPlancher-heightSolive);
//                g.drawRect( width,
//                            height + larPlancher,
//                            lonPlancher,
//                            heightSolive);
//            }
//    }
    
    
    public void drawEntremise(Graphics g, double longueur) {
        //Loop de dessin de chaque entremise dans le ArrayList<Entremises>
        for (Entremises ent : controller.getCabanon().getPlancher().getEntremise()) {
            int lonEntremiseInch = controller.toPixel(controller.getCabanon().getPlancher().getLargeur() - 4);
            int larEntremise = controller.toPixel(2);
            double murTopPoint = controller.getCabanon().getPlancher().getY();
            g.setColor(Color.black);

            if (ent.getStatutSelection()) {
                // draw a bolder contour for the selected accessory
                Graphics2D g2 = (Graphics2D) g;
                g2.setStroke(new BasicStroke(3)); // set the stroke width to 3
                if (!ent.getIsValid()) {
                    g.setColor(Color.RED);
                }
                g.drawRect((int) ent.getPoint().x,
                        (int) murTopPoint + larEntremise,
                        larEntremise,
                        lonEntremiseInch);
                g2.setStroke(new BasicStroke(1)); // reset the stroke width to

            } else {
                if (!ent.getIsValid()) {
                    Graphics2D g2 = (Graphics2D) g;
                    g.setColor(Color.RED);
                }
                g.drawRect((int) ent.getPoint().x,
                        (int) murTopPoint + larEntremise,
                        larEntremise,
                        lonEntremiseInch);
            }
        }
    }
}
