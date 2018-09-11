package calculus_con;


/**

 * TPSP Product class Calculus
 * Amrith Lotlikar
 * TPSP Final Presentation April 27, 2016
 */
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
public class Calculus
{
    ExecutorService CalculusConcurrency;
    double classintegralstep;
    public Calculus()
    {
        classintegralstep = 0;
        CalculusConcurrency = Executors.newCachedThreadPool();
    }

    public Calculus(double integralstep)
    {
        classintegralstep = integralstep;
        CalculusConcurrency = Executors.newCachedThreadPool();
    }
    
    public void changeStep(double integralstep)
    
    {
       classintegralstep = integralstep;
    }
    
    /**
    -This method inherits a function and a number of terms, and then finds the sum of the first n terms.
     */
    public double Summation(int d, Function myfunction)
    {
        double sum = 0.0;
        int rightlimit = d;
        int cores = Runtime.getRuntime().availableProcessors();
        PartialSum partialSum[] = new PartialSum[cores];
        int left, right;
        for(int k = 0; k<cores; k++)
        {
            left = rightlimit*k/cores+1;
            right = rightlimit*(k+1)/cores; 
            partialSum[k] = new PartialSum(left,right,myfunction);
            CalculusConcurrency.execute(partialSum[k]);
        }
        for(int k = 0; k<cores; k++)
        {
            while(!partialSum[k].isdone())
            {
            }
            sum += partialSum[k].getsum();
        }
        return sum;
    }
     public double Summation(int d, Polynomial poly)
    {
        double sum = 0.0;
        int rightlimit = d;
        int cores = Runtime.getRuntime().availableProcessors();
        PartialSum partialSum[] = new PartialSum[cores];
        int left, right;
        for(int k = 0; k<cores; k++)
        {
            left = rightlimit*k/cores+1;
            right = rightlimit*(k+1)/cores; 
            partialSum[k] = new PartialSum(left,right,poly);
            CalculusConcurrency.execute(partialSum[k]);
        }
        for(int k = 0; k<cores; k++)
        {
            while(!partialSum[k].isdone())
            {
            }
            sum += partialSum[k].getsum();
        }
        return sum;
    }

    /**
    -This method inherits a function and a number of terms, and then finds the sum of the first n terms.
     */
    public double Summation(int n,int d,Function function)
    {
        double sum = 0.0;
        int rightlimit = d;
        int leftlimit = n;
        int cores = Runtime.getRuntime().availableProcessors();
        PartialSum partialSum[] = new PartialSum[cores];
        int left, right;
        for(int k = 0; k<cores; k++)
        {
            left = (rightlimit-leftlimit+1)*k/cores+leftlimit;
            right = (rightlimit-leftlimit+1)*(k+1)/cores+leftlimit-1; 
            partialSum[k] = new PartialSum(left,right,function);
            CalculusConcurrency.execute(partialSum[k]);
        }
        for(int k = 0; k<cores; k++)
        {
            while(!partialSum[k].isdone())
            {
            }
            sum += partialSum[k].getsum();
        }
        return sum;
    }
    
    public double Summation(int n,int d,Polynomial poly)
    {
        double sum = 0.0;
        int rightlimit = d;
        int leftlimit = n;
        int cores = Runtime.getRuntime().availableProcessors();
        PartialSum partialSum[] = new PartialSum[cores];
        int left, right;
        for(int k = 0; k<cores; k++)
        {
            left = (rightlimit-leftlimit+1)*k/cores+leftlimit;
            right = (rightlimit-leftlimit+1)*(k+1)/cores+leftlimit-1; 
            partialSum[k] = new PartialSum(left,right,poly);
            CalculusConcurrency.execute(partialSum[k]);
        }
        for(int k = 0; k<cores; k++)
        {
            while(!partialSum[k].isdone())
            {
            }
            sum += partialSum[k].getsum();
        }
        return sum;
    }

    public static double SerialSummation(int d, Function function)
    {
        double sum = 0.0;
        for(int k =1; k<=d; k++)
        {
            sum+=function.evaluate(k);
        }
        return sum;
    }
    
    public static double SerialSummation(int d, Polynomial poly)
    {
        double sum = 0.0;
        for(int k =1; k<=d; k++)
        {
            sum+=poly.evaluate(k);
        }
        return sum;
    }

