/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package RA_LIB;

/**
 *それぞれの応答解析法を束ねるabstractclass
 * @author araki keita
 */
public abstract class ResponceAnalyzer {
    public abstract void CALC(double iniDisp,double iniVel);
    public abstract void CALC();
    public abstract double[][] getRESPONCE();
    public abstract double[] getQ();
}
