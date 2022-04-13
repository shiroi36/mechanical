/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package SA_LIB.SA.MATRIX;
import MATH_LIB.MATRIX_LIB.MATRIX_CALC;
/**
 *
 * @author ArakiKeita
 */
public class SAMatrix extends MATRIX_CALC{
    /**
     * 部材座標系における釣り合いマトリクスを出力
     * @param L
     * @return
     */
    public double[][] H(double L){
        double[][] H=super.getI(3);
        H[2][1]=L;
        return H;
    }
    public double[][] Hinv(double L){
        double[][] H=super.getI(3);
        H[2][1]=-L;
        return H;
    }
}
