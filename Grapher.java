package calculus_con;


/**
 * This class contains graphing utiities.
 * 
 * Amrith Lotlikar 
 * TPSP Final Presentation Version April 27, 2016
 */
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.awt.*;
public class Grapher
{
    private int xbound;
    private int ybound;
    private double xmin;
    private double xmax;
    private double ymin;
    private double ymax;
    private Color draw;
    private Color fill;
    private double xrange;
    private double yrange;
    private double xlabels;
    private double ylabels;
    private ExecutorService GrapherConcurrency;

    /**
     * Constructor for objects of class Grapher
     */
    public Grapher(int XBOUND, int YBOUND, double XMIN, double XMAX, double YMIN, double YMAX)
    {
        xbound = XBOUND;
        ybound = YBOUND;
        xmin = XMIN;
        ymin = YMIN;
        xmax = XMAX;
        ymax = YMAX;
        draw = Color.black;
        fill = Color.black;
        xrange = xmax-xmin;
        yrange = ymax-ymin;
        GrapherConcurrency = Executors.newCachedThreadPool();
        xlabels = 10.0;
        ylabels = 10.0;
    }

    /**
     * Constructor for objects of class Grapher
     */
    public Grapher(double XMIN, double XMAX, double YMIN, double YMAX)
    {
        xbound = 500;
        ybound = 500;
        xmin = XMIN;
        ymin = YMIN;
        xmax = XMAX;
        ymax = YMAX;
        draw = Color.black;
        fill = Color.black;
        xrange = xmax-xmin;
        yrange = ymax-ymin;
        GrapherConcurrency = Executors.newCachedThreadPool();
        xlabels = 10.0;
        ylabels = 10.0;
    }

    /**
     * Constructor for objects of class Grapher
     */
    public Grapher(int XBOUND, int YBOUND, double XMIN, double XMAX, double YMIN, double YMAX, Color DRAW, Color FILL, double XLABLES, double YLABLES)
    {
        xbound = XBOUND;
        ybound = YBOUND;
        xmin = XMIN;
        ymin = YMIN;
        xmax = XMAX;
        ymax = YMAX;
        draw = DRAW;
        fill = FILL;
        xrange = xmax-xmin;
        yrange = ymax-ymin;
        GrapherConcurrency = Executors.newCachedThreadPool();
        xlabels = XLABLES;
        ylabels = YLABLES;
    }

    /**
     * Constructor for objects of class Grapher
     */
    public Grapher(double XMIN, double XMAX, double YMIN, double YMAX, Color DRAW, Color FILL)
    {
        xbound = 500;
        ybound = 500;
        xmin = XMIN;
        ymin = YMIN;
        xmax = XMAX;
        ymax = YMAX;
        draw = DRAW;
        fill = FILL;
        xrange = xmax-xmin;
        yrange = ymax-ymin;
        GrapherConcurrency = Executors.newCachedThreadPool();
        xlabels = 10.0;
        ylabels = 10.0;
    }

    public void setWindow(double XMIN, double XMAX, double YMIN, double YMAX)
    {
        xmin = XMIN;
        ymin = YMIN;
        xmax = XMAX;
        ymax = YMAX;
        xrange = xmax-xmin;
        yrange = ymax-ymin;
    }

    public void setColor(Color DRAW, Color FILL)
    {
        draw = DRAW;
        fill = FILL;
    }

    public void setLabels(double XLABELS, double YLABELS)
    {   
        xlabels = XLABELS;
        ylabels = YLABELS;
    }

    public void drawAxes(Graphics g)
    {
        g.setColor(draw);
        if(xmin<=0&&xmax>=0)
        {
            g.drawLine(xValueToApp(0.0),0,xValueToApp(0.0),ybound);
        }
        if(ymin<=0&&ymax>=0)
        {
            g.drawLine(0,yValueToApp(0.0),xbound,yValueToApp(0.0));
        }
        g.drawString("0",xValueToApp(0.0),xValueToApp(0.0));
        double value = 0.0;
        String label = ""+value;
        while(xValueToApp(value+xlabels)<xbound)
        {value+=xlabels;
            label = ""+value;
            g.drawString(label,xValueToApp(value),yValueToApp(0.0));
        }
        value = 0.0;
        while(xValueToApp(value-xlabels)>0)
        {value-=xlabels;
            label = ""+value;
            g.drawString(label,xValueToApp(value),yValueToApp(0.0));
        }
        value = 0.0;
        while(yValueToApp(value+ylabels)>0)
        {value+=ylabels;
            label = ""+value;
            g.drawString(label,xValueToApp(0.0),yValueToApp(value));
        }
        value = 0.0;
        while(yValueToApp(value-ylabels)<ybound)
        {value-=ylabels;
            label = ""+value;
            g.drawString(label,xValueToApp(0.0),yValueToApp(value));
        }
    }

