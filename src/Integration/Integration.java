/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Integration;

/**
 *
 * @author BaconAndNachos
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.StringTokenizer;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import HelpPackage.UIcontrols;
import HelpPackage.ButtonHandling;
import HelpPackage.UIControlsHandling;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Control;
import javafx.util.Duration;
/**
 *
 * @author cstuser
 */
public class Integration implements UIcontrols
{
    static AreaChart<Number, Number> areaChart ;
    static XYChart.Series functionSeries;
    static XYChart.Series trapezoidalFunctionSeries;
    static Pane container;
    static NumberAxis yAxis;
    static NumberAxis xAxis;
    
    static Timeline timeline;
    static double inintialTime,iniPauseTime,iniResumeTime;
    public static void setInterface()
    {
        xAxis = new NumberAxis(-10,10,2);
        yAxis = new NumberAxis();
        
        areaChart = new AreaChart(xAxis,yAxis);
        areaChart.setMinSize(1000, 700);
        areaChart.setMaxSize(areaChart.getMinWidth(), areaChart.getMinHeight());
        areaChart.setCreateSymbols(true);
        areaChart.setLegendVisible(false);
         container = new Pane();
         container.setPrefWidth(IntegrationContainer.getWidth() - 400);
         container.setPrefHeight(150);
         container.setMinSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);
         container.setMaxSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);
         
        areaChart.setTitle("Integrated Function(using trapezoids)");
         Rectangle border = new Rectangle(0,0,container.getPrefWidth(), container.getPrefHeight());
        border.setFill(Color.web("#ffa07a"));
        border.setStroke(Color.web("#46211a"));
        border.setStrokeWidth(5);
         
         UIControlsHandling.handleIntegrationControls();
         
        resultLabelIntegration.setLayoutY(container.getPrefHeight()/2);
        resultIntegration.setLayoutY(container.getPrefHeight()/2);
        approximateResultLabelIntegration.setLayoutY(container.getPrefHeight()/2);
        approximateResultIntegration.setLayoutY(container.getPrefHeight()/2);
        relativeErrorDisplayLabel.setLayoutY(container.getPrefHeight()/2);
        relativeErrorLabel.setLayoutY(container.getPrefHeight()/2);
        
        
        resultLabelIntegration.setLayoutX(50);
        resultIntegration.setLayoutX(resultLabelIntegration.getLayoutX()+ 155);
        approximateResultLabelIntegration.setLayoutX(resultIntegration.getLayoutX() + 100);
        approximateResultIntegration.setLayoutX(approximateResultLabelIntegration.getLayoutX() + 225);
        relativeErrorDisplayLabel.setLayoutX(approximateResultIntegration.getLayoutX() + 150);
        relativeErrorLabel.setLayoutX(relativeErrorDisplayLabel.getLayoutX() + 150);
        
        
         container.getChildren().addAll(border,resultLabelIntegration,resultIntegration,
                 approximateResultLabelIntegration,approximateResultIntegration,relativeErrorDisplayLabel,relativeErrorLabel);
        IntegrationContainer.getChildren().addAll(container, new HBox(200,integrationControlsGrid,areaChart));
        ButtonHandling.handleSetInterface();
   
    }
    public static void start()
    {
        if(Start.getText().equals("Resume"))
        {
            resume();
        }
        else{
            
        ButtonHandling.handleStart();
        functionSeries = new XYChart.Series<>();
        trapezoidalFunctionSeries = new XYChart.Series<>();
        areaChart.getData().addAll(functionSeries,trapezoidalFunctionSeries);
   //     xAxis.setLowerBound(Double.parseDouble(lowerBoundField.getText()));
 //       xAxis.setUpperBound(Double.parseDouble(upperBoundTextField.getText()));
        
        Queue<String> queue = new LinkedList();
        String input = enterFunctionTextFieldIntegration.getText();
        String inputWithNoSpaces = input.replaceAll("[\t\n ]", "");
        String delimiters = "+-";
        StringTokenizer t = new StringTokenizer(inputWithNoSpaces,delimiters,true);
        while (t.hasMoreTokens()) {
                    queue.add(t.nextToken());
                
        }
        if(!queue.peek().equals("-"))
        {
            Queue temp = new LinkedList(queue);
            queue = new LinkedList<>();
            queue.add("+");
            queue.addAll(temp);
        }
        
       int numberOfPolys = queue.size()/2;
       double upperBoundResult= 0;
       double lowerBoundResult = 0;
        StringBuilder[] polynomials = new StringBuilder[numberOfPolys];
        for(int i = 0 ; i<polynomials.length ; ++i)
        {   
                polynomials[i] = new StringBuilder();
                polynomials[i].append( (queue.poll() + queue.poll()) );
                
                //System.out.println(IndefiniteIntegral(polynomials[i]));         //every index in this array is a expression string
                upperBoundResult += IndefiniteIntegral(polynomials[i],upperBoundTextField.getText());
                lowerBoundResult += IndefiniteIntegral(polynomials[i],lowerBoundField.getText());
        }
        double result = upperBoundResult - lowerBoundResult;                //EXACT VALUE
        result=  Math.round(result*100)/100;
        Integration.resultIntegration.setText(String.valueOf(result));                 //SET EXACT VALUE ON SCREN
        
        trapezoidalEvaluation();
        
        double absError = ((Double.parseDouble(resultIntegration.getText())) - Double.parseDouble(approximateResultIntegration.getText()));
        double relError = Math.abs(absError / (Double.parseDouble(resultIntegration.getText())));
         relError*=100.0;
        relativeErrorLabel.setText(String.valueOf(Math.round(relError*100.0)/100.0) + "%");
        inintialTime = System.nanoTime();
    
       double lowerBound = Double.parseDouble(lowerBoundField.getText());
       double upperBound = Double.parseDouble(upperBoundTextField.getText());
       double divisions = Double.parseDouble(nDivisionTextField.getText());
       double deltaX = (upperBound - lowerBound)/divisions;
       createGraph(lowerBound,upperBound,deltaX);
       zoomInCheckBox.setOnAction((event) -> {
           xAxis.setUpperBound(upperBound+1);
           xAxis.setLowerBound(lowerBound-1);
           
       });
        }
                }
        
    public static double IndefiniteIntegral(StringBuilder expr,String bound)
    {
        StringBuilder constant =  new StringBuilder("");
        StringBuilder variables = new StringBuilder("");
        StringBuilder indefiniteIntegral = new StringBuilder("");
          try{
              Double.parseDouble(String.valueOf(expr.charAt(1)));           //make sure a constatns is always there, say if user enters x instead of 1x
          }
          catch(Exception e)
          {
              expr.insert(1, "1");
          }
        for(int i = 0 ; i<expr.length(); ++i)
        {             
            constant.append(expr.charAt(i));
          try{
             Double.parseDouble(String.valueOf(expr.charAt(i+1)));
            }
            catch(NumberFormatException|StringIndexOutOfBoundsException e)
            {
                if(e instanceof StringIndexOutOfBoundsException)
                {
                    variables.append("1");
                }else {
                 variables.append(expr.substring(i+1)); }
                break;
            }
        }
            if(String.valueOf(variables.charAt(0)).equals("1"))
            {
                indefiniteIntegral.append("x");
            }
            else if(String.valueOf(variables.charAt(0)).equals("x"))        //MAKE SURE THE X IS ALONE (NOTHING IN THE SECOND INDEX OF CHAR AT(1) 
            {
                try{
                    String.valueOf(variables.charAt(1));                    //IF THERE IS A STRING THERE LIKE ^ INTEGRATE WITH RULE x^n+1/n+1
                    Integer n = Integer.parseInt(String.valueOf(variables.charAt(2))) + 1;  //GET THE NUMBER N AND ADD 1
                   indefiniteIntegral.append("x^" + n + "/" + n);
                  }
                catch(StringIndexOutOfBoundsException e)                    //IF IT IS JUST X, ERROR WILL HAPPEN WHEN TRYING TO READ NEXT CHAR. USE RULE x^2/2
                {
                    indefiniteIntegral.append("x^2/2");
                }
            }
            indefiniteIntegral.insert(0, constant);
           return definiteIntegral(indefiniteIntegral, bound);        //EVALUATE THE INDEFINITE BY PLUGGING IN A BOUND. TURNING IT INTO DEFINITE
    }
    public static double definiteIntegral(StringBuilder indefIntegral,String b)
    {
        String bound = b;
          StringBuilder upper = new StringBuilder();
        int index = indefIntegral.indexOf("x");
       upper = indefIntegral.replace(index, index+1, bound);       //REPLACE X BY THE BOUND
       upper.insert(index, "*");            //ADD THE * SIGN IN THERE
      return evaluate(upper);
    }
    public static void trapezoidalEvaluation()
    {
        double n = Double.parseDouble(nDivisionTextField.getText());
        double upperBound = Double.parseDouble(upperBoundTextField.getText());
        double lowerBound = Double.parseDouble(lowerBoundField.getText());
        double deltaX = (upperBound - lowerBound) / n ;
        double  result = 0;
        for(double i = lowerBound ; i<=upperBound ; i+=deltaX)
        {
         if(i==lowerBound || i> (upperBound  - deltaX))
         result+= evaluateFunctionAt(i);
         else 
             result+= 2*evaluateFunctionAt(i);
        }
        result  = (result*deltaX)/2 ; 
        approximateResultIntegration.setText(String.valueOf(Math.round(result*1000.0)/1000.0));
    }
    public static double evaluateFunctionAt(double x)
    {
        StringBuilder function = new StringBuilder(enterFunctionTextFieldIntegration.getText().replaceAll("[\t\n ]", ""));
       while(function.indexOf("x") >= 0)
        {
            int index = function.indexOf("x");
            try{
             Double.parseDouble(String.valueOf(function.charAt(index-1)));      //try check for constatns
             }
            catch(Exception e)
            {
                function.insert(index, "1");
               index = function.indexOf("x");
            } 
            function.replace(index, index+1, String.valueOf(x));        //replace x
            function.insert(index, "*");
        }
        return evaluate(function);
     }
    public static Double evaluate(StringBuilder expression)
    {
        String delimiters = "+-/^*";
        StringTokenizer tokenizer = new StringTokenizer(expression.toString(),delimiters,true);
        LinkedList<String> arrayList =  new LinkedList<>();
        Queue<String> expressionQueue = new LinkedList<>();
        while(tokenizer.hasMoreTokens())
        {
            arrayList.add(tokenizer.nextToken());
        }
        for(int i = 0 ; i <arrayList.size() ; ++i)
        {
            if(arrayList.get(0).equals("-") || (arrayList.get(i).equals("-") && arrayList.get(i-1).equals("*")))        //this makes sure the - sign doesnt get in the way
            {
                arrayList.set(i+1, String.valueOf(Double.parseDouble(arrayList.get(i+1)) *-1));
                arrayList.remove(i);
            }
        }
        if(arrayList.get(0).equals("+"))
        {
            arrayList.remove(0);
        }
        expressionQueue.addAll(arrayList);
        Stack<String> opStack = new Stack<>();      //STACK TO HOLD OPERATORS
        Stack<Double> valStack = new Stack<>();    // STACK TO HOLD VALUES
         Iterator<String> qlter = expressionQueue.iterator();   //TO SCCAN THE QUEUE
           while (qlter.hasNext()) {
            String token = qlter.next();    
             try{                                        //Check if token is integer
                Double.parseDouble(token);
                valStack.push(Double.parseDouble(token)); //PUT IN INTO THE VALUE STACK
            }
            catch(NumberFormatException e) //If not, it's an operator. Move on.
            {
                if( (token.equals("+")) || (token.equals("-")) || (token.equals("*")) || (token.equals("^")) || (token.equals("/")))
                {
                    while( (!opStack.isEmpty())&& ( precedence(token) <= precedence(opStack.peek()) ) )
                    {
                        valStack.push(applyStackTopOperator(valStack, opStack));
                    }
                        opStack.push(token);
                }
            }
        }
        while(opStack.isEmpty() == false)
                {
                    valStack.push(applyStackTopOperator(valStack, opStack));
                }
            return valStack.pop(); 
        
    }
    public static int precedence(String operator)           //THIS METHOD CHECKS THE PRECEDENCE OF THE OPERATOR
    {
        if((operator.equals("+")) || (operator.equals("-")) )
           return 1;
       else if((operator.equals("*")) || (operator.equals("/")))
           return 2;
       else if(operator.equals("^"))                    //^ HAS THE HIGHEST PRECENCE
           return 3;
       else throw new IllegalArgumentException("BAD");
    }
   private static double applyStackTopOperator(Stack<Double> valStack,Stack<String> opStack)      //THIS METHOD USES APPLIS CALCULATIONS
    {
        double val1 = valStack.pop();
        double val2 = valStack.pop();
        String op = opStack.pop();
        switch(op)
        {
            case "+" : return (val1 + val2) ;
            case "-" : return (val2 - val1);
            case "*" : return (val1 * val2);
            case "/" : return (val2 / val1);
            case "^" : return (Math.pow(val2, val1));
            default : throw new IllegalArgumentException("BAD");
        }
    }
      private static void createGraph(double lowerBound,double upperBound,double deltaX)
   {     
       for(double x = -10.0; x <= 10 ; x+=0.5)
       {
         double y = evaluateFunctionAt(x);
        functionSeries.getData().add(new XYChart.Data<>(x,y));
       }
       for(double x = lowerBound ; x<=upperBound ; x+=deltaX)
       {
           XYChart.Series s= new XYChart.Series<>();
           double y = evaluateFunctionAt(x);
           s.getData().add(new XYChart.Data(x,y));
           s.getData().add(new XYChart.Data(x,0));
           
           trapezoidalFunctionSeries.getData().add(new XYChart.Data<>(x,y));
           areaChart.getData().add(s);
       //    s.getNode().getStyleClass().add("area-lines");
       s.getNode().setStyle("-fx-stroke : black");
       }
   }
      
   public static void reset()
   {
       zoomInCheckBox.setSelected(false);
       ButtonHandling.handleReset();
       areaChart.getData().clear();
       functionSeries.getData().clear();
       
       //enterFunctionTextFieldIntegration.setText("");
      // upperBoundTextField.setText("");
       //lowerBoundField.setText("");
  //     nDivisionTextField.setText("");
       approximateResultIntegration.setText("");
       resultIntegration.setText("");
       relativeErrorLabel.setText("");
   }
   public static void pause()
   {
       iniPauseTime+=System.nanoTime();
       timeline.stop();
       ButtonHandling.handlePause();
   }
   public static void resume()
   {
       iniResumeTime+=System.nanoTime();
       timeline.play();
       ButtonHandling.handleResume();
   }
   public static void Exit()
   {
        container.getChildren().clear();
        IntegrationContainer.getChildren().clear();
        integrationControlsGrid.getChildren().clear();
       
   }
   
}

