/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SlitInterference;

/**
 *
 * @author BaconAndNachos
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import static HelpPackage.Constants.slitXposition;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import HelpPackage.UIcontrols;
import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.scene.shape.Arc;
import HelpPackage.ButtonHandling;
import HelpPackage.UIControlsHandling;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Control;
import javafx.scene.shape.Line;

/**
 *
 * @author cstuser
 */
public class SlitInterference implements UIcontrols {
    private static double initialTime,initialPausedTime,initialResumeTime;
    static Rectangle mainSlitRectangle;
    static Rectangle firstSlit;
    static Rectangle secondSlit;
    static Rectangle screen;
    static int arcCount;
    static LineChart<Number,Number> lineChart; 
    static XYChart.Series series;
    static double count;
    static Rectangle centerFringe;
    static Pane container;
    static ArrayList<Rectangle> brightFringes;
    static ArrayList<Line > lineList;
    static ArrayList<Arc> arcList;
    static AnimationTimer animation;
    static NumberAxis xAxis;
    static NumberAxis yAxis;
    

    public static void setInterface() {
        ButtonHandling.handleSetInterface();
        container = new Pane();
        container.setPrefWidth(800);
        container.setPrefHeight(500);
        container.setMaxSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);
        container.setMinSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);
        
        xAxis = new NumberAxis();
        yAxis = new NumberAxis();
        xAxis.setLabel("distance from center bright fringe in px");
        yAxis.setLabel("wave amplitude");
        lineChart  = new LineChart(xAxis,yAxis);
        lineChart.setCreateSymbols(false);
        lineChart.setLegendVisible(false);
        lineChart.setMinSize(800, 800);
        lineChart.setMaxSize(lineChart.getMinWidth(), lineChart.getMaxHeight());
        
        Rectangle border = new Rectangle(0, 0, container.getPrefWidth(), container.getPrefHeight());
        border.setFill(Color.web("#ffa07a"));
        border.setStroke(Color.web("#46211a"));
        border.setStrokeWidth(5);
        mainSlitRectangle = new Rectangle(20, 250);
        mainSlitRectangle.setX(slitXposition);
     //   mainSlitRectangle.setY(container.getPrefHeight() / 2 - mainSlitRectangle.getHeight() / 2);
        mainSlitRectangle.setY(0);
          mainSlitRectangle.setHeight(container.getPrefHeight());
             screen = new Rectangle(20, container.getPrefHeight());

        firstSlit = new Rectangle(20, 15);
        secondSlit = new Rectangle(20, 15);

        firstSlit.setFill(Color.web("#ffa07a"));
        secondSlit.setFill(Color.web("#ffa07a"));

        firstSlit.setLayoutX(60);
        secondSlit.setLayoutX(60);

        UIControlsHandling.handleInterferenceControls();

        firstSlit.yProperty().bind(Bindings.subtract(mainSlitRectangle.getY() + mainSlitRectangle.getHeight() / 2 - firstSlit.getHeight(), slitDistanceSlider.valueProperty().divide(2)));
        secondSlit.yProperty().bind(Bindings.add(mainSlitRectangle.getY() + mainSlitRectangle.getHeight() / 2, slitDistanceSlider.valueProperty().divide(2)));
        screen.xProperty().bind(Bindings.add(mainSlitRectangle.getX() + mainSlitRectangle.getWidth(), distanceFromScreenSlider.valueProperty()));

        interferenceControlsGrid.setPadding(new Insets(0, 0, 0, 35));

        container.getChildren().addAll(border, mainSlitRectangle, firstSlit, secondSlit, screen);
        interferenceContainer.getChildren().addAll(container, new HBox(interferenceControlsGrid,lineChart));
        lineChart.setTranslateX(400);
        lineChart.setTranslateY(-500);
        
                

    }


    public static void start() {
        if(Start.getText().equals("Resume"))
        {
            resume();
        }
        else{
            
        ButtonHandling.handleStart();
        series = new XYChart.Series<>();
        lineChart.getData().add(series);
        arcCount = 2;
        count=0;
        arcList = new ArrayList<>();
        lineList = new ArrayList<>();
        brightFringes = new ArrayList<>();
         double slitDistance = secondSlit.getY() - (firstSlit.getY() + firstSlit.getHeight());
        double distanceFromScreen = screen.getX() - (mainSlitRectangle.getX() + mainSlitRectangle.getWidth());
        double wavelength = wavelengthSlider.getValue();
        createFringes(slitDistance,distanceFromScreen,wavelength);
            createGraph(wavelength);
        initialTime = System.nanoTime();
        
        animation = new AnimationTimer() {
            @Override
            public void handle(long now) {
               
                double currentTime = (now - initialTime - (initialResumeTime-initialPausedTime)) / 1000000000.0;
                createLines(currentTime);
                createArcs(currentTime);
                
                                 for(Line l : lineList)
                                 {
                                    l.setStartX(l.getStartX()+0.5);
                                    l.setEndX(l.getStartX());
                                    removeLines(l);
                                 }
                                      for(Arc a : arcList)
                                  {
                                      a.setRadiusX(a.getRadiusX()+1);
                                      a.setOpacity(a.getOpacity() - 0.005);
                                      a.setRadiusY(a.getRadiusY()+1);
                                      removeArcs(a);
                                      
                                  }
            }
        };
        animation.start();
        }
    }
    public static void createFringes(double slitDistance,double distanceFromScreen,double wavelength)
    {
        Color color = getColor(wavelengthSlider.getValue());
        
        double yb = (distanceFromScreen * wavelength) / slitDistance;         //1st bright fringe 
        double yd = (distanceFromScreen * wavelength * 0.5) / slitDistance;     //1st dark fringe

        double height = yb - yd;          //Height of each fringe
        height /= 80;                      //Scale it
        centerFringe = new Rectangle(screen.getWidth(), height);

        centerFringe.setFill(color);
        centerFringe.setX(screen.getX());
        centerFringe.setY(screen.getY() + screen.getHeight() / 2 - centerFringe.getHeight() / 2);     //set the center fringe

     
        container.getChildren().addAll(centerFringe);

           for (int i = 0; i < 100; i += 2) {

            brightFringes.add(new Rectangle(screen.getWidth(), height, color));        //upper fringe
            brightFringes.add(new Rectangle(screen.getWidth(), height, color));        //lower fringe

            brightFringes.get(i).setY(centerFringe.getY() - (((i) + 2) * height));       //2,4,6,8,...                      
            brightFringes.get(i + 1).setY(centerFringe.getY() + (((i) + 2) * height));

            brightFringes.get(i).setX(screen.getX());                     //upper
            brightFringes.get(i + 1).setX(screen.getX());                     //lower

            container.getChildren().addAll(brightFringes.get(i), brightFringes.get(i + 1));

            if (brightFringes.get(i + 1).getY() + 2 * brightFringes.get(i + 1).getHeight() >= (screen.getHeight())) {
                if (brightFringes.get(i + 1).getY() + brightFringes.get(i + 1).getHeight() > (screen.getHeight())) {
                    double clipDistance = (brightFringes.get(i + 1).getY() + brightFringes.get(i + 1).getHeight()) - screen.getHeight();
                    brightFringes.get(i + 1).setHeight(brightFringes.get(i + 1).getHeight() - clipDistance);           //cut the clipping when its out of bounds

                }
                break;
            }
        }
    }
    public static void createArcs(double time)
    {
        if(time>=count){        //Every count time
            
           arcList.add(new Arc(mainSlitRectangle.getX() + mainSlitRectangle.getWidth(),     //upper arc
                   firstSlit.getY() + firstSlit.getHeight() / 2, firstSlit.getHeight()/2, firstSlit.getHeight()/2, 270, 180));
          arcList.add(new Arc(mainSlitRectangle.getX() + mainSlitRectangle.getWidth(),      //lower arc
                   secondSlit.getY() + secondSlit.getHeight() / 2, firstSlit.getHeight()/2, firstSlit.getHeight()/2, 270, 180));
           arcList.get(arcCount-2).setFill(Color.TRANSPARENT);
           arcList.get(arcCount-1).setFill(Color.TRANSPARENT);
           arcList.get(arcCount-2).setStroke(Color.DARKRED);
           arcList.get(arcCount-1).setStroke(Color.DARKRED);
           
           
           container.getChildren().addAll(arcList.get(arcCount-2),arcList.get(arcCount-1));
     
       
        arcCount+=2;
        count+=0.1;
        }
    }
    public static void createLines(double time)
    {
        if(time>(count) )
        {
            Line l = new Line(0,0,0,container.getPrefHeight());
            l.setStroke(Color.DARKRED);
           
            lineList.add(l);
            container.getChildren().add(l);
        }
    }
    public static void createGraph(double wavelength)
    {
        //double iniY = brightFringes.get(0).getY()-brightFringes.get(0).getHeight()/2;
        double iniY = brightFringes.get(1).getY() - brightFringes.get(1).getHeight()/2;
        for(int i = 0; i<=brightFringes.size() ; i++)
        {
            if(i%2==0)
            {
                series.getData().add(new XYChart.Data<>(i*iniY,wavelength/10));
                 series.getData().add(new XYChart.Data<>(-i*iniY,wavelength/10));
            }
            else{
                series.getData().add(new XYChart.Data<>(i*iniY,0));
                     series.getData().add(new XYChart.Data<>(-i*iniY,0));
                
            }
        }
            
    }
    public static void removeArcs(Arc a)
    {
        if(a.getOpacity()<=0)
            container.getChildren().remove(a);
    }
    public static void removeLines(Line l)
    {
        if(l.getStartX() >= (mainSlitRectangle.getX()) )
        {
            container.getChildren().remove(l);
        }
    }
    public static Color getColor(double wavelength) {
        double attenuation = 0;
        double R = 0;
        double G = 0;
        double B = 0;
        double gamma = 0.2;
        if (wavelength >= 380 & wavelength <= 440) {
            attenuation = 0.3 + 0.7 * (wavelength - 380) / (440 - 380);
            R = Math.pow(((-(wavelength - 440) / (440 - 380)) * attenuation), gamma);
            G = 0.0;
            B = Math.pow((1.0 * attenuation), gamma);
        } else if (wavelength >= 440 & wavelength <= 490) {
            R = 0.0;
            G = Math.pow(((wavelength - 440) / (490 - 440)), gamma);
            B = 1.0;
        } else if (wavelength >= 490 & wavelength <= 510) {
            R = 0.0;
            G = 1.0;
            B = Math.pow((-(wavelength - 510) / (510 - 490)), gamma);
        } else if (wavelength >= 510 & wavelength <= 580) {
            R = Math.pow(((wavelength - 510) / (580 - 510)), gamma);

            G = 1.0;
            B = 0.0;
        } else if (wavelength >= 580 & wavelength <= 645) {
            R = 1.0;
            G = Math.pow((-(wavelength - 645) / (645 - 580)), gamma);

            B = 0.0;
        } else if (wavelength >= 645 & wavelength <= 750) {
            attenuation = 0.3 + 0.7 * (750 - wavelength) / (750 - 645);
            R = Math.pow((1.0 * attenuation), gamma);
            G = 0.0;
            B = 0.0;
        } else {
            R = 0.0;
            G = 0.0;
            B = 0.0;
        }
        R = R * 255;
        G = G * 255;
        B = B * 255;
        return Color.rgb((int) R, (int) G, (int) B);

    }
        public static void reset() 
        {
         initialPausedTime=0;
        initialResumeTime=0;
        ButtonHandling.handleReset();
        series.getData().clear();
        lineChart.getData().clear();
        
        animation.stop();
        slitDistanceSlider.setValue(INITIAL_SLIT_DISTANCE);
        distanceFromScreenSlider.setValue(INITIAL_SCREEN_DISTANCE);
        wavelengthSlider.setValue(INITIAL_WAVELENGTH);
        for (Rectangle r : brightFringes) {
            container.getChildren().remove(r);
        }
        for(Arc a : arcList)
        {
            container.getChildren().remove(a);
        }
        for(Line l : lineList)
        {
            container.getChildren().remove(l);
        }
        lineList.clear();
        arcList.clear();
        container.getChildren().remove(centerFringe);
        brightFringes.clear();
    }

    public static void pause()
    {
        ButtonHandling.handlePause();
        animation.stop();
        initialPausedTime +=System.nanoTime();
    }
    public static void resume()
    {
        ButtonHandling.handleResume();
        animation.start();
        initialResumeTime += System.nanoTime();
        
    }
    public static void Exit()
    {
        container.getChildren().clear();
        interferenceContainer.getChildren().clear();
        interferenceControlsGrid.getChildren().clear();
    }
}

    