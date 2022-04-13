/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package RA_LIB.RA_RKG;

import RA_LIB.RA_RKG.RS_ODE_FUNCTION;
import RA_LIB.ResponceAnalyzer;
import RA_LIB.AbstractModel.Model.ElasticModel;
import RA_LIB.WAVE.WAVE;

/**
 *ルンゲクッタジル法で一階連立常微分方程式を解くプログラム
 * @author araki keita
 */
public class RA_RKG extends ResponceAnalyzer{
    double ds,h,omega,K;
    double[] y0;
    double[][] y;
    RAfunc f=new RAfunc();
    public RA_RKG(WAVE wave,ElasticModel model){
        h=model.geth();
        omega=model.getOmega();
        y0=wave.getY0();
        ds=wave.getds();
        K=model.getK();
    }
    class RAfunc extends RS_ODE_FUNCTION{
        public double[] function(double time,double[] y,double y0){
            double[] f=new double [y.length];
            f[0]=y[1];
            f[1]=-2*h*omega*y[1]-Math.pow(omega, 2)*y[0]-y0;
    //        System.out.println(h+"      "+omega);
            return f;
        }
    }
    public void CALC(){
        this.CALC(0, 0);
    }
    public void CALC(double iniDisp,double iniVel){
        int isn=y0.length;//プロット点をいくつ進めるかということ
        double initime=0;
        double[] iniy={iniDisp,iniVel};
        y=new double[iniy.length+1][isn];
        double[] q=new double[iniy.length];
        double[] k=new double[iniy.length];
        double[] ct={0,0.5,0,0.5};
        double[] cq={2,1,1,2};
        double[] ck1={0.5,1-Math.sqrt(0.5),1+Math.sqrt(0.5),1/6};
        double[] ck2={0.5,1-Math.sqrt(0.5),1+Math.sqrt(0.5),0.5};
        y[1][0]=iniDisp;
        y[2][0]=iniVel;
        for(int i=0;i<isn-1;i++){
            y[0][i]=initime;
            double[] y00={y0[i],(y0[i+1]+y0[i])/2,(y0[i+1]+y0[i])/2,y0[i+1]};
            for(int s=0;s<4;s++){
                initime+=ct[s]*ds;
                for(int u=0;u<iniy.length;u++){
                    k[u]=ds*f.function(initime, iniy,y00[s])[u];
                }
                for(int u=0;u<iniy.length;u++){
                    iniy[u]+=ck1[s]*(k[u]-cq[s]*q[u]);
                }
                for(int u=0;u<iniy.length;u++){
                    q[u]+=3*(ck1[s]*(k[u]-cq[s]*q[u]))-ck2[s]*k[u];
                }
            }
            for(int s=0;s<iniy.length;s++){
                y[s+1][i+1]=iniy[s];
            }
        }
    }
    public double[] getTime(){
        return y[0];
    }
    public double[][] getRESPONCE(){
        double[][] res=new double[3][y0.length];
        res[0]=y[1];
        res[1]=y[2];
        for(int i=0;i<y0.length;i++){
            res[2][i]=-2*h*omega*y[2][i]-Math.pow(omega, 2)*y[1][i]-y0[i];
        }
        return res;
    }
    public double[] getQ(){
        double[] Q=new double[y0.length];
        for(int i=0;i<y0.length;i++){
            Q[i]=K*y[1][i];
        }
        return Q;
    }
}