    public int xValueToApp(double input)
    {
        double fraction = (input-xmin)/xrange;
        return (int)Math.round((double)xbound*fraction);
    }

    public int yValueToApp(double input)
    {
        double fraction = (input-ymin)/yrange;
        return ybound-(int)Math.round((double)ybound*fraction);
    }

    public double xAppToValue(int input)
    {
        double fraction = (double)input/(double)xbound;
        return xmin + xrange*fraction;
    }

    public double yAppToValue(int input)
    {
        double fraction = (double)input/(double)ybound;
        return ymax - yrange*fraction;
    }

    public void drawGraph(Graphics g, Function function)
    {
        int cores = Runtime.getRuntime().availableProcessors();
        PartialGraph partialGraph[] = new PartialGraph[cores];
        int begin, end;
        g.setColor(draw);
        for(int k=0; k<cores; k++)
        {
            begin = xbound*k/cores;
            end = xbound*(k+1)/cores;
            partialGraph[k] = new PartialGraph(g, function, xmin, xmax, ymin, ymax, xbound, ybound, begin, end);
            GrapherConcurrency.execute(partialGraph[k]);
        }
        for(int k = 0; k<cores; k++)
        {
            while(!partialGraph[k].isdone())
            {
            }
        }
    }

    public void drawGraph(Graphics g, Polynomial polynomial)
    {
        int cores = Runtime.getRuntime().availableProcessors();
        PartialGraph partialGraph[] = new PartialGraph[cores];
        int begin, end;
        g.setColor(draw);
        for(int k=0; k<cores; k++)
        {
            begin = xbound*k/cores;
            end = xbound*(k+1)/cores;
            partialGraph[k] = new PartialGraph(g, polynomial, xmin, xmax, ymin, ymax, xbound, ybound, begin, end);
            GrapherConcurrency.execute(partialGraph[k]);
        }
        for(int k = 0; k<cores; k++)
        {
            while(!partialGraph[k].isdone())
            {
            }
        }
    }

    public void drawSerialGraph(Graphics g,Function function)
    {
        int firstoutput;
        int secondoutput;
        g.setColor(draw);
        firstoutput = yValueToApp(function.evaluate(xAppToValue(0)));
        for(int i = 0; i<xbound; i++)
        {
            secondoutput = yValueToApp(function.evaluate(xAppToValue(i+1)));
            g.drawLine(i,firstoutput,i+1,secondoutput);
            firstoutput = secondoutput;
        }    
    }

    public void drawSerialGraph(Graphics g,Polynomial polynomial)
    {
        int firstoutput;
        int secondoutput;
        g.setColor(draw);
        firstoutput = yValueToApp(polynomial.evaluate(xAppToValue(0)));
        for(int i = 0; i<xbound; i++)
        {
            secondoutput = yValueToApp(polynomial.evaluate(xAppToValue(i+1)));
            g.drawLine(i,firstoutput,i+1,secondoutput);
            firstoutput = secondoutput;
        }    
    }
    
    public double solveZero(Graphics g, Function function, double leftlimit, double rightlimit)
    {
        int count = 0;
        boolean found = false;
        double test = (leftlimit + rightlimit)/2;
        int randomizer = 3;
        while(count <5000 && !found)
        {
          if(function.evaluate(test)<0.001&&function.evaluate(test)>-0.001)
          {
              found = true;
              return test;  
            
          }
          test = -1.0*function.evaluate(test)/function.differentiate(test);
          if(test<leftlimit){test = leftlimit +(rightlimit-leftlimit)/randomizer; randomizer++;}
          if(test>rightlimit){test = leftlimit +(rightlimit-leftlimit)/randomizer; randomizer++;}
          count++;
        }
        return -1.001;
    }
    
