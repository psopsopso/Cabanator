package Cabanator.domain.drawing;

import Cabanator.domain.cabanon.CabanonController;
import Cabanator.domain.cabanon.Entremises;
import Cabanator.domain.cabanon.mur.Montants;
import Cabanator.domain.cabanon.plancher.Plancher;
import Cabanator.domain.cabanon.toit.Toit;
import Cabanator.domain.cabanon.toit.ToitDessus;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.util.ArrayList;


public class ToitDessusDrawer {
    
    private final CabanonController controller;
    
    
    public ToitDessusDrawer(Graphics g, double longueur, double largeur)
    {
        controller = CabanonController.getInstance();
    }
    
    public void drawToitDessus(Graphics g, int longueur, int largeur, double longueurExtremite) {
               
        for (Montants fermes : controller.getCabanon().getToitDessus().getFermesMur()) {
                    g.drawRect((int) (fermes.getX()),
                            (int) (fermes.getY()),
                            (int) (fermes.getLargeur()),
                            (int) (fermes.getLongueur()));
        }
            
            
            /*for(int i=0; i<= nbFerme; i++){
                 //ferme extremité début
                if(i == 0){
                    int tempCount = 0;
                    for(int j=0; j< controller.toPixel(controller.getCabanon().getToitDessus().getLargeur()); j+=controller.toPixel(controller.getCabanon().getToitDessus().getDistEntreEntremise())){
                        if(tempCount ==0){ 
                            g.drawRect(
                        controller.toPixel(4), 
                        0+tempCount*controller.toPixel(controller.getCabanon().getToitDessus().getDistEntreEntremise()),
                   controller.toPixel(controller.getCabanon().getToitDessus().getDistanceExtremite()), 
                   controller.toPixel(4));
                        tempCount++;
                        }
                        g.drawRect(
                        controller.toPixel(4), 
                        0+tempCount*controller.toPixel(controller.getCabanon().getToitDessus().getDistEntreEntremise())-controller.toPixel(4),
                   controller.toPixel(controller.getCabanon().getToitDessus().getDistanceExtremite()), 
                  controller.toPixel(4));
                        tempCount++;
                        }
                
                g.setColor(Color.blue);
                g.drawRect(
                        0, 
                        0,
                     controller.toPixel(4), 
                          (int)controller.toPixel(controller.getCabanon().getToitDessus().getLargeur())/2);
                g.drawRect(
                        0,
                        (int)(controller.toPixel(controller.getCabanon().getToitDessus().getLargeur())/2), 
                        controller.toPixel(4), 
                        (int)(controller.toPixel(controller.getCabanon().getToitDessus().getLargeur())/2));
                g.setColor(Color.black);

                }else if(i == nbFerme){
                                //ferme fin extremite
                    
                    int tempCount = 0;
                    for(int j=0; j< controller.toPixel(controller.getCabanon().getToitDessus().getLargeur()); j+=controller.toPixel(controller.getCabanon().getToitDessus().getDistEntreEntremise())){
                        if(tempCount ==0){ 
                            g.drawRect(
                        ((int)controller.toPixel(controller.getCabanon().getToitDessus().getDistanceExtremite()) + i*(int)controller.toPixel(controller.getCabanon().getToitDessus().getDistEntreFermes()))-controller.toPixel(4),  
                        0+tempCount*controller.toPixel(controller.getCabanon().getToitDessus().getDistEntreEntremise()),
                   controller.toPixel(controller.getCabanon().getToitDessus().getDistanceExtremite()), 
                   controller.toPixel(4));
                        tempCount++;
                        }
                        g.drawRect(
                        ((int)controller.toPixel(controller.getCabanon().getToitDessus().getDistanceExtremite()) + i*(int)controller.toPixel(controller.getCabanon().getToitDessus().getDistEntreFermes()))-controller.toPixel(4), 
                        0+tempCount*controller.toPixel(controller.getCabanon().getToitDessus().getDistEntreEntremise())-controller.toPixel(4),
                   controller.toPixel(controller.getCabanon().getToitDessus().getDistanceExtremite()), 
                  controller.toPixel(4));
                        tempCount++;
                        }
                g.setColor(Color.blue);
                
                g.drawRect(
                        ((int)controller.toPixel(controller.getCabanon().getToitDessus().getDistanceExtremite()+4) + i*(int)controller.toPixel(controller.getCabanon().getToitDessus().getDistEntreFermes())), 
                        0,
                     controller.toPixel(4), 
                          (int)controller.toPixel(controller.getCabanon().getToitDessus().getLargeur())/2);
                g.drawRect(
                        ((int)controller.toPixel(controller.getCabanon().getToitDessus().getDistanceExtremite()*2+4) + i*(int)controller.toPixel(controller.getCabanon().getToitDessus().getDistEntreFermes())), 
                        (int)(controller.toPixel(controller.getCabanon().getToitDessus().getLargeur())/2), 
                        controller.toPixel(4), 
                        (int)(controller.toPixel(controller.getCabanon().getToitDessus().getLargeur())/2));
                g.setColor(Color.black);
                }
                else{
                    
                g.drawRect(
                        (i*(int)controller.toPixel(controller.getCabanon().getToitDessus().getDistEntreFermes()+4)), 
                        0,
                     controller.toPixel(4), 
                          (int)controller.toPixel(controller.getCabanon().getToitDessus().getLargeur())/2);
                g.drawRect(
                        (i*(int)controller.toPixel(controller.getCabanon().getToitDessus().getDistEntreFermes()+4)), 
                        (int)(controller.toPixel(controller.getCabanon().getToitDessus().getLargeur())/2), 
                        controller.toPixel(4), 
                        (int)(controller.toPixel(controller.getCabanon().getToitDessus().getLargeur())/2));

            }

    }*/
    }  
}
