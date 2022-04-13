/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package STATICS_LIB;

/**
 *
 * @author kato
 */

public class statics {
    double[] a;
    double E=0;
    double S=0;
    public statics(double[] a){
        this.a=a;
        for(int i=0;i<a.length;i++){
            E+=a[i]/a.length;
        }
        for(int i=0;i<a.length;i++){
            S+=Math.pow(a[i]-E, 2)/(a.length-1);
        }
    }
    public double getmean(){
        return E;
    }
    public double getAbsoluteMax(){
        double max=0;
        for(int i=0;i<a.length;i++){
            if(max<Math.abs(a[i])){
                max=Math.abs(a[i]);
            }
        }
        return max;
    }
    public double getMax(){
        double max=0;
        for(int i=0;i<a.length;i++){
            if(max<a[i]){
                max=a[i];
            }
        }
        return max;
    }
    public double getsigma(){
        return Math.sqrt(S);
    }
}
