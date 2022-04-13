/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package SA_LIB.SA.MEMB;
import MATH_LIB.MATRIX_LIB.MATRIX_CALC;

/**
 *
 * @author araki keita
 */
public abstract class AbstractMember extends MATRIX_CALC{
    public abstract double[][][] getC();
    public abstract double[][] getK();
    public abstract double[][] getEqLoadInSC();
    public abstract void setLoad(MemberLoadInfo mli);
    public abstract void setMemberStress(double[] Pm);
    public abstract void setMemberDisp(double[] Pd);
    public abstract double[][] getStress();
}