    /**
    -This method inherits a function and a number of terms, and then finds the sum of the first n terms.
     */
    public static double SerialSummation(int n,int d, Function function)
    {
        double sum = 0.0;
        for(int k =n; k<=d; k++)
        {
            sum+=function.evaluate(k);
        }
        return sum;
    }
    
     public static double SerialSummation(int n,int d, Polynomial poly)
    {
        double sum = 0.0;
        for(int k =n; k<=d; k++)
        {
            sum+=poly.evaluate(k);
        }
        return sum;
    }

    
    public double LeftHandRiemannSum(double leftlimit,double rightlimit,int steps,Function function)
    {
        double leftRiemannSum = 0.0;
        int cores = Runtime.getRuntime().availableProcessors();
        PartialRiemann partialRiemann[] = new PartialRiemann[cores];
        int firstsub, lastsub;
        double interval = (rightlimit-leftlimit)/(double)(steps);
        for(int k = 0; k<cores; k++)
        {
            firstsub = steps*k/cores;
            lastsub = steps*(k+1)/cores-1;
            partialRiemann[k] = new PartialRiemann(leftlimit+interval*(double)firstsub,lastsub-firstsub+1,interval,function);
            CalculusConcurrency.execute(partialRiemann[k]);
        }
        for(int k = 0; k<cores; k++)
        {
            while(!partialRiemann[k].isdone())
            {
            }
            leftRiemannSum += partialRiemann[k].getpartialriemann();
        }
        leftRiemannSum *= interval;
        return leftRiemannSum;
    }
    
    public double LeftHandRiemannSum(double leftlimit,double rightlimit,int steps, Polynomial poly)
    {
        double leftRiemannSum = 0.0;
        int cores = Runtime.getRuntime().availableProcessors();
        PartialRiemann partialRiemann[] = new PartialRiemann[cores];
        int firstsub, lastsub;
        double interval = (rightlimit-leftlimit)/(double)(steps);
        for(int k = 0; k<cores; k++)
        {
            firstsub = steps*k/cores;
            lastsub = steps*(k+1)/cores-1;
            partialRiemann[k] = new PartialRiemann(leftlimit+interval*(double)firstsub,lastsub-firstsub+1,interval,poly);
            CalculusConcurrency.execute(partialRiemann[k]);
        }
        for(int k = 0; k<cores; k++)
        {
            while(!partialRiemann[k].isdone())
            {
            }
            leftRiemannSum += partialRiemann[k].getpartialriemann();
        }
        leftRiemannSum *= interval;
        return leftRiemannSum;
    }

    public double SerialLeftHandRiemannSum(double leftlimit,double rightlimit,int steps, Function function)
    {
        double leftRiemannSum = 0.0;
        double interval = (rightlimit-leftlimit)/(double)(steps);
        for(int k = 0; k< steps; k++)
        {   
            leftRiemannSum+=function.evaluate(leftlimit + (double)k*interval);
        }
        leftRiemannSum *= interval;
        return leftRiemannSum;
    }
    
     public double SerialLeftHandRiemannSum(double leftlimit,double rightlimit,int steps, Polynomial poly)
    {
        double leftRiemannSum = 0.0;
        double interval = (rightlimit-leftlimit)/(double)(steps);
        for(int k = 0; k< steps; k++)
        {   
            leftRiemannSum+=poly.evaluate(leftlimit + (double)k*interval);
        }
        leftRiemannSum *= interval;
        return leftRiemannSum;
    }

    
    public double RightHandRiemannSum(double leftlimit,double rightlimit,int steps,Function function)
    {
        double rightRiemannSum = 0.0;
        int cores = Runtime.getRuntime().availableProcessors();
        PartialRiemann partialRiemann[] = new PartialRiemann[cores];
        int firstsub, lastsub;
        double interval = (rightlimit-leftlimit)/(double)(steps);
        for(int k = 0; k<cores; k++)
        {
            firstsub = steps*k/cores+1;
            lastsub = steps*(k+1)/cores;
            partialRiemann[k] = new PartialRiemann(leftlimit+interval*(double)firstsub,lastsub-firstsub+1,interval,function);
            CalculusConcurrency.execute(partialRiemann[k]);
        }
        for(int k = 0; k<cores; k++)
        {
            while(!partialRiemann[k].isdone())
            {
            }
            rightRiemannSum += partialRiemann[k].getpartialriemann();
        }
        rightRiemannSum *= interval;
        return rightRiemannSum;
    }
    
