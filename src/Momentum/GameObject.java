/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Momentum;

/**
 *
 * @author BaconAndNachos
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import Momentum.Vector2D;
import java.util.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;

/**
 *
 * @author BaconAndNachos
 */
public class GameObject {

  protected Circle circle;
    protected Vector2D position;
    protected Vector2D velocity;
    protected Vector2D acceleration;
    boolean bottom = true;
    boolean top = true;
    boolean left = true;
    boolean right = true;
    static boolean collision = true;
    private double mass;
    public GameObject(Vector2D position, Vector2D velocity, Vector2D acceleration, double radius) {
        this.position = position;
        this.velocity = velocity;
        this.acceleration = acceleration;

        circle = new Circle(0.0, 0.0, radius);
        circle.setLayoutX(position.getX());
        circle.setLayoutY(position.getY());
    }

    public Vector2D getPosition() {
        return position;
    }

    public Vector2D getVelocity() {
        return velocity;
    }

    public void setPosition(double x, double y) {
        position.setX(x);
        position.setY(y);
        circle.setLayoutX(position.getX());
        circle.setLayoutY(position.getY());
    }

    public void setVelocity(double x, double y) {
        velocity.setX(x);
        velocity.setY(y);

    }

    public Circle getCircle() {
        return circle;
    }

    
    public void update(double dt) {

        position = position.add(velocity.mul(dt));

        circle.setLayoutX(position.getX());
        circle.setLayoutY(position.getY());

        Pane pane = (Pane) circle.getParent();
        if (right && (position.getX() >= pane.getPrefWidth() - circle.getRadius())) //RIGHT
        {

            velocity.setX(velocity.getX() * -1);
            right = false;
            top = true;
            bottom = true;
            left = true;
            collision = true;
        } else if (left && ((position.getX() <= circle.getRadius()))) //LEFT   //MAKE THIS ONLY HAPPEN ONCE
        {

            velocity.setX(velocity.getX() * -1);
            right = true;
            top = true;
            bottom = true;
            left = false;
            collision = true;

        } else if (bottom && (position.getY() >= pane.getPrefHeight() - circle.getRadius())) //BOTTOM
        {

            velocity.setY(velocity.getY() * -1);
            bottom = false;
            top = true;
            left = true;
            right = true;
            collision = true;
        } else if ((position.getY() <= circle.getRadius()) && top) //TOP
        {
            velocity.setY(velocity.getY() * -1);
            top = false;
            bottom = true;
            left = true;
            right = true;
            collision = true;
        }
    }

    public  void change() {
        top = true;
        bottom = true;
        left = true;
        right = true;
    }

    public static boolean getCollisionStatus() {
        return collision;
    }

    public static void collision(GameObject g1, GameObject g2) {

        collision = false;

        double dx = g1.getPosition().getX() - g2.getPosition().getX();
        double dy = g1.getPosition().getY() - g2.getPosition().getY();

        double collision_angle = Math.atan2(dy, dx) ;
     
          double vx_after_rotation_1 = (g1.getVelocity().getX() * Math.cos(collision_angle)) + (g1.getVelocity().getY() * Math.sin(collision_angle)); //XCOS THETA  + YSIN THETA
        double vy_after_rotation_1 = -(g1.getVelocity().getX() * Math.sin(collision_angle)) + (g1.getVelocity().getY() * Math.cos(collision_angle)); //-XSIN THETA  + YCOS THETA

        double vx_after_rotation_2 = (g2.getVelocity().getX() * Math.cos(collision_angle)) + (g2.getVelocity().getY() * Math.sin(collision_angle));
        double vy_after_rotation_2 = -(g2.getVelocity().getX() * Math.sin(collision_angle)) + (g2.getVelocity().getY() * Math.cos(collision_angle));

    
        double vx_after_rotation_1_adjusted = (vx_after_rotation_1*(g1.getMass() - g2.getMass()) 
                + 2*(g2.getMass()*vx_after_rotation_2)   )/ (g1.getMass() + g2.getMass())  ;
        
        
         double vx_after_rotation_2_adjusted = (vx_after_rotation_2*(g2.getMass() - g1.getMass()) 
                + 2*(g1.getMass()*vx_after_rotation_1)   )/ (g1.getMass() + g2.getMass())  ;
        
        double vy_after_rotation_1_adjusted = vy_after_rotation_1;      //DONT CHANGE THE Y

        double vy_after_rotation_2_adjusted = vy_after_rotation_2;

        double final_vx_re_rotated_1 = (vx_after_rotation_1_adjusted * Math.cos(collision_angle))        //XCOS THETA  - YSIN THETA
                - (vy_after_rotation_1_adjusted * Math.sin(collision_angle));

        double final_vy_re_rotated_1 = (vx_after_rotation_1_adjusted * Math.sin(collision_angle))           //XSIN THETA  + YCOS THETA
                + (vy_after_rotation_1_adjusted * Math.cos(collision_angle));

        double final_vx_re_rotated_2 = (vx_after_rotation_2_adjusted * Math.cos(collision_angle))
                - (vy_after_rotation_2_adjusted * Math.sin(collision_angle));

        double final_vy_re_rotated_2 = (vx_after_rotation_2_adjusted * Math.sin(collision_angle))
                + (vy_after_rotation_2_adjusted * Math.cos(collision_angle));

        g1.setVelocity(final_vx_re_rotated_1, final_vy_re_rotated_1);

        g2.setVelocity(final_vx_re_rotated_2, final_vy_re_rotated_2);

    }
    public static void setCollisionStatus(boolean b)
    {
        collision = b;
    }
    public double getMass()
    {
        return this.mass;
    }
    public void setMass(double mass)
    {
        this.mass = mass;
    }
  
public double momentum()
{
    return this.mass*(this.getVelocity().getX()+this.getVelocity().getY());
}


public static double getTotalMomentum(GameObject g1, GameObject g2)
{
      return g1.momentum() + g2.momentum();
              
}

}
    
 

