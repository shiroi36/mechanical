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
public class TEST230929 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        System.out.println();
        double[][] node={
            {0,0},
            {1000,0},
            {2000,0},
            {3000,0},
            {4000,0},
            {5000,0},
            {6000,0},
//            {7000,0},
        };
        int[][] memb={
            {0,1},
            {1,2},
            {2,3},
            {3,4},
            {4,5},
            {5,6},
//            {6,7},
        };
        SectionInfo[] si={
            //E,I,A,gamma
            new SectionInfo(2.05*Math.pow(10, 5),100000,10000,0),
        };
        EndInfo[] ei=new EndInfo[memb.length];
        AbstractMember[] am=new AbstractMember[memb.length];
        ei[0]=new EndInfo(node[memb[0][0]],node[memb[0][1]],new String[]{"r","r"});
        ei[1]=new EndInfo(node[memb[1][0]],node[memb[1][1]],new String[]{"r","r"});
        ei[2]=new EndInfo(node[memb[2][0]],node[memb[2][1]],new String[]{"r","r"});
        ei[3]=new EndInfo(node[memb[3][0]],node[memb[3][1]],new String[]{"r","r"});
        ei[4]=new EndInfo(node[memb[4][0]],node[memb[4][1]],new String[]{"r","r"});
        ei[5]=new EndInfo(node[memb[5][0]],node[memb[5][1]],new String[]{"r","r"});
//        ei[6]=new EndInfo(node[memb[6][0]],node[memb[6][1]],new String[]{"r","r"});
        am[0]=new StraightMember(ei[0],si[0]);
        am[0].setLoad(new MemberLoadInfo(0,1,100));
        am[1]=new StraightMember(ei[1],si[0]);
        am[1].setLoad(new MemberLoadInfo(1,1,100));
        am[2]=new StraightMember(ei[2],si[0]);
        am[2].setLoad(new MemberLoadInfo(2,1,100));
        am[3]=new StraightMember(ei[3],si[0]);
        am[3].setLoad(new MemberLoadInfo(3,1,100));
        am[4]=new StraightMember(ei[4],si[0]);
        am[4].setLoad(new MemberLoadInfo(4,1,100));
        am[5]=new StraightMember(ei[5],si[0]);
        am[5].setLoad(new MemberLoadInfo(5,1,100));
//        am[6]=new StraightMember(ei[6],si[0]);
//        am[6].setLoad(new MemberLoadInfo(6,1,100));

//        たぶん反時計回りが正
//架構用3.8kN/m2*5m*1m=19
//        NodeLoadInfo[] NLI={
//            new NodeLoadInfo(3,new double[] {0,9500,0}),
//            new NodeLoadInfo(4,new double[] {0,9500,0}),
//        };
        
        SupportCondition sc=new SupportCondition(0,0);
        sc.setSupportCondition("r", "r", "f");
        SupportCondition sc1=new SupportCondition(1,0);
        sc1.setSupportCondition("r", "r", "f");
        SupportCondition sc2=new SupportCondition(2,0);
        sc2.setSupportCondition("r", "r", "f");
        SupportCondition sc3=new SupportCondition(3,0);
        sc3.setSupportCondition("r", "r", "f");
        SupportCondition sc4=new SupportCondition(4,0);
        sc4.setSupportCondition("r", "r", "f");
        SupportCondition sc5=new SupportCondition(5,0);
        sc5.setSupportCondition("r", "r", "f");
        SupportCondition sc6=new SupportCondition(6,0);
        sc6.setSupportCondition("r", "r", "f");
//        SupportCondition sc7=new SupportCondition(7,0);
//        sc7.setSupportCondition("r", "r", "f");

//        SupportCondition[] SC={sc,sc1,sc2,sc3,sc4};
        SupportCondition[] SC={sc,sc1,sc2,sc3,sc4,sc5,sc6};
        WholeFrame model=new WholeFrame(node,memb,am);
//        model.setNodeLoad(NLI);
        model.setSupportCondition(SC);
        
        
        model.CALC();
        MATRIX_CALC mc=new MATRIX_CALC();
        System.out.println();
        System.out.println("getwholeP ");
        mc.PrintArray(model.getWholeP());
        double S=50;
        AbstractMember [] minfo=model.getMemberInfo();
        for (int i = 0; i < minfo.length; i++) {
            AbstractMember minfo1 = minfo[i];
            minfo1.getStress();
        }
        
        outputData out=new outputData(model,S,100,1.0*Math.pow(10, 5));
        
        //cdxszaで応力表示
        DesktopFrame frame=new DesktopFrame(out);
        
////        new outputEPS("abc.eps",out);
////        System.out.println(Math.sin(Math.PI/2));
    }

}
