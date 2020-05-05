/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CircularMotion;

/**
 *
 * @author cstuser
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import javafx.animation.AnimationTimer;
import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import HelpPackage.UIcontrols;
import HelpPackage.ButtonHandling;
import HelpPackage.UIControlsHandling;

import com.sun.javafx.fxml.expression.BinaryExpression;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import javafx.animation.KeyFrame;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.transform.Rotate;
import javax.naming.Binding;

/**
 *
 * @author BaconAndNachos
 */
public class Uniform_Circular_Motion implements UIcontrols {

    private static PathTransition transition;
    private static LineChart<Number, Number> lineChart;
    private static LineChart<Number, Number> lineChart2;
    private static XYChart.Series posXSeries;
    private static XYChart.Series posYSeries;
    static Timer timer;
    static Timeline timeline;
    private static AnimationTimer animation;
    private static Pane container;
    static Circle centerCircle;
    //   static double speed, radius, acceleration, period,angular_frequency;
    static VectorLineUCM vectorLineAcceleration;
    static VectorLineUCM vectorLineVelocity;
    private static double initialTime, iniPauseTime, iniResumeTime;

    private static double lastFrameTime = 0.0;

    public static void setInterface() {
        lineChart = new LineChart(new NumberAxis(), new NumberAxis(-300, 300, 100));
        lineChart2 = new LineChart(new NumberAxis(), new NumberAxis(-300, 300, 100));
        lineChart.setCreateSymbols(false);
        lineChart2.setCreateSymbols(false);
        lineChart.setTitle("Graph of X translation vs time");
        lineChart.setMinSize(500, 500);
        lineChart2.setMinSize(lineChart.getMinWidth(), lineChart.getMinHeight());
        lineChart.setMaxSize(lineChart.getMinWidth(), lineChart.getMinHeight());
        lineChart2.setMaxSize(lineChart.getMinWidth(), lineChart.getMinHeight());
        lineChart2.setTitle("Graph of Y translation vs time");
        lineChart.setLegendVisible(false);
        lineChart2.setLegendVisible(false);

        container = new Pane();
        container.setPrefWidth(700);
        container.setPrefHeight(350);
        orbitPathUCM.setFill(Color.web("ffa07a"));
        orbitPathUCM.setStroke(Color.BLACK);

        orbitPathUCM.radiusProperty().bind(changeRadiusSliderUCM.valueProperty());

        VBox displayNumbersPane = new VBox(25);

        displayNumbersPane.getChildren().addAll(velocityDisplayLabelUCM, velocityLabelUCM, angularAccDisplayLabelUCM,
                angularAccLabelUCM, accelerationDisplayLabelUCM, accelerationLabelUCM, centripedalForceDisplayLabelUCM, centripedalForceLabelUCM);
        displayNumbersPane.setAlignment(Pos.CENTER);
        UIControlsHandling.handleUCMcontrols();
        Rectangle border = new Rectangle(0, 0, container.getPrefWidth(), container.getPrefHeight());
        border.setFill(Color.web("#ffa07a"));
        border.setStroke(Color.web("#46211a"));
        border.setStrokeWidth(5);

        orbitPathUCM.setCenterX(container.getPrefWidth() / 2);
        orbitPathUCM.setCenterY(container.getPrefHeight() / 2);
        orbitCircleUCM.centerXProperty().bind(Bindings.add(orbitPathUCM.centerXProperty(), orbitPathUCM.radiusProperty()));
        orbitCircleUCM.centerYProperty().bind(orbitPathUCM.centerYProperty());
        orbitCircleUCM.radiusProperty().bind(changeMassSliderUCM.valueProperty());
        centerCircle = new Circle(container.getPrefWidth() / 2, container.getPrefHeight() / 2, 5);
        container.getChildren().addAll(border, orbitPathUCM, centerCircle, orbitCircleUCM);

        cicularMotionContainer.getChildren().addAll(new HBox(20, container, new HBox(100, displayNumbersPane,
                new VBox(40, showVelocityVectorCheckboxUCM, showAccelerationVectorCheckboxUCM, hideOrbitCheckBox))),
                new HBox(100, circularMotionUIgrid, lineChart, lineChart2));
        ButtonHandling.handleSetInterface();
    }