     public double solveIntersect(Graphics g, Function functionOne, Function functionTwo, double leftlimit, double rightlimit)
    {
        int count = 0;
        boolean found = false;
        double test = (leftlimit + rightlimit)/2;
        int randomizer = 3;
        while(count <5000 && !found)
        {
          if(functionOne.evaluate(test)-functionTwo.evaluate(test)<0.001&&functionOne.evaluate(test)-functionTwo.evaluate(test)>-0.001)
          {
              found = true;
              return test;  
            
          }
          test = -1.0*(functionOne.evaluate(test)-functionTwo.evaluate(test))/(functionOne.differentiate(test)-functionTwo.differentiate(test));
         if(test<leftlimit){test = leftlimit +(rightlimit-leftlimit)/randomizer; randomizer++;}
          if(test>rightlimit){test = leftlimit +(rightlimit-leftlimit)/randomizer; randomizer++;}
          count++;
        }
        return -1.001;
    }
    
    public double solveZero(Graphics g, Polynomial polynomial, double leftlimit, double rightlimit)
    {
        int count = 0;
        boolean found = false;
        double test = (leftlimit + rightlimit)/2;
        int randomizer =3;
        Polynomial polyderiv = polynomial.differentiate();
        while(count <5000 && !found)
        {
          if(polynomial.evaluate(test)<0.001&&polynomial.evaluate(test)>-0.001)
          {
              found = true;
              return test;  
            
          }
          test = -1.0*polynomial.evaluate(test)/polyderiv.evaluate(test);
           if(test<leftlimit){test = leftlimit +(rightlimit-leftlimit)/randomizer; randomizer++;}
          if(test>rightlimit){test = leftlimit +(rightlimit-leftlimit)/randomizer; randomizer++;}
          count++;
        }
        return -1.001;
    }
    

    public void fillGraph(Graphics g, Function function)
    {
        g.setColor(fill);
        int cores = Runtime.getRuntime().availableProcessors();
        PartialFillGraph partialFillGraph[] = new PartialFillGraph[cores];
        int begin, end;
        for(int k=0; k<cores; k++)
        {
            begin = xbound*k/cores;
            end = xbound*(k+1)/cores;
            partialFillGraph[k] = new PartialFillGraph(g, function, xmin, xmax, ymin, ymax, xbound, ybound, begin, end);
            GrapherConcurrency.execute(partialFillGraph[k]);
        }
        for(int k = 0; k<cores; k++)
        {
            while(!partialFillGraph[k].isdone())
            {
            }
        }
        drawGraph(g,function);
    }

    public void fillGraph(Graphics g, Polynomial polynomial)
    {
        g.setColor(fill);
        int cores = Runtime.getRuntime().availableProcessors();
        PartialFillGraph partialFillGraph[] = new PartialFillGraph[cores];
        int begin, end;
        for(int k=0; k<cores; k++)
        {
            begin = xbound*k/cores;
            end = xbound*(k+1)/cores;
            partialFillGraph[k] = new PartialFillGraph(g, polynomial, xmin, xmax, ymin, ymax, xbound, ybound, begin, end);
            GrapherConcurrency.execute(partialFillGraph[k]);
        }
        for(int k = 0; k<cores; k++)
        {
            while(!partialFillGraph[k].isdone())
            {
            }
        }
        drawGraph(g,polynomial);
    }

    public void GraphLessThan(Graphics g, Function function)
    {
        g.setColor(fill);
        int cores = Runtime.getRuntime().availableProcessors();
        PartialGraphLessThan partialGraphLessThan[] = new PartialGraphLessThan[cores];
        int begin, end;
        for(int k=0; k<cores; k++)
        {
            begin = xbound*k/cores;
            end = xbound*(k+1)/cores;
            partialGraphLessThan[k] = new PartialGraphLessThan(g, function, xmin, xmax, ymin, ymax, xbound, ybound, begin, end);
            GrapherConcurrency.execute(partialGraphLessThan[k]);
        }
        for(int k = 0; k<cores; k++)
        {
            while(!partialGraphLessThan[k].ishalfdone())
            {
            }
        }
        g.setColor(Color.white);
        for(int k = 0; k<cores; k++)
        {
            GrapherConcurrency.execute(partialGraphLessThan[k]);
        }
        for(int k = 0; k<cores; k++)
        {
            while(!partialGraphLessThan[k].isdone())
            {
            }
        }
        g.setColor(draw);
        drawGraph(g,function);
    }

