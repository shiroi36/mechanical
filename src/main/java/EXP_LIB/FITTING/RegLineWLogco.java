/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package EXP_LIB.FITTING;

/**
 *両対数グラフにおける回帰直線
 * @author araki keita
 */
public class RegLineWLogco extends RegLine {
    /**
     * 回帰線を求める値をセットするプログラム
     * @param X
     * @param Y
     */
    public void setvalue(double[] X,double[] Y){
        double Xmean=0,Ymean=0,XY=0,XX=0;
        if(X.length==Y.length){System.out.println("オッケー");}
        else{System.out.println("だめ");}
        for(int i=0;i<X.length;i++){
            Xmean+=Math.log10(X[i])/X.length;
            Ymean+=Math.log10(Y[i])/Y.length;
        }
        for(int i=0;i<X.length;i++){
            XY+=(Math.log10(X[i])-Xmean)*(Math.log10(Y[i])-Ymean);
            XX+=Math.pow((Math.log10(X[i])-Xmean), 2);
        }
        a=XY/XX;
        b=Ymean-a*Xmean;
    }
    /**
     * あるX座標における回帰直線のY座標を返す
     * @param X　X座標
     * @return　Y座標
     *
     */
    public double getValue(double X){
        return Math.pow(10, b)*Math.pow(X, a);
    }
    /**
     * 回帰直線の指定した二点の値を返す
     * @param Xmin　始点
     * @param Xmax　終点
     * @return　二点の座標を[2][2]の配列で表す
     * [0][0],[0][1]　始点のX座標とY座標
     * [1][0],[1][1]　終点のX座標とY座標
     */
    public double[][] getLine(double Xmin,double Xmax){
        double[][] value=new double[2][2];
        value[0][0]=Xmin;value[0][1]=Xmax;
        value[1][0]=Math.pow(10, b)*Math.pow(Xmin, a);value[1][1]=Math.pow(10, b)*Math.pow(Xmax, a);
        return value;
    }
}
