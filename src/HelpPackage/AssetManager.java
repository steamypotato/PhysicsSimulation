/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HelpPackage;

import java.io.File;
import javafx.scene.image.Image;

/**
 *
 * @author BaconAndNachos
 */
public class AssetManager {
    
      public static Image getEarthImage()
    {
         File file = new File("./images/earth3.png");
        return new Image(file.toURI().toString());
    }
       public static Image getSpaceshipImage()
    {
         File file = new File("./images/spaceship.png");
        return new Image(file.toURI().toString());
    }
       
       
       public static Image getSpaceBackgroundImage()
    {
         File file = new File("./images/space.jpg");
        return new Image(file.toURI().toString());
    }
}