    public double RightHandRiemannSum(double leftlimit,double rightlimit,int steps, Polynomial poly)
    {
        double rightRiemannSum = 0.0;
        int cores = Runtime.getRuntime().availableProcessors();
        PartialRiemann partialRiemann[] = new PartialRiemann[cores];
        int firstsub, lastsub;
        double interval = (rightlimit-leftlimit)/(double)(steps);
        for(int k = 0; k<cores; k++)
        {
            firstsub = steps*k/cores+1;
            lastsub = steps*(k+1)/cores;
            partialRiemann[k] = new PartialRiemann(leftlimit+interval*(double)firstsub,lastsub-firstsub+1,interval,poly);
            CalculusConcurrency.execute(partialRiemann[k]);
        }
        for(int k = 0; k<cores; k++)
        {
            while(!partialRiemann[k].isdone())
            {
            }
            rightRiemannSum += partialRiemann[k].getpartialriemann();
        }
        rightRiemannSum *= interval;
        return rightRiemannSum;
    }

    public double SerialRightHandRiemannSum(double leftlimit,double rightlimit,int steps,Function function)
    {
        double rightRiemannSum = 0.0;
        double interval = (rightlimit-leftlimit)/(double)(steps);
        for(int k = 1; k<= steps; k++)
        {   
            rightRiemannSum+=function.evaluate(leftlimit + (double)k*interval);
        }
        rightRiemannSum *= interval;
        return rightRiemannSum;
    }
    
    public double SerialRightHandRiemannSum(double leftlimit,double rightlimit,int steps, Polynomial poly)
    {
        double rightRiemannSum = 0.0;
        double interval = (rightlimit-leftlimit)/(double)(steps);
        for(int k = 1; k<= steps; k++)
        {   
            rightRiemannSum+=poly.evaluate(leftlimit + (double)k*interval);
        }
        rightRiemannSum *= interval;
        return rightRiemannSum;
    }

    
    public double MidPointRiemannSum(double leftlimit,double rightlimit,int steps,Function function)
    {
        double midRiemannSum = 0.0;
        int cores = Runtime.getRuntime().availableProcessors();
        PartialRiemann partialRiemann[] = new PartialRiemann[cores];
        double firstsub, lastsub;
        double interval = (rightlimit-leftlimit)/(double)(steps);
        for(int k = 0; k<cores; k++)
        {
            firstsub = steps*k/cores+1.0/2.0;
            lastsub = steps*(k+1)/cores-1.0/2.0;
            partialRiemann[k] = new PartialRiemann(leftlimit+interval*firstsub,(int)Math.floor(lastsub)-(int)Math.floor(firstsub)+1,interval,function);
            CalculusConcurrency.execute(partialRiemann[k]);
        }
        for(int k = 0; k<cores; k++)
        {
            while(!partialRiemann[k].isdone())
            {
            }
            midRiemannSum += partialRiemann[k].getpartialriemann();
        }
        midRiemannSum *= interval;
        return midRiemannSum;
    }
    
    public double MidPointRiemannSum(double leftlimit,double rightlimit,int steps, Polynomial poly)
    {
        double midRiemannSum = 0.0;
        int cores = Runtime.getRuntime().availableProcessors();
        PartialRiemann partialRiemann[] = new PartialRiemann[cores];
        double firstsub, lastsub;
        double interval = (rightlimit-leftlimit)/(double)(steps);
        for(int k = 0; k<cores; k++)
        {
            firstsub = steps*k/cores+1.0/2.0;
            lastsub = steps*(k+1)/cores-1.0/2.0;
            partialRiemann[k] = new PartialRiemann(leftlimit+interval*firstsub,(int)Math.floor(lastsub)-(int)Math.floor(firstsub)+1,interval,poly);
            CalculusConcurrency.execute(partialRiemann[k]);
        }
        for(int k = 0; k<cores; k++)
        {
            while(!partialRiemann[k].isdone())
            {
            }
            midRiemannSum += partialRiemann[k].getpartialriemann();
        }
        midRiemannSum *= interval;
        return midRiemannSum;
    }