    public void GraphLessThan(Graphics g, Polynomial polynomial)
    {
        g.setColor(fill);
        int cores = Runtime.getRuntime().availableProcessors();
        PartialGraphLessThan partialGraphLessThan[] = new PartialGraphLessThan[cores];
        int begin, end;
        for(int k=0; k<cores; k++)
        {
            begin = xbound*k/cores;
            end = xbound*(k+1)/cores;
            partialGraphLessThan[k] = new PartialGraphLessThan(g, polynomial, xmin, xmax, ymin, ymax, xbound, ybound, begin, end);
            GrapherConcurrency.execute(partialGraphLessThan[k]);
        }
        for(int k = 0; k<cores; k++)
        {
            while(!partialGraphLessThan[k].ishalfdone())
            {
            }
        }
        g.setColor(Color.white);
        for(int k = 0; k<cores; k++)
        {
            GrapherConcurrency.execute(partialGraphLessThan[k]);
        }
        for(int k = 0; k<cores; k++)
        {
            while(!partialGraphLessThan[k].isdone())
            {
            }
        }
        g.setColor(draw);
        drawGraph(g,polynomial);
    }

    public void GraphGreaterThan(Graphics g, Function function)
    {
        g.setColor(Color.white);
        int cores = Runtime.getRuntime().availableProcessors();
        PartialGraphLessThan partialGraphLessThan[] = new PartialGraphLessThan[cores];
        int begin, end;
        for(int k=0; k<cores; k++)
        {
            begin = xbound*k/cores;
            end = xbound*(k+1)/cores;
            partialGraphLessThan[k] = new PartialGraphLessThan(g, function, xmin, xmax, ymin, ymax, xbound, ybound, begin, end);
            GrapherConcurrency.execute(partialGraphLessThan[k]);
        }
        for(int k = 0; k<cores; k++)
        {
            while(!partialGraphLessThan[k].ishalfdone())
            {
            }
        }
        g.setColor(fill);
        for(int k = 0; k<cores; k++)
        {
            GrapherConcurrency.execute(partialGraphLessThan[k]);
        }
        for(int k = 0; k<cores; k++)
        {
            while(!partialGraphLessThan[k].isdone())
            {
            }
        }
        g.setColor(draw);
        drawGraph(g,function);
    }

    public void GraphGreaterThan(Graphics g, Polynomial polynomial)
    {
        g.setColor(Color.white);
        int cores = Runtime.getRuntime().availableProcessors();
        PartialGraphLessThan partialGraphLessThan[] = new PartialGraphLessThan[cores];
        int begin, end;
        for(int k=0; k<cores; k++)
        {
            begin = xbound*k/cores;
            end = xbound*(k+1)/cores;
            partialGraphLessThan[k] = new PartialGraphLessThan(g, polynomial, xmin, xmax, ymin, ymax, xbound, ybound, begin, end);
            GrapherConcurrency.execute(partialGraphLessThan[k]);
        }
        for(int k = 0; k<cores; k++)
        {
            while(!partialGraphLessThan[k].ishalfdone())
            {
            }
        }
        g.setColor(fill);
        for(int k = 0; k<cores; k++)
        {
            GrapherConcurrency.execute(partialGraphLessThan[k]);
        }
        for(int k = 0; k<cores; k++)
        {
            while(!partialGraphLessThan[k].isdone())
            {
            }
        }
        g.setColor(draw);
        drawGraph(g,polynomial);
    }

    public void fillInBetween(Graphics g, Function functionOne, Function functionTwo)
    {
        g.setColor(fill);
        int cores = Runtime.getRuntime().availableProcessors();
        PartialFillBetweenGraph partialFillBetweenGraph[] = new PartialFillBetweenGraph[cores];
        int begin, end;
        for(int k=0; k<cores; k++)
        {
            begin = xbound*k/cores;
            end = xbound*(k+1)/cores;
            partialFillBetweenGraph[k] = new PartialFillBetweenGraph(g, functionOne, functionTwo,xmin, xmax, ymin, ymax, xbound, ybound, begin, end);
            GrapherConcurrency.execute(partialFillBetweenGraph[k]);
        }
        for(int k = 0; k<cores; k++)
        {
            while(!partialFillBetweenGraph[k].isdone())
            {
            }
        }
        drawGraph(g,functionOne);
        drawGraph(g,functionTwo);
    }

