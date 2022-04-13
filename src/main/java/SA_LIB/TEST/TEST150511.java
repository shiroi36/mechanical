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
public class TEST150511 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        System.out.println();
        double[][] node={{0,0},{9000,0},{18000,0}};
        int[][] memb={{0,1},{1,2}};
        SectionInfo[] si={
            new SectionInfo(2.05*Math.pow(10, 5),114*Math.pow(10, 7),18720,0),
            new SectionInfo(2.05*Math.pow(10, 5),114*Math.pow(10, 7),18720,0)
        };
        EndInfo[] ei=new EndInfo[memb.length];
        AbstractMember[] am=new AbstractMember[memb.length];
        ei[0]=new EndInfo(node[memb[0][0]],node[memb[0][1]],new String[]{"r","r"});
        am[0]=new StraightMember(ei[0],si[0]);
        ei[1]=new EndInfo(node[memb[1][0]],node[memb[1][1]],new String[]{"r","r"});
        am[1]=new StraightMember(ei[1],si[1]);

//        たぶん反時計回りが正
        NodeLoadInfo[] NLI={
            new NodeLoadInfo(0,new double[] {0,0,1000000}),
//            new NodeLoadInfo(0,new double[] {1,3000,0}),
            new NodeLoadInfo(2,new double[] {0,0,-1000000}),
        };
        SupportCondition sc=new SupportCondition(0,0);
        sc.setSupportCondition("r", "r", "f");
        SupportCondition sc2=new SupportCondition(1,0);
        sc2.setSupportCondition("f", "r", "f");
        SupportCondition sc3=new SupportCondition(2,0);
        sc3.setSupportCondition("f", "r", "f");

        SupportCondition[] SC={sc,sc2,sc3};
        WholeFrame model=new WholeFrame(node,memb,am);
        model.setNodeLoad(NLI);
        model.setSupportCondition(SC);
        model.CALC();
        MATRIX_CALC mc=new MATRIX_CALC();
        System.out.println();
        mc.PrintArray(model.getWholeP());
        double S=200;
        AbstractMember [] minfo=model.getMemberInfo();
        for (int i = 0; i < minfo.length; i++) {
            AbstractMember minfo1 = minfo[i];
            minfo1.getStress();
        }
        
        outputData out=new outputData(model,S,0.1,1.0*Math.pow(10, 3));
        DesktopFrame frame=new DesktopFrame(out);
////        new outputEPS("abc.eps",out);
////        System.out.println(Math.sin(Math.PI/2));
    }

}
