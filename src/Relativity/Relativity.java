/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Relativity;

/**
 *
 * @author BaconAndNachos
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import HelpPackage.AssetManager;
import HelpPackage.ButtonHandling;

import HelpPackage.UIControlsHandling;
import java.text.NumberFormat;
import javafx.animation.AnimationTimer;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import HelpPackage.UIcontrols;
import static HelpPackage.UIcontrols.elapsedTimeLabelMoving;
import static HelpPackage.UIcontrols.elapsedTimeLabelStationnary;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.CornerRadii;

;

/**
 *
 * @author cstuser
 */
public class Relativity implements UIcontrols {

    private static double initialTime, initialPausedTime, initialResumeTime;
    private static LineChart lengthContractionLineChart;
    private static LineChart timeDilationLineChart;
    private static XYChart.Series<Number,Number> lengthContractionSeries;
    private static XYChart.Series<Number,Number> lengthContractionSeriesSpaceship;
    private static XYChart.Series<Number,Number> timeDilationSeries;
    private static XYChart.Series<Number,Number> timeDilationSeriesSpaceship;
    
    private static AnimationTimer animation;
    private static ImageView spaceship, earth;
    private static Pane container;
    
       public static void setInterface() {
           ObservableList<String> list = FXCollections.observableArrayList("0.1c","0.2c","0.3c","0.4c","0.5c","0.6c","0.7c","0.8c","0.9c","1.0c");
    //    lengthContractionLineChart = new LineChart<>(new CategoryAxis(list),new NumberAxis("Length" ,0, spaceshipWidth+10, 10));
          lengthContractionLineChart = new LineChart<>(new NumberAxis("speed of c" , 0,1.0,0.1),new NumberAxis("Length" ,0, INITIAL_SPACESHIP_WIDTH+10, 10));
          timeDilationLineChart =new LineChart<>(new NumberAxis("speed of c,",0.1 , 1.0 , 0.1),new NumberAxis("Value of gamma",0,10,1));
          lengthContractionLineChart.setCreateSymbols(false);
          timeDilationLineChart.setCreateSymbols(false);
          lengthContractionLineChart.setLegendVisible(false);
          timeDilationLineChart.setLegendVisible(false);
          timeDilationLineChart.setTitle("Time dilation chart");
          lengthContractionLineChart.setTitle("Length contraction chart");
          container = new Pane();
          container.setMinSize(RelativityContainer.getWidth(),350);
          container.setMaxSize(container.getMinWidth(),container.getMinHeight());
         
          lengthContractionLineChart.setMinSize(600, 550);
          timeDilationLineChart.setMinSize(lengthContractionLineChart.getMinWidth(), lengthContractionLineChart.getMinHeight());
          timeDilationLineChart.setMaxSize(lengthContractionLineChart.getMinWidth(), lengthContractionLineChart.getMinHeight());
          lengthContractionLineChart.setMaxSize(lengthContractionLineChart.getMinWidth(), lengthContractionLineChart.getMinHeight());
          
          
          
            BackgroundImage backImg = new BackgroundImage(AssetManager.getSpaceBackgroundImage(),
                    
                BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        container.setBackground(new Background(backImg));
    
        UIControlsHandling.handleRelativityControls();
      
        Rectangle border = new Rectangle(0, 0, container.getPrefWidth(), container.getMinHeight());
        border.setFill(Color.TRANSPARENT);
        border.setStroke(Color.BLACK);
        
        spaceship = new ImageView(AssetManager.getSpaceshipImage());
        earth = new ImageView(AssetManager.getEarthImage());
        earth.setLayoutX(10);
        earth.setLayoutY(10);
        earth.setFitWidth(250);
        earth.setFitHeight(250);
        spaceship.setLayoutX(earth.getLayoutX() + earth.getFitWidth());
        spaceship.setLayoutY(earth.getLayoutY()+40);
        spaceship.setFitWidth(INITIAL_SPACESHIP_WIDTH);
        spaceship.setFitHeight(150);
        elapsedTimeLabelMoving.layoutXProperty().bind(spaceship.layoutXProperty());
        elapsedTimeLabelMoving.setLayoutY(spaceship.getY() + spaceship.getFitHeight() + 20);
        elapsedTimeLabelStationnary.setLayoutX(earth.getX());
        elapsedTimeLabelStationnary.setLayoutY(earth.getY() + earth.getFitHeight() + 20);

        ButtonHandling.handleSetInterface();
        speedTextField.textProperty().bindBidirectional(speedSlider.valueProperty(), NumberFormat.getNumberInstance());
        container.getChildren().addAll(border, earth, spaceship, elapsedTimeLabelMoving, elapsedTimeLabelStationnary);
        RelativityContainer.getChildren().addAll((container), new HBox(100,relativityUIgrid,lengthContractionLineChart,timeDilationLineChart));
      
 
    }

    
    
    public static void Start() {
        if (Start.getText().equals("Resume")) {
            resume();
        } else {
            ButtonHandling.handleStart();
            initialTime = System.nanoTime();
            
            lengthContractionSeries = new XYChart.Series<>();
            lengthContractionSeriesSpaceship = new XYChart.Series<>();
            
            timeDilationSeries = new XYChart.Series<>();
            timeDilationSeriesSpaceship = new XYChart.Series<>();
            lengthContractionLineChart.getData().add(lengthContractionSeries);
            lengthContractionLineChart.getData().add(lengthContractionSeriesSpaceship);
            timeDilationLineChart.getData().add(timeDilationSeries);
            timeDilationLineChart.getData().add(timeDilationSeriesSpaceship);
            
            lengthContractionSeries.getNode().getStyleClass().add("rel-series-line");
            timeDilationSeries.getNode().getStyleClass().add("rel-series-line");
            
            
            double userSpeed = Double.parseDouble(speedTextField.getText().replaceAll(" ", ""));
            createGraphs(userSpeed);
            double gamma = 1 / (Math.sqrt(1 - userSpeed*userSpeed)); // 1/sqrt(1 - v^2/c^2) but c = 1
            spaceship.setFitWidth(spaceship.getFitWidth() / gamma);       //adjust width based off gamma
            
           
            
            animation = new AnimationTimer() {
                @Override
                public void handle(long now) {
                    double currentTime = (now - initialTime - (initialResumeTime - initialPausedTime)) / 1000000000.0;
                  

                    if (slowModeCheckBox.isSelected()) {
                        spaceship.setLayoutX(spaceship.getLayoutX() + userSpeed);
                        double currentTimeRounded = (double) (Math.round(currentTime * 100.0) / 100.0);
                        double newTimeRounded  = (double)(Math.round(currentTimeRounded*gamma *100.0)/100.0);
                        elapsedTimeLabelMoving.setText("Elapsed Time : " + currentTimeRounded + " seconds");
                        elapsedTimeLabelStationnary.setText("Elapsed time : " + newTimeRounded +  " seconds / " + Math.round( (newTimeRounded/86400)*100.0)/100.0 + " days " );
                               
                    } else {
                        spaceship.setLayoutX(spaceship.getLayoutX() + userSpeed*10);
                       double currentTimeRounded = (double) (Math.round(currentTime * 100.0) / 100.0);
                        double newTimeRounded  = (double)(Math.round(currentTimeRounded*gamma *100.0)/100.0);
                        elapsedTimeLabelMoving.setText("Elapsed Time : " + currentTimeRounded + " seconds");
                        elapsedTimeLabelStationnary.setText("Elapsed time : " + newTimeRounded +  " seconds / " + Math.round( (newTimeRounded/86400)*100.0)/100.0 + " days " );
               
                    }

                }
            };
            animation.start();
        }
    }

 
    public static void reset() {
        ButtonHandling.handleReset();
               lengthContractionSeriesSpaceship.getData().clear();
               lengthContractionSeries.getData().clear();
               lengthContractionLineChart.getData().clear(); 
               timeDilationSeries.getData().clear();
               timeDilationSeriesSpaceship.getData().clear();
               timeDilationLineChart.getData().clear();
        animation.stop();
        initialPausedTime = 0;
        initialResumeTime = 0;
        spaceship.setFitWidth(INITIAL_SPACESHIP_WIDTH);
        spaceship.setLayoutX(earth.getLayoutX() + earth.getFitWidth() + 20);
        elapsedTimeLabelMoving.setText("Elapsed Time : ");
        elapsedTimeLabelStationnary.setText("Elapsed Time : ");

    }

    public static void pause() {
        initialPausedTime += System.nanoTime();
        animation.stop();
        ButtonHandling.handlePause();
    }

    public static void resume() {
        initialResumeTime += System.nanoTime();
        animation.start();
        ButtonHandling.handleResume();
}

    public static void Exit() {
        container.getChildren().clear();
        RelativityContainer.getChildren().clear();
        relativityUIgrid.getChildren().clear();
    }
    public static void createGraphs(double speed)
    {
       for(double i = 0 ; i<1.0 ; i += 0.001)
        {
            lengthContractionSeries.getData().add(new XYChart.Data(i,newLength(i)));
        }
          for(double i = 0 ; i<speed ; i+= 0.001)
        {
            lengthContractionSeriesSpaceship.getData().add(new XYChart.Data(i, (newLength(i))));
        }
         for(double i = 0; i<10 ; i+=0.001)
          {
              timeDilationSeries.getData().add(new XYChart.Data(i,newTime(i)));
          }
          for(double i = 0; i<speed ; i+=0.001)
          {
              timeDilationSeriesSpaceship.getData().add(new XYChart.Data(i,newTime(i)));
          }
          
    }
    public static double newLength(double c)
    {
        return  INITIAL_SPACESHIP_WIDTH / (1 / (Math.sqrt(1 - c*c)));
    }
     public static double newTime(double c)
    {
        return  (1 / (Math.sqrt(1 - c*c)));
    }
}
