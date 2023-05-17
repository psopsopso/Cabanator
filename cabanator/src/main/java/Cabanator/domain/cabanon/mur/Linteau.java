/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Cabanator.domain.cabanon.mur;

import java.awt.Point;
import java.io.Serializable;

/**
 *
 * @author patricesorrant
 */
public class Linteau implements Serializable{

    private Point coordonnesLinteau;
    private double hauteurLinteau; // 6, 8 ou 10
    //private double largeurLinteau;

    public Linteau(Point coordonnesLinteau, /*double largeurLinteau,*/ double hauteurLinteau) {
        this.coordonnesLinteau = coordonnesLinteau;
        //this.largeurLinteau = largeurLinteau;
        this.hauteurLinteau = hauteurLinteau;
    }

    public Point getCoordonnesLinteau() {
        return coordonnesLinteau;
    }

    public double getHauteurLinteau() {
        return hauteurLinteau;
    }
    
    public void setHauteurLinteau(double hauteur)
    {
        this.hauteurLinteau = hauteur;
    }

    /*public double getLargeurLinteau() {
        return largeurLinteau;
    }*/

}
