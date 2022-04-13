/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package RA_LIB.AbstractModel;
/**
 *
 * @author kato
 */
public abstract class AbstractModel {
    public abstract double getK();
    public abstract void setK(double[] RD,double[] res,double Q);
    public abstract boolean seekChangePointFlag(double[] res,double Q);
    public abstract boolean getPlasticFlag();
    public abstract double geth();
    public abstract double getOmega();
}