    public double SerialMidPointRiemannSum(double leftlimit,double rightlimit,int steps,Function function)
    {
        double midRiemannSum = 0.0;
        double interval = (rightlimit-leftlimit)/(double)(steps);
        for(int k = 0; k< steps; k++)
        {   
            midRiemannSum+=function.evaluate(leftlimit + ((double)k+0.5)*interval);
        }
        midRiemannSum *= interval;
        return midRiemannSum;
    }
    
     public double SerialMidPointRiemannSum(double leftlimit,double rightlimit,int steps, Polynomial poly)
    {
        double midRiemannSum = 0.0;
        double interval = (rightlimit-leftlimit)/(double)(steps);
        for(int k = 0; k< steps; k++)
        {   
            midRiemannSum+=poly.evaluate(leftlimit + ((double)k+0.5)*interval);
        }
        midRiemannSum *= interval;
        return midRiemannSum;
    }

    public double Integration(double leftlimit,double rightlimit, Function function)
    {
        double IntegralSum = 0.0;
        int cores = Runtime.getRuntime().availableProcessors();
        PartialIntegral partialIntegral[] = new PartialIntegral[cores];
        double integralstep, firstsub, lastsub;
        int totalLoops;
        if (classintegralstep==0.0)
        {
          integralstep = 0.001;       
        }
        else 
        {
           integralstep = classintegralstep;
        }
        totalLoops = (int)Math.ceil((rightlimit - leftlimit)/integralstep);
        for(int k = 0; k<cores; k++)
        { 
         firstsub = totalLoops*k/cores+1.0/2.0;
         lastsub = totalLoops*(k+1)/cores-1.0/2.0;
         partialIntegral[k] = new PartialIntegral(leftlimit+integralstep*firstsub,(int)Math.floor(lastsub)-(int)Math.floor(firstsub)+1,integralstep,function);
         CalculusConcurrency.execute(partialIntegral[k]);
        }
        for(int k = 0; k<cores; k++)
        {
            while(!partialIntegral[k].isdone())
            {
            }
        }
        for(int k = 0; k<cores; k++)
        {
            IntegralSum += partialIntegral[k].getpartialintegral();
        }
        IntegralSum*=integralstep;
        return IntegralSum;
    }
    
    public double Integration(double leftlimit,double rightlimit, Polynomial poly)
    {
        double IntegralSum = 0.0;
        int cores = Runtime.getRuntime().availableProcessors();
        PartialIntegral partialIntegral[] = new PartialIntegral[cores];
        double integralstep, firstsub, lastsub;;
        int totalLoops;
        if (classintegralstep==0.0)
        {
          integralstep = 0.001;       
        }
        else 
        {
           integralstep = classintegralstep;
        }
        totalLoops = (int)Math.ceil((rightlimit - leftlimit)/integralstep);
        for(int k = 0; k<cores; k++)
        { 
         firstsub = totalLoops*k/cores+1.0/2.0;
         lastsub = totalLoops*(k+1)/cores-1.0/2.0;
         partialIntegral[k] = new PartialIntegral(leftlimit+integralstep*firstsub,(int)Math.floor(lastsub)-(int)Math.floor(firstsub)+1,integralstep, poly);
         CalculusConcurrency.execute(partialIntegral[k]);
        }
        for(int k = 0; k<cores; k++)
        {
            while(!partialIntegral[k].isdone())
            {
            }
        }
        for(int k = 0; k<cores; k++)
        {
            IntegralSum += partialIntegral[k].getpartialintegral();
        }
        IntegralSum*=integralstep;
        return IntegralSum;
    }
    
    public double SerialIntegration(double leftlimit,double rightlimit)
    {
        double IntegralSum = 0.0;
        return 0.0;
    }
    
    
    public double Product(int d, Function function)
    {
        double product = 1.0;
        int rightlimit = d;
        int cores = Runtime.getRuntime().availableProcessors();
        PartialProduct partialProduct[] = new PartialProduct[cores];
        int left, right;
        for(int k = 0; k<cores; k++)
        {left = rightlimit*k/cores+1;
            right = rightlimit*(k+1)/cores; 
            partialProduct[k] = new PartialProduct(left,right,function);
            CalculusConcurrency.execute(partialProduct[k]);
        }
        for(int k = 0; k<cores; k++)
        {while(!partialProduct[k].isdone()){}
        }
        for(int k = 0; k<cores; k++)
        {product *= partialProduct[k].getproduct();
        }
        return product;
    }
    
