/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HelpPackage;

/**
 *
 * @author BaconAndNachos
 */
public class ButtonHandling implements UIcontrols
{
    public static void handlePause()
    {
        disableAllButtons();
    //    Resume.setDisable(false);
      Start.setDisable(false);
      Reset.setDisable(false);
    }
    public static void handleSetInterface()
    {
        disableAllButtons();
        Start.setDisable(false);
        Done.setDisable(false);
    }
    
    public static void handleStart()
    {
        Start.setText("Resume");
        disableAllButtons();
        Reset.setDisable(false);
        Pause.setDisable(false);
        
        
    }
    public static void handleReset()
    {
         disableAllButtons();
         Start.setText("Start");
        Start.setDisable(false);
        Done.setDisable(false);
        
    }
      public static void handleResume()
    {
        
        disableAllButtons();
        Reset.setDisable(false);
        Pause.setDisable(false);
        
    }
    public static void disableAllButtons()
    {
        Pause.setDisable(true);
        Reset.setDisable(true);
        Start.setDisable(true);
        Done.setDisable(true);
    }
}
