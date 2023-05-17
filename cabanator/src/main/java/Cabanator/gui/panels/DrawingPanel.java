package Cabanator.gui.panels;

import Cabanator.domain.cabanon.CabanonController;
import Cabanator.domain.cabanon.Entremises;
import Cabanator.gui.frames.MainWindow;
import Cabanator.domain.drawing.PlancherDrawer;

import Cabanator.domain.drawing.MurDrawer;
import Cabanator.domain.drawing.ToitDevantDrawer;
import Cabanator.domain.cabanon.plancher.Plancher;
import Cabanator.domain.drawing.ToitDessusDrawer;
import java.awt.BasicStroke;

import java.awt.Color;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import javax.swing.SwingUtilities;

public class DrawingPanel extends javax.swing.JPanel {

    private MainWindow mainWindow;
    private final CabanonController controller = CabanonController.getInstance();
    private PlancherDrawer plancherDrawer;
    private MurDrawer murDrawerNord;
    private MurDrawer murDrawerSud;
    private MurDrawer murDrawerOuest;
    private MurDrawer murDrawerEst;
    private double mouseWheelPointX;
    private double mouseWheelPointY;
    private ToitDevantDrawer toitDevantDrawer;
    private ToitDessusDrawer toitDessusDrawer;
    private double scale = 1.0;
    private boolean grilleActive = false;

    
    public DrawingPanel() {

    }

    public DrawingPanel(MainWindow mainWindow) {

        this.mainWindow = mainWindow;
        
                addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    controller.updateCabanon();
                } }});

        addMouseWheelListener(new MouseAdapter() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                mouseWheelPointX = e.getXOnScreen();
                mouseWheelPointY = e.getYOnScreen();
                double zoom = e.getPreciseWheelRotation() > 0 ? 0.9 : 1.1;
                
                scale *= zoom;
                

                controller.setZoomScale(scale);
                revalidate();
                repaint();
            }
        });

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

    if(mainWindow != null){
    g2d.drawRect( 0, 0,mainWindow.getDRAWING_PANEL_WIDTH() , mainWindow.getDRAWING_PANEL_HEIGHT() );
    }
            AffineTransform at = g2d.getTransform();
//            at.translate(mouseWheelPointX, mouseWheelPointY);
            at.scale(scale, scale);
