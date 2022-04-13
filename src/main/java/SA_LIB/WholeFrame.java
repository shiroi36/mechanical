/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package SA_LIB;
import SA_LIB.SA.MEMB.AbstractMember;
import MATH_LIB.MATRIX_LIB.MATRIX_CALC;
import java.util.ArrayList;
/**
 *
 * @author araki keita
 */
public class WholeFrame extends MATRIX_CALC{
    double[][] node;
    AbstractMember[] member;
    int[][] memb;
    ArrayList<NodeLoadInfo> Nload=new ArrayList<NodeLoadInfo>();
    /**
     *
     * @param node x座標・ｙ座標と入力
     * @param memb
     */
    public WholeFrame(double[][] node,int[][] memb,AbstractMember[] member){
        this.node=node;
        this.member=member;
        this.memb=memb;
    }
    /**
     * 全体骨組みの接続マトリクスを作成するメソッド
     * 100717チェック終了
     * @return
     */
    private double[][] getConnectMatrix(){
        int nodenum=node.length;
        int membnum=member.length;
        System.out.println(nodenum+" "+membnum);
        double[][] C=new double[3*nodenum][3*membnum];
        for(int i=0;i<membnum;i++){
            int num0=memb[i][0];
            int num1=memb[i][1];
            System.out.println(num0+"  "+num1);
            double[][][] membC=member[i].getC();
            System.out.println(membC.length+" "+membC[0].length);
            this.addMatrixToMatrix(C, membC[0], 3*num0, 3*i);
            this.addMatrixToMatrix(C, membC[1], 3*num1, 3*i);
        }
        return C;
    }
    public double[][] getWholeK(){
        double[][] C=this.getConnectMatrix();
        int membnum=member.length;
        double[][] Km=new double[3*membnum][3*membnum];
        for(int i=0;i<membnum;i++){
            this.addMatrixToMatrix(Km, member[i].getK(), 3*i, 3*i);
        }
        double[][] K=super.multiply(super.multiply(C, Km), super.Transpose(C));
        return K;
    }
    public void setNodeLoad(NodeLoadInfo nli){
        Nload.add(nli);
    }
    public double[] getWholeP(){
        double[] P=new double[3*node.length];
        //部材の等価接点荷重を加えていく
        for(int i=0;i<member.length;i++){
            for(int s=0;s<2;s++){
                this.addVectorToVector(P, member[i].getEqLoadInSC()[s], 3*memb[i][s]);
            }
        }
        //接点荷重を加えていく
        for(int i=0;i<Nload.size();i++){
            NodeLoadInfo nli=Nload.get(i);
            int Pnode=nli.getNode();
            double[] nodeP=nli.getNQM();
            this.addVectorToVector(P, nodeP, 3*Pnode);
        }
        return P;
    }
    public double[][] getNode(){
        return node;
    }
    public int[][] getMemb(){
        return memb;
    }
    private void addMatrixToMatrix(double[][] added,double[][] add,int iniRow,int iniShort){
        for(int i=0;i<add.length;i++){
            for(int s=0;s<add[i].length;s++){
                added[iniRow+i][iniShort+s]+=add[i][s];
            }
        }
    }
    private void addVectorToVector(double[] added,double[] add,int iniRow){
        for(int i=0;i<add.length;i++){
            added[iniRow+i]+=add[i];
        }
    }
}
