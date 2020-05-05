/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CircularMotion;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.transform.Rotate;

/**
 *
 * @author BaconAndNachos
 */
public class VectorLineUCM extends Line{
    Line arrow1;
    Line arrow2;
    Rotate rotateVector;
    Rotate rotateArrow1;
    Rotate rotateArrow2;
    DoubleProperty dx;
    DoubleProperty dy;
    
    public VectorLineUCM()
    {
        dx = new SimpleDoubleProperty(0);
        dy =new SimpleDoubleProperty(0);
        arrow1 = new Line();
        arrow2 = new Line();
        rotateArrow1 = new Rotate(-45);
        rotateArrow2 = new Rotate(45);
        rotateVector =new Rotate();
        Uniform_Circular_Motion.getPane().getChildren().addAll(this,arrow1,arrow2);
        
    }
    public void createAccelerationArrow(double acceleration)
    {
          rotateVector.angleProperty().bind(Bindings.createDoubleBinding(() -> {
            return   Math.toDegrees(Math.atan2(dy.get(), dx.get())); //To change body of generated lambdas, choose Tools | Templates.
        }, dy,dx));
          
          
           this.endXProperty().bind(Bindings.createDoubleBinding(() -> {
           return  this.startXProperty().get() + ((acceleration/2)*Math.cos(Math.toRadians(rotateVector.angleProperty().get()))); //To change body of generated lambdas, choose Tools | Templates.
        }, rotateVector.angleProperty(),this.startXProperty()));
           
             this.endYProperty().bind(Bindings.createDoubleBinding(() -> {
           return this.startYProperty().get() + ((acceleration/2)*Math.sin(Math.toRadians(rotateVector.angleProperty().get()))); //To change body of generated lambdas, choose Tools | Templates.
        }, rotateVector.angleProperty(),this.startYProperty()));
          
             
        rotateVector.pivotXProperty().bind(this.startXProperty());
        rotateVector.pivotYProperty().bind(this.startYProperty());
        createVectorArrows(135,225);
       
    }
    public void createVelocityArrow(double velocity)
    {
        rotateVector.angleProperty().bind(Bindings.createDoubleBinding(() -> {
            return   Math.toDegrees(Math.atan2(dy.get(), dx.get())); //To change body of generated lambdas, choose Tools | Templates.
        }, dy,dx));
        
          this.endXProperty().bind(Bindings.createDoubleBinding(() -> {
           return  this.startXProperty().get() + ((velocity/2)*Math.cos( Math.toRadians( rotateVector.angleProperty().get() -90))); //To change body of generated lambdas, choose Tools | Templates.
        }, rotateVector.angleProperty(),this.startXProperty()));
          this.endYProperty().bind(Bindings.createDoubleBinding(() -> {
           return this.startYProperty().get() + ((velocity/2)*Math.sin( Math.toRadians(rotateVector.angleProperty().get() - 90))); //To change body of generated lambdas, choose Tools | Templates.
        }, rotateVector.angleProperty(),this.startYProperty()));
         createVectorArrows(-135,-45);
    }
    public void createVectorArrows(double angle1,double angle2)
    {   arrow1.startXProperty().bind(this.endXProperty());
        arrow1.startYProperty().bind(this.endYProperty());
      
        arrow1.endXProperty().bind(Bindings.add(10,this.endXProperty()));
        arrow1.endYProperty().bind(arrow1.startYProperty());
        
             
        arrow2.startXProperty().bind(this.endXProperty());
        arrow2.startYProperty().bind(this.endYProperty());
      
        arrow2.endXProperty().bind(Bindings.add(10,this.endXProperty()));
        arrow2.endYProperty().bind(arrow2.startYProperty());
        
              
        rotateArrow1.pivotXProperty().bind(arrow1.startXProperty());
        rotateArrow1.pivotYProperty().bind(arrow1.startYProperty());
        rotateArrow2.pivotXProperty().bind(arrow2.startXProperty());
        rotateArrow2.pivotYProperty().bind(arrow2.startYProperty());
        rotateArrow1.angleProperty().bind(Bindings.createDoubleBinding(() -> {
           return rotateVector.angleProperty().get()-angle1; //To change body of generated lambdas, choose Tools | Templates.
        }, rotateVector.angleProperty()));

        rotateArrow2.angleProperty().bind(Bindings.createDoubleBinding(() -> {
           return rotateVector.angleProperty().get()-angle2; //To change body of generated lambdas, choose Tools | Templates.
        }, rotateVector.angleProperty()));

          arrow1.getTransforms().add(rotateArrow1);
          arrow2.getTransforms().add(rotateArrow2);
        
    }
    
    
    public DoubleProperty getStartXProperty()
    {
        return this.startXProperty();
    }
    public DoubleProperty getStartYProperty()
    {
        return this.startYProperty();
    }
    public DoubleProperty getDx()
    {
        return this.dx;
    }
    public DoubleProperty getDy()
    {
        return this.dy;
    }
    public Line getVectorLine()
    {
        return this;
    }
            
    public Line Arrow1()
    {
        return this.arrow1;
    }
    
    public Line Arrow2()
    {
        return this.arrow2;
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
