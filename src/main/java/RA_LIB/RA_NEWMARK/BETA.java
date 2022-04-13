/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package RA_LIB.RA_NEWMARK;

/**
 *
 * @author kato
 */
public class BETA {
//    public final static BETA IMPULSE=new BETA((double)0);
    public final static BETA STEP=new BETA((double)1/8);
    public final static BETA LINEAR=new BETA((double)1/6);
    public final static BETA  AVERAGE=new BETA((double)1/4);
    double value;
    public BETA(double value){
        this.value=value;
    }
    public double getValue(){
        return value;
    }
}
