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
public class TEST130110 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
//        double k=0.5*4.12*Math.pow(10, 11)*2000/(4*2.13*Math.pow(10, 5)*114*Math.pow(10, 7))/2000*1650;
        double k=0.5*5.29*Math.pow(10, 11)*2000/(4*2.13*Math.pow(10, 5)*114*Math.pow(10, 7))/2000*1650;
        System.out.println(k);
        System.out.println();
        double[][] node={{0,0},{0,350},{2000,0},{0,-350},{-2000,0},{0,2000},{0,-2000}};
        int[][] memb={{0,1},{0,2},{0,3},{0,4},{1,5},{3,6}};
        SectionInfo[] si={new SectionInfo(2.13*Math.pow(10, 8),114*Math.pow(10, 7),18720,0),
                            new SectionInfo(2.15*Math.pow(10, 5),197*Math.pow(10, 7),23150,0),
                            new SectionInfo(2.13*Math.pow(10, 8),114*Math.pow(10, 7),18720,0),
                            new SectionInfo(2.15*Math.pow(10, 5),197*Math.pow(10, 7),23150,0),
                            new SectionInfo(2.13*Math.pow(10, 5),114*Math.pow(10, 7),18720,0),
                            new SectionInfo(2.13*Math.pow(10, 5),114*Math.pow(10, 7),18720,0)};
        EndInfo[] ei=new EndInfo[memb.length];
        AbstractMember[] am=new AbstractMember[memb.length];
        ei[0]=new EndInfo(node[memb[0][0]],node[memb[0][1]],new String[]{"r","r"});
        am[0]=new StraightMember(ei[0],si[0]);
        ei[1]=new EndInfo(node[memb[1][0]],node[memb[1][1]],new String[]{"r","r"});
        am[1]=new StraightMember(ei[1],si[1]);
        ei[2]=new EndInfo(node[memb[2][0]],node[memb[2][1]],new String[]{"r","r"});
        am[2]=new StraightMember(ei[2],si[2]);
        ei[3]=new EndInfo(node[memb[3][0]],node[memb[3][1]],new String[]{"r","r"});
        am[3]=new StraightMember(ei[3],si[3]);
        
        ei[4]=new EndInfo(node[memb[4][0]],node[memb[4][1]],new String[]{""+k,"r"});
        am[4]=new StraightMember(ei[4],si[4]);
        ei[5]=new EndInfo(node[memb[5][0]],node[memb[5][1]],new String[]{""+k,"r"});
        am[5]=new StraightMember(ei[5],si[5]);

        double S=100;
        NodeLoadInfo[] NLI={new NodeLoadInfo(5,new double[] {1,0,0}),new NodeLoadInfo(6,new double[] {-1,0,0})};
        SupportCondition sc=new SupportCondition(2,0);
        sc.setSupportCondition("f", "r", "f");
        SupportCondition sc2=new SupportCondition(4,0);
        sc2.setSupportCondition("r", "r", "f");

        SupportCondition[] SC={sc,sc2};
        WholeFrame model=new WholeFrame(node,memb,am);
        model.setNodeLoad(NLI);
        model.setSupportCondition(SC);
        model.CALC();
        MATRIX_CALC mc=new MATRIX_CALC();
        System.out.println();
        mc.PrintArray(model.getWholeP());
        outputData out=new outputData(model,S,0.01,10);
        DesktopFrame frame=new DesktopFrame(out);
////        new outputEPS("abc.eps",out);
////        System.out.println(Math.sin(Math.PI/2));
    }

}