     public double Product(int d, Polynomial poly)
    {
        double product = 1.0;
        int rightlimit = d;
        int cores = Runtime.getRuntime().availableProcessors();
        PartialProduct partialProduct[] = new PartialProduct[cores];
        int left, right;
        for(int k = 0; k<cores; k++)
        {left = rightlimit*k/cores+1;
            right = rightlimit*(k+1)/cores; 
            partialProduct[k] = new PartialProduct(left,right,poly);
            CalculusConcurrency.execute(partialProduct[k]);
        }
        for(int k = 0; k<cores; k++)
        {while(!partialProduct[k].isdone()){}
        }
        for(int k = 0; k<cores; k++)
        {product *= partialProduct[k].getproduct();
        }
        return product;
    }

    
    public double Product(int n,int d,Function function)
    {
        double product = 1.0;
        int rightlimit = d;
        int leftlimit = n;
        int cores = Runtime.getRuntime().availableProcessors();
        PartialProduct partialProduct[] = new PartialProduct[cores];
        int left, right;
        for(int k = 0; k<cores; k++)
        {
            left = (rightlimit-leftlimit+1)*k/cores+leftlimit;
            right = (rightlimit-leftlimit+1)*(k+1)/cores+leftlimit-1; 
            partialProduct[k] = new PartialProduct(left,right,function);
            CalculusConcurrency.execute(partialProduct[k]);
        }
        for(int k = 0; k<cores; k++)
        {
            while(!partialProduct[k].isdone())
            {
            }
            product *= partialProduct[k].getproduct();
        }
        return product;
    }
    
    public double Product(int n,int d, Polynomial poly)
    {
        double product = 1.0;
        int rightlimit = d;
        int leftlimit = n;
        int cores = Runtime.getRuntime().availableProcessors();
        PartialProduct partialProduct[] = new PartialProduct[cores];
        int left, right;
        for(int k = 0; k<cores; k++)
        {
            left = (rightlimit-leftlimit+1)*k/cores+leftlimit;
            right = (rightlimit-leftlimit+1)*(k+1)/cores+leftlimit-1; 
            partialProduct[k] = new PartialProduct(left,right,poly);
            CalculusConcurrency.execute(partialProduct[k]);
        }
        for(int k = 0; k<cores; k++)
        {
            while(!partialProduct[k].isdone())
            {
            }
            product *= partialProduct[k].getproduct();
        }
        return product;
    }

    public static double SerialProduct(int d,Function function)
    {
        double product = 1.0;
        for(int k =1; k<=d; k++)
        {
            product*=function.evaluate(k);
        }
        return product;
    }
    
    public static double SerialProduct(int d, Polynomial poly)
    {
        double product = 1.0;
        for(int k =1; k<=d; k++)
        {
            product*=poly.evaluate(k);
        }
        return product;
    }

   
    public static double SerialProduct(int n,int d,Function function)
    {
        double product = 1.0;
        for(int k =n; k<=d; k++)
        {
            product*=function.evaluate(k);
        }
        return product;
    }
    
     public static double SerialProduct(int n,int d, Polynomial poly)
    {
        double product = 1.0;
        for(int k =n; k<=d; k++)
        {
            product*=poly.evaluate(k);
        }
        return product;
    }
    
    public double AreaBetween(double leftlimit,double rightlimit, Function functionOne, Function functionTwo)
    {
        double IntegralSum = 0.0;
        int cores = Runtime.getRuntime().availableProcessors();
        PartialAreaBetween partialAreaBetween[] = new PartialAreaBetween[cores];
        double integralstep, firstsub, lastsub;
        int totalLoops;
        if (classintegralstep==0.0)
        {
          integralstep = 0.001;       
        }
        else 
        {
           integralstep = classintegralstep;
        }
        totalLoops = (int)Math.ceil((rightlimit - leftlimit)/integralstep);
        for(int k = 0; k<cores; k++)
        { 
         firstsub = totalLoops*k/cores+1.0/2.0;
         lastsub = totalLoops*(k+1)/cores-1.0/2.0;
         partialAreaBetween[k] = new PartialAreaBetween(leftlimit+integralstep*firstsub,(int)Math.floor(lastsub)-(int)Math.floor(firstsub)+1,integralstep,functionOne,functionTwo);
         CalculusConcurrency.execute(partialAreaBetween[k]);
        }
        for(int k = 0; k<cores; k++)
        {
            while(!partialAreaBetween[k].isdone())
            {
            }
        }
        for(int k = 0; k<cores; k++)
        {
            IntegralSum += partialAreaBetween[k].getpartialintegral();
        }
        IntegralSum*=integralstep;
        return IntegralSum;
    }
    
