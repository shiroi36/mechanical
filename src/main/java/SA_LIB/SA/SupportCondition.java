/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package SA_LIB.SA;

/**
 *
 * @author Araki Keita
 */
public class SupportCondition {
    boolean zure;//基準座標系から　true　ずれているfalse　ずれていない
    double theta;
    String[] Support;
    int node;
    int SupportIndex;//0 個別入力　1 ピンか固定かの二択　2 ローラー
    public SupportCondition(int node,double theta){
        this.node=node;
        if(theta==0){
            zure=false;
        }
        else{
            zure=true;
            this.theta=theta;
        }
    }
    public double[][] getT(){
        double[][] T =new double[3][];
        double s=Math.sin(theta/180*Math.PI);
        double c=Math.cos(theta/180*Math.PI);
        T[0]=new double[] {c,-s,0};
        T[1]=new double[] {s, c, 0};
        T[2]=new double[] {0,0,1};
        return T;
    }
    public int getNode(){return node;}
    public boolean getKatamukiFlag(){return zure;}
    /**
     *
     * @param Xsupport　X座標（支点座標）の支点条件　ｒ拘束，ｆ自由，何かの数値：半剛
     * @param Ysupport　Y座標（支点座標）の支点条件　ｒ拘束，ｆ自由，何かの数値：半剛
     * @param ThetaSupport　θ座標（支点座標）の支点条件　ｒ固定，ｆ自由，何かの数値：半剛
     */
    public void setSupportCondition(String Xsupport,String Ysupport,String ThetaSupport){
        Support=new String[3];
        Support[0]=Xsupport;
        Support[1]=Ysupport;
        Support[2]=ThetaSupport;
    }
    public String[] getSupportCondition(){return Support;}
}
