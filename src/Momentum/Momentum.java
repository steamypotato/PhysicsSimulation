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
import javafx.animation.AnimationTimer;
import javafx.geometry.Orientation;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import HelpPackage.UIcontrols;
import javafx.beans.binding.Bindings;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import HelpPackage.ButtonHandling;
import HelpPackage.UIControlsHandling;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Control;
import javafx.scene.layout.VBox;

/**
 *
 * @author BaconAndNachos
 */
public class Momentum implements UIcontrols {

    private static double initialTime, initialPausedTime, initialResumeTime;
    private static GameObject g1, g2;
    private static double lastFrameTime;
    private static Pane container;
    private static Momentum instance;
    private static AnimationTimer animation;
    static double count = 0;
  //  private static LineChart<Number, Number> lineChartBall1;
    //private static LineChart<Number, Number> lineChartBall2;
private static LineChart<Number, Number> lineChart;
    
  //  private static XYChart.Series firstBallMometumSeries;
    //private static XYChart.Series secondBallMometumSeries;
      private static XYChart.Series totalMomentumSeries;
    
    private static VectorLine vectorLineFirstBall;
    private static VectorLine vectorLineSecondBall;

    public static void setInterface() {

        initialPausedTime = 0;
        initialResumeTime = 0;
        initialTime = 0;
        ButtonHandling.handleSetInterface();
        lineChart = new LineChart<>(new NumberAxis(), new NumberAxis());
        lineChart.setMinSize(800, 500);
        lineChart.setMaxSize(lineChart.getMinWidth(), lineChart.getMinHeight());
        lineChart.setCreateSymbols(false);
        lineChart.setTitle("Total momentum of system of two balls");
 /*       lineChartBall2 = new LineChart<>(new NumberAxis(), new NumberAxis());
        lineChartBall2.setMinSize(lineChartBall1.getMinWidth(), lineChartBall1.getMinHeight());
        lineChartBall2.setMaxSize(lineChartBall2.getMinWidth(), lineChartBall2.getMinHeight());
        lineChartBall2.setCreateSymbols(false);
          lineChartBall1.setTitle("Magnitude of velocity for brown ball");
        lineChartBall2.setTitle("Magnitude of velocity for whiteball");
   */    
        container = new Pane();
        container.setPrefHeight(300);
        container.setPrefWidth(1000);
        container.setMaxSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);
        container.setMinSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);

        Rectangle border = new Rectangle(0, 0, container.getPrefWidth(), container.getPrefHeight());
       border.setFill(Color.web("#ffa07a"));
       border.setStroke(Color.web("#46211a"));
       border.setStrokeWidth(5);
        
        UIControlsHandling.handleMomentumUIcontrols();

        g1 = new GameObject(new Vector2D(70, 0), new Vector2D(0, 0), null, 0);
        g2 = new GameObject(new Vector2D(container.getPrefWidth() - 70, 0), new Vector2D(0, 0), null, 0);
        
        g1.getCircle().radiusProperty().bind(Bindings.multiply(5, firstBallMassSliderMomentum.valueProperty()));     //adjust size of the ball with mass slider and scale it
        g2.getCircle().radiusProperty().bind(Bindings.multiply(5, secondBallMassSliderMomentum.valueProperty()));

        g1.getCircle().setFill(Color.web("A43820"));
        g1.getCircle().setStroke(Color.web("#46211a"));
        g2.getCircle().setFill(Color.web("ffffff"));
        g2.getCircle().setStroke(Color.web("#46211a"));
        g1.getCircle().layoutYProperty().bind(firstBallYPositionSliderMomentum.valueProperty());
        g2.getCircle().layoutYProperty().bind(secondBallYPositionSliderMomentum.valueProperty());

        container.getChildren().addAll(border, g1.getCircle(), g2.getCircle());
        MomentumContainer.getChildren().addAll(new HBox(80, new HBox(5, firstBallYPositionSliderMomentum,
                container, secondBallYPositionSliderMomentum), new HBox(10,new VBox(25,beforeCollisionLabel,firstBallDisplayMomentumBeforeCollision,firstBallMomentumLabelBefore,
               secondBallDisplayMomentumBeforeCollision, secondBallMomentumLabelBefore,beforeCollisionDisplayLabelTotalMomentum,totalMomentumLabelBefore),new VBox(25,afterCollisionLabel,
               firstBallDisplayMomentumAfterCollision,firstBallMomentumLabelAfter,secondBallDisplayMomentumAfterCollision,secondBallMomentumLabelAfter,
                        afterCollisionDisplayLabelTotalMomentum,totalMomentumLabelAfter ))),
                new HBox(175, momentumUIgrid, lineChart));

    }

    public static void start() {
        if (Start.getText().equals("Resume")) {
            resume();
        } else {
            ButtonHandling.handleStart();

            totalMomentumSeries = new XYChart.Series<>();
            lineChart.getData().add(totalMomentumSeries);
      //      secondBallMometumSeries = new XYChart.Series<>();
            

         //   lineChartBall1.getData().addAll(firstBallMometumSeries);
       //     lineChartBall2.getData().addAll(secondBallMometumSeries);
          
            vectorLineFirstBall = new VectorLine();
            vectorLineSecondBall = new VectorLine();
            vectorLineFirstBall.setInvisible();
            vectorLineSecondBall.setInvisible();
   //         container.getChildren().addAll(vectorLineFirstBall, vectorLineSecondBall);
            showVelocityVectorCheckBoxMomentum.setOnAction((event) -> {
                
                 if(showVelocityVectorCheckBoxMomentum.isSelected())
                {   vectorLineFirstBall.setDefaultColor();
                    vectorLineSecondBall.setDefaultColor(); 
                }
                 else
                 {      vectorLineFirstBall.setInvisible();
                     vectorLineSecondBall.setInvisible(); }
            });     
            g1.getCircle().layoutYProperty().unbind(); //a bound value cannot be set so unbind
            g2.getCircle().layoutYProperty().unbind();
            g1.setMass(firstBallMassSliderMomentum.getValue());
            g2.setMass(secondBallMassSliderMomentum.getValue());
            lastFrameTime = 0;

            g1.setPosition(g1.getPosition().getX(), firstBallYPositionSliderMomentum.getValue());       //set ininitial positions and vels from sliders
            g2.setPosition(g2.getPosition().getX(), secondBallYPositionSliderMomentum.getValue());
            g1.setVelocity(firstBallXspeedSliderMomentum.getValue(), firstBallYspeedSliderMomentum.getValue());
            g2.setVelocity(secondBallXspeedSliderMomentum.getValue(), secondBallYspeedSliderMomentum.getValue());
            initialTime = System.nanoTime();
        
            animation = new AnimationTimer() {
                @Override
                public void handle(long now) {
                  
                    
                 double currentTime = (now - initialTime - (initialResumeTime - initialPausedTime)) / 1000000000.0;
                 double frameDeltaTime = currentTime - lastFrameTime;
              lastFrameTime = currentTime;
                    vectorLineFirstBall.setPosition(g1.getPosition().getX(), g1.getPosition().getY(), g1.getVelocity());
                    vectorLineSecondBall.setPosition(g2.getPosition().getX(), g2.getPosition().getY(), g2.getVelocity());
                    g1.update(frameDeltaTime);
                    g2.update(frameDeltaTime);
                    updateGraph(currentTime, g1, g2);
                    double d = Vector2D.distance(g1.getPosition(), g2.getPosition());
                    if ((d < g1.getCircle().getRadius() + g2.getCircle().getRadius()) && GameObject.getCollisionStatus()) { //prevents the code from happening multiple times(clipping)
                        firstBallMomentumLabelBefore.setText(String.valueOf(Math.round(g1.momentum())) + "kg*px/s");
                        secondBallMomentumLabelBefore.setText(String.valueOf(Math.round(g2.momentum())) + "kg*px/s");
                        totalMomentumLabelBefore.setText(String.valueOf(Math.round(GameObject.getTotalMomentum(g1, g2))) + "kg*px/s");
                        GameObject.collision(g1, g2);
                         firstBallMomentumLabelAfter.setText(String.valueOf(Math.round(g1.momentum())) + "kg*px/s");
                        secondBallMomentumLabelAfter.setText(String.valueOf(Math.round(g2.momentum())) + "kg*px/s");
                        totalMomentumLabelAfter.setText(String.valueOf(Math.round(GameObject.getTotalMomentum(g1, g2))) + "kg*px/s");
                        
                        g1.change();
                        g2.change();
                    }
                }
            };
            animation.start();
        }
    }

    public static void updateGraph(double time, GameObject g1, GameObject g2) {

  //      if (time >= count) {
            totalMomentumSeries.getData().add(new XYChart.Data<>(time, GameObject.getTotalMomentum(g1, g2)));
          //  secondBallMometumSeries.getData().add(new XYChart.Data<>(time, g2.momentum()));
    //      count += 0.25;
      //  }

    }

    public static void reset() {
        ButtonHandling.handleReset();
        GameObject.setCollisionStatus(true);
        count = 0;
        totalMomentumSeries.getData().clear();
     //   secondBallMometumSeries.getData().clear();
        lineChart.getData().clear();
    //    lineChartBall2.getData().clear();
        
        vectorLineFirstBall.remove();
        vectorLineSecondBall.remove();
        
        initialPausedTime = 0;
        initialResumeTime = 0;

        animation.stop();
        g1.change();
        g2.change();

        g1.setPosition(BALL1_INITIAL_X, 0);
        g2.setPosition(container.getPrefWidth() - BALL1_INITIAL_X, 0);

        g1.getCircle().layoutYProperty().bind(firstBallYPositionSliderMomentum.valueProperty());
        g2.getCircle().layoutYProperty().bind(secondBallYPositionSliderMomentum.valueProperty());
        showVelocityVectorCheckBoxMomentum.setSelected(false);

    }

    public static void pause() {
        ButtonHandling.handlePause();

        initialPausedTime += System.nanoTime();
        animation.stop();
    }

    public static void resume() {
        ButtonHandling.handleResume();
        initialResumeTime += System.nanoTime();
        animation.start();
    }

    public static Pane getPane() {
        return container;
    }

    public static void Exit() {

        container.getChildren().clear();
        momentumUIgrid.getChildren().clear();
        MomentumContainer.getChildren().clear();
    }
    
}
