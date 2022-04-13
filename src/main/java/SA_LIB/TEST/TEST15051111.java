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
public class TEST15051111 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        System.out.println();
        double[][] node={{0,0},{2300,0},{0,-1800},{-2300,0}};
        int[][] memb={{0,2},{0,3},{0,1}};
        SectionInfo[] si={
            new SectionInfo(2.05*Math.pow(10, 5),110*Math.pow(10, 7),205000,0),
            new SectionInfo(2.05*Math.pow(10, 5),83*Math.pow(10, 7),205000,0),
            new SectionInfo(2.05*Math.pow(10, 5),83*Math.pow(10, 7),205000,0)
        };
        EndInfo[] ei=new EndInfo[memb.length];
        AbstractMember[] am=new AbstractMember[memb.length];
        ei[0]=new EndInfo(node[memb[0][0]],node[memb[0][1]],new String[]{"r","r"});
        ei[1]=new EndInfo(node[memb[0][0]],node[memb[0][1]],new String[]{"r","r"});
        ei[2]=new EndInfo(node[memb[0][0]],node[memb[0][1]],new String[]{"r","r"});
        am[0]=new StraightMember(ei[0],si[0]);
        am[1]=new StraightMember(ei[1],si[1]);
        am[2]=new StraightMember(ei[2],si[2]);

//        たぶん反時計回りが正
        NodeLoadInfo[] NLI={
            new NodeLoadInfo(2,new double[] {1000,0,0}),
        };
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
        double S=100;
        AbstractMember [] minfo=model.getMemberInfo();
        for (int i = 0; i < minfo.length; i++) {
            AbstractMember minfo1 = minfo[i];
            minfo1.getStress();
        }
        
        outputData out=new outputData(model,S,1,1.0*Math.pow(10, 11));
        DesktopFrame frame=new DesktopFrame(out);
////        new outputEPS("abc.eps",out);
////        System.out.println(Math.sin(Math.PI/2));
    }

}
