/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package EXP_LIB.FITTING.SSC;

import MATH_LIB.FUNCTION.Function;
/**
 *
 * @author keita
 */
public class SSCFunction extends Function{
    double a1,a2,b1,b2;
    SSCFunction(double a1,double a2,double b1,double b2){
        this.a1=a1;
        this.a2=a2;
        this.b1=b1;
        this.b2=b2;
    }
    public double getValue(double x){
        return a2*Math.log(x)-a1*x+b2-b1;
    }
}
