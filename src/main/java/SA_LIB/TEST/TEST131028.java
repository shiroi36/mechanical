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
public class TEST131028 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        System.out.println();
        double[][] node={{0,2025},{-900,0},{0,0},{900,0}};
        int[][] memb={{0,2},{2,1},{2,3}};
        SectionInfo[] si={new SectionInfo(205000,13.8*Math.pow(10, 7),9600,0),
                            new SectionInfo(205000,17.7*Math.pow(10, 7),9888,0),
                            new SectionInfo(205000,17.7*Math.pow(10, 7),9888,0)};
        EndInfo[] ei=new EndInfo[memb.length];
        AbstractMember[] am=new AbstractMember[memb.length];
        
        ei[0]=new EndInfo(node[memb[0][0]],node[memb[0][1]],new String[]{"r","r"});
        am[0]=new StraightMember(ei[0],si[0]);        
        ei[1]=new EndInfo(node[memb[1][0]],node[memb[1][1]],new String[]{"r","r"});
        am[1]=new StraightMember(ei[1],si[1]);
        ei[2]=new EndInfo(node[memb[2][0]],node[memb[2][1]],new String[]{"r","r"});
        am[2]=new StraightMember(ei[2],si[2]);
        

        double S=100;
        NodeLoadInfo[] NLI={new NodeLoadInfo(0,new double[] {1000,0,0})};
        SupportCondition sc=new SupportCondition(1,0);
        sc.setSupportCondition("r", "r", "f");
        SupportCondition sc2=new SupportCondition(3,0);
        sc2.setSupportCondition("r", "r", "f");

        SupportCondition[] SC={sc,sc2};
        WholeFrame model=new WholeFrame(node,memb,am);
        model.setNodeLoad(NLI);
        model.setSupportCondition(SC);
        model.CALC();
        MATRIX_CALC mc=new MATRIX_CALC();
        System.out.println();
        mc.PrintArray(model.getWholeP());
        outputData out=new outputData(model,S,5,1000);
        DesktopFrame frame=new DesktopFrame(out);
////        new outputEPS("abc.eps",out);
////        System.out.println(Math.sin(Math.PI/2));
    }

}
