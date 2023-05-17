package Cabanator.domain.drawing;

import Cabanator.domain.cabanon.CabanonController;
import Cabanator.domain.cabanon.mur.EntremisesMur;
import Cabanator.domain.cabanon.mur.Montants;
import java.util.ArrayList;
import Cabanator.domain.cabanon.mur.Mur;
import Cabanator.domain.cabanon.mur.Ouverture;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;

public class MurDrawer {

    private final CabanonController controller;

    public MurDrawer(Graphics g, double longueur, double hauteur, double distanceMontant, String orientation) {
        controller = CabanonController.getInstance();
    }

    public void drawMurs(Graphics g, String orientation, int longueur, int largeur) {
        int widthSolive = controller.toPixel(controller.getCabanon().getWidthSolive());
        // +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-
        int compteurMontant = 1;
        int widthMontant = controller.toPixel(2);
        double lonMurInch;
        double hautMurInch;
        double width;
        int height;
        double valideNbMontant;
        double distMontant;
        Graphics2D g2d = (Graphics2D) g;
        Stroke oldStroke = g2d.getStroke();

        // Draw all the walls
        if (controller.getOrientation().toUpperCase().equals(orientation)) {
            if (controller.getOrientation().equals("Nord") || controller.getOrientation().equals("Sud")) {
                lonMurInch = controller.getCabanonLongueur() - 12; // Le -12 car Nord/Sud est attaché au mur Est/Ouest (donc 6 po de solive chaque côté)
                controller.getSelectedMur(orientation).setLongueur(lonMurInch);
                lonMurInch = controller.toPixel(lonMurInch);
            } else {
                lonMurInch = controller.getCabanonLargeur();
                controller.getSelectedMur(orientation).setLongueur(lonMurInch);
                lonMurInch = controller.toPixel(lonMurInch);
            }
            hautMurInch = controller.getCabanonHauteur();
            controller.getSelectedMur(orientation).setLargeur(hautMurInch);
            hautMurInch = controller.toPixel(hautMurInch);

            width = (int) ((longueur - lonMurInch));
            height = (int) ((largeur - hautMurInch));

            distMontant = controller.getDistanceMontant();
            controller.getSelectedMur(orientation).setDistanceMontantMur(distMontant);
            distMontant = controller.toPixel(distMontant);

            //Draw murs
            Mur selectedMur = controller.getCabanon().getSelectedMur(orientation);
            for (Mur bloc : selectedMur.getMursBlocs()) {
                g.drawRect((int) bloc.getX(), (int) bloc.getY(), controller.toPixel((int) bloc.getLongueur()), widthSolive);
                g.drawRect((int) bloc.getX(), (int) bloc.getY() + widthSolive, widthSolive, controller.toPixel((int) bloc.getLargeur()) - (widthSolive * 2));
                g.drawRect((int) bloc.getX() + controller.toPixel((int) bloc.getLongueur()) - widthSolive, (int) bloc.getY() + widthSolive, widthSolive, controller.toPixel((int) bloc.getLargeur()) - (widthSolive * 2));
                g.drawRect((int) bloc.getX(), (int) bloc.getY() + controller.toPixel((int) bloc.getLargeur()) - widthSolive, controller.toPixel((int) bloc.getLongueur()), widthSolive);
            }

//            valideNbMontant = controller.getNbMontants();
//
//            ArrayList<Integer> xStartListOuv = new ArrayList<Integer>();
//            ArrayList<Integer> xEndListOuv = new ArrayList<Integer>();
//            ArrayList<Ouverture> ouvs = controller.getSelectedMur(orientation).getOuvertures();
//            for (Ouverture ouv : ouvs) {
//                int topLeftX = ouv.getOuvCoordinates()[0][1];
//                int topRightX = ouv.getOuvCoordinates()[0][2];
//                xStartListOuv.add(topLeftX);
//                xEndListOuv.add(topRightX);
//            }
            // Draw blocs
            controller.getSelectedMur(orientation).wallBlocks();
            // draw montants
            if (controller.getCurrentView().equals(controller.getSelectedMur(orientation).getOrientation())) {
                if (controller.getSelectedMur(orientation).getMontantsMur() != null) {
                    for (Montants mont : controller.getSelectedMur(orientation).getMontantsMur()) {
                        g.drawRect((int) (mont.getX()),
                                (int) (mont.getY()),
                                (int) (mont.getLargeur()),
                                (int) (mont.getLongueur()));
                    }
                }
            }
        }
    }

