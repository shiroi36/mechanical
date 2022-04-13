/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package SA_LIB.SA.MEMB;
import java.util.ArrayList;

/**
 *0端→１端で統一している．
 * point[0] xaxis
 * point[1] yaxis
 * @author ArakiKeita
 */
public class StraightMemberWithRigid extends AbstractMember{
    double[] L=new double[4];//L[0]全体の長さL[1]0端の剛域の長さL[2]変形を生じる部材長さL[3]1端の剛域の長さ
    double s,c,E,I,A,gamma;
    String end0,end1;
    ArrayList<MemberLoadInfo> load=new ArrayList<MemberLoadInfo>();
    public StraightMemberWithRigid(EndInfo info,SectionInfo cs){
        double[] p0=info.getPoint0();
        double[] p1=info.getPoint1();
        double[] rigid=info.getRigid();
        L[0]=Math.sqrt(Math.pow(p1[0]-p0[0], 2)+Math.pow(p1[1]-p0[1], 2));
        L[1]=rigid[0];
        L[3]=rigid[1];
        L[2]=L[0]-(rigid[0]+rigid[1]);
        s=(p1[1]-p0[1])/L[0];
        c=(p1[0]-p0[0])/L[0];
        E=cs.getE();
        I=cs.getI();
        A=cs.getA();
        gamma=cs.getGamma();
        end0=info.getCondition()[0];
        end1=info.getCondition()[1];
    }
    /**
     * 剛域を持つ部材における変換マトリクスを得るメソッド
     * （部材座標系）100617チェック終了
     * @return
     */
    private double[][] C0(){
        double[][] C0=new double[6][];
        C0[0]=new double[]{-1,0,0};
        C0[1]=new double[]{0,-1,0};
        C0[2]=new double[]{0,-(L[0]-L[3]),-1};
        C0[3]=new double[]{1,0,0};
        C0[4]=new double[]{0,1,0};
        C0[5]=new double[]{0,-L[3],1};
        return C0;
    }
    /**
     * 剛域を持つ部材における変換マトリクスを得るメソッド
     * （基準座標系）100617チェック終了
     * @return
     */
    private double[][] C(double sin,double cos){
        double[][] C=new double[6][];
        C[0]=new double[]{-cos,sin,0};
        C[1]=new double[]{-sin,-cos,0};
        C[2]=new double[]{0,-(L[0]-L[3]),-1};
        C[3]=new double[]{cos,-sin,0};
        C[4]=new double[]{sin,cos,0};
        C[5]=new double[]{0,-L[3],1};
        return C;
    }
    public double[][][] getC(){
        double[][][] C=new double[2][3][];
        C[0][0]=new double[]{-c,s,0};
        C[0][1]=new double[]{-s,-c,0};
        C[0][2]=new double[]{0,-(L[0]-L[3]),-1};
        C[1][0]=new double[]{c,-s,0};
        C[1][1]=new double[]{s,c,0};
        C[1][2]=new double[]{0,-L[3],1};
        return C;
    }
    private double[][] H(double L){
        double[][] H=super.getI(3);
        H[2][1]=L;
        return H;
    }
    private double[][] H(){
        return this.H(L[0]);
    }
    private double[][] Hinv(double L){
        double[][] H=super.getI(3);
        H[2][1]=-L;
        return H;
    }
    private double[][] Hinv(){
        return this.Hinv(L[0]);
    }
    private double[][] T(){
        double[][] T =new double[3][];
        T[0]=new double[] {c,-s,0};
        T[1]=new double[] {s, c, 0};
        T[2]=new double[] {0,0,1};
        return T;
    }
    /**
     * 端部条件を入れた等断面直線材の剛性マトリクスを得るメソッド
     * 100617チェック終了
     * @return　端部条件入れた剛性マトリクス
     */
    public double[][] getK(){
        double[][] K=new double[3][3];
        if(end0.contains("p")==true){
            if(end1.contains("p")==true){K[0][0]=E*A/L[2];}
            else if(end1.contains("r")==true){
                K[0][0]=E*A/L[2];
                K[1][1]=3*I*E/((1+gamma/2)*Math.pow(L[2], 3));
                K[1][2]=-3*I*E/((1+gamma/2)*Math.pow(L[2], 2));
                K[2][1]=K[1][2];
                K[2][2]=3*I*E/((1+gamma/2)*L[2]);
            }
            else{
                double k=Double.parseDouble(end1);
                K[0][0]=E*A/L[2];
                K[1][1]=12*E*I*k/(Math.pow(L[2], 3)*(2*(2+gamma)*k+3));
                K[1][2]=-6*E*I*k/(Math.pow(L[2], 2)*(k*(2+gamma)+1.5));
                K[2][1]=K[1][2];
                K[2][2]=12*E*I*k/(L[2]*(2*(2+gamma)*k+3));
            }
        }
        else if(end0.contains("r")==true){
            if(end1.contains("p")==true){
                K[0][0]=E*A/L[2];
                K[1][1]=3*I*E/((1+gamma/2)*Math.pow(L[2], 3));
            }
            else if(end1.contains("r")==true){
                K[0][0]=E*A/L[2];
                K[1][1]=12*I*E/((1+gamma*2)*Math.pow(L[2], 3));
                K[1][2]=-6*I*E/((1+gamma*2)*Math.pow(L[2], 2));
                K[2][1]=K[1][2];
                K[2][2]=(1+gamma/2)*4*E*I/((1+2*gamma)*L[2]);
            }
            else{
                double k1=Double.parseDouble(end1);
                double k=2*(1+2*gamma)*k1+2+gamma;
                K[0][0]=E*A/L[2];
                K[1][1]=6*E*I*(4*k1+1)/(Math.pow(L[2],3)*k);
                K[1][2]=-12*E*I*k1/(Math.pow(L[2],2)*k);
                K[2][1]=K[1][2];
                K[2][2]=8*E*I*k1*(1+0.5*gamma)/(L[2]*k);
            }
        }
        else{
            double k0=Double.parseDouble(end0);
            if(end1.contains("p")==true){
                K[0][0]=E*A/L[2];
                K[1][1]=12*E*I*k0/(Math.pow(L[2], 3)*(2*(2+gamma)*k0+3));
            }
            else if(end1.contains("r")==true){
                double k=2*(1+2*gamma)*k0+2+gamma;
                K[0][0]=E*A/L[2];
                K[1][1]=6*E*I*(4*k0+1)/(Math.pow(L[2],3)*k);
                K[1][2]=-6*E*I*(2*k0+1)/(Math.pow(L[2],2)*k);
                K[2][1]=K[1][2];
                K[2][2]=2*E*I*(4*k0*(1+0.5*gamma)+3)/(L[2]*k);
            }
            else{
                double k1=Double.parseDouble(end1);
                double k3=2*(k0+1)*(k1+1)-0.5+gamma*(4*k0*k1+k0+k1);
                K[0][0]=E*A/L[2];
                K[1][1]=12*E*I*(4*k0*k1+k0+k1)/(Math.pow(L[2], 3)*2*k3);
                K[1][2]=-6*E*I*(2*k0*k1+k1)/(Math.pow(L[2], 2)*k3);
                K[2][1]=K[1][2];
                K[2][2]=2*E*I*(4*k0*k1*(1+0.5*gamma)+3*k1)/(L[2]*k3);
            }
        }
        return K;
    }
    /**
     * 直線材の剛性マトリクスを出力(基準座標系)
     * チェック終了100618
     * @return
     */
    public double[][] getKinStandardCoordinates(){
        double[][] K;
        K=super.multiply(super.multiply(this.C(s, c), this.getK()), super.Transpose(this.C(s, c)));
        return K;
    }
    /**
     * 直線材の剛性マトリクスを出力(部材座標系)
     * チェック終了100618
     * @return
     */
    public double[][] getKinMemberCoordinates(){
        double[][] K;
        K=super.multiply(super.multiply(this.C0(), this.getK()), super.Transpose(this.C0()));
        return K;
    }