//            at.translate(-mouseWheelPointX, -mouseWheelPointY);
            g2d.setTransform(at);
        
        
        revalidate();
        draw(g2d);

        g2d.dispose();

    }

    public MurDrawer getMurDrawerSud() {
        return murDrawerSud;
    }

    public MurDrawer getMurDrawerOuest() {
        return murDrawerOuest;
    }

    public MurDrawer getMurDrawerEst() {
        return murDrawerEst;
    }

    public MurDrawer getMurDrawerNord() {
        return murDrawerNord;
    }

    public void draw(Graphics2D g) {
        if (mainWindow != null) {
            if (murDrawerNord == null) {
                murDrawerNord = new MurDrawer(g, mainWindow.getCabanonLongueurField(), mainWindow.getCabanonHauteurField(), mainWindow.getDistanceMontantsOuvertureMur(), "NORD");
            }
            if (plancherDrawer == null) {
                plancherDrawer = new PlancherDrawer(g, mainWindow.getCabanonLongueurField(), mainWindow.getCabanonLargeurField(), mainWindow.getPlancherDistanceSolives());
            }
            if (murDrawerSud == null) {
                murDrawerSud = new MurDrawer(g, mainWindow.getCabanonLongueurField(), mainWindow.getCabanonHauteurField(), mainWindow.getDistanceMontantsOuvertureMur(), "SUD");
            }
            if (murDrawerOuest == null) {
                murDrawerOuest = new MurDrawer(g, mainWindow.getCabanonLargeurField(), mainWindow.getCabanonHauteurField(), mainWindow.getDistanceMontantsOuvertureMur(), "OUEST");
            }
            if (murDrawerEst == null) {
                murDrawerEst = new MurDrawer(g, mainWindow.getCabanonLargeurField(), mainWindow.getCabanonHauteurField(), mainWindow.getDistanceMontantsOuvertureMur(), "EST");
            }
            if (toitDevantDrawer == null) {
                toitDevantDrawer = new ToitDevantDrawer(g, mainWindow.getTaillePorteAFaux());
            }
            if (toitDessusDrawer == null) {
                toitDessusDrawer = new ToitDessusDrawer(g, mainWindow.getCabanonLongueurField(), mainWindow.getCabanonLargeurField());
            }

            grilleActive = mainWindow.getGrilleToggled();
            if (grilleActive) {
                for (int i = 0; i < mainWindow.getDRAWING_PANEL_WIDTH() / mainWindow.getBASE_SPACING(); i++) {
                    g.setColor(Color.LIGHT_GRAY);
                    g.drawLine(i * controller.toPixel(mainWindow.getBASE_SPACING()), 0, i * controller.toPixel(mainWindow.getBASE_SPACING()), controller.toPixel(mainWindow.getDRAWING_PANEL_HEIGHT()));
                }
                for (int i = 0; i < mainWindow.getDRAWING_PANEL_HEIGHT() / mainWindow.getBASE_SPACING(); i++) {
                    g.setColor(Color.LIGHT_GRAY);
                    g.drawLine(0, i * controller.toPixel(mainWindow.getBASE_SPACING()), controller.toPixel(mainWindow.getDRAWING_PANEL_WIDTH()), i * controller.toPixel(mainWindow.getBASE_SPACING()));
                }
            }
            g.setColor(Color.black);
            //g.drawRect(0, 0, mainWindow.getDRAWING_PANEL_WIDTH(), mainWindow.getDRAWING_PANEL_HEIGHT());
            if (mainWindow.getVueSelection().equals("Plancher")) {
                plancherDrawer.drawPlancher(g, mainWindow.getDRAWING_PANEL_WIDTH(), mainWindow.getDRAWING_PANEL_HEIGHT());
                plancherDrawer.drawEntremise(g, mainWindow.getCabanonLongueurField());

            }

            if (mainWindow.getVueSelection().equals("Mur Ouest")) {
                murDrawerOuest.drawMurs(g, "OUEST", mainWindow.getDRAWING_PANEL_WIDTH(), mainWindow.getDRAWING_PANEL_HEIGHT());
                murDrawerOuest.drawOuvertures(g, "OUEST");
                murDrawerOuest.drawEntremises(g, "OUEST");
                controller.getCabanon().getNb2x4();
            }

            if (mainWindow.getVueSelection().equals("Mur Est")) {
                murDrawerEst.drawMurs(g, "EST", mainWindow.getDRAWING_PANEL_WIDTH(), mainWindow.getDRAWING_PANEL_HEIGHT());
                murDrawerEst.drawOuvertures(g, "EST");
                murDrawerEst.drawEntremises(g, "EST");
            }

            if (mainWindow.getVueSelection().equals("Mur Nord")) {
                murDrawerNord.drawMurs(g, "NORD", mainWindow.getDRAWING_PANEL_WIDTH(), mainWindow.getDRAWING_PANEL_HEIGHT());
                murDrawerNord.drawOuvertures(g, "NORD");
                murDrawerNord.drawEntremises(g, "NORD");
            }

            if (mainWindow.getVueSelection().equals("Mur Sud")) {
                murDrawerSud.drawMurs(g, "SUD", mainWindow.getDRAWING_PANEL_WIDTH(), mainWindow.getDRAWING_PANEL_HEIGHT());
                murDrawerSud.drawOuvertures(g, "SUD");
                murDrawerSud.drawEntremises(g, "SUD");
            }

            if (mainWindow.getVueSelection().equals("Toit Devant")) {
                toitDevantDrawer.drawToitDevant(g, mainWindow.getDRAWING_PANEL_WIDTH());
            }
            if (mainWindow.getVueSelection().equals("Toit Dessus")) {
                toitDessusDrawer.drawToitDessus(g, mainWindow.getDRAWING_PANEL_WIDTH(), mainWindow.getDRAWING_PANEL_HEIGHT(), mainWindow.getDistanceExtremite());
            }
            g.dispose();
            System.gc();
            mainWindow.repaint();
        }
    }
}
