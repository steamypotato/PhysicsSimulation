/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TaylorSeries;

import HelpPackage.ButtonHandling;
import HelpPackage.UIControlsHandling;
import HelpPackage.UIcontrols;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Orientation;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 *
 * @author cstuser
 */
public class TaylorSeries implements UIcontrols {

    static Pane container;
    static AreaChart<Number, Number> realFunctionLineChart;
    static LineChart<Number, Number> approximateTaylorFunctionLineChart;
    static AnimationTimer animation;
    static NumberAxis xAxisTaylorFunction;
    static NumberAxis yAxisTaylorFunction;
    static NumberAxis xAxisExactFunction;
    static NumberAxis yAxisExactFunction;
    static double initialTime, iniPauseTime, iniResumeTime;
    static XYChart.Series approximateTaylorFunctionSeries;
     static XYChart.Series approximateTaylorFunctionSeries1;
    static ArrayList<XYChart.Series> seriesList;
    static ArrayList<XYChart.Series> seriesList1;
    static XYChart.Series realFunctionSeries;

    public static void setInterface() {

        container = new Pane();
        container.setPrefWidth(900);
        container.setPrefHeight(100);

        Rectangle border = new Rectangle(0, 0, container.getPrefWidth(), container.getPrefHeight());
          border.setFill(Color.web("#ffa07a"));
        border.setStroke(Color.web("#46211a"));
        border.setStrokeWidth(5);

        realFunctionLineChart = new AreaChart<>(new NumberAxis(), new NumberAxis());
     
        // realFunctionLineChart.setPrefSize(500, 500);
        xAxisTaylorFunction = new NumberAxis(-20,20,2);
        yAxisTaylorFunction = new NumberAxis(-20,20,2);
 
        
        approximateTaylorFunctionLineChart = new LineChart<>(xAxisTaylorFunction, yAxisTaylorFunction);
        approximateTaylorFunctionLineChart.setLegendVisible(false);
        approximateTaylorFunctionLineChart.setMinSize(1300, 900);
        approximateTaylorFunctionLineChart.setMaxSize(approximateTaylorFunctionLineChart.getWidth(), approximateTaylorFunctionLineChart.getHeight());
        approximateTaylorFunctionLineChart.setCreateSymbols(false);
          approximateTaylorFunctionLineChart.setTitle("Taylor series visualisation at A = 0");
        UIControlsHandling.handleTaylorSeriesControls();

        TaylorSeriesFunctionText.setLayoutY(container.getPrefHeight() / 2);
        TaylorSeriesFunctionText.setLayoutX(50);

        container.getChildren().addAll(border, TaylorSeriesFunctionText);
        taylorSeriesContainer.getChildren().addAll(new HBox(200, taylorFunctionsListView, container), new HBox(100, taylorSeriesControlsVBox,
                approximateTaylorFunctionLineChart));
        ButtonHandling.handleSetInterface();
    }

    public static void start() {   //System.out.println(factorial(170));
        if (Start.getText().equals("Resume")) {
            resume();
        } else {
            taylorFunctionsListView.setDisable(true);
            approximateTaylorFunctionSeries = new XYChart.Series();
            approximateTaylorFunctionSeries1= new XYChart.Series();;
            seriesList = new ArrayList<>();
              seriesList1 = new ArrayList<>();
            //realFunctionSeries = new XYChart.Series();
          //  realFunctionLineChart.getData().add(realFunctionSeries);
            for (int i = 0; i < 50; ++i) {
                seriesList.add(new XYChart.Series<>());
                seriesList1.add(new XYChart.Series<>());
                approximateTaylorFunctionLineChart.getData().addAll(seriesList.get(i),seriesList1.get(i));

            }
            
            approximateTaylorFunctionLineChart.getData().addAll(approximateTaylorFunctionSeries,approximateTaylorFunctionSeries1);
            approximateTaylorFunctionSeries.getNode().getStyleClass().add("custom-series-line");
            approximateTaylorFunctionSeries1.getNode().getStyleClass().add("custom-series-line");
            
            ButtonHandling.handleStart();
            initialTime = System.nanoTime();
              displayExpansion();
       
            animation = new AnimationTimer() {
                @Override
                public void handle(long time) {
                    double currentTime = (time - initialTime - (iniResumeTime - iniPauseTime)) / 1_000_000_000;
                    double x = currentTime;
                  
                    double m = 51;
                //    double y1 = Taylor(x, m);
                  //  double y2 = Taylor(-x, m);
                    setupTaylor(x,m);
                    
                //    double y2 = Taylor(x, m);
            
                    
                   
                   
                    
                }
            };
            animation.start();
         

        }
    }
    public static void setupTaylor(double x,double m)
    {
          double y1 = Taylor(x, m);
          double y2 = Taylor(-x, m);
           switch (taylorFunctionsListView.getSelectionModel().getSelectedItem().toLowerCase()) {
            case "sinx":
             sinLines(x);
             break;
            case "cosx":
             cosLines(x);
             break;
            case "e^x":
             exLines(x);
             break;
        }
                approximateTaylorFunctionSeries.getData().add(new XYChart.Data(x, y1));
                approximateTaylorFunctionSeries1.getData().add(new XYChart.Data(-x, y2));
                
    }
    public static double Taylor(double x, double m) {
        switch (taylorFunctionsListView.getSelectionModel().getSelectedItem().toLowerCase()) {
            case "sinx":
                return sinTaylor(x, m);
            case "cosx":
               return cosTaylor(x, m);
            case "e^x":
                return exTaylor(x, m);
        }
        return 0.0;
    }

