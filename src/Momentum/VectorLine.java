/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Momentum;

import Momentum.Vector2D;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.transform.Rotate;

/**
 *
 * @author BaconAndNachos
 */
public class VectorLine extends Line{
    private Line arrow1;
    private Rotate rotateArrow1;
    private Line arrow2;
    private Rotate rotateArrow2;
    private double angle;
        public VectorLine()
        {
           
            this.angle = angle;
            arrow1 = new Line();
            arrow2 = new Line();
            Momentum.getPane().getChildren().addAll(this,arrow1,arrow2);
            rotateArrow1 =new Rotate();
            rotateArrow2 = new Rotate();
            arrow1.getTransforms().add(rotateArrow1);
            arrow2.getTransforms().add(rotateArrow2);
        }
        public void setPosition(double sX,double sY,Vector2D v)
        {
            this.setStartX(sX);
            this.setStartY(sY);
            this.setEndX(this.getStartX()+ v.getX()/2);
            this.setEndY(this.getStartY() + v.getY()/2);
            createArrow(v);
        }
        public void createArrow(Vector2D v)
        {
            arrow1.setStartX(this.getEndX());
            arrow1.setStartY(this.getEndY());
            arrow1.setEndX(arrow1.getStartX() + 10);
            arrow1.setEndY(arrow1.getStartY());
            
            arrow2.setStartX(this.getEndX());
            arrow2.setStartY(this.getEndY());
            arrow2.setEndX(arrow1.getStartX() + 10);
            arrow2.setEndY(arrow1.getStartY());
            
            rotateArrow1.setPivotX(this.getEndX());
            rotateArrow1.setPivotY(this.getEndY());
            rotateArrow1.setAngle(v.direction() - 135);
            
            rotateArrow2.setPivotX(this.getEndX());
            rotateArrow2.setPivotY(this.getEndY());
            rotateArrow2.setAngle(v.direction() - 225);
            
        }
        public void remove()
        {
            Momentum.getPane().getChildren().removeAll(arrow1,arrow2,this);
        }
        public void setInvisible()
        {
            this.setStroke(Color.TRANSPARENT);
            arrow1.setStroke(Color.TRANSPARENT);
            arrow2.setStroke(Color.TRANSPARENT);
            
        }
        public void setDefaultColor()
        {
            this.setStroke(Color.BLACK);
                 arrow1.setStroke(Color.BLACK);
                 arrow2.setStroke(Color.BLACK);
       
        }
        
}
