/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package SA_LIB.SA.Model.InputInfo;

/**
 *
 * @author ArakiKeita
 */
public class EndInfo {
    double[] point0,point1,rigid;
    String[] condition;
    public EndInfo(double[] point0,double[] point1,double[] rigid,String[] condition){
        this.point0=point0;
        this.point1=point1;
        this.rigid=rigid;
        this.condition=condition;
    }
    public double[] getPoint0(){return point0;}
    public double[] getPoint1(){return point1;}
    public double[] getRigid(){return rigid;}
    public String[] getCondition(){return condition;}
}