    public void drawEntremises(Graphics g, String orientation) {
        controller.fixDistanceMontants();
        if (controller.getCurrentView().equals(controller.getSelectedMur(orientation).getOrientation())) {
            if (controller.getSelectedMur(orientation).getEntremisesMur() != null) {
                for (EntremisesMur ent : controller.getSelectedMur(orientation).getEntremisesMur()) {
                    Graphics2D g2 = (Graphics2D) g;
                    if (ent.getStatutSelection()) {
                        // draw a bolder contour for the selected accessory
                        if (!ent.getIsValid()) {
                            g2.setColor(Color.red);
                        }
                        g2.setStroke(new BasicStroke(3)); // set the stroke width to 3
                        g2.drawRect((int) (ent.getX()),
                                (int) (ent.getY()),
                                (int) controller.toPixel(ent.getLongueur() - 2),
                                (int) controller.toPixel(ent.getLargeur()));
                        g2.setStroke(new BasicStroke(1)); // reset the stroke width to 1
                        g.setColor(Color.black);
                    } else {
                        if (!ent.getIsValid()) {
                            g2.setColor(Color.red);
                        }
                        g2.drawRect((int) (ent.getX()),
                                (int) (ent.getY()),
                                (int) controller.toPixel(ent.getLongueur() - 2),
                                (int) controller.toPixel(ent.getLargeur()));

                    }
                }
            }
        }
    }