    public static void start() {
        if (Start.getText().equals("Resume")) {

            resume();
        } else {
            initialTime = System.nanoTime();
            ButtonHandling.handleStart();
            posXSeries = new XYChart.Series<>();
            posYSeries = new XYChart.Series<>();

            lineChart.getData().add(posXSeries);
            lineChart2.getData().add(posYSeries);

            double mass = changeMassSliderUCM.getValue();
            double radius = changeRadiusSliderUCM.getValue();
            double period = changePeriodSliderUCM.getValue();
            double angular_acc = (2 * Math.PI) / period;
            double velocity = radius * angular_acc;
            double acceleration = (angular_acc * angular_acc) * radius;
            angularAccLabelUCM.setText((String.valueOf(Math.round(angular_acc * 100.0) / 100.0)) + "rad/s^2");
            accelerationLabelUCM.setText(String.valueOf(Math.round(acceleration * 100.0) / 100.0) + "px/s^2");
            velocityLabelUCM.setText(String.valueOf(Math.round(velocity * 100.0) / 100.0) + "px/s");
            centripedalForceLabelUCM.setText(String.valueOf(Math.round((mass * (velocity * velocity)) / radius)) + "N");

            vectorLineAcceleration = new VectorLineUCM();          //create accelerationVector
            vectorLineAcceleration.getStartXProperty().bind(Bindings.add(orbitCircleUCM.centerXProperty(), orbitCircleUCM.translateXProperty()));
            vectorLineAcceleration.getStartYProperty().bind(Bindings.add(orbitCircleUCM.centerYProperty(), orbitCircleUCM.translateYProperty()));
            vectorLineAcceleration.getDx().bind(Bindings.subtract(centerCircle.centerXProperty(), vectorLineAcceleration.getStartXProperty()));
            vectorLineAcceleration.getDy().bind(Bindings.subtract(centerCircle.centerYProperty(), vectorLineAcceleration.getStartYProperty()));
            vectorLineAcceleration.createAccelerationArrow(acceleration);

            vectorLineVelocity = new VectorLineUCM();              ////create accelerationVector
            vectorLineVelocity.getStartXProperty().bind(Bindings.add(orbitCircleUCM.centerXProperty(), orbitCircleUCM.translateXProperty()));
            vectorLineVelocity.getStartYProperty().bind(Bindings.add(orbitCircleUCM.centerYProperty(), orbitCircleUCM.translateYProperty()));
            vectorLineVelocity.getDx().bind(Bindings.subtract(centerCircle.centerXProperty(), vectorLineVelocity.getStartXProperty()));
            vectorLineVelocity.getDy().bind(Bindings.subtract(centerCircle.centerYProperty(), vectorLineVelocity.getStartYProperty()));
            vectorLineVelocity.createVelocityArrow(velocity);
            vectorLineVelocity.setInvisible();
            vectorLineAcceleration.setInvisible();

            showAccelerationVectorCheckboxUCM.setOnAction((event) -> {
                if (showAccelerationVectorCheckboxUCM.isSelected()) {
                    vectorLineAcceleration.setDefaultColor();
                } else {
                    vectorLineAcceleration.setInvisible();
                }
            });

            showVelocityVectorCheckboxUCM.setOnAction((event) -> {
                if (showVelocityVectorCheckboxUCM.isSelected()) {
                    vectorLineVelocity.setDefaultColor();
                } else {
                    vectorLineVelocity.setInvisible();
                }
            });
            hideOrbitCheckBox.setOnAction((event) -> {
                if (!hideOrbitCheckBox.isSelected()) {
                    orbitPathUCM.setStroke(Color.BLACK);
                } else {
                    orbitPathUCM.setStroke(Color.TRANSPARENT);
                }
            });

            //    }
            transition = new PathTransition(Duration.seconds(period), orbitPathUCM, orbitCircleUCM);
            transition.setInterpolator(Interpolator.LINEAR);
            transition.setCycleCount(Timeline.INDEFINITE);

            timeline = new Timeline(new KeyFrame(Duration.seconds(0.06), (event) -> {
                double currentTime = (System.nanoTime() - initialTime - (iniResumeTime - iniPauseTime)) / 1000000000;
                updateGraph(currentTime, orbitCircleUCM.getTranslateX(), orbitCircleUCM.getTranslateY());
            }));
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();

            transition.play();
        }
    }

    public static void updateGraph(double time, double x, double y) {
        posXSeries.getData().add(new XYChart.Data(time, x));
        posYSeries.getData().add(new XYChart.Data(time, y));

    }

    public static void reset() {
        ButtonHandling.handleReset();
        posXSeries.getData().clear();
        posYSeries.getData().clear();
        lineChart.getData().clear();
        lineChart2.getData().clear();
        velocityLabelUCM.setText("");
        accelerationLabelUCM.setText("");
        angularAccLabelUCM.setText("");
        centripedalForceLabelUCM.setText("");
        timeline.stop();
        transition.stop();
        showAccelerationVectorCheckboxUCM.setSelected(false);
        showVelocityVectorCheckboxUCM.setSelected(false);
        hideOrbitCheckBox.setSelected(false);

        container.getChildren().removeAll(vectorLineAcceleration.getVectorLine(), vectorLineAcceleration.Arrow1(), vectorLineAcceleration.Arrow2());
        container.getChildren().removeAll(vectorLineVelocity.getVectorLine(), vectorLineVelocity.arrow1, vectorLineVelocity.arrow2);
      
        changeRadiusSliderUCM.setValue(INITIAL_RADIUS);
        changePeriodSliderUCM.setValue(INITIAL_PERIOD);
        changeMassSliderUCM.setValue(INITIAL_MASS_UCM);
        orbitCircleUCM.setTranslateX(0);
        orbitCircleUCM.setTranslateY(0);
    }

    public static void pause() {
        iniPauseTime += System.nanoTime();
        ButtonHandling.handlePause();
        transition.pause();
        timeline.stop();
    }

    public static void resume() {
        iniResumeTime += System.nanoTime();
        ButtonHandling.handleResume();
        transition.play();
        timeline.play();
    }

    public static Pane getPane() {
        return container;
    }

    public static void Exit() {
        container.getChildren().clear();
        cicularMotionContainer.getChildren().clear();
        circularMotionUIgrid.getChildren().clear();
    }

}