    //以下荷重に関する情報
    public void setLoad(MemberLoadInfo loadinfo){
        load.add(loadinfo);
    }
    /**
     * 部材の固定端反力を出すメソッド
     * 100629チェック終了
     * @return　ＣＭＱ
     */
    private double[][] getEndReaction(){
        double[][] reac=new double [2][3];
        double[][][] p=new double[3][2][3];
        for(int i=0;i<load.size();i++){
            MemberLoadInfo li=load.get(i);
            if(li.getLoadType()==0){
                if(li.getLoadDistance()<L[1]){
                    double a=li.getLoadDistance();
                    double b=L[1]-a;
                    super.add(p[0], this.getConsentrateLoadReaction(a, b, L[1], 0, li.getLoadValue()));
                }
                else if(L[1]<=li.getLoadDistance()&&li.getLoadDistance()<=(L[0]-L[3])){
                    double a=li.getLoadDistance()-L[1];
                    double b=L[2]-a;
                    System.out.println(a+" "+b+" "+L[2]+" "+gamma);
                    super.add(p[1], this.getConsentrateLoadReaction(a, b, L[2], gamma, li.getLoadValue()));
                }
                else if((L[0]-L[3])<li.getLoadDistance()&&li.getLoadDistance()<=L[0]){
                    double a=li.getLoadDistance()-(L[0]-L[3]);
                    double b=L[3]-a;
                    super.add(p[2], this.getConsentrateLoadReaction(a, b, L[3], 0, li.getLoadValue()));
                }
            }
            else if(li.getLoadType()==1){
                super.add(p[0], this.getDistriuteLoadReaction(L[1], li.getLoadValue()));
                super.add(p[1], this.getDistriuteLoadReaction(L[2], li.getLoadValue()));
                super.add(p[2], this.getDistriuteLoadReaction(L[3], li.getLoadValue()));
            }
        }
        //端部条件を入れる。
        if(end0.contains("p")==true){
            p[0]=this.getReaction0Rigid(p[0], 0, L[1], 0);
            if(end1.contains("p")==true){
                p[1]=this.getReactionBothSpring(p[1], 0, 0, L[2], gamma);
                p[2]=this.getReaction1Rigid(p[2], 0, L[3], 0);
            }
            else if(end1.contains("r")==true){
                p[1]=this.getReaction1Rigid(p[1], 0, L[2], gamma);
            }
            else{
                double k1=Double.parseDouble(end1);
                p[2]=this.getReaction1Rigid(p[2], k1, L[3], 0);
                p[1]=this.getReactionBothSpring(p[1], 0, k1, L[2], gamma);
            }
        }
        else if(end0.contains("r")==true){
            if(end1.contains("p")==true){
                p[2]=this.getReaction1Rigid(p[2], 0, L[3], 0);
                p[1]=this.getReaction0Rigid(p[1], 0, L[2], gamma);
            }
            else if(end1.contains("r")==true){}
            else{
                double k1=Double.parseDouble(end1);
                p[2]=this.getReaction1Rigid(p[2], k1, L[3], 0);
                p[1]=this.getReaction0Rigid(p[1], k1, L[2], gamma);
            }
        }
        else{
            double k0=Double.parseDouble(end0);
            p[0]=this.getReaction0Rigid(p[0], k0, L[1], 0);
            if(end1.contains("p")==true){
                p[2]=this.getReaction1Rigid(p[2], 0, L[3], 0);
                p[1]=this.getReactionBothSpring(p[1], k0, 0, L[2], gamma);
            }
            else if(end1.contains("r")==true){
                p[1]=this.getReaction1Rigid(p[1], k0, L[2], gamma);
            }
            else{
                double k1=Double.parseDouble(end1);
                p[2]=this.getReaction1Rigid(p[2], k1, L[3], 0);
                p[1]=this.getReactionBothSpring(p[1], k0, k1, L[2], gamma);
            }
        }
        //剛域計算
        reac[0]=super.plus(p[0][0], super.multiply(this.H(L[1]), super.plus(p[0][1], p[1][0])));
        reac[1]=super.plus(super.multiply(this.Hinv(L[3]), super.plus(p[1][1], p[2][0])), p[2][1]);
        return reac;
    }
    private double[][] getEqLoad(){
        double[][] load=super.multiply(this.getEndReaction(), -1);
        return load;
    }
    public double[][] getEqLoadInSC(){
        double[][] l=this.getEqLoad();
        double[][] load=super.Transpose(super.multiply(this.T(), super.Transpose(l)));
        return load;
    }
    /**
     * 両端がバネの時のＣＭＱ
     * 100629チェック終了
     * @param reaction　両端剛のときの固定端反力
     * @param k0　0端のばね定数
     * @param k1　1端のばね定数
     * @param L　部材長さ
     * @param gamma　せん断変形の比率γ
     * @return　両端がバネの時のＣＭＱ
     */
    private double[][] getReactionBothSpring(double[][] reaction,double k0,double k1,double L,double gamma){
        double[][] react=new double[2][3];
        double[][] a0=super.getI(3);
        double[][] b1=super.getI(3);
        double[][] b0=new double[3][3];
        double[][] a1=new double[3][3];
        double k=2*(k0+1)*(k1+1)-0.5+gamma*(4*k0*k1+k0+k1);
        a0[1][2]=-3/(2*L)*(2*k1+1)/k;
        a0[2][2]-=(3+2*k1*(2+gamma))/(2*k);
        b0[1][2]=-3/(2*L)*(2*k0+1)/k;
        b0[2][2]=-k0*(1-gamma)/k;
        a1[1][2]=3/(2*L)*(2*k1+1)/k;
        a1[2][2]=-k1*(1-gamma)/k;
        b1[1][2]=3/(2*L)*(2*k0+1)/k;
        b1[2][2]-=(3+2*k0*(2+gamma))/(2*k);
        react[0]=super.plus(super.multiply(a0, reaction[0]), super.multiply(b0, reaction[1]));
        react[1]=super.plus(super.multiply(a1, reaction[0]), super.multiply(b1, reaction[1]));
        return react;
    }
    /**
     * 0端が剛、1端がピンの時のＣＭＱ
     * 100629チェック終了
     * @param reaction　両端剛のときの固定端反力
     * @param k1　1端のばね定数
     * @param L　部材長さ
     * @param gamma　せん断変形の比率γ
     * @return　0端が剛、1端がピンの時のＣＭＱ
     */
    private double[][] getReaction0Rigid(double[][] reaction,double k1,double L,double gamma){
        double[][] react=new double[2][3];
        double[][] b1=super.getI(3);
        double[][] b0=new double[3][3];
        double k=2*(k1+1)+gamma*(4*k1+1);
        b0[1][2]=-3/L/k;
        b0[2][2]=-(1-gamma)/k;
        b1[1][2]=3/L/k;
        b1[2][2]-=(2+gamma)/k;
        react[0]=super.plus(reaction[0], super.multiply(b0, reaction[1]));
        react[1]=super.multiply(b1, reaction[1]);
        return react;
    }
    /**
     * 0端がピン、1端が剛のときのＣＭＱ
     * 100629チェック終了
     * @param reaction　両端固定の時のＣＭＱ
     * @param k0　0端のばね定数
     * @param L　部材長さ
     * @param gamma　せん断変形の比率γ
     * @return　0端がピン、1端が剛のときのＣＭＱ
     */
    private double[][] getReaction1Rigid(double[][] reaction,double k0,double L,double gamma){
        double[][] react=new double[2][3];
        double k=2*(k0+1)+gamma*(4*k0+1);
        double[][] a0=super.getI(3);
        double[][] a1=new double[3][3];
        a0[1][2]=-3/L/k;
        a0[2][2]-=(2+gamma)/k;
        a1[1][2]=3/L/k;
        a1[2][2]=-(1-gamma)/k;
        react[0]=super.multiply(a0, reaction[0]);
        react[1]=super.plus(super.multiply(a1, reaction[0]),reaction[1]);
        return react;
    }
    /**
     * 集中荷重の固定端反力を算出するメソッド
     * 100621チェック完了
     * @param a ０端から集中荷重までの距離
     * @param b １端から集中荷重までの距離
     * @param L 部材長さ
     * @param gamma せん断変形の割合γ
     * @param LoadValue 集中荷重の大きさ
     * @return  集中荷重の０端と１端のＣＭＱ
     */
    private double[][] getConsentrateLoadReaction(double a,double b,double L,double gamma,double LoadValue){
        double[][] p=new double [2][3];
        p[0][0]=0;
        p[0][1]=(b*b*(3*a+b)/Math.pow(L, 3)+2*b/L*gamma)/(1+2*gamma)*LoadValue;
        p[0][2]=a*b/L*(b/L+gamma)/(1+2*gamma)*LoadValue;
        p[1][0]=0;
        p[1][1]=(a*a*(3*b+a)/Math.pow(L, 3)+2*a/L*gamma)/(1+2*gamma)*LoadValue;
        p[1][2]=-a*b/L*(a/L+gamma)/(1+2*gamma)*LoadValue;
        return p;
    }
    
    /**
     * 等分布荷重についてのＣＭＱを算出するメソッド
     * １００６２１チェック終了
     * @param L 作用する部材長さ
     * @param LoadValue 荷重の大きさ
     * @return  ０端と１端についてのＣＭＱ
     */
    private double[][] getDistriuteLoadReaction(double L,double LoadValue){
        double[][] p=new double [2][3];
        p[0][0]=0;
        p[0][1]=LoadValue*L/2;
        p[0][2]=LoadValue*L*L/12;
        p[1][0]=0;
        p[1][1]=LoadValue*L/2;
        p[1][2]=-LoadValue*L*L/12;
        return p;
    }
    public void setMemberStress(double[] Pm){}
    public double[][] getStress(){
        double[][] a=new double[1][1];
        return a;
    }

    @Override
    public void setMemberDisp(double[] Pd) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
