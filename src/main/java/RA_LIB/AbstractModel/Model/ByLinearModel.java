/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package RA_LIB.AbstractModel.Model;
import RA_LIB.AbstractModel.AbstractModel;

/**
 * バイリニア復元力特性をもつ建物モデルを計算するプログラム
 * @author araki
 */
public class ByLinearModel extends AbstractModel{
    double iniK;
    double K;
    double gamma;
    double h;
    double mass;
    double Qy;//弾制限
    double LinearMax,LinearMin;
    boolean Plasticflag;//true 二次剛性　false 初期剛性
    double RD;
    double Omega;
    /**
     * 建物情報をセット
     * @param k　初期剛性
     * @param gamma　剛性低下率
     * @param Qy　降伏荷重
     * @param h　モデル側の減衰定数
     * @param mass　モデル側の質量
     */
    public ByLinearModel(double k,double gamma,double Qy,double h,double mass){
        this.iniK=k;
        this.gamma=gamma;
        this.h=h;
        this.mass=mass;
        this.Qy=Qy;
    }
    /**
     * 塑性変形時の弾性限範囲を計算するメソッド
     * @param RD　塑性変形
     */
   private void setLinerRange(double RD){
        LinearMax=Qy/iniK+RD;
        LinearMin=-Qy/iniK+RD;
    }
    /**
     * 剛性を計算するメソッド
     * @param RD　塑性変形
     * @param y　計算時の応答
     */
    public void setK(double[] RD,double[] y,double Q){
        this.RD=RD[0]+RD[1];
        this.setLinerRange(this.RD);//なんか誤差が出そうな予感てかこれでは危険側100412
        if(y[0]>LinearMin&&y[0]<LinearMax){
            K=iniK;
            Plasticflag=false;
        }
        else if(y[0]<=LinearMin){
            if(y[1]>=0){
                K=iniK;
                Plasticflag=false;
            }
            else{
                K=gamma*iniK;
                Plasticflag=true;
            }
        }
        else if(y[0]>=LinearMax){
            if(y[1]<=0){
                K=iniK;
                Plasticflag=false;
            }
            else{
                K=gamma*iniK;
                Plasticflag=true;
            }
        }
    }
    /**
     * ステップ前後で剛性変化点を迎えたかどうかを計算するプログラム
     * @param res　ステップ時の応答配列
     * @param Q　ステップ時の変位復元力
     * @return 剛性変化点を迎えたか否かをbooleanで返す。
     */
    public boolean seekChangePointFlag(double[] res,double Q){
        boolean flag2=false;
        if(Plasticflag==true){
            if(Q<0&&res[1]>0){
                flag2=true;
            }
            else if(Q>0&&res[1]<0){
                flag2=true;
            }
        }
        else if(Plasticflag==false){
            if(res[0]<LinearMin){
                flag2=true;
            }
            else if(res[0]>LinearMax){
                flag2=true;
            }
        }
        else{flag2=false;}
        return flag2;
    }
    /**
     *剛性を判別するメソッド
     * @return　剛性番号の整数
     */
    public boolean getPlasticFlag(){return Plasticflag;}
    /**
     * 剛性を出力
     * @return　剛性の値
     */
    public double getK(){return K;}
    /**
     * 減衰定数を出力
     * @return　減衰定数の値
     */
    public double geth(){return h;}
    /**
     * 固有振動数を出力
     * @return　固有縁振動数を出力
     */
    public double getOmega(){return Math.sqrt(K/mass);}
}