    public double AreaBetween(double leftlimit,double rightlimit, Polynomial functionOne, Polynomial functionTwo)
    {
        double IntegralSum = 0.0;
        int cores = Runtime.getRuntime().availableProcessors();
        PartialAreaBetween partialAreaBetween[] = new PartialAreaBetween[cores];
        double integralstep, firstsub, lastsub;
        int totalLoops;
        if (classintegralstep==0.0)
        {
          integralstep = 0.001;       
        }
        else 
        {
           integralstep = classintegralstep;
        }
        totalLoops = (int)Math.ceil((rightlimit - leftlimit)/integralstep);
        for(int k = 0; k<cores; k++)
        { 
         firstsub = totalLoops*k/cores+1.0/2.0;
         lastsub = totalLoops*(k+1)/cores-1.0/2.0;
         partialAreaBetween[k] = new PartialAreaBetween(leftlimit+integralstep*firstsub,(int)Math.floor(lastsub)-(int)Math.floor(firstsub)+1,integralstep,functionOne,functionTwo);
         CalculusConcurrency.execute(partialAreaBetween[k]);
        }
        for(int k = 0; k<cores; k++)
        {
            while(!partialAreaBetween[k].isdone())
            {
            }
        }
        for(int k = 0; k<cores; k++)
        {
            IntegralSum += partialAreaBetween[k].getpartialintegral();
        }
        IntegralSum*=integralstep;
        return IntegralSum;
    }
    public double AbsoluteIntegration(double leftlimit,double rightlimit, Function function)
    {
        double IntegralSum = 0.0;
        int cores = Runtime.getRuntime().availableProcessors();
        PartialAbsoluteIntegral partialAbsoluteIntegral[] = new PartialAbsoluteIntegral[cores];
        double integralstep, firstsub, lastsub;
        int totalLoops;
        if (classintegralstep==0.0)
        {
          integralstep = 0.001;       
        }
        else 
        {
           integralstep = classintegralstep;
        }
        totalLoops = (int)Math.ceil((rightlimit - leftlimit)/integralstep);
        for(int k = 0; k<cores; k++)
        { 
         firstsub = totalLoops*k/cores+1.0/2.0;
         lastsub = totalLoops*(k+1)/cores-1.0/2.0;
         partialAbsoluteIntegral[k] = new PartialAbsoluteIntegral(leftlimit+integralstep*firstsub,(int)Math.floor(lastsub)-(int)Math.floor(firstsub)+1,integralstep, function);
         CalculusConcurrency.execute(partialAbsoluteIntegral[k]);
        }
        for(int k = 0; k<cores; k++)
        {
            while(!partialAbsoluteIntegral[k].isdone())
            {
            }
        }
        for(int k = 0; k<cores; k++)
        {
            IntegralSum += partialAbsoluteIntegral[k].getpartialintegral();
        }
        IntegralSum*=integralstep;
        return IntegralSum;
    }
    public double AbsoluteIntegration(double leftlimit,double rightlimit, Polynomial function)
    {
        double IntegralSum = 0.0;
        int cores = Runtime.getRuntime().availableProcessors();
        PartialAbsoluteIntegral partialAbsoluteIntegral[] = new PartialAbsoluteIntegral[cores];
        double integralstep, firstsub, lastsub;
        int totalLoops;
        if (classintegralstep==0.0)
        {
          integralstep = 0.001;       
        }
        else 
        {
           integralstep = classintegralstep;
        }
        totalLoops = (int)Math.ceil((rightlimit - leftlimit)/integralstep);
        for(int k = 0; k<cores; k++)
        { 
         firstsub = totalLoops*k/cores+1.0/2.0;
         lastsub = totalLoops*(k+1)/cores-1.0/2.0;
         partialAbsoluteIntegral[k] = new PartialAbsoluteIntegral(leftlimit+integralstep*firstsub,(int)Math.floor(lastsub)-(int)Math.floor(firstsub)+1,integralstep, function);
         CalculusConcurrency.execute(partialAbsoluteIntegral[k]);
        }
        for(int k = 0; k<cores; k++)
        {
            while(!partialAbsoluteIntegral[k].isdone())
            {
            }
        }
        for(int k = 0; k<cores; k++)
        {
            IntegralSum += partialAbsoluteIntegral[k].getpartialintegral();
        }
        IntegralSum*=integralstep;
        return IntegralSum;
    }
}
class PartialAbsoluteIntegral implements Runnable
{
    private double firstterm;
    private int loops;
    private double interval;
    private double partialintegral;
    private boolean done;
    private Function function;
    private Polynomial poly;
    private boolean polynomial;
    public PartialAbsoluteIntegral(double FIRSTTERM, int LOOPS, double INTERVAL, Function FUNCTION)
    {   
        firstterm = FIRSTTERM;
        loops = LOOPS;
        interval = INTERVAL;
        partialintegral = 0.0;
        done = false;
        function = FUNCTION;
        polynomial = false;
    }
    
