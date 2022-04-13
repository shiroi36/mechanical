/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package STATICS_LIB;

/**
 *
 * @author kato
 */
public class LogNormalDist extends function{
    double sigma;
    double mu;
    public LogNormalDist(double mu,double sigma){
        this.mu=mu;
        this.sigma=sigma;
    }
    public double getValue(double x){
        if(x<=0){return 0;}
        else{
            double value=1/(Math.sqrt(2*Math.PI)*sigma*x)*Math.exp(-Math.pow((Math.log(x)-mu)/sigma, 2)/2);
            return value;
        }

    }
}
