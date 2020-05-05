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

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 *
 * @author cstuser
 */
public interface UIcontrols extends Constants {

    //Main Menu Controls
        VBox mainMenuPane = new VBox(20);
        VBox secondMenuPane = new VBox(20);


    Button wavesButton = new Button("Waves");
    Button mechanicsButton = new Button("Mechanics");
    Button calButton = new Button("Calculus");

    Button animation1Button = new Button("Animation 1");
    Button animation2Button = new Button("Animation 2");
    Button backButton = new Button("Back");
    
    Button Start = new Button("Start");
    Button Reset = new Button("Reset");
    Button Pause = new Button("Pause");
  //  Button Help = new Button("Help");
    Button Done = new Button("Done");
 
    //Relativity
    TextField speedTextField = new TextField();
    Label speedLabel = new Label("Enter speed where c = 1");
    Slider  speedSlider= new Slider(0, 0.999, 0.3);

    VBox RelativityContainer = new VBox(100);
    GridPane relativityUIgrid =new GridPane();
    CheckBox slowModeCheckBox =new CheckBox("Slow down");
    Label elapsedTimeLabelStationnary = new Label("Elapsed time : ");
    Label elapsedTimeLabelMoving = new Label("Elapsed time : ");
    

    //Momentum
    GridPane momentumUIgrid = new GridPane();
    VBox MomentumContainer = new VBox(100);

    Slider firstBallXspeedSliderMomentum = new Slider(0, 400,100);      //speeds
    Slider firstBallYspeedSliderMomentum = new Slider(0, 400,0);
    Slider secondBallXspeedSliderMomentum = new Slider(0, 400,0);
    Slider secondBallYspeedSliderMomentum = new Slider(0, 400,0);
    
    Slider firstBallYPositionSliderMomentum = new Slider(0+30,300-30,150+15);
    Slider secondBallYPositionSliderMomentum = new Slider(0+30,300-30,150+15);
    Slider firstBallMassSliderMomentum = new Slider(1, 10, 4);
    Slider secondBallMassSliderMomentum = new Slider(1,10,4);
    
    Label firstBallXspeedLabelMomentum = new Label("set first ball x speed");
    Label secondBallXspeedLabelMomentum = new Label("set second ball x speed");
    
    Label firstBallYspeedLabelMomentum = new Label("set first ball y speed");
    Label secondBallYspeedLabelMomentum = new Label("set second ball y speed");
    
    Label firstBallMassLabelMomentum = new Label("set fist ball mass");
    Label secondBallMassLabelMomentum = new Label("set second ball mass");
    CheckBox showVelocityVectorCheckBoxMomentum = new CheckBox("Show velocity vectors");
    
    Label firstBallLabel = new Label("First ball");
    Label secondBallLabel = new Label("Second ball");
    
    Label beforeCollisionLabel = new Label("Before collision");
    Label firstBallDisplayMomentumBeforeCollision = new Label("Momentum of first ball :");
    Label firstBallMomentumLabelBefore = new Label("");
    Label  secondBallDisplayMomentumBeforeCollision = new Label("Momentum of second ball :");
        Label secondBallMomentumLabelBefore = new Label("");
    Label beforeCollisionDisplayLabelTotalMomentum = new Label("Total momentum :");
    Label totalMomentumLabelBefore = new Label("");
    
    
    
    Label afterCollisionLabel = new Label("After collision");
    Label firstBallDisplayMomentumAfterCollision = new Label("Momentum of first ball :");
    Label firstBallMomentumLabelAfter = new Label("");
    Label  secondBallDisplayMomentumAfterCollision = new Label("Momentum of second ball :");
    Label secondBallMomentumLabelAfter = new Label("");
    Label afterCollisionDisplayLabelTotalMomentum = new Label("Total momentum :");
    Label totalMomentumLabelAfter = new Label("");
    
    
    
    
    
    
  
    //Label totalMomentumLabel = new Label("");
    
    
    
   //CICULAR MOTION
    VBox cicularMotionContainer = new VBox(50);
    Circle orbitCircleUCM = new Circle();
    Circle orbitPathUCM = new Circle();
    GridPane circularMotionUIgrid =new GridPane();
    
    Slider changeRadiusSliderUCM = new Slider(25, 140, INITIAL_RADIUS);
    Label changeRadiusLabelUCM = new Label("Enter radius");
    Slider changePeriodSliderUCM = new Slider(1, 10, INITIAL_PERIOD);
    Label changePeriodLabelUCM = new Label("Enter period");
    Slider changeMassSliderUCM = new Slider(5, 22, INITIAL_MASS_UCM);
    Label changeMassLabelUCM = new Label("Enter mass");
  
    
    
   
    CheckBox showAccelerationVectorCheckboxUCM = new CheckBox("show acceleration vector");
    CheckBox showVelocityVectorCheckboxUCM = new CheckBox("show velocity vector");
    CheckBox hideOrbitCheckBox = new CheckBox("Hide orbit path");
    Label accelerationDisplayLabelUCM = new Label("Acceleration");
    Label accelerationLabelUCM = new Label("");
    Label velocityDisplayLabelUCM = new Label("Velocity");
    Label velocityLabelUCM = new Label("");
    Label angularAccDisplayLabelUCM = new Label("Angular Acceleration");
    Label angularAccLabelUCM = new Label("");
    Label centripedalForceDisplayLabelUCM = new Label("Centripedal Force");
    Label centripedalForceLabelUCM = new Label("");
    
    
    
    //Integration
    VBox IntegrationContainer = new VBox(100);
    GridPane integrationControlsGrid = new GridPane();
    TextField enterFunctionTextFieldIntegration = new TextField("x^2+5x-6");
    Label enterFunctionLabelIntegration = new Label("Enter a function");
    TextField upperBoundTextField = new TextField("6");
    Label uppoerBoundLabel = new Label("Enter upper bound of integration");
    TextField lowerBoundField = new TextField("-1");
    Label lowerBoundLabel = new Label("Enter lower bound of integration");
    TextField nDivisionTextField = new TextField("8");
    Label nDvisionsLabel = new Label("Enter seperations");
    CheckBox zoomInCheckBox = new CheckBox("Scale the graph ");
    Label resultLabelIntegration = new Label("Exact Value : ");
    Label resultIntegration = new Label();
    Label approximateResultLabelIntegration = new Label("Approximate Value : ");
    Label approximateResultIntegration = new Label();
    Label relativeErrorDisplayLabel = new Label("relative error : ");
    Label relativeErrorLabel = new Label("");
    
    //INTERFERENCE
    VBox interferenceContainer = new VBox(100);
    GridPane interferenceControlsGrid =new GridPane();
    Slider slitDistanceSlider = new Slider(10, 150, INITIAL_SLIT_DISTANCE);
    Label wavelengthLabel = new Label("wavelength");
    Label slitDistanceLabel = new Label("distance between slits");
    Label distanceFromScreenLabel = new Label("distance to screen");

    
    Slider wavelengthSlider = new Slider(400,700,INITIAL_WAVELENGTH);
    Slider distanceFromScreenSlider = new Slider(200, 550, INITIAL_SCREEN_DISTANCE);

    
        //TAYLOR SERIES
    TextField TaylorSeriesFunctionTextField = new TextField("sinx");
    VBox taylorSeriesControlsVBox =new VBox();
    Label TaylorSeriesFunctionText = new Label();
    ObservableList<String> items = FXCollections.observableArrayList("sinx", "cosx" ,"e^x" );
    ListView<String> taylorFunctionsListView = new ListView<>(items);
    VBox taylorSeriesContainer = new VBox(100);
    
}
