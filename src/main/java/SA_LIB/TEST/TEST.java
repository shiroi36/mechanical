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
public class TEST {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        double[][] node={{0,0},{3000,4000},{7000,0},{7000,4000}};
        int[][] memb={{0,1},{1,3},{3,2}};
        SectionInfo si=new SectionInfo(2,5,8,0);
        EndInfo[] ei=new EndInfo[memb.length];
        AbstractMember[] am=new AbstractMember[memb.length];
        for(int i=0;i<memb.length;i++){
            ei[i]=new EndInfo(node[memb[i][0]],node[memb[i][1]],new double[] {0,0},new String[]{"r","r"});
            am[i]=new StraightMemberWithRigid(ei[i],si);
        }
        double S=100;
        NodeLoadInfo nli=new NodeLoadInfo(1,new double[] {100,150,200});
        NodeLoadInfo[] NLI={nli};
        SupportCondition sc=new SupportCondition(1,30);
        sc.setSupportCondition("r", "r", "r");
        SupportCondition sc2=new SupportCondition(0,30);
        sc2.setSupportCondition("r", "f", "335");
        SupportCondition[] SC={sc,sc2};
        WholeFrame model=new WholeFrame(node,memb,am);
        model.setNodeLoad(NLI);
        model.setSupportCondition(SC);
        model.CALC();
        MATRIX_CALC mc=new MATRIX_CALC();
//        mc.PrintArray(model.getWholeP());
        outputData out=new outputData(model,S,100,1000);
        DesktopFrame frame=new DesktopFrame(out);
////        new outputEPS("abc.eps",out);
////        System.out.println(Math.sin(Math.PI/2));
    }

}
