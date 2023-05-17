package Cabanator.domain.drawing;

import Cabanator.domain.cabanon.CabanonController;
import Cabanator.domain.cabanon.Entremises;
import Cabanator.domain.cabanon.toit.Toit;
import Cabanator.domain.cabanon.plancher.Plancher;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Polygon;

public class ToitDevantDrawer {
    private ArrayList<Entremises> entremise;
    private final CabanonController controller;
    
    public ToitDevantDrawer(Graphics g, double largeur) {
        controller = CabanonController.getInstance();
        controller.getCabanon().getToit().setTaillePorteAFaux(largeur);
    }
    
    public void drawToitDevant(Graphics g, int longueur) {
//        int width = (int)(longueur - lonPlancher);
//        int height = (int)(largeur - larPlancher);
        controller.getCabanon().getToit().setCoteGaucheToit(longueur);
        controller.getCabanon().getToit().setCoteDroitToit(longueur);
        controller.getCabanon().getToit().setBaseToit(longueur);
        controller.getCabanon().getToit().setPoutreMilieu(longueur);
        controller.getCabanon().getToit().setPoutresGauche(longueur);
        controller.getCabanon().getToit().setPoutresDroite(longueur);
        g.drawPolygon(controller.getCabanon().getToit().getCoteGaucheToit());
        g.drawPolygon(controller.getCabanon().getToit().getCoteDroitToit());
        g.drawPolygon(controller.getCabanon().getToit().getBaseToit());
        g.drawPolygon(controller.getCabanon().getToit().getPoutreMilieu());
                            
            for(Polygon poly: controller.getCabanon().getToit().getPoutresCoteGaucheToit()) {
                g.setColor(Color.red);
                g.drawPolygon(poly);
            }
            for(Polygon poly: controller.getCabanon().getToit().getPoutresCoteDroitToit()) {
                g.setColor(Color.blue);
                g.drawPolygon(poly);
            }
        }
        
    }
    

