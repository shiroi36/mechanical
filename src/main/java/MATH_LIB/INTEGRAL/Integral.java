/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package MATH_LIB.INTEGRAL;
import MATH_LIB.FUNCTION.Function;
/**
 *
 * @author Araki Keita
 */
public class Integral {

    public double getResultBySimpson(int n,double xmin,double xmax,Function f){
        if(n==0) return 0;
        double x;
        double h=(xmax-xmin)/n;
        double sum=f.getValue(xmin);
        for(int i=1;i<n;i+=2){
            x=xmin+i*h;
            sum+=4*f.getValue(x)+2*f.getValue(x+h);
        }
        sum-=f.getValue(xmax);
        return sum*h/3;
    }
}