     public PartialAbsoluteIntegral(double FIRSTTERM, int LOOPS, double INTERVAL,Polynomial POLYNOMIAL)
    {   
        firstterm = FIRSTTERM;
        loops = LOOPS;
        interval = INTERVAL;
        partialintegral = 0.0;
        done = false;
        poly = POLYNOMIAL;
        polynomial = true;
    }
    
    public void run()
    {
        if(polynomial)
        {
         for(int k = 0; k<loops; k++)
        {
            partialintegral += Math.abs(poly.evaluate(firstterm+k*interval)); 
        }   
        }
        else
        {
        for(int k = 0; k<loops; k++)
        {
            partialintegral += Math.abs(function.evaluate(firstterm+k*interval)); 
        }
        }
        done = true;
    }

    public boolean isdone()
    {
        return done;
    }

    public double getpartialintegral()
    {
        return partialintegral;
    }

}
class PartialAreaBetween implements Runnable
{
    private double firstterm;
    private int loops;
    private double interval;
    private double partialintegral;
    private boolean done;
    private Function functionOne;
    private Function functionTwo;
    private Polynomial polyOne;
    private Polynomial polyTwo;
    private boolean polynomial;
    public PartialAreaBetween(double FIRSTTERM, int LOOPS, double INTERVAL, Function FUNCTIONONE, Function FUNCTIONTWO)
    {   
        firstterm = FIRSTTERM;
        loops = LOOPS;
        interval = INTERVAL;
        partialintegral = 0.0;
        done = false;
        functionOne = FUNCTIONONE;
        functionTwo = FUNCTIONTWO;
        polynomial = false;
    }
    
     public PartialAreaBetween(double FIRSTTERM, int LOOPS, double INTERVAL,Polynomial POLYNOMIALONE, Polynomial POLYNOMIALTWO)
    {   
        firstterm = FIRSTTERM;
        loops = LOOPS;
        interval = INTERVAL;
        partialintegral = 0.0;
        done = false;
        polyOne = POLYNOMIALONE;
        polyTwo = POLYNOMIALTWO;
        polynomial = true;
    }
    
    public void run()
    {
        if(polynomial)
        {
         for(int k = 0; k<loops; k++)
          {
            partialintegral += Math.abs(polyOne.evaluate(firstterm+k*interval)-polyTwo.evaluate(firstterm+k*interval)); 
          }   
        }
        else
        {
          for(int k = 0; k<loops; k++)
          {
            partialintegral += Math.abs(functionOne.evaluate(firstterm+k*interval)-functionTwo.evaluate(firstterm+k*interval)); 
          }
        }
        done = true;
    }

    public boolean isdone()
    {
        return done;
    }

    public double getpartialintegral()
    {
        return partialintegral;
    }

}
class PartialRiemann implements Runnable
{
    private double firstterm;
    private int loops;
    private double interval;
    private double partialriemann;
    private boolean done;
    private Function function;
    private Polynomial poly;
    private boolean polynomial;
    public PartialRiemann(double FIRSTTERM, int LOOPS, double INTERVAL, Function FUNCTION)
    { 
        firstterm = FIRSTTERM;
        loops = LOOPS;
        interval = INTERVAL;
        partialriemann = 0.0;
        done = false;
        function = FUNCTION;
        polynomial = false;
    }
    
