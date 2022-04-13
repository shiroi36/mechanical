/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package SA_LIB.TEST;
import SA_LIB.SA.*;
import SA_LIB.IO.OUTPUT.*;
import SA_LIB.SA.MEMB.*;
import MATH_LIB.MATRIX_LIB.MATRIX_CALC;
/**
 *
 * @author Araki Keita
 */
public class TEST1007282 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        double[][] node={{3000,4000},{7000,4000},{0,0},{7000,0}};
        int[][] memb={{2,0},{3,1},{0,1}};
        SectionInfo[] si={new SectionInfo(2.1,52*Math.pow(10, 8),250000,0),
                            new SectionInfo(2.1,52*Math.pow(10, 8),250000,0),
                            new SectionInfo(2.1,54*Math.pow(10, 8),180000,0)};
        EndInfo[] ei=new EndInfo[memb.length];
        AbstractMember[] am=new AbstractMember[memb.length];
        for(int i=0;i<memb.length;i++){
//            System.out.println(node[memb[i][0]][0]+"    "+node[memb[i][0]][1]+"    "+node[memb[i][1]][0]+"    "+node[memb[i][1]][1]);
            ei[i]=new EndInfo(node[memb[i][0]],node[memb[i][1]],new String[]{"r","r"});
            am[i]=new StraightMember(ei[i],si[i]);
        }
//        am[0].setLoad(new MemberLoadInfo(0,1,0.0024));
//        am[2].setLoad(new MemberLoadInfo(2,0,10,2000));
        double S=100;
//        NodeLoadInfo[] NLI={new NodeLoadInfo(0,new double[] {5,0,0}),new NodeLoadInfo(1,new double[] {5,0,0})};
        NodeLoadInfo[] NLI={new NodeLoadInfo(3,new double[] {0,5,0})};
        SupportCondition sc=new SupportCondition(2,0);
        sc.setSupportCondition("r", "r", "r");
        SupportCondition sc2=new SupportCondition(3,30);
        sc2.setSupportCondition("f", "r", "f");
//        SupportCondition sc2=new SupportCondition(3,0);
//        sc2.setSupportCondition("r", "r", "r");
        SupportCondition[] SC={sc,sc2};
        WholeFrame model=new WholeFrame(node,memb,am);
        model.setNodeLoad(NLI);
        model.setSupportCondition(SC);
        model.CALC();
        MATRIX_CALC mc=new MATRIX_CALC();
        mc.PrintArray(model.getWholeP());
        outputData out=new outputData(model,S,0.01,10);
        DesktopFrame frame=new DesktopFrame(out);
////        new outputEPS("abc.eps",out);
////        System.out.println(Math.sin(Math.PI/2));
    }

}
