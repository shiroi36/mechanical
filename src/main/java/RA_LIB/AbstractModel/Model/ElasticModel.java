/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package RA_LIB.AbstractModel.Model;

import RA_LIB.AbstractModel.*;

/**
 *
 * @author araki keita
 */
public class ElasticModel extends AbstractModel{
    double iniK;
    double h;
    double mass;
    double LinearMax,LinearMin;
    boolean Kflag=false;
//    public ElasticModel(){}
    double Omega;
    /**
     * 建物情報をセット
     * @param k　初期剛性
     * @param h　モデル側の減衰定数
     * @param mass　モデル側の質量
     */
    public ElasticModel(double k,double h,double mass){
        this.iniK=k;
        this.h=h;
        this.mass=mass;
        Omega=Math.sqrt(k/mass);
    }
    /**
     * 入力情報をセット
     * @param T　入力の固有周期
     * @param h　入力の減衰定数
     */
    public ElasticModel(double T,double h){
        Omega=2*Math.PI/T;
        iniK=Math.pow(Omega, 2);
        this.h=h;
    }
//    public double[][] MakeErrorSmall(int i,double[][] res,double[] Q,double RD){
//        return res;
//    }
    public boolean seekChangePointFlag(double[] res,double Q){
//        return res;
        return false;
    }
    public double getK(){return iniK;}
    public void setK(double[] RD,double[] res,double Q){}
    public boolean getPlasticFlag(){return false;}
    public double geth(){return h;}
    public double getOmega(){return Omega;}
}