    public void fillInBetween(Graphics g, Polynomial polynomialOne, Polynomial polynomialTwo)
    {
        g.setColor(fill);
        int cores = Runtime.getRuntime().availableProcessors();
        PartialFillBetweenGraph partialFillBetweenGraph[] = new PartialFillBetweenGraph[cores];
        int begin, end;
        for(int k=0; k<cores; k++)
        {
            begin = xbound*k/cores;
            end = xbound*(k+1)/cores;
            partialFillBetweenGraph[k] = new PartialFillBetweenGraph(g, polynomialOne, polynomialTwo,xmin, xmax, ymin, ymax, xbound, ybound, begin, end);
            GrapherConcurrency.execute(partialFillBetweenGraph[k]);
        }
        for(int k = 0; k<cores; k++)
        {
            while(!partialFillBetweenGraph[k].isdone())
            {
            }
        }
        drawGraph(g,polynomialOne);
        drawGraph(g,polynomialTwo);
    }
}

class PartialFillBetweenGraph implements Runnable
{
    private Graphics g;
    private Function functionOne;
    private Function functionTwo;
    private Polynomial polynomialOne;
    private Polynomial polynomialTwo;
    private double xmin;
    private double xmax, xrange;
    private double ymin;
    private double ymax, yrange; 
    private int xbound;
    private int ybound;
    private int begin;
    private int end;
    private boolean done;
    private boolean func;
    public PartialFillBetweenGraph(Graphics G, Function FUNCTIONONE, Function FUNCTIONTWO, double XMIN, double XMAX, double YMIN, double YMAX, int XBOUND, int YBOUND,int BEGIN, int END)
    {
        g = G;
        functionOne = FUNCTIONONE;
        functionTwo = FUNCTIONTWO;
        xmin = XMIN;
        xmax = XMAX;
        ymin = YMIN;
        ymax = YMAX; 
        xbound = XBOUND;
        ybound = YBOUND;
        begin = BEGIN;
        end = END;
        done = false;
        xrange = xmax-xmin;
        yrange = ymax - ymin;
        func = true;
    }

    public PartialFillBetweenGraph(Graphics G, Polynomial POLYNOMIALONE, Polynomial POLYNOMIALTWO, double XMIN, double XMAX, double YMIN, double YMAX, int XBOUND, int YBOUND,int BEGIN, int END)
    {
        g = G;
        polynomialOne = POLYNOMIALONE;
        polynomialTwo = POLYNOMIALTWO;
        xmin = XMIN;
        xmax = XMAX;
        ymin = YMIN;
        ymax = YMAX; 
        xbound = XBOUND;
        ybound = YBOUND;
        begin = BEGIN;
        end = END;
        done = false;
        xrange = xmax-xmin;
        yrange = ymax - ymin;
        func = false;
    }

    public void run()
    {
        int firstoutputOne;
        int secondoutputOne;
        int firstoutputTwo;
        int secondoutputTwo;
        int xcoords[] = new int[4];
        int ycoords[] = new int[4];
        if(func)
        {
            firstoutputOne = yValueToApp(functionOne.evaluate(xAppToValue(begin)));
            firstoutputTwo = yValueToApp(functionTwo.evaluate(xAppToValue(begin)));
            for(int i = begin; i<end; i++)
            {
                secondoutputOne = yValueToApp(functionOne.evaluate(xAppToValue(i+1)));
                secondoutputTwo = yValueToApp(functionTwo.evaluate(xAppToValue(i+1)));
                if(firstoutputOne>firstoutputTwo)
                {
                    xcoords[0] = i;
                    xcoords[1] = i;
                    xcoords[2] = i+1;
                    xcoords[3] = i+1;
                    ycoords[0] = firstoutputOne;
                    ycoords[1] = firstoutputTwo;
                    ycoords[2] = secondoutputTwo;
                    ycoords[3] = secondoutputOne;
                    g.fillPolygon(new Polygon(xcoords,ycoords,4));
                }
                firstoutputOne = secondoutputOne;
                firstoutputTwo = secondoutputTwo;
            }
        }
        else
        {
            firstoutputOne = yValueToApp(polynomialOne.evaluate(xAppToValue(begin)));
            firstoutputTwo = yValueToApp(polynomialTwo.evaluate(xAppToValue(begin)));
            for(int i = begin; i<end; i++)
            {
                secondoutputOne = yValueToApp(polynomialOne.evaluate(xAppToValue(i+1)));
                secondoutputTwo = yValueToApp(polynomialTwo.evaluate(xAppToValue(i+1)));
                if(firstoutputOne>firstoutputTwo)
                {
                    xcoords[0] = i;
                    xcoords[1] = i;
                    xcoords[2] = i+1;
                    xcoords[3] = i+1;
                    ycoords[0] = firstoutputOne;
                    ycoords[1] = firstoutputTwo;
                    ycoords[2] = secondoutputTwo;
                    ycoords[3] = secondoutputOne;
                    g.fillPolygon(new Polygon(xcoords,ycoords,4));
                }
                firstoutputOne = secondoutputOne;
                firstoutputTwo = secondoutputTwo;
            }
        }
        done = true;
    }

