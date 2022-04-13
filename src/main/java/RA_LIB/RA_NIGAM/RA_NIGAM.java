/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package RA_LIB.RA_NIGAM;
import RA_LIB.WAVE.WAVE;
import RA_LIB.AbstractModel.Model.ElasticModel;
import RA_LIB.ResponceAnalyzer;
import MATH_LIB.MATRIX_LIB.MATRIX_CALC;

/**
 *　Nigam法によって応答解析を行うクラス
 * @author araki keita
 */
public class RA_NIGAM extends ResponceAnalyzer{
    public WAVE wave;
    public ElasticModel model;
    double[][] res;
    double[] Q;
    public RA_NIGAM(WAVE wave,ElasticModel model){
        this.wave=wave;
        this.model=model;
        res=new double[wave.getY0().length][3];
        Q=new double[wave.getY0().length];
    }
    /**
     * 初期変位及び初期速度を適用した場合の応答解析をNigamの方法で
     * 解くメソッド
     * @param iniDisp　初期変位
     * @param iniVel　初期速度
     */
    public void CALC(double iniDisp,double iniVel){
        double h=model.geth();
        double w=model.getOmega();
        double w1=Math.sqrt(1-Math.pow(h, 2))*w;
        double dt=wave.getds();

        double a11=Math.exp(-h*w*dt)*(h/Math.sqrt(1-Math.pow(h, 2))*Math.sin(w1*dt)
                +Math.cos(w1*dt));
        double a12=Math.exp(-h*w*dt)/w1*Math.sin(w1*dt);
        double a21=-w/Math.sqrt(1-Math.pow(h, 2))*Math.exp(-h*w*dt)*Math.sin(w1*dt);
        double a22=Math.exp(-h*w*dt)*(Math.cos(w1*dt)-h/Math.sqrt(1-Math.pow(h, 2))*Math.sin(w1*dt));
        double b11=Math.exp(-h*w*dt)*(((2*Math.pow(h, 2)-1)/(Math.pow(w, 2)*dt)+h/w)*Math.sin(w1*dt)/w1
                +((2*h)/(Math.pow(w, 3)*dt)+1/Math.pow(w, 2))*Math.cos(w1*dt))
                -(2*h)/(Math.pow(w, 3)*dt);
        double b12=-Math.exp(-h*w*dt)*((2*Math.pow(h, 2)-1)/(Math.pow(w, 2)*dt)*Math.sin(w1*dt)/w1
                +(2*h)/(Math.pow(w, 3)*dt)*Math.cos(w1*dt))-1/Math.pow(w, 2)+(2*h)/(Math.pow(w, 3)*dt);
        double b21=Math.exp(-h*w*dt)*(((2*Math.pow(h, 2)-1)/(Math.pow(w, 2)*dt)+h/w)
                *(Math.cos(w1*dt)-h/Math.sqrt(1-Math.pow(h, 2))*Math.sin(w1*dt))
                -((2*h)/(Math.pow(w, 3)*dt)+1/Math.pow(w, 2))
                *(w1*Math.sin(w1*dt)+h*w*Math.cos(w1*dt)))+1/(Math.pow(w, 2)*dt);
        double b22=-Math.exp(-h*w*dt)*((2*Math.pow(h, 2)-1)/(Math.pow(w, 2)*dt)
                *(Math.cos(w1*dt)-h/Math.sqrt(1-Math.pow(h, 2))*Math.sin(w1*dt))
                -(2*h)/(Math.pow(w, 3)*dt)*(w1*Math.sin(w1*dt)+h*w*Math.cos(w1*dt)))
                -1/(Math.pow(w, 2)*dt);
        
        double[][] a={{a11,a12},{a21,a22}};
        double[][] b={{b11,b12},{b21,b22}};
        res[0][0]=iniDisp;
        res[0][1]=iniVel;
        res[0][2]=-1*wave.getY0(0)
                            -2*model.geth()*model.getOmega()*iniVel
                            -Math.pow(model.getOmega(), 2)*iniDisp;
        Q[0]=model.getK()*iniDisp;
        MATRIX_CALC mc=new MATRIX_CALC();
        for(int i=0;i<wave.getY0().length-1;i++){
            double[] nextstep=mc.plus(mc.multiply(a, new double[] {res[i][0],res[i][1]})
                    , mc.multiply(b, new double[] {wave.getY0(i),wave.getY0(i+1)}));
            res[i+1][0]=nextstep[0];
            res[i+1][1]=nextstep[1];
            res[i+1][2]=-1*wave.getY0(i+1)
                            -2*model.geth()*model.getOmega()*nextstep[1]
                            -Math.pow(model.getOmega(), 2)*nextstep[0];
            Q[i+1]=model.getK()*nextstep[0];
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
