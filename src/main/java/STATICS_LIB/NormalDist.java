/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package STATICS_LIB;

/**
 *
 * @author araki keita
 */
public class NormalDist extends function{
    double mu;
    double sigma;
    boolean flag=true;
    double p,th;
    public NormalDist(double mu,double sigma){
        this.mu=mu;
        this.sigma=sigma;
    }
    public double getValue(double x){
        double value;
        value=1/(Math.sqrt(2*Math.PI)*sigma)*Math.exp(-Math.pow((x-mu)/sigma, 2)/2);
        return value;
    }
    public double getRandomValue(){
        if(flag){
            flag=false;
            p=sigma*Math.sqrt(-2*Math.log(1-Math.random()));
            th=2*Math.PI*Math.random();
            return p*Math.cos(th)+mu;
        }
        else{
            flag=true;
            return p*Math.sin(th)+mu;
        }
    }
}