    private int xValueToApp(double input)
    {
        double fraction = (input-xmin)/xrange;
        return (int)Math.round((double)xbound*fraction);
    }

    private int yValueToApp(double input)
    {
        double fraction = (input-ymin)/yrange;
        return ybound-(int)Math.round((double)ybound*fraction);
    }

    private double xAppToValue(int input)
    {
        double fraction = (double)input/(double)xbound;
        return xmin + xrange*fraction;
    }

    private double yAppToValue(int input)
    {
        double fraction = (double)input/(double)ybound;
        return ymax - yrange*fraction;
    }

    public boolean isdone()
    {
        return done;
    }
}

class PartialGraph implements Runnable
{
    private Graphics g;
    private Function function;
    private Polynomial polynomial;
    private double xmin;
    private double xmax, xrange;
    private double ymin;
    private double ymax, yrange; 
    private int xbound;
    private int ybound;
    private int begin;
    private int end;
    private boolean done;
    private boolean func;
    PartialGraph(Graphics G, Function FUNCTION, double XMIN, double XMAX, double YMIN, double YMAX, int XBOUND, int YBOUND,int BEGIN, int END)
    {
        g = G;
        function = FUNCTION;
        xmin = XMIN;
        xmax = XMAX;
        ymin = YMIN;
        ymax = YMAX; 
        xbound = XBOUND;
        ybound = YBOUND;
        begin = BEGIN;
        end = END;
        done = false;
        xrange = xmax-xmin;
        yrange = ymax - ymin;
        func = true;
    }

    PartialGraph(Graphics G, Polynomial POLYNOMIAL, double XMIN, double XMAX, double YMIN, double YMAX, int XBOUND, int YBOUND,int BEGIN, int END)
    {
        g = G;
        polynomial = POLYNOMIAL;
        xmin = XMIN;
        xmax = XMAX;
        ymin = YMIN;
        ymax = YMAX; 
        xbound = XBOUND;
        ybound = YBOUND;
        begin = BEGIN;
        end = END;
        done = false;
        xrange = xmax-xmin;
        yrange = ymax - ymin;
        func = false;
    }

    public void run()
    {
        int firstoutput;
        int secondoutput;
        if(func)
        {
            firstoutput = yValueToApp(function.evaluate(xAppToValue(begin)));
            for(int i = begin; i<end; i++)
            {
                secondoutput = yValueToApp(function.evaluate(xAppToValue(i+1)));
                g.drawLine(i,firstoutput,i+1,secondoutput);
                firstoutput = secondoutput;
            }
        }
        else
        {
            firstoutput = yValueToApp(polynomial.evaluate(xAppToValue(begin)));
            for(int i = begin; i<end; i++)
            {
                secondoutput = yValueToApp(polynomial.evaluate(xAppToValue(i+1)));
                g.drawLine(i,firstoutput,i+1,secondoutput);
                firstoutput = secondoutput;
            }
        }
        done = true;
    }

    private int xValueToApp(double input)
    {
        double fraction = (input-xmin)/xrange;
        return (int)Math.round((double)xbound*fraction);
    }

    private int yValueToApp(double input)
    {
        double fraction = (input-ymin)/yrange;
        return ybound-(int)Math.round((double)ybound*fraction);
    }

    private double xAppToValue(int input)
    {
        double fraction = (double)input/(double)xbound;
        return xmin + xrange*fraction;
    }

    private double yAppToValue(int input)
    {
        double fraction = (double)input/(double)ybound;
        return ymax - yrange*fraction;
    }

    public boolean isdone()
    {
        return done;
    }
}

