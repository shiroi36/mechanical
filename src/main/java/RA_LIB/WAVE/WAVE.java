/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package RA_LIB.WAVE;

/**
 *
 * @author araki keita
 */
public class WAVE {
    public double ds;
    public double[] y0;
    public WAVE(double ds,double[] y0){
        this.ds=ds;
        this.y0=y0;
    }
    public double getds(){
        return ds;
    }
    public double getdy0(int i){
        double dy0=y0[i+1]-y0[i];
        return dy0;
    }
    public double getY0(int i){
        return y0[i];
    }
    public double[] getY0(){
        return y0;
    }
    public double[] getTime(){
        double[] time=new double[y0.length];
        for(int i=0;i<y0.length;i++){
            time[i]=ds*i;
        }
        return time;
    }
}
