/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 * 実験値による回帰直線(regressionline)を作成するプログラム
 */

package EXP_LIB.FITTING;

/**
 *
 * @author araki keita
 */
public class RegLogLine {
    public double a;
    public double b;
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
//            System.out.println(Math.log(X[i])+"\t"+X[i]+"\t"+Y[i]);
            Xmean+=Math.log(X[i])/X.length;
            Ymean+=Y[i]/Y.length;
        }
        for(int i=0;i<X.length;i++){
            XY+=(Math.log(X[i])-Xmean)*(Y[i]-Ymean);
            XX+=Math.pow((Math.log(X[i])-Xmean), 2);
        }
        a=XY/XX;
        b=Ymean-a*Xmean;
    }
    /**
     * 回帰直線の傾きを返す
     * @return　回帰直線の傾き
     */
    public double getGraditent(){
        return a;
    }
    /**
     * 回帰直線の切片を返す
     * @return  回帰直線の切片をdoubleで
     */
    public double getIntercept(){
        return b;
    }
    /**
     * あるX座標における回帰直線のY座標を返す
     * @param X　X座標
     * @return　Y座標
     */
    public double getValue(double X){
        return a*X+b;
    }
    /**
     * 回帰直線の指定した二点の値を返す
     * @param Xmin　始点
     * @param Xmax　終点
     * @return　二点の座標を[2][2]の配列で表す
     * [0][0],[0][1]　始点のX座標とY座標
     * [1][0],[1][1]　終点のX座標とY座標
     */
    public double[][] getLine(double Xmin,double Xmax,double ds){
        int num=(int)((Xmax-Xmin)/ds)+1;
        double[][] value=new double[2][num];
        for(int i=0;i<num;i++){
            value[0][i]=Xmin+ds*i;
            value[1][i]=a*Math.log(value[0][i])+b;
//            System.out.println(value[0][i]+"    "+value[1][i]);
        }
        return value;
    }
}
