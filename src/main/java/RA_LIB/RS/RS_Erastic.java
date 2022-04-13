/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package RA_LIB.RS;
import RA_LIB.AbstractModel.Model.ElasticModel;
import RA_LIB.RA_NIGAM.RA_NIGAM;
import RA_LIB.RA_NEWMARK.*;
import RA_LIB.WAVE.WAVE;

/**
 *応答スペクトルを計算するクラスです．
 * 弾性解析においては，nigamの方法をつかうと
 * 厳密解を与えるのでそれを採用
 * @author Araki Keita
 */
public class RS_Erastic {
    double[][]  maxres;
    public RS_Erastic(double h,double startT,double endT, double dT,WAVE wave){
        int num=(int)((endT-startT)/dT);
        maxres=new double[4][num];
        for(int i=0;i<num;i++){
            maxres[0][i]=dT*i;
            ElasticModel model=new ElasticModel(dT*i,h);
            RA_NIGAM rn=new RA_NIGAM(wave,model);
            rn.CALC();
            double[][] res=rn.getRESPONCE();
//            RA_NM rn=new RA_NM(BETA.AVERAGE,wave,model);
//            rn.CALC();
//            double[][] res=rn.getRESPONCE();

            for(int s=0;s<res.length;s++){
                double max=0;
                for(int t=0;t<res[s].length;t++){
                    if(max<Math.abs(res[s][t])){
                        max=Math.abs(res[s][t]);
                    }
                }
                maxres[1+s][i]=max;
            }

        }
    }
    public double[][] getResponce(){
        return maxres;
    }
}
