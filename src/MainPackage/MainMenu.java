/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainPackage;
import HelpPackage.UIcontrols;
import Integration.Integration;
import Momentum.Momentum;
import Relativity.Relativity;
import CircularMotion.Uniform_Circular_Motion;
import TaylorSeries.TaylorSeries;
import SlitInterference.SlitInterference;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author cstuser
 */
public class MainMenu extends Application implements UIcontrols {

    boolean Mechanics = false;
    boolean Waves = false;
    boolean Calculus = false;

    boolean MOMENTUM = false;
    boolean RELATIVITY = false;
    boolean UCM = false;
    boolean TAYLORSERIES = false;
    boolean INTEGRATION = false;
    boolean INTERFERENCE = false;
    private Scene firstMenuScene;
    private Scene SecondMenuScene;
    
    private Scene momentumScene;        //6 scenes
    private Scene relScene;
    private Scene ucmScene;
    private Scene integrationScene;
    private Scene interferenceScene;
    private Scene taylorSeriesScene;
    
            
    
    @Override
    public void start(Stage primaryStage) {
   
        
        mainMenuPane.getChildren().addAll(mechanicsButton, wavesButton, calButton);
      
       //second menu
        secondMenuPane.getChildren().addAll(animation1Button, animation2Button,backButton);

        //Scenes
         firstMenuScene = new Scene(mainMenuPane, 300, 500);
         SecondMenuScene = new Scene(secondMenuPane, 600, 300);
        
        mainMenuPane.setAlignment(Pos.CENTER);
        secondMenuPane.setAlignment(Pos.CENTER);
        
//  mainMenuPane.setSpacing(100);
      
        
         momentumScene = new Scene(MomentumContainer,1900,1000);
         relScene = new Scene(RelativityContainer,1900,1000);  
         ucmScene = new Scene(cicularMotionContainer,1900,1000);
         integrationScene = new Scene(IntegrationContainer,1900,1000);
         interferenceScene = new Scene(interferenceContainer,1900,1000);
         taylorSeriesScene = new Scene(taylorSeriesContainer,1900,1000);
    
        setupCSSsheets();
        primaryStage.setScene(firstMenuScene);
        primaryStage.show();

        mechanicsButton.setOnAction((event) -> {
            Mechanics = true;
            primaryStage.setScene(SecondMenuScene);
        });
        wavesButton.setOnAction((event) -> {
            Waves = true;
            primaryStage.setScene(SecondMenuScene);
        });
        calButton.setOnAction((event) -> {
            Calculus = true;
            primaryStage.setScene(SecondMenuScene);
      });
        animation1Button.setOnAction((event) -> {                   //ANIMATION 1
                 primaryStage.setX(0);
        primaryStage.setY(0);
            if (Waves) 
            {
                RELATIVITY = true;
                 primaryStage.setScene(relScene);                   //RELATIVITY SETUP
                Relativity.setInterface();                  
            }
            else if(Mechanics)
            {
                MOMENTUM = true;
                primaryStage.setScene(momentumScene);                //MOMENTUM SETUP
                Momentum.setInterface();                     
            }
            else if(Calculus)
            {
                INTEGRATION= true;
                primaryStage.setScene(integrationScene);
                Integration.setInterface();
            }
        });
        animation2Button.setOnAction((event) -> {                   //ANIMATION 2
                 primaryStage.setX(0);
        primaryStage.setY(0);
            if(Mechanics)                                           //CIRCULAR MOTION SETUP
            {   
                UCM = true;
                primaryStage.setScene(ucmScene);
                Uniform_Circular_Motion.setInterface();
            }
            else if(Waves)                                          //SLIT INTERFERENCE SETUP
            {
                INTERFERENCE = true;
                primaryStage.setScene(interferenceScene);
                SlitInterference.setInterface();
            }
            else if(Calculus)
            {     TAYLORSERIES = true;
            primaryStage.setScene(taylorSeriesScene);
            TaylorSeries.setInterface();
            }
        });
            backButton.setOnAction((event) -> {                                 //BACK
            Calculus=false;
            Mechanics=false;
            Waves=false;
            primaryStage.setScene(firstMenuScene);
        });
        Start.setOnAction((event) -> {                                      //START
                if(RELATIVITY)
                Relativity.Start();
                else if(MOMENTUM)
                    Momentum.start();
                else if(UCM)
                    Uniform_Circular_Motion.start();
                else if(INTEGRATION)
                    Integration.start();
                else if(INTERFERENCE)
                    SlitInterference.start();
                else if(TAYLORSERIES)
                    TaylorSeries.start();
        });
        Reset.setOnAction((event) -> {                                      //RESET
                if(RELATIVITY)
                Relativity.reset();
                else if(UCM)
                    Uniform_Circular_Motion.reset();
                else if(MOMENTUM)
                    Momentum.reset();
                else if(INTERFERENCE)
                    SlitInterference.reset();
                else if(INTEGRATION)
                    Integration.reset();
                else if(TAYLORSERIES)
                    TaylorSeries.reset();
        });
          Pause.setOnAction((event) -> {                                    //PAUSE
                if(RELATIVITY)
                Relativity.pause();
                else if(MOMENTUM)
                    Momentum.pause();
                else if(INTERFERENCE)
                    SlitInterference.pause();
                else if(UCM)
                  Uniform_Circular_Motion.pause();
                else if(INTEGRATION)
                    Integration.pause();
                else if(TAYLORSERIES)
                    TaylorSeries.pause();
            });
          Done.setOnAction((event) -> {                                     //DONE
               if (MOMENTUM ) 
                 Momentum.Exit();
              else if(UCM)
                   Uniform_Circular_Motion.Exit();
               else if(INTEGRATION)
                  Integration.Exit();
              else if(TAYLORSERIES)
                   TaylorSeries.Exit();
               else if(RELATIVITY)
                   Relativity.Exit();
               else if(INTERFERENCE) {
                   SlitInterference.Exit();
               }
                 reset();
              primaryStage.setScene(firstMenuScene);
          });
         
    }
    public void reset()
    {
               Waves=false;
               Mechanics=false;
               Calculus=false;
               MOMENTUM = false;
               RELATIVITY = false;
               UCM = false;
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    public void setupCSSsheets()
    {
        firstMenuScene.getStylesheets().add("HelpPackage/sheet.css");
        SecondMenuScene.getStylesheets().add("HelpPackage/sheet.css");
          
        momentumScene.getStylesheets().add("HelpPackage/sheet.css");
        
        
        relScene.getStylesheets().add("HelpPackage/sheet.css");
          ucmScene.getStylesheets().add("HelpPackage/sheet.css");
          integrationScene.getStylesheets().add("HelpPackage/sheet.css");
          taylorSeriesScene.getStylesheets().add("HelpPackage/sheet.css");
          interferenceScene.getStylesheets().add("HelpPackage/sheet.css");
     
        mainMenuPane.setSpacing(75);
        
    }
}
