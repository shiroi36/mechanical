/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package RA_LIB.AbstractModel.Model;
import RA_LIB.AbstractModel.AbstractModel;

/**
 *Cloughモデルの復元力特性を表すクラス
 * @author araki keita
 */
public class CloughModel extends AbstractModel{
    public double iniK;
    public double K;
    public double Qy;
    public double gamma;
    public double h;
    public double mass;
    public boolean PlasticFlag;
    public int Kflag;//0劣化した時の剛性+1塑性時の剛性+2もどり剛性+ 3劣化した時の剛性-4塑性時の剛性-5もどり剛性-
    double changeDis=0;
    double changeQ=0;
    double LinearLoad;
    /**
     * モデル化に必要な情報を入力するコンストラクタ
     * @param K         初期剛性
     * @param gamma     二次剛性比
     * @param Qy        降伏荷重
     * @param h         減衰定数
     * @param mass      質量
     */
    public CloughModel(double K,double gamma,double Qy,double h,double mass){
        iniK=K;
        this.gamma=gamma;
        this.mass=mass;
        this.h=h;
        this.Qy=Qy;
    }
    /**
     * ステップ時の合成を算出するメソッド
     * @param RD    残留変形　RD[0]正側RD[1]負側
     * @param res   算出したいステップ時の応答
     * @param Q     算出したいステップ時の復元力
     */
    public void setK(double[] RD,double[] res,double Q){
        //0530-1711cloughmodelsetK完了
        double backstepKflag=Kflag;
        if(Q>=0){
            LinearLoad=Qy+gamma*iniK*RD[0];
            if(res[1]<0){
                K=iniK;
                PlasticFlag=false;
                Kflag=2;
            }
            else if(res[1]>=0&&Q<=LinearLoad){
                if(backstepKflag!=0){
                    changeDis=res[0];
                    changeQ=Q;
                }
//                System.out.println(changeQ+"   "+changeDis);
                K=(LinearLoad-changeQ)/(RD[0]-changeDis+Qy/iniK);
                PlasticFlag=false;
                Kflag=0;
            }
            else if(res[1]>=0&&Q>LinearLoad){
                K=gamma*iniK;
                PlasticFlag=true;
                Kflag=1;
            }
        }
        else if(Q<0){
            LinearLoad=-Qy-gamma*iniK*RD[1];
            if(res[1]>0){
                K=iniK;
                PlasticFlag=false;
                Kflag=5;
            }
            else if(res[1]<=0&&Q>LinearLoad){
                if(backstepKflag!=0){
                    changeDis=res[0];
                    changeQ=Q;
                }
//                System.out.println(changeQ+"   "+changeDis);
                K=(LinearLoad-changeQ)/(RD[1]-changeDis-Qy/iniK);
                PlasticFlag=false;
                Kflag=3;
            }
            else if(res[1]<=0&&Q<=LinearLoad){
                PlasticFlag=true;
                K=gamma*iniK;
                Kflag=4;
            }
        }
//        System.out.println(Kflag);
    }
    /**
     * ステップ前後で剛性変化点を迎えたかどうかを計算するメソッド
     * @param res   ステップ時の応答配列
     * @param Q     ステップ時の変位復元力
     * @return      剛性変化点を迎えたかどうかをbooleanにして返す
     */
    public boolean seekChangePointFlag(double[] res,double Q){
        boolean flag=false;
        if(Kflag==0){
            if(Q>LinearLoad){
                flag=true;
            }
            else if(res[1]<0){
                flag=true;
            }
        }
        else if(Kflag==1&&res[1]<0){
            flag=true;
        }
        else if(Kflag==2){
            if(Q<0){
                flag=true;
            }
            else if(res[1]>0){
                flag=true;
            }
        }

        else if(Kflag==3){
            if(Q<LinearLoad){
                flag=true;
            }
            else if(res[1]>0){
                flag=true;
            }
        }
        else if(Kflag==4&&res[1]>0){
            flag=true;
        }
        else if(Kflag==5){
            if(Q>0){
                flag=true;
            }
            else if(res[1]<0){
                flag=true;
            }
        }
        return flag;
    };
    /**
     *剛性を判別するメソッド
     * @return　剛性番号の整数
     */
    public boolean getPlasticFlag(){return PlasticFlag;}
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
