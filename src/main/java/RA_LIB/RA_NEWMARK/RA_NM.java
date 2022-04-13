/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package RA_LIB.RA_NEWMARK;
import RA_LIB.WAVE.WAVE;
import RA_LIB.AbstractModel.AbstractModel;
import MATH_LIB.MATRIX_LIB.GAUSS_SYOUKYO_HOU;
import RA_LIB.ResponceAnalyzer;

/**
 *newmarkβ法（増分変位による表現）で応答時刻歴波形を作成するプログラム
 * @author araki
 */
public class RA_NM extends ResponceAnalyzer{
    double beta;
    AbstractModel model;
    WAVE wave;
    double[][] res;
    public double[] RD=new double[2];//RD[0]正側RD[1]負側
    double[] Q;
    double Error;

    /**
     * 変数を設定する
     * @param beta　BETAクラスの数字
     * @param wave　　WAVEクラスのオブジェクト
     * @param model　建物側のモデル情報（AbstractModelクラス）
     */
    public RA_NM(BETA beta,WAVE wave,AbstractModel model){
        this.beta=beta.getValue();
        this.wave=wave;
        this.model=model;
        res=new double[wave.getY0().length][3];
        Q=new double[wave.getY0().length];
        Error=1;//誤差の許容範囲を決める。
//        System.out.println(this.beta+"  "+model.getK()+"  "+wave.getds());
    }
    /**
     * newmarkβを使用して応答を計算する
     * @param i　応答を計算するステップ数
     * @param model　建物側のモデル情報（AbstractModelクラス）
     * @param dt　ステップ時の刻み時間
     * @param dy0　ステップ時の加速度入力
     * @return　ステップ時の応答の値を変位・速度・加速度の順に長さ3の配列
     */
    private double[] NM(int i,AbstractModel model,double dt, double dy0){
//        System.out.println("done");
        double omega=model.getOmega();
        double h=model.geth();
        double[][] K={{1,0,-beta*Math.pow(dt, 2)},{0,1,-0.5*dt},{Math.pow(omega, 2),2*h*omega,1}};
        double[] P={res[i][1]*dt+0.5*res[i][2]*Math.pow(dt, 2),dt*res[i][2],-dy0};
        GAUSS_SYOUKYO_HOU gs=new GAUSS_SYOUKYO_HOU();
        double[] dy=gs.getARRAY(K, P);
        return dy;
    }
    /**
     * 応答時刻歴波形を計算するメソッド
     * @param iniDisp 初期変位
     * @param iniVel 初期速度
     * @param iniAcc 初期加速度
     */
    public void CALC(double iniDisp,double iniVel){
        RD[0]=0;RD[1]=0;
        for(int i=0;i<wave.getY0().length-1;i++){
                model.setK(RD, res[i],Q[i]);
                if(i==0){
                    res[0][0]=iniDisp;
                    res[0][1]=iniVel;
                    res[0][2]=-1*wave.getY0(i)
                            -2*model.geth()*model.getOmega()*iniVel
                            -Math.pow(model.getOmega(), 2)*iniDisp;
                    Q[0]=0;
                }
                double[] dy=this.NM(i, model,wave.getds(),wave.getdy0(i));
                for(int s=0;s<dy.length;s++){
                    res[i+1][s]=res[i][s]+dy[s];
                }
                Q[i+1]=Q[i]+model.getK()*dy[0];

                /**y0を線形補間して細かく計算する
                 * ここで誤差を小さくする
                 * 出来ることならある閾値εを設けてε以下となるまで
                 * 小さく分けるようにしたい。
                 * が、大体100回程度で十分な精度を示すので、このままでいい気がする
                 */
                int n=100;//分ける回数
                if(model.seekChangePointFlag( res[i+1], Q[i+1])==true){
                    for(int s=0;s<res[i].length;s++){
                        res[i+1][s]=res[i][s];
                    }
                    Q[i+1]=Q[i];
                    double ddy0=wave.getdy0(i)/n;
                    double dds=wave.getds()/n;
                    for(int s=0;s<n;s++){
                        model.setK(RD, res[i+1],Q[i+1]);
                        double[] dy2=this.NM(i+1, model,dds,ddy0);
                        for(int t=0;t<dy2.length;t++){
                            res[i+1][t]+=dy2[t];
                        }
                        Q[i+1]+=model.getK()*dy2[0];
                        if(model.getPlasticFlag()==true){
                            if(dy2[0]>=0){
                                RD[0]+=dy2[0];
                            }else{
                                RD[1]+=dy2[0];
                            }
                        }
                    }
                }else{
                    if(model.getPlasticFlag()==true){
                        if(dy[0]>=0){
                            RD[0]+=dy[0];
                        }else{
                            RD[1]+=dy[0];
                        }
                    }
                }
            }
    }
    /**
     * 全ての初期条件を0にしたときの応答時刻歴波形を計算するメソッド
     */
    public void CALC(){
        this.CALC(0, 0);
    }
    /**
     * 応答時刻歴波形を出力
     * @return 変位・速度・加速度の配列3の時刻歴波形
     */
    public double[][] getRESPONCE(){
        double res2[][]=new double[3][res.length];
        for(int i=0;i<res.length;i++){
            for(int s=0;s<3;s++){
                res2[s][i]=res[i][s];
            }
        }
        return res2;
    }
    /**
     * 変位復元力を出力するプログラム
     * @return　変位復元力の時刻歴波形
     */
    public double[] getQ(){return Q;}
}
