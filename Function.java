package calculus_con;


/**
 * This class is a template for a function.
 * 
 * Amrith Lotlikar 
 * TPSP Final Presentation Version April 27, 2016
 */
public class Function
{
    public Function()
    {
    }
    public double evaluate(double x)
    {
        return 0.0;
    }
    public double differentiate(double x)
    {
       return (this.evaluate(x+.001)-this.evaluate(x-.001))/.002;
    }
}