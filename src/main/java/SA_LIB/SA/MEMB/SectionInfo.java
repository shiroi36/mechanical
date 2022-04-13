/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package SA_LIB.SA.MEMB;

/**
 *
 * @author ArakiKeita
 */
public class SectionInfo {
    double E,I,A,gamma;
    public SectionInfo(double E,double I,double A,double gamma){
        this.E=E;
        this.I=I;
        this.A=A;
        this.gamma=gamma;
    }
    public double getE(){return E;}
    public double getA(){return A;}
    public double getI(){return I;}
    public double getGamma(){return gamma;}
}