class PartialFillGraph implements Runnable
{
    private Graphics g;
    private Function function;
    private Polynomial polynomial;
    private double xmin;
    private double xmax, xrange;
    private double ymin;
    private double ymax, yrange; 
    private int xbound;
    private int ybound;
    private int begin;
    private int end;
    private boolean done;
    private boolean func;
    PartialFillGraph(Graphics G, Function FUNCTION, double XMIN, double XMAX, double YMIN, double YMAX, int XBOUND, int YBOUND,int BEGIN, int END)
    {
        g = G;
        function = FUNCTION;
        xmin = XMIN;
        xmax = XMAX;
        ymin = YMIN;
        ymax = YMAX; 
        xbound = XBOUND;
        ybound = YBOUND;
        begin = BEGIN;
        end = END;
        done = false;
        xrange = xmax-xmin;
        yrange = ymax - ymin;
        func = true;
    }

    PartialFillGraph(Graphics G, Polynomial POLYNOMIAL, double XMIN, double XMAX, double YMIN, double YMAX, int XBOUND, int YBOUND,int BEGIN, int END)
    {
        g = G;
        polynomial = POLYNOMIAL;
        xmin = XMIN;
        xmax = XMAX;
        ymin = YMIN;
        ymax = YMAX; 
        xbound = XBOUND;
        ybound = YBOUND;
        begin = BEGIN;
        end = END;
        done = false;
        xrange = xmax-xmin;
        yrange = ymax - ymin;
        func = false;
    }

    public void run()
    {
        int firstoutput;
        int secondoutput;
        int zero = yValueToApp(0.0);
        int q = 0;
        int xcoords[] = new int[4];
        int ycoords[] = new int[4];
        if(func)
        {
            firstoutput = yValueToApp(function.evaluate(xAppToValue(begin)));
            for(int i = begin; i<end; i++)
            {
                secondoutput = yValueToApp(function.evaluate(xAppToValue(i+1)));
                xcoords[0] = i;
                xcoords[1] = i;
                xcoords[2] = i+1;
                xcoords[3] = i+1;
                ycoords[0] = zero;
                ycoords[1] = firstoutput;
                ycoords[2] = secondoutput;
                ycoords[3] = zero;
                g.fillPolygon(new Polygon(xcoords,ycoords,4));
                firstoutput = secondoutput;
            }
        }
        else
        {
            firstoutput = yValueToApp(polynomial.evaluate(xAppToValue(begin)));
            for(int i = begin; i<end; i++)
            {
                secondoutput = yValueToApp(polynomial.evaluate(xAppToValue(i+1)));
                xcoords[0] = i;
                xcoords[1] = i;
                xcoords[2] = i+1;
                xcoords[3] = i+1;
                ycoords[0] = zero;
                ycoords[1] = firstoutput;
                ycoords[2] = secondoutput;
                ycoords[3] = zero;
                g.fillPolygon(new Polygon(xcoords,ycoords,4));
                firstoutput = secondoutput;
            }
        }
        done = true;
    }

    private int xValueToApp(double input)
    {
        double fraction = (input-xmin)/xrange;
        return (int)Math.round((double)xbound*fraction);
    }

    private int yValueToApp(double input)
    {
        double fraction = (input-ymin)/yrange;
        return ybound-(int)Math.round((double)ybound*fraction);
    }

    private double xAppToValue(int input)
    {
        double fraction = (double)input/(double)xbound;
        return xmin + xrange*fraction;
    }

    private double yAppToValue(int input)
    {
        double fraction = (double)input/(double)ybound;
        return ymax - yrange*fraction;
    }

    public boolean isdone()
    {
        return done;
    }
}

class PartialGraphLessThan implements Runnable
{
    private Graphics g;
    private Function function;
    private Polynomial polynomial;
    private double xmin;
    private double xmax, xrange;
    private double ymin;
    private double ymax, yrange; 
    private int xbound;
    private int ybound;
    private int begin;
    private int end;
    private boolean done;
    private boolean func;
    private boolean halfdone;
    PartialGraphLessThan(Graphics G, Function FUNCTION, double XMIN, double XMAX, double YMIN, double YMAX, int XBOUND, int YBOUND,int BEGIN, int END)
    {
        g = G;
        function = FUNCTION;
        xmin = XMIN;
        xmax = XMAX;
        ymin = YMIN;
        ymax = YMAX; 
        xbound = XBOUND;
        ybound = YBOUND;
        begin = BEGIN;
        end = END;
        done = false;
        xrange = xmax-xmin;
        yrange = ymax - ymin;
        func = true;
    }

