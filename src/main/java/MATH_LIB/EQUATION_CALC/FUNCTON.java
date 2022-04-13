/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package MATH_LIB.EQUATION_CALC;

/**
 *
 * @author araki keita
 */
public class FUNCTON {
    double M;
    double Amax;
    public FUNCTON(double M,double Amax){
        this.M=M;
        this.Amax=Amax;
    }
    public double function(double x){
        System.out.println(M+"  "+Amax);
        double y;
        y=0.51*M-Math.log10(Amax)-Math.log10(x+0.006*Math.pow(10, 0.51*M))-0.0034*x+0.59;//ここで関数を定義します
        return y;
    }

}