     public PartialRiemann(double FIRSTTERM, int LOOPS, double INTERVAL, Polynomial POLYNOMIAL)
    { 
        firstterm = FIRSTTERM;
        loops = LOOPS;
        interval = INTERVAL;
        partialriemann = 0.0;
        done = false;
        poly = POLYNOMIAL;
        polynomial = true;
    }

    public void run()
    {
        if(polynomial)
        {
          for(int k = 0; k<loops; k++)
         {
            partialriemann += poly.evaluate(firstterm+k*interval); 
         }
        }
        else
        {
         for(int k = 0; k<loops; k++)
         {
            partialriemann += function.evaluate(firstterm+k*interval); 
         }
        }
        done = true;
    }

    public boolean isdone()
    {
        return done;
    }

    public double getpartialriemann()
    {
        return partialriemann;
    }

}
class PartialSum implements Runnable
{
    private int first;
    private int last;
    private boolean done;
    private double sum;
    private Function function;
    private boolean polynomial;
    private Polynomial poly;
    public PartialSum(int x, int y, Function z)
    {
        first = x;
        last = y;
        done = false;
        sum = 0.0;
        function = z;
        polynomial = false;
    }
    
     public PartialSum(int x, int y, Polynomial z)
    {
        first = x;
        last = y;
        done = false;
        sum = 0.0;
        poly = z;
        polynomial = true;
    }

    public void run()
    {
        if(polynomial)
        {
           for(int k = first; k<= last; k++)
          {
            sum += poly.evaluate(k);
          } 
        }
        else
       {
        for(int k = first; k<= last; k++)
        {
            sum += function.evaluate(k);
        }
       }
        done = true;
    }

    public boolean isdone()
    {
        return done;
    }

    public double getsum()
    {
        return sum;
    }
}
class PartialProduct implements Runnable
{
    private int first;
    private int last;
    private boolean done;
    private double product;
    private Function function;
    private boolean polynomial;
    private Polynomial poly;
    public PartialProduct(int x, int y, Function z)
    {
        first = x;
        last = y;
        done = false;
        product = 1.0;
        function = z;
        polynomial = false;
    }
    
    public PartialProduct(int x, int y, Polynomial z)
    {
        first = x;
        last = y;
        done = false;
        product = 1.0;
        poly = z;
        polynomial = true;
    }

    public void run()
    {
        if(polynomial)
        { for(int k = first; k<= last; k++)
          {
            product *= poly.evaluate(k);
          }
        }
        else
        {
            for(int k = first; k<= last; k++)
        {
            product *= function.evaluate(k);
        }
      }
        done = true;
    }

    public boolean isdone()
    {
        return done;
    }

    public double getproduct()
    {
        return product;
    }

}

class PartialIntegral implements Runnable
{
    private double firstterm;
    private int loops;
    private double interval;
    private double partialintegral;
    private boolean done;
    private Function function;
    private Polynomial poly;
    private boolean polynomial;
    public PartialIntegral(double FIRSTTERM, int LOOPS, double INTERVAL, Function FUNCTION)
    {   
        firstterm = FIRSTTERM;
        loops = LOOPS;
        interval = INTERVAL;
        partialintegral = 0.0;
        done = false;
        function = FUNCTION;
        polynomial = false;
    }
    
     public PartialIntegral(double FIRSTTERM, int LOOPS, double INTERVAL,Polynomial POLYNOMIAL)
    {   
        firstterm = FIRSTTERM;
        loops = LOOPS;
        interval = INTERVAL;
        partialintegral = 0.0;
        done = false;
        poly = POLYNOMIAL;
        polynomial = true;
    }
    
    public void run()
    {
        if(polynomial)
        {
         for(int k = 0; k<loops; k++)
        {
            partialintegral += poly.evaluate(firstterm+k*interval); 
        }   
        }
        else
        {
        for(int k = 0; k<loops; k++)
        {
            partialintegral += function.evaluate(firstterm+k*interval); 
        }
        }
        done = true;
    }

    public boolean isdone()
    {
        return done;
    }

    public double getpartialintegral()
    {
        return partialintegral;
    }

}