    PartialGraphLessThan(Graphics G, Polynomial POLYNOMIAL, double XMIN, double XMAX, double YMIN, double YMAX, int XBOUND, int YBOUND,int BEGIN, int END)
    {
        g = G;
        polynomial = POLYNOMIAL;
        xmin = XMIN;
        xmax = XMAX;
        ymin = YMIN;
        ymax = YMAX; 
        xbound = XBOUND;
        ybound = YBOUND;
        begin = BEGIN;
        end = END;
        done = false;
        xrange = xmax-xmin;
        yrange = ymax - ymin;
        func = false;
    }

    public void run()
    {
        int firstoutput;
        int secondoutput;
        int q = 0;
        int xcoords[] = new int[4];
        int ycoords[] = new int[4];

        if(func)
        {
            if(!halfdone)
            {
                firstoutput = yValueToApp(function.evaluate(xAppToValue(begin)));
                for(int i = begin; i<end; i++)
                {
                    secondoutput = yValueToApp(function.evaluate(xAppToValue(i+1)));
                    xcoords[0] = i;
                    xcoords[1] = i;
                    xcoords[2] = i+1;
                    xcoords[3] = i+1;
                    ycoords[0] = ybound;
                    ycoords[1] = firstoutput;
                    ycoords[2] = secondoutput;
                    ycoords[3] = ybound;
                    g.fillPolygon(new Polygon(xcoords,ycoords,4));
                    firstoutput = secondoutput;
                }
                halfdone = true;
            }
            else
            {
                firstoutput = yValueToApp(function.evaluate(xAppToValue(begin)));
                for(int i = begin; i<end; i++)
                {
                    secondoutput = yValueToApp(function.evaluate(xAppToValue(i+1)));
                    xcoords[0] = i;
                    xcoords[1] = i;
                    xcoords[2] = i+1;
                    xcoords[3] = i+1;
                    ycoords[0] = 0;
                    ycoords[1] = firstoutput;
                    ycoords[2] = secondoutput;
                    ycoords[3] = 0;
                    g.fillPolygon(new Polygon(xcoords,ycoords,4));
                    firstoutput = secondoutput;
                }
                done = true;
            }
        }
        else
        {
            if(!halfdone)
            {
                firstoutput = yValueToApp(polynomial.evaluate(xAppToValue(begin)));
                for(int i = begin; i<end; i++)
                {
                    secondoutput = yValueToApp(polynomial.evaluate(xAppToValue(i+1)));
                    xcoords[0] = i;
                    xcoords[1] = i;
                    xcoords[2] = i+1;
                    xcoords[3] = i+1;
                    ycoords[0] = ybound;
                    ycoords[1] = firstoutput;
                    ycoords[2] = secondoutput;
                    ycoords[3] = ybound;
                    g.fillPolygon(new Polygon(xcoords,ycoords,4));
                    firstoutput = secondoutput;
                }
                halfdone = true;
            }
            else
            {
                firstoutput = yValueToApp(polynomial.evaluate(xAppToValue(begin)));
                for(int i = begin; i<end; i++)
                {
                    secondoutput = yValueToApp(polynomial.evaluate(xAppToValue(i+1)));
                    xcoords[0] = i;
                    xcoords[1] = i;
                    xcoords[2] = i+1;
                    xcoords[3] = i+1;
                    ycoords[0] = 0;
                    ycoords[1] = firstoutput;
                    ycoords[2] = secondoutput;
                    ycoords[3] = 0;
                    g.fillPolygon(new Polygon(xcoords,ycoords,4));
                    firstoutput = secondoutput;
                }
                done = true;
            }
        }
    }

    private int xValueToApp(double input)
    {
        double fraction = (input-xmin)/xrange;
        return (int)Math.round((double)xbound*fraction);
    }

    private int yValueToApp(double input)
    {
        double fraction = (input-ymin)/yrange;
        return ybound-(int)Math.round((double)ybound*fraction);
    }

    private double xAppToValue(int input)
    {
        double fraction = (double)input/(double)xbound;
        return xmin + xrange*fraction;
    }

    private double yAppToValue(int input)
    {
        double fraction = (double)input/(double)ybound;
        return ymax - yrange*fraction;
    }

    public boolean isdone()
    {
        return done;
    }

    public boolean ishalfdone()
    {
        return halfdone;
    }
}

