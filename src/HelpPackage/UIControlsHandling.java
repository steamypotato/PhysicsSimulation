/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HelpPackage;



import javafx.beans.binding.Bindings;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

/**
 *
 * @author BaconAndNachos
 */
public class UIControlsHandling implements UIcontrols{
    
    
    
    
    
    public static void handleMomentumUIcontrols()
    {
        momentumUIgrid.add(Start, 1, 0);                  //ADD UICONTROLS TO THE SCREEN
        momentumUIgrid.add(Pause, 1, 1);
        momentumUIgrid.add(Reset, 1, 2);
      //  momentumUIgrid.add(Help, 1, 3);
        momentumUIgrid.add(Done, 1, 3);
       
        momentumUIgrid.add(firstBallLabel, 2, 0);
        momentumUIgrid.add(firstBallXspeedLabelMomentum, 2, 1);  
        momentumUIgrid.add(firstBallXspeedSliderMomentum, 2, 2);
        momentumUIgrid.add(firstBallYspeedLabelMomentum, 2, 3);  
        momentumUIgrid.add(firstBallYspeedSliderMomentum, 2, 4);
        momentumUIgrid.add(firstBallMassLabelMomentum, 2, 5);
        momentumUIgrid.add(firstBallMassSliderMomentum, 2, 6);
        momentumUIgrid.add(secondBallLabel, 3, 0);
        momentumUIgrid.add(secondBallXspeedLabelMomentum, 3, 1);
        momentumUIgrid.add(secondBallXspeedSliderMomentum, 3, 2);
        momentumUIgrid.add(secondBallYspeedLabelMomentum, 3, 3);
        momentumUIgrid.add(secondBallYspeedSliderMomentum, 3, 4);
        momentumUIgrid.add(secondBallMassLabelMomentum, 3, 5);
        momentumUIgrid.add(secondBallMassSliderMomentum, 3, 6);
        momentumUIgrid.add(showVelocityVectorCheckBoxMomentum, 4, 0);
        
       firstBallYPositionSliderMomentum.setPadding(new Insets(0, 0, 0, 50));
        secondBallYPositionSliderMomentum.setPadding(new Insets(50, 0, 0, 0));
        momentumUIgrid.setVgap(20);
        momentumUIgrid.setHgap(20);
        momentumUIgrid.getStyleClass().add("GridPane");
        firstBallYPositionSliderMomentum.getStyleClass().add("vertical-slider");
        secondBallYPositionSliderMomentum.getStyleClass().add("vertical-slider");
        
       momentumUIgrid.setPadding(new Insets(0, 0, 0, 35));
        
    }
    public static void handleUCMcontrols()
    {
        orbitCircleUCM.setFill(Color.web("#A43820"));
       circularMotionUIgrid.add(Start, 1, 0);                                                       //ADD COMPONENTS TO THE UI
        circularMotionUIgrid.add(Pause, 1, 1);
        circularMotionUIgrid.add(Reset, 1, 2);
        circularMotionUIgrid.add(Done, 1, 3);
        
        circularMotionUIgrid.add(changeRadiusLabelUCM, 2, 0);
         circularMotionUIgrid.add(changeRadiusSliderUCM, 2, 1);
        circularMotionUIgrid.add(changePeriodLabelUCM, 2, 2);
        circularMotionUIgrid.add(changePeriodSliderUCM, 2, 3);
        circularMotionUIgrid.add(changeMassLabelUCM, 2, 4);
        circularMotionUIgrid.add(changeMassSliderUCM, 2, 5);
        
    
        
        circularMotionUIgrid.getStyleClass().add("GridPane");
        

    }
    public static void handleRelativityControls()
    {
        relativityUIgrid.add(Start, 1, 0);
        relativityUIgrid.add(Pause, 1, 1);
        relativityUIgrid.add(Reset, 1, 2);
     //   relativityUIgrid.add(Help, 1, 3);
        relativityUIgrid.add(Done, 1, 3);
      
        relativityUIgrid.add(speedLabel, 2, 0);
        relativityUIgrid.add(speedSlider, 2, 1);
        relativityUIgrid.add(speedTextField,2 ,2);
        relativityUIgrid.add(slowModeCheckBox, 2,3);
     
        relativityUIgrid.getStyleClass().add("GridPane");
        speedTextField.getStyleClass().add("rel-text-field");
       elapsedTimeLabelStationnary.getStyleClass().add("white-label");
       elapsedTimeLabelMoving.getStyleClass().add("white-label");
        
   
        }
    public static void handleInterferenceControls()
    {
        interferenceControlsGrid.add(Start, 1, 0);
        interferenceControlsGrid.add(Pause, 1, 1);
        interferenceControlsGrid.add(Reset, 1, 2);
        interferenceControlsGrid.add(Done, 1, 3);
  
        interferenceControlsGrid.add(slitDistanceLabel, 2, 0);
        interferenceControlsGrid.add(slitDistanceSlider, 2, 1);
        interferenceControlsGrid.add(distanceFromScreenLabel, 2, 2);
        interferenceControlsGrid.add(distanceFromScreenSlider, 2, 3);
        interferenceControlsGrid.add(wavelengthLabel, 3, 0);
        interferenceControlsGrid.add(wavelengthSlider, 3, 1);
        interferenceControlsGrid.getStyleClass().add("GridPane");
        
     }
    public static void handleIntegrationControls()
    {
           integrationControlsGrid.add(Start, 1, 0);
        integrationControlsGrid.add(Pause, 1, 1);
              integrationControlsGrid.add(Reset, 1, 2);
  
        //integrationControlsGrid.add(Help, 1, 3);
        integrationControlsGrid.add(Done, 1, 3);
     
        integrationControlsGrid.add(enterFunctionTextFieldIntegration, 2, 0);
        integrationControlsGrid.add(upperBoundTextField, 2, 1);
        integrationControlsGrid.add(lowerBoundField, 2, 2);
        integrationControlsGrid.add(nDivisionTextField, 2, 3);
        integrationControlsGrid.add(zoomInCheckBox, 2, 4);
        integrationControlsGrid.add(enterFunctionLabelIntegration, 3, 0);
        integrationControlsGrid.add(uppoerBoundLabel, 3, 1);
        integrationControlsGrid.add(lowerBoundLabel, 3, 2);
        integrationControlsGrid.add(nDvisionsLabel, 3, 3);
        
        integrationControlsGrid.getStyleClass().add("GridPane");
    
        nDivisionTextField.getStyleClass().add("small-text-field");
        upperBoundTextField.getStyleClass().add("small-text-field");
        lowerBoundField.getStyleClass().add("small-text-field");
        
        
        GridPane.setHalignment(upperBoundTextField, HPos.CENTER);
        GridPane.setHalignment(lowerBoundField, HPos.CENTER);
        GridPane.setHalignment(nDivisionTextField, HPos.CENTER);
        
        
     
    }
    public static void handleTaylorSeriesControls()
    {
            taylorFunctionsListView.setPrefWidth(100);
              
            taylorFunctionsListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
      taylorSeriesControlsVBox.getChildren().addAll(Start,Reset,Pause,Done);
        
taylorSeriesControlsVBox.setSpacing(30);
taylorSeriesControlsVBox.setPadding(new Insets(0,0,0,50));
        
    }
      public static void handleMomentumSliders()
        {
        firstBallXspeedSliderMomentum.setShowTickLabels(true);
        firstBallXspeedSliderMomentum.setShowTickMarks(true);
        firstBallYspeedSliderMomentum.setShowTickLabels(true);
        firstBallYspeedSliderMomentum.setShowTickMarks(true);
        secondBallXspeedSliderMomentum.setShowTickLabels(true);
        secondBallXspeedSliderMomentum.setShowTickMarks(true);
        secondBallYspeedSliderMomentum.setShowTickLabels(true);
        secondBallYspeedSliderMomentum.setShowTickMarks(true);
        firstBallMassSliderMomentum.setShowTickMarks(true);
        firstBallMassSliderMomentum.setShowTickLabels(true);
        secondBallMassSliderMomentum.setShowTickMarks(true);
        secondBallMassSliderMomentum.setShowTickLabels(true);
        
      }
      public static void handleUCMSliders()
      {
        changeRadiusSliderUCM.setShowTickMarks(true);
        changeRadiusSliderUCM.setShowTickLabels(true);

        changePeriodSliderUCM.setShowTickMarks(true);
        changePeriodSliderUCM.setShowTickLabels(true);
      }
}
