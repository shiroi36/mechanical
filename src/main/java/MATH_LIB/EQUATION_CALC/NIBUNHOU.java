/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MATH_LIB.EQUATION_CALC;

import MATH_LIB.FUNCTION.Function;

/**
 *
 * @author araki keita
 */
public class NIBUNHOU {

    public double getnumber(double xmax, double xmin, Function f, double eps) {
        double xlow, xup, xmid;
        double ylow, yup, ymid;
        eps = eps * (xmax - xmin);//eps 解の精度の閾値
        xlow = xmin;
        xup = xmax;
        xmid = 0.0;
        ylow = f.getValue(xlow);
        yup = f.getValue(xup);
        if (ylow * yup > 0) {
            System.out.println("ERROR!!ymin*ymax=0.0");
            return 0.0;
        }
        double num;
        num = 1000000;//何回繰り返すかということ
        for (int i = 0; i < num; i++) {
            xmid = (xup + xlow) / 2;
            ymid = f.getValue(xmid);
            if (ylow * ymid > 0) {
                xlow = xmid;
                ylow = ymid;
            } else {
                xup = xmid;
                yup = ymid;
            }
            if (Math.abs(xup - xlow) < eps) {
                break;
            }
        }
        return xmid;
    }

}
