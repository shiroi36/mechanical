/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package EXP_LIB.FITTING;

import EXP_LIB.EXP_LIB;
import IO_LIB.XLS_OPE;
import GRAPH_LIB.XY.XYGRAPH;
import EXP_LIB.FITTING.SSC.StrainStressCurveFitting;
import IO_LIB.SQL_OPE;
/**
 *
 * @author keita
 */
public class FittingTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        EXP_LIB exp=new EXP_LIB();
        RegLogLine rll=new RegLogLine();
        XYGRAPH plt=new XYGRAPH();
        StrainStressCurveFitting sscf=new StrainStressCurveFitting();
        SQL_OPE sql=new SQL_OPE("jdbc:h2:tcp://localhost/C:/DB/exp110323","keita","araki");
        double[][] mat=sql.getQueryData("select strain,load_N_mm2 from kd_mat;");
//        sscf.setValue(mat, 43, 52);
        sscf.Fitting();
        double[][] reg=sscf.getSSCFitting();
//        rll.setvalue(mat[0], mat[1]);
//        double[][] a=rll.getLine(0.01, 0.15,0.001);
        plt.setValue("1", mat);
//        plt.setValue("2",a);
        plt.setValue("2",reg);
        plt.PLOT();
    }

}