    public static void sinLines(double x) {
        int count = 1;
        boolean alternate = false;
        for (int i = 0; i < seriesList.size(); i++) {
            if (!alternate) {
               seriesList.get(i).getData().add(new XYChart.Data<>(x, Math.pow(x, count) / factorial(count)));       //pos x
               seriesList1.get(i).getData().add(new XYChart.Data<>(-x, Math.pow(-x, count) / factorial(count)));
            } else {
               seriesList.get(i).getData().add(new XYChart.Data<>(x, -Math.pow(x, count) / factorial(count)));    //pos x
               seriesList1.get(i).getData().add(new XYChart.Data<>(-x, -Math.pow(-x, count) / factorial(count)));       //pos x
            }
            count += 2;
            alternate ^= true;
        }
    }
    public static void cosLines(double x) {
        int count = 0;
        boolean alternate = false;
        for (int i = 0; i < seriesList.size(); i++) {
           if (!alternate) {
                seriesList.get(i).getData().add(new XYChart.Data<>(x, Math.pow(x, count) / factorial(count))); //1 - x^2/2 + x^4/4
                seriesList1.get(i).getData().add(new XYChart.Data<>(-x, Math.pow(-x, count) / factorial(count)));
           
            } else {
                seriesList.get(i).getData().add(new XYChart.Data<>(x, (-1*Math.pow(x, count)) / factorial(count)));
                   seriesList1.get(i).getData().add(new XYChart.Data<>(-x, -Math.pow(-x, count) / factorial(count)));
            }
            count += 2;
            alternate ^= true;
            
        }
    }

    public static void exLines(double x) {
        int count = 0;
        for (int i = 0; i < seriesList.size(); i++) {
            seriesList.get(i).getData().add(new XYChart.Data<>(x, Math.pow(x, count) / factorial(count)));
            seriesList1.get(i).getData().add(new XYChart.Data<>(-x, -Math.pow(-x, count) / factorial(count)));
            count += 1;
        }
    }

    public static double sinTaylor(double x, double m) {
        double y = 0;
        boolean alternate = false;
        for (int i = 1; i <= m; i += 2) {
            if (!alternate) {
                // seriesList.add(new XYChart.Series<>());
                y += Math.pow(x, i) / factorial(i);
               } else {
                y -= Math.pow(x, i) / factorial(i);
            }
            alternate ^= true;
        }

        return y;
    }

    public static double cosTaylor(double x, double m) {
        double y = 0;
        boolean alternate = false;
        for (int i = 0; i <= m; i += 2) {
            if (!alternate) {
                y += Math.pow(x, i) / factorial(i);
            } else {
                y -= Math.pow(x, i) / factorial(i);
            }
            alternate ^= true;
        }
        return y;
    }

    private static double funtion(double x) {
        String function = taylorFunctionsListView.getSelectionModel().getSelectedItem();
        switch (function.toLowerCase()) {
            case "sinx":
               ;
               
                return Math.sin(x);
            case "cosx":
                return Math.cos(x);
            case "e^x":
                return Math.pow(Math.E, x);
            case "sinhx":
                return Math.sinh(x);
        }
        return 0.0;

    }

    public static double exTaylor(double x, double m) {
        double y = 0;
        for (int i = 0; i < m; i++) {
            y += Math.pow(x, i) / factorial(i);
        }
        return y;
    }

    public static double sinhTaylor(double x, double m) {
        double y = 0;
        for (int i = 1; i <= m; i += 2) {
            y += Math.pow(x, i) / factorial(i);
        }
        return y;
    }

    public static double factorial(double n) {
        return n <= 1 ? 1 : n * factorial(n - 1);
    }

    public static void displayExpansion() {
        String function = taylorFunctionsListView.getSelectionModel().getSelectedItem();
        switch (function.toLowerCase()) {
            case "sinx":
                xAxisTaylorFunction.setUpperBound(20);
                yAxisTaylorFunction.setUpperBound(10);
                yAxisTaylorFunction.setLowerBound(-10);
                
           TaylorSeriesFunctionText.setText("Maulaurin expansion of sinx : x - x^3/3! + x^5/5! - x^7/7! + ...");
                break;
            case "cosx":
                xAxisTaylorFunction.setUpperBound(20);
                yAxisTaylorFunction.setUpperBound(10);
                yAxisTaylorFunction.setLowerBound(-10);
                TaylorSeriesFunctionText.setText("Maulaurin expansion of cosx : 1 - x^2/2! + x^4/4! -x^6/6! + ....");
                break;
            case "e^x":
               //  yAxisTaylorFunction.setUpperBound(100);
                xAxisTaylorFunction.setUpperBound(10);
                yAxisTaylorFunction.setLowerBound(-5);
                TaylorSeriesFunctionText.setText("Maulaurin expansion of e^x : x + x^2/2! + x^3/3!+ x^4/4! ");
                break;
           
        }
    }

    public static void Exit() {

        container.getChildren().clear();
        taylorSeriesControlsVBox.getChildren().clear();
        taylorSeriesContainer.getChildren().clear();
    }

    public static void reset() {
        animation.stop();
        iniPauseTime = 0;
        iniResumeTime = 0;

        approximateTaylorFunctionSeries.getData().clear();
        approximateTaylorFunctionLineChart.getData().clear();
        approximateTaylorFunctionSeries1.getData().clear();
     //   realFunctionSeries.getData().clear();
   //     realFunctionLineChart.getData().clear();
        TaylorSeriesFunctionText.setText("");
        ButtonHandling.handleReset();
            taylorFunctionsListView.setDisable(false);
    }

    public static void pause() {
        ButtonHandling.handlePause();
        iniPauseTime += System.nanoTime();
        animation.stop();
    }

    public static void resume() {
        ButtonHandling.handleResume();
        iniResumeTime += System.nanoTime();
        animation.start();

    }
}
