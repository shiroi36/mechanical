/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package MATH_LIB.ODE;

import MATH_LIB.FUNCTION.ODE_FUNCTION;

/**
 *ルンゲクッタジル法で一階連立常微分方程式を解くプログラム
 * @author araki keita
 */
public class RKG {
    public double[][] RKG(double[] iniy,double initime,ODE_FUNCTION f,double dt,int isn){
        double[][] y=new double[iniy.length+1][isn];
        double[] q=new double[iniy.length];
        double[] k=new double[iniy.length];
        double[] ct={0,0.5,0,0.5};
        double[] cq={2,1,1,2};
        double[] ck1={0.5,1-Math.sqrt(0.5),1+Math.sqrt(0.5),1/6};
        double[] ck2={0.5,1-Math.sqrt(0.5),1+Math.sqrt(0.5),0.5};
        for(int i=0;i<isn;i++){
            y[0][i]=initime;
            for(int s=0;s<iniy.length;s++){
                y[s+1][i]=iniy[s];
            }
            for(int s=0;s<4;s++){
                initime+=ct[s]*dt;
                for(int u=0;u<iniy.length;u++){
                    k[u]=dt*f.function(initime, iniy)[u];
                }
                for(int u=0;u<iniy.length;u++){
                    iniy[u]+=ck1[s]*(k[u]-cq[s]*q[u]);
                }
                for(int u=0;u<iniy.length;u++){
                    q[u]+=3*(ck1[s]*(k[u]-cq[s]*q[u]))-ck2[s]*k[u];
                }
            }
        }
        return y;
    }
}
