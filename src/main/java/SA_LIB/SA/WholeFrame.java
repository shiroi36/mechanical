/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package SA_LIB.SA;
import SA_LIB.SA.MEMB.AbstractMember;
import MATH_LIB.MATRIX_LIB.MATRIX_CALC;
import java.util.ArrayList;
/**
 *
 * @author araki keita
 */
public class WholeFrame extends MATRIX_CALC{
    private double[][] node;
    private AbstractMember[] member;
    private int[][] memb;
    private NodeLoadInfo[] Nload;
    private SupportCondition[] sup;
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
        double[][] C=new double[3*nodenum][3*membnum];
        for(int i=0;i<membnum;i++){
            int num0=memb[i][0];
            int num1=memb[i][1];
            double[][][] membC=member[i].getC();
            this.addMatrixToMatrix(C, membC[0], 3*num0, 3*i);
            this.addMatrixToMatrix(C, membC[1], 3*num1, 3*i);
        }
        return C;
    }
    private double[][] getMemberK(){
        int membnum=member.length;
        double[][] Km=new double[3*membnum][3*membnum];
        for(int i=0;i<membnum;i++){
            this.addMatrixToMatrix(Km, member[i].getK(), 3*i, 3*i);
        }
//        System.out.println("-----getMemberK-------");
//        super.PrintMatrix(Km);
//        System.out.println("---------------");
        return Km;
    }
    /**
     * 構成方程式マトリクスを作成し全体剛性マトリクス（支点条件未適用）を
     * 作成するメソッド
     * @return
     */
    private double[][] getFrameK(){
        double[][] C=this.getConnectMatrix();
        double[][] Km=this.getMemberK();
        double[][] K=super.multiply(super.multiply(C, Km), super.Transpose(C));
        return K;
    }
    public void setSupportCondition(SupportCondition[] sup){
        this.sup=sup;
    }
    /**
     * 節点荷重をセットするメソッド
     * @param nli　セットする節点荷重
     */
    public void setNodeLoad(NodeLoadInfo[] nli){
        Nload=nli;
    }
    /**
     * 全体骨組みにおける荷重ベクトル（支点条件未適用）を作成するメソッド
     * @return
     */
    public double[] getWholeP(){
        double[] P=new double[3*node.length];
        //部材の等価接点荷重を加えていく
        for(int i=0;i<member.length;i++){
            for(int s=0;s<2;s++){
                this.addVectorToVector(P, member[i].getEqLoadInSC()[s], 3*memb[i][s]);
            }
        }
        //接点荷重を加えていく
        try{
            for(int i=0;i<Nload.length;i++){
                NodeLoadInfo nli=Nload[i];
                int Pnode=nli.getNode();
                double[] nodeP=nli.getNQM();
                this.addVectorToVector(P, nodeP, 3*Pnode);
            }
        }catch(NullPointerException e){
            
        }
        return P;
    }
    public double[][] getNode(){
        return node;
    }
    public int[][] getMemb(){
        return memb;
    }
    public AbstractMember[] getMemberInfo(){
        return member;
    }
    /**
     * addedのiniRow行iniShort列をaddの右上としてadd行列を加える
     * 100726チェック完了
     * @param added
     * @param add
     * @param iniRow
     * @param iniShort
     */
    private void addMatrixToMatrix(double[][] added,double[][] add,int iniRow,int iniShort){
        for(int i=0;i<add.length;i++){
            for(int s=0;s<add[i].length;s++){
                added[iniRow+i][iniShort+s]+=add[i][s];
            }
        }
    }
    /**
     * matrixのiniRow行iniShort列をreplaceの行列に置き換える
     * 100726チェック完了
     * @param replaced
     * @param replace
     * @param iniRow
     * @param iniShort
     */
    private void replaceMatrix(double[][] replaced,double[][] replace,int iniRow,int iniShort){
        for(int i=0;i<replace.length;i++){
            for(int s=0;s<replace[i].length;s++){
                replaced[iniRow+i][iniShort+s]=replace[i][s];
            }
        }
    }
    /**
     * matrixからRightUpperNumberを[0][0]としてRow行Short列の行列を切り出す
     * 100726チェック終了
     * @param vector
     * @param RightUpperRow
     * @param RightUpperShort
     * @param RowLength
     * @param ShortLength
     * @return
     */
    private double[][] sampleMatrixToMatrix(double[][] matrix,int RightUpperRow,int RightUpperShort,int RowLength,int ShortLength){
        double[][] sample=new double[RowLength][ShortLength];
        for(int i=0;i<RowLength;i++){
            for(int s=0;s<ShortLength;s++){
                sample[i][s]=matrix[RightUpperRow+i][RightUpperShort+s];
            }
        }
        return sample;
    }
    /**
     * addedベクトルのiniRow行目ににaddベクトルを加える
     * 100726チェック終了
     * @param added
     * @param add
     * @param iniRow
     */
    private void addVectorToVector(double[] added,double[] add,int iniRow){
        for(int i=0;i<add.length;i++){
            added[iniRow+i]+=add[i];
        }
    }
    /**
     * replacedベクトルのiniRow行目以降をreplaceベクトルで置き換える
     * 100726チェック終了
     * @param replaced
     * @param replace
     * @param iniRow
     */
    private void replaceVector(double[] replaced,double[] replace,int iniRow){
        for(int i=0;i<replace.length;i++){
            replaced[iniRow+i]=replace[i];
        }
    }
    /**
     * vectorベクトルのiniRow行目からlength長さのベクトルを抽出する
     * 100726チェック終了
     * @param vector
     * @param iniRow
     * @param length
     * @return
     */
    private double[] sampleVector(double[] vector,int iniRow,int length){
        double[] sample=new double[length];
        for(int i=0;i<length;i++){
            sample[i]=vector[iniRow+i];
        }
        return sample;
    }
    public void CALC(){
        ArrayList<Integer> katamukiNode=new ArrayList<Integer>();
        double[] dispInSC;
        double[][] K=this.getFrameK();
        double[] P=this.getWholeP();
        
//        System.out.println("-----getFrameK-------");
//        super.PrintMatrix(K);
//        System.out.println("---------------");

//        super.PrintArray(P);
        //支点条件の導入
        for(int i=0;i<sup.length;i++){
            //支点の傾きの情報の導入
            boolean kataflag=sup[i].getKatamukiFlag();
            int supnode=sup[i].getNode();
            if(kataflag==true){
                katamukiNode.add(i);
                double[][] T=sup[i].getT();
                double[] Psample=this.sampleVector(P, 3*supnode, 3);
                this.replaceVector(P, super.multiply(super.Transpose(T), Psample), 3*supnode);
                for(int s=0;s<node.length;s++){
                    double[][] Ksample=this.sampleMatrixToMatrix(K, 3*supnode, 3*s, 3, 3);
                    this.replaceMatrix(K, super.multiply(super.Transpose(T), Ksample), 3*supnode, 3*s);
                }
                for(int s=0;s<node.length;s++){
                    double[][] Ksample=this.sampleMatrixToMatrix(K, 3*s, 3*supnode, 3, 3);
                    this.replaceMatrix(K, super.multiply(Ksample, T), 3*s, 3*supnode);
                }
            }
            //支点情報の取得
            String[] supInfo=sup[i].getSupportCondition();
            for(int s=0;s<supInfo.length;s++){
                if(supInfo[s].contains("r")==true){
                    for(int t=0;t<K.length;t++){
                        K[t][3*supnode+s]=0;
                        K[3*supnode+s][t]=0;
                    }
                    K[3*supnode+s][3*supnode+s]=1;
                    P[3*supnode+s]=0;
                }
                else if(supInfo[s].contains("f")){}
                else{
                    double k=Double.parseDouble(supInfo[s]);
                    K[3*supnode+s][3*supnode+s]+=k;
                }
            }
        }
        dispInSC=super.GAUSS_SOKYO_HOU(K, P);
        for(int i=0;i<katamukiNode.size();i++){
            int kn=sup[katamukiNode.get(i)].getNode();
            double[][] T=sup[katamukiNode.get(i)].getT();
            double[] dsample=this.sampleVector(dispInSC, 3*kn, 3);
            this.replaceVector(dispInSC, super.multiply(T, dsample), 3*kn);
        }
        System.out.println("基準座標系の変位");
        super.PrintArray(dispInSC);
        System.out.println("");
        double[][] Km=this.getMemberK();
        double[][] C=this.getConnectMatrix();
        double[] pm=super.multiply(Km, super.multiply(super.Transpose(C), dispInSC));
        for(int i=0;i<memb.length;i++){
            double[] pm2={pm[3*i],pm[3*i+1],pm[3*i+2]};
            System.out.println("");
            super.PrintArray(pm2);
            int i2=memb[i][0];
            double[] ds={dispInSC[3*i2],dispInSC[3*i2+1],dispInSC[3*i2+2]};
            System.out.println("【部材"+i+"】");
            member[i].setMemberStress(pm2);
            member[i].setMemberDisp(ds);
        }
    }
}