    public void drawOuvertures(Graphics g, String orientation) {
        controller.fixOuvertureHeight();
        int hauteurBlocMax = controller.toPixel(192);
        double hauteurCabanonPixel = controller.getCabanonHauteur();
        hauteurCabanonPixel = controller.toPixel(hauteurCabanonPixel);

        if (controller.getCurrentView().equals(controller.getSelectedMur(orientation).getOrientation())) {
            if (controller.getSelectedMur(orientation).getOuvertures() != null) {
                for (Ouverture ouverture : controller.getSelectedMur(orientation).getOuvertures()) {
                    //ouverture.g

                    Mur murActif = controller.getSelectedMur(orientation);
                    double largeurMontant = controller.toPixel(2);

                    double distanceMontantsPixelMur = murActif.getDistanceMontantMur();
                    distanceMontantsPixelMur = controller.toPixel(distanceMontantsPixelMur);

                    double distanceMontantsPixelOuv = ouverture.getDistanceMontantOuv();
                    distanceMontantsPixelOuv = controller.toPixel(distanceMontantsPixelOuv);

                    double hauteurOuverturePixel = ouverture.gethauteurOuverture();
                    hauteurOuverturePixel = controller.toPixel(hauteurOuverturePixel);

                    double longueurOuverturePixel = ouverture.getlongueurOuverture();
                    longueurOuverturePixel = controller.toPixel(longueurOuverturePixel);

                    double largeurOuverturePixel = ouverture.getlargeurOuverture();
                    largeurOuverturePixel = controller.toPixel(largeurOuverturePixel);

                    double hauteurLinteauPixel = ouverture.getHauteurLinteau();
                    hauteurLinteauPixel = controller.toPixel(hauteurLinteauPixel);

                    double longueurCabanonPixel = controller.getCabanonLongueur();
                    longueurCabanonPixel = controller.toPixel(longueurCabanonPixel);

                    double largeurCabanonPixel = controller.getCabanonLargeur();
                    largeurCabanonPixel = controller.toPixel(largeurCabanonPixel);

                    int blocActifEnY = controller.getSelectedMur(orientation).blocEnY(ouverture);
                    int compteurBloc = (int) Math.floor(hauteurCabanonPixel / controller.toPixel(192));

                    double hauteurTopOuverture = (int) ouverture.getPoint().y - hauteurLinteauPixel;

//                    if (ouverture.getTypeOuverture().equals("Porte")) {
//                        g.setColor(Color.blue);
//                    } else if (ouverture.getTypeOuverture().equals("Fenêtre")) {
//                        g.setColor(Color.green);
//                    } else if (!ouverture.getIsValid()) {
//                        g.setColor(Color.red);
//                    }
                    if (!ouverture.getIsValid()) {
                        g.setColor(Color.RED);
                    } else {
                        g.setColor(Color.GREEN);
                    }

                    if (ouverture.getStatutSelection()) {
                        // draw a bolder contour for the selected accessory

                        Graphics2D g2 = (Graphics2D) g;
                        g2.setStroke(new BasicStroke(3)); // set the stroke width to 3
                        g2.drawRect((int) (ouverture.getPoint().x), (int) (ouverture.getPoint().y), (int) longueurOuverturePixel, (int) largeurOuverturePixel);
                        g2.setStroke(new BasicStroke(1)); // reset the stroke width to 1
                    } else {
                        g.drawRect((int) (ouverture.getPoint().x), (int) (ouverture.getPoint().y), (int) longueurOuverturePixel, (int) largeurOuverturePixel);

                    }

                    //if (ouverture.getLinteau() != null) {
                    //    g.setColor(Color.darkGray);
                    //    Linteau linteau = ouverture.getLinteau();
                    //}
                    // Outer montants
                    int y = (int) murActif.getY();
                    g.setColor(Color.black);

                    int entremiseOuvertureGaucheXStart = (int) (ouverture.getPoint().x - controller.toPixel(4)) - (int) controller.toPixel(6);
                    int entremiseOuvertureDroiteXStart = (int) (ouverture.getPoint().x + longueurOuverturePixel + controller.toPixel(4));
                    int entremiseOuvertureY = ouverture.getPoint().y + ((int) (largeurOuverturePixel) / 2);
                    int montantExterneGaucheStart = entremiseOuvertureGaucheXStart - controller.toPixel(2);
                    int montantExterneDroitStart = entremiseOuvertureDroiteXStart + (int) controller.toPixel(6);

                    if (compteurBloc > blocActifEnY /*&& blocActifEnY == 0*/ && ouverture.getIsValid()) {
                        // Montants plus près (red)
                        g.drawRect(
                                (int) (ouverture.getPoint().x - controller.toPixel(4) + 1),
                                y + hauteurBlocMax * blocActifEnY + controller.toPixel(2),
                                controller.toPixel(2),
                                ((int) hauteurBlocMax * (blocActifEnY + 1)) - controller.toPixel(2) - (hauteurBlocMax * blocActifEnY));

                        g.drawRect(
                                (int) (ouverture.getPoint().x + longueurOuverturePixel + controller.toPixel(2)),
                                y + hauteurBlocMax * blocActifEnY + controller.toPixel(2),
                                controller.toPixel(2),
                                ((int) hauteurBlocMax * (blocActifEnY + 1)) - controller.toPixel(2) - (hauteurBlocMax * blocActifEnY));

                        g.drawRect(
                                (int) (ouverture.getPoint().x - controller.toPixel(2)),
                                y + hauteurBlocMax * blocActifEnY + controller.toPixel(2),
                                controller.toPixel(2),
                                ((int) hauteurBlocMax * (blocActifEnY + 1)) - controller.toPixel(2) - (hauteurBlocMax * blocActifEnY));

                        g.drawRect(
                                (int) (ouverture.getPoint().x + longueurOuverturePixel),
                                y + hauteurBlocMax * blocActifEnY + controller.toPixel(2),
                                controller.toPixel(2),
                                ((int) hauteurBlocMax * (blocActifEnY + 1)) - controller.toPixel(2) - (hauteurBlocMax * blocActifEnY));

                        g.drawRect(
                                montantExterneGaucheStart,
                                y + hauteurBlocMax * blocActifEnY + controller.toPixel(2),
                                controller.toPixel(2),
                                ((int) hauteurBlocMax * (blocActifEnY + 1)) - controller.toPixel(2) - (hauteurBlocMax * blocActifEnY));

                        g.drawRect(
                                montantExterneDroitStart,
                                y + hauteurBlocMax * blocActifEnY + controller.toPixel(2),
                                controller.toPixel(2),
                                ((int) hauteurBlocMax * (blocActifEnY + 1)) - controller.toPixel(2) - (hauteurBlocMax * blocActifEnY));

                    }
//
                    if (compteurBloc == blocActifEnY && ouverture.getIsValid()) {
                        // Montant plus loin (grey)
                        g.drawRect(
                                (int) (ouverture.getPoint().x - controller.toPixel(4) + 1),
                                y + hauteurBlocMax * blocActifEnY + controller.toPixel(2),
                                controller.toPixel(2),
                                (int) (hauteurCabanonPixel - (compteurBloc * hauteurBlocMax)) - controller.toPixel(2));
                        g.drawRect(
                                (int) (ouverture.getPoint().x + longueurOuverturePixel + controller.toPixel(2)),
                                y + hauteurBlocMax * blocActifEnY + controller.toPixel(2),
                                controller.toPixel(2),
                                (int) (hauteurCabanonPixel - (compteurBloc * hauteurBlocMax)) - controller.toPixel(2));

                        g.drawRect(
                                (int) (ouverture.getPoint().x - controller.toPixel(2)),
                                y + hauteurBlocMax * blocActifEnY + controller.toPixel(2),
                                controller.toPixel(2),
                                (int) (hauteurCabanonPixel - (compteurBloc * hauteurBlocMax)) - controller.toPixel(2));

                        g.drawRect(
                                (int) (ouverture.getPoint().x + longueurOuverturePixel),
                                y + hauteurBlocMax * blocActifEnY + controller.toPixel(2),
                                controller.toPixel(2),
                                (int) (hauteurCabanonPixel - (compteurBloc * hauteurBlocMax)) - controller.toPixel(2));

                        g.drawRect(
                                montantExterneGaucheStart,
                                y + hauteurBlocMax * blocActifEnY + controller.toPixel(2),
                                controller.toPixel(2),
                                (int) (hauteurCabanonPixel - (compteurBloc * hauteurBlocMax)) - controller.toPixel(2));

                        g.drawRect(
                                montantExterneDroitStart,
                                y + hauteurBlocMax * blocActifEnY + controller.toPixel(2),
                                controller.toPixel(2),
                                (int) (hauteurCabanonPixel - (compteurBloc * hauteurBlocMax)) - controller.toPixel(2));

                    }
//
//
//                    if (compteurBloc == 0) {
//                        g.drawRect(
//                                (int) (ouverture.getPoint().x - controller.toPixel(4) + 1),
//                                y + hauteurBlocMax * blocActifEnY + controller.toPixel(2),
//                                controller.toPixel(2),
//                                ((int) hauteurBlocMax * (blocActifEnY + 1)) - controller.toPixel(2) - (hauteurBlocMax * blocActifEnY));
//                    }
//
//                    g.setColor(Color.red);
//                    g.drawRect((int) (ouverture.getPoint().x - controller.toPixel(4) + 1), y + hauteurBlocMax * blocActifEnY + controller.toPixel(2), controller.toPixel(2), hauteurBlocMax * (blocActifEnY + 1) - (hauteurBlocMax * blocActifEnY)); // hauteurBlocMax * (blocActifEnY + 1) - (hauteurBlocMax * blocActifEnY));
//                    g.drawRect((int) (ouverture.getPoint().x + longueurOuverturePixel + controller.toPixel(2)), y + hauteurBlocMax * blocActifEnY + controller.toPixel(2), controller.toPixel(2), hauteurBlocMax * (blocActifEnY + 1) - (hauteurBlocMax * blocActifEnY));
//                    g.setColor(Color.gray);
//                    g.drawRect((int) (ouverture.getPoint().x - controller.toPixel(2)), y + hauteurBlocMax * blocActifEnY + controller.toPixel(2), controller.toPixel(2), hauteurBlocMax * (blocActifEnY + 1) - (hauteurBlocMax * blocActifEnY));
//                    g.drawRect((int) (ouverture.getPoint().x + longueurOuverturePixel), y + hauteurBlocMax * blocActifEnY + controller.toPixel(2), controller.toPixel(2), hauteurBlocMax * (blocActifEnY + 1) - (hauteurBlocMax * blocActifEnY));
                    // Montants supérieurs et inférieurs à l'ouverture
                    int numMontants = ((int) longueurOuverturePixel - (int) distanceMontantsPixelOuv) / (controller.toPixel(2) + (int) distanceMontantsPixelOuv);
                    int x = (int) ouverture.getPoint().x + (int) distanceMontantsPixelOuv;

                    if (distanceMontantsPixelOuv <= longueurOuverturePixel && ouverture.getIsValid()) {
                        for (int i = 0; i <= numMontants; i++) {
                            // draw top montant

                            g.drawRect(
                                    x,
                                    y + hauteurBlocMax * blocActifEnY + controller.toPixel(2),
                                    (int) largeurMontant,
                                    (int) (hauteurTopOuverture - hauteurBlocMax * blocActifEnY - y));

                            // draw bottom montant
                            if (ouverture.getTypeOuverture() != "Porte") {

                                if (compteurBloc > blocActifEnY /*&& blocActifEnY == 0*/) {
                                    g.drawRect(
                                            x,
                                            (int) (ouverture.getPoint().y + largeurOuverturePixel + controller.toPixel(2)),
                                            (int) largeurMontant,
                                            (int) (hauteurBlocMax * (blocActifEnY + 1) - (largeurOuverturePixel + ouverture.getPoint().y - y) - controller.toPixel(2)));
                                }
                                if (compteurBloc == blocActifEnY) {
                                    g.drawRect(
                                            x,
                                            (int) (ouverture.getPoint().y + largeurOuverturePixel + controller.toPixel(2)),
                                            (int) largeurMontant,
                                            (int) ((hauteurCabanonPixel - (compteurBloc * hauteurBlocMax)) - largeurOuverturePixel - (ouverture.getPoint().y - (y + (compteurBloc * hauteurBlocMax)) + controller.toPixel(4))));
                                }
                                if (compteurBloc == 0) {

                                    g.drawRect(
                                            x,
                                            (int) (ouverture.getPoint().y + largeurOuverturePixel + controller.toPixel(2)),
                                            (int) largeurMontant,
                                            (int) (hauteurCabanonPixel - (largeurOuverturePixel + ouverture.getPoint().y - y) - controller.toPixel(2)));
                                }
                                /*if(ouverture.getPoint().y > controller.toPixel(192)+y && ouverture.getPoint().y < controller.toPixel(384)+y)
                                    {
                                        g.drawRect(
                                            x,
                                            (int) (ouverture.getPoint().y + largeurOuverturePixel + controller.toPixel(2)),
                                            (int) largeurMontant,
                                            (int) (hauteurCabanonPixel - controller.toPixel(192) - (largeurOuverturePixel + ouverture.getPoint().y-y) - controller.toPixel(2)));
                                    }
                                    if(ouverture.getPoint().y > controller.toPixel(384)+y && ouverture.getPoint().y < controller.toPixel(576)+y)
                                    {

                                    }


                            else if(compteurBloc != 0)
                            {
                                    g.drawRect(
                                            x,
                                            (int) (ouverture.getPoint().y + largeurOuverturePixel + controller.toPixel(2)),
                                            (int) largeurMontant,
                                            (int) (hauteurCabanonPixel%controller.toPixel(192) - (largeurOuverturePixel + ouverture.getPoint().y-y) - controller.toPixel(2)));
                            }*/
                            }
                            x += largeurMontant + distanceMontantsPixelOuv;
                        }
                    }
                    // Linteau
                    g.setColor(Color.black);
                    g.fillRect((int) (ouverture.getPoint().x - controller.toPixel(2)), (int) (ouverture.getPoint().y - hauteurLinteauPixel), (int) (longueurOuverturePixel + controller.toPixel(4) - 1), (int) (hauteurLinteauPixel));

                    // Lower ouverture bois
                    g.setColor(Color.pink);
                    g.fillRect((int) (ouverture.getPoint().x - controller.toPixel(2)),
                            (int) (ouverture.getPoint().y + largeurOuverturePixel),
                            (int) longueurOuverturePixel + controller.toPixel(4) - 1,
                            controller.toPixel(2));

                    // Entremises des ouvertures et montants associés
//                    int entremiseOuvertureGaucheXStart = (int) (ouverture.getPoint().x - controller.toPixel(4)) - (int) controller.toPixel(6);
//                    int entremiseOuvertureDroiteXStart = (int) (ouverture.getPoint().x + longueurOuverturePixel + controller.toPixel(4));
//                    int entremiseOuvertureY = ouverture.getPoint().y + ((int) (largeurOuverturePixel) / 2);
//                    int montantExterneGaucheStart = entremiseOuvertureGaucheXStart - controller.toPixel(2);
//                    int montantExterneDroitStart = entremiseOuvertureDroiteXStart + (int) controller.toPixel(6);
                    if (ouverture.getIsValid()) {
                        g.setColor(Color.MAGENTA);
                        g.drawRect(
                                entremiseOuvertureGaucheXStart,
                                entremiseOuvertureY,
                                (int) controller.toPixel(6),
                                controller.toPixel(2)
                        );
                        g.drawRect(
                                entremiseOuvertureDroiteXStart,
                                entremiseOuvertureY,
                                (int) controller.toPixel(6),
                                controller.toPixel(2)
                        );
                    };

//                    g.setColor(Color.black);
//                    g.drawRect(
//                            montantExterneGaucheStart,
//                            (int) murActif.getY() + 2,
//                            controller.toPixel(2),
//                            hauteurBlocMax * blocActifEnY - 4
//                    );
//
//                    g.drawRect(
//                            montantExterneDroitStart,
//                            (int) murActif.getY() + 2,
//                            controller.toPixel(2),
//                            hauteurBlocMax * blocActifEnY - 4
//                    );
                }
            }
        }
    }
}
